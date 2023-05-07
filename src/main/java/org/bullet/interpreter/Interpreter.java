package org.bullet.interpreter;

import org.bullet.base.components.*;
import org.bullet.base.types.*;
import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.compiler.ast.nodes.*;
import org.bullet.compiler.lexer.Lexer;
import org.bullet.compiler.parser.Parser;
import org.bullet.exceptions.*;
import org.bullet.exceptions.RuntimeException;
import org.bullet.exceptions.common.DefinedException;
import org.bullet.exceptions.common.FileCorruptingExceiption;
import org.bullet.exceptions.common.ParsingException;
import org.bullet.exceptions.common.UnderfineException;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Huyemt
 */

public class Interpreter extends Visitor {
    public final BulletRuntime runtime;

    public Interpreter(String source, BulletRuntime runtime) throws ParsingException {
        this.runtime = runtime;

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer, this);
        ProgramNode programNode = parser.Parse();

        // 声明函数
        for (FunctionNode node : parser.functions.values()) {
            runtime.functions.put(node.name, new BtCustomFunction(this, node));
        }

        BlockNode blockNode = new BlockNode();
        blockNode.position = programNode.position;
        blockNode.statements = programNode.statements;
        blockNode.level = 0;

        BtScope scope = new BtScope();
        scope.position = programNode.position;
        scope.from = null;
        scope.node = blockNode;

        runtime.scope = scope;
    }

    public Interpreter(File file, BulletRuntime runtime) throws ParsingException, FileCorruptingExceiption {
        this.runtime = runtime;

        Lexer lexer = new Lexer(file);
        Parser parser = new Parser(lexer, this);
        ProgramNode programNode = parser.Parse();

        // 声明函数
        for (FunctionNode node : parser.functions.values()) {
            runtime.functions.put(node.name, new BtCustomFunction(this, node));
        }

        BlockNode blockNode = new BlockNode();
        blockNode.position = programNode.position;
        blockNode.statements = programNode.statements;
        blockNode.level = 0;

        BtScope scope = new BtScope();
        scope.position = programNode.position;
        scope.from = null;
        scope.node = blockNode;

        runtime.scope = scope;
    }

    public Object eval() throws RuntimeException {
        return runtime.scope.node.accept(this);
    }

    @Override
    public Object goProgram(ProgramNode node) throws RuntimeException {
        Object result = null;

        for (Node node1 : node.statements) {
            result = node1.accept(this);
        }

        return result;
    }

    @Override
    public Object goBinary(BinaryNode node) throws RuntimeException {
        if (node.operator == BinaryNode.Operator.OR || node.operator == BinaryNode.Operator.AND) {
            boolean l = translateBool(node.left);
            return node.operator == BinaryNode.Operator.OR ? l || translateBool(node.right) : l && translateBool(node.right);
        }

        Object right = node.right.accept(this);
        Object left = node.left.accept(this);

        if (node.operator == BinaryNode.Operator.EQUAL || node.operator == BinaryNode.Operator.NOT_EQUAL) {
            return (node.operator == BinaryNode.Operator.EQUAL) == left.equals(right);
        }

        if (node.operator == BinaryNode.Operator.GREATER || node.operator == BinaryNode.Operator.GREATER_OR_EQUAL || node.operator == BinaryNode.Operator.LESSER || node.operator == BinaryNode.Operator.LESSER_OR_EQUAL) {
            if (left instanceof BtNumber && right instanceof BtNumber) {
                int flag = ((BtNumber) left).compare((BtNumber) right);

                if (node.operator == BinaryNode.Operator.GREATER) return flag > 0;
                if (node.operator == BinaryNode.Operator.GREATER_OR_EQUAL) return flag > 0 || flag == 0;
                if (node.operator == BinaryNode.Operator.LESSER) return flag < 0;

                return flag < 0 || flag == 0;
            }

            return false;
        }

        if (left instanceof BtNumber) {
            switch (node.operator) {
                case ADD:
                    if (right instanceof BtNumber) return ((BtNumber) left).add((BtNumber) right);

                    throw new RuntimeException(node.position, String.format("Addition of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case SUB:
                    if (right instanceof BtNumber) return ((BtNumber) left).subtract((BtNumber) right);

                    throw new RuntimeException(node.position, String.format("Subtraction of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case MUL:
                    if (right instanceof BtNumber) return ((BtNumber) left).multiply((BtNumber) right);

                    throw new RuntimeException(node.position, String.format("Multiplication of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case DIV:
                    if (right instanceof BtNumber) {
                        if (((BtNumber) right).toInteger() == 0) throw new RuntimeException(node.position, "Cannot divide by zero");

                        return ((BtNumber) left).divide((BtNumber) right);
                    }

                    throw new RuntimeException(node.position, String.format("Division of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case MOD:
                    if (right instanceof BtNumber) {
                        if (((BtNumber) right).toInteger() == 0) throw new RuntimeException(node.position, "Cannot divide by zero");

                        return ((BtNumber) left).mod((BtNumber) right);
                    }

                    throw new RuntimeException(node.position, String.format("Remainder of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case POW:
                    if (right instanceof BtNumber) return ((BtNumber) left).pow(((BtNumber) right).toInteger());

                    throw new RuntimeException(node.position, String.format("Exponentiation  of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
            }

            throw new RuntimeException(node.position, "Unsupported binary operator");
        }

        if (left instanceof Boolean) {
            throw new RuntimeException(node.position, "Boolean values cannot perform operations other than \"&&\" and \"||\"");
        }

        if (left instanceof String) {
            if (!(right instanceof BtList || right instanceof BtDictionary)) {
                if (node.operator == BinaryNode.Operator.ADD) {
                    if (right instanceof String) return ((String) left).concat(String.valueOf(right));

                    throw new RuntimeException(node.position, "A string can only be added to a string");
                }

                if (node.operator == BinaryNode.Operator.MUL) {
                    if (right instanceof BtNumber) return ((String) left).repeat(((BtNumber) right).toInteger());

                    throw new RuntimeException(node.position, "A string can only be multiplied to a number");
                }
            }

            throw new RuntimeException(node.position, "String values cannot perform operations other than \"+\", \"*\", \"==\" and \"!=\"");
        }

        if (left instanceof BtList) {
            if (node.operator == BinaryNode.Operator.ADD) {
                if (right instanceof BtList) {
                    BtList list = new BtList();
                    list.addAll((BtList) left);
                    list.addAll((BtList) right);
                    return list;
                }

                throw new RuntimeException(node.right.position, "List can only be added with list");
            }

            throw new RuntimeException(node.position, "Lists cannot perform operations other than \"+\", \"==\" and \"!=\"");
        }

        if (left instanceof BtByteString || left instanceof BtByte || left instanceof BtDictionary || left instanceof BtNull) {
            throw new RuntimeException(node.position, String.format("%s cannot perform operations other than \"==\" and \"!=\"", left.getClass().getSimpleName()));
        }

        throw new RuntimeException(node.position, "Unsupported type operation");
    }

    @Override
    public Object goUnary(UnaryNode node) throws RuntimeException {
        Object left = node.left.accept(this);

        if (left instanceof BtNumber) {
            switch (node.operator) {
                case PLUS:
                    return left;
                case MINUS:
                    return ((BtNumber) left).negate();
                default:
                    throw new RuntimeException(node.position, "Unsupported unary operator");
            }
        }

        if (left instanceof Boolean) {
            if (node.operator == UnaryNode.Operator.NOT) return !((Boolean) left);

            throw new RuntimeException(node.position, "Boolean values can only be inverted unary");
        }

        throw new RuntimeException(node.left.position, String.format("Data type of operation is not supported:  \"%s\"", left.toString()));
    }

    @Override
    public Object goConstant(ConstantNode node) throws RuntimeException {
        if (node.value instanceof BtNumber || node.value instanceof Boolean || node.value instanceof String || node.value instanceof BtNull || node.value instanceof BtByteString || node.value instanceof BtByte) {
            if (node.value instanceof String || node.value instanceof BtByteString) {
                return valueOfIndex(node.value, node.indexNode);
            }

            return node.value;
        }

        throw new RuntimeException(node.position, String.format("Unsupported data type:  \"%s\"", node.value.toString()));
    }

    @Override
    public Object goStatement(StatementNode node) throws RuntimeException {
        return node.left != null ? node.left.accept(this) : null;
    }

    @Override
    public Object goVariable(VariableNode node) throws RuntimeException {
        try {
            Object v = runtime.environment != null && runtime.environment.params.containsKey(node.name) ? runtime.environment.params.get(node.name) : runtime.scope.findVariable(node.name).getValue();
            return valueOfIndex(v, node.indexNode);
        } catch (BulletException e) {
            throw new RuntimeException(node.position, e.getMessage());
        }
    }

    @Override
    public Object goAssign(AssignNode node) throws RuntimeException {
        if (node.left instanceof VariableNode) {
            Object result = node.right.accept(this);

            if (node.complexLevel > 1) {
                return result;
            }

            return changeValue(result, node);
        }

        throw new RuntimeException(node.position, "Only variables can be assigned values");
    }

    @Override
    public Object goIf(IfNode node) throws RuntimeException {
        boolean flag = translateBool(node.condition);

        if (flag) {
            if (node.body != null) {
                return node.body.accept(this);
            }
        } else if (node.elseBody != null) {
            return node.elseBody.accept(this);
        }

        return null;
    }

    @Override
    public Object goBlock(BlockNode node) throws RuntimeException {
        if (runtime.scope.node != node && node.level > runtime.scope.node.level) {
            runtime.scope = runtime.createScope(node);
        } else if (runtime.environment != null && runtime.environment.body.level == node.level) {
            BlockNode blockNode = new BlockNode();
            blockNode.level = node.level + 1;
            blockNode.position = node.position;
            blockNode.statements = node.statements;

            runtime.scope = runtime.createScope(blockNode);
        }

        for (Node statement : node.statements) {
            statement.accept(this);

            if (runtime.returnValue != null) {
                break;
            }

            if (runtime.loopStatus == LoopStatus.BREAK || runtime.loopStatus == LoopStatus.CONTINUE) {
                break;
            }
        }

        if (runtime.environment != null && runtime.environment.from != null && runtime.loopLevel == 0) {
            runtime.scope = runtime.environment.from;
        } else if (runtime.scope.from != null) {
            runtime.scope = runtime.scope.from;
        }

        return runtime.returnValue;
    }

    @Override
    public Object goFor(ForNode node) throws RuntimeException {
        runtime.loopLevel++;

        BlockNode forBlock = new BlockNode();
        forBlock.level = runtime.scope.node.level;
        forBlock.position = node.position;

        runtime.scope = runtime.createScope(forBlock);

        if (node.init != null) {
            node.init.accept(this);
        }

        boolean condition = translateBool(node.condition);
        boolean flag = condition;
        Object result = null;

        while (condition) {
            if (node.body != null) {
                result = node.body.accept(this);
            } else {
                break;
            }

            if (runtime.returnValue != null) {
                runtime.loopLevel--;
                runtime.loopStatus = LoopStatus.NONE;
                runtime.scope = runtime.scope.from;
                return runtime.returnValue;
            }

            if (runtime.loopStatus == LoopStatus.BREAK) {
                break;
            }

            if (runtime.loopStatus == LoopStatus.CONTINUE) {
                runtime.loopStatus = LoopStatus.NONE;
                continue;
            }

            if (node.increase != null) {
                node.increase.accept(this);
            }

            condition = (Boolean) node.condition.accept(this);
        }

        runtime.loopStatus = LoopStatus.NONE;

        /*
        运行到这里说明已经跳出循环了
         */
        if (node.elseBody != null && flag) {
            result = node.elseBody.accept(this);
        }

        runtime.scope = runtime.scope.from;
        runtime.loopLevel--;

        return result;
    }

    @Override
    public Object goUntil(UntilNode node) throws RuntimeException {
        runtime.loopLevel++;

        boolean purpose = translateBool(node.purpose);
        boolean flag = purpose;
        Object result = null;

        while (!purpose) {
            if (node.body != null) {
                result = node.body.accept(this);
            } else {
                break;
            }

            if (runtime.returnValue != null) {
                runtime.loopLevel--;
                runtime.loopStatus = LoopStatus.NONE;
                return runtime.returnValue;
            }

            if (runtime.loopStatus == LoopStatus.BREAK) {
                break;
            }

            if (runtime.loopStatus == LoopStatus.CONTINUE) {
                runtime.loopStatus = LoopStatus.NONE;
                continue;
            }

            purpose = (Boolean) node.purpose.accept(this);
        }

        runtime.loopStatus = LoopStatus.NONE;

        /*
        运行到这里说明已经跳出循环了
         */
        if (node.elseBody != null && !flag) {
            result = node.elseBody.accept(this);
        }

        runtime.loopLevel--;

        return result;
    }

    @Override
    public Object goFunctionCall(FunctionCallNode node) throws RuntimeException {
        Node run = null;
        try {
            BtFunction function = runtime.findFunction(node.name);
            ArrayList<Object> args = new ArrayList<>();

            for (Map.Entry<String, Node> entry : node.args.entrySet()) {
                run = node.args.get(entry.getKey());

                args.add(run.accept(this));
            }

            return valueOfIndex(function.invokeFV(args.toArray()), node.indexNode);
        } catch (BulletException e) {
            throw new RuntimeException(run == null ? node.position : run.position, e.getMessage());
        }
    }

    @Override
    public Object goBuiltInFunctionCall(BuiltInFunctionCallNode node) throws RuntimeException {
        Node run = null;
        try {
            BtBulitInFunction function = BulletRuntime.builtInfunctions.get(node.name);
            Object r;

            if (function.isVarParam) {
                ArrayList<Object> list = new ArrayList<>();

                for (Node v : node.args1) {
                    list.add(v.accept(this));
                }

                r = function.invokeFV(list.toArray());
            } else {

                LinkedHashMap<String, Object> args = new LinkedHashMap<>();

                for (Map.Entry<String, Node> entry : node.args.entrySet()) {
                    run = entry.getValue();

                    args.put(entry.getKey(), run == null ? null : run.accept(this));
                }

                for (Map.Entry<String, Object> entry : function.args.entrySet()) {
                    if (args.get(entry.getKey()) == null) {

                        if (entry.getValue() == null) {
                            throw new ParsingException(node.position, String.format("Missing parameter \"%s\"", entry.getKey()));
                        }

                        args.put(entry.getKey(), function.args.get(entry.getKey()));
                    }
                }
                r = function.invokeFV(args.values().toArray());
            }

            return valueOfIndex(r, node.indexNode);
        } catch (BulletException e) {
            throw new RuntimeException(run == null ? node.position : run.position, e.getMessage());
        }
    }

    @Override
    public Object goReturn(ReturnNode node) throws RuntimeException {
        if (runtime.returnValue != null) {
            return runtime.returnValue;
        }

        return runtime.returnValue = (node.left == null ? false : node.left.accept(this));
    }

    @Override
    public Object goBreak(BreakNode node) throws RuntimeException {
        if (runtime.loopLevel == 0) {
            throw new RuntimeException(node.position, "Does not match the corresponding loop body");
        }

        runtime.loopStatus = LoopStatus.BREAK;
        return null;
    }

    @Override
    public Object goContinue(ContinueNode node) throws RuntimeException {
        if (runtime.loopLevel == 0) {
            throw new RuntimeException(node.position, "Does not match the corresponding loop body");
        }

        runtime.loopStatus = LoopStatus.CONTINUE;
        return null;
    }

    @Override
    public Object goList(ListNode node) throws RuntimeException {
        BtList list = new BtList();

        for (int i = 0; i < node.values.size(); i++) {
            list.add(node.values.get(i).accept(this));
        }

        return valueOfIndex(list, node.indexNode);
    }

    @Override
    public Object goDictionary(DictionaryNode node) throws RuntimeException {
        BtDictionary dictionary = new BtDictionary();

        if (node.vector.size() > 0) {
            Object key;
            Object value;

            for (Map.Entry<Node, Node> entry : node.vector.entrySet()) {
                key = entry.getKey().accept(this);
                value = entry.getValue().accept(this);

                if (key instanceof String) {
                    dictionary.add(key.toString(), value);
                } else {
                    throw new RuntimeException(entry.getValue().position, "It is not a string");
                }
            }
        }

        return valueOfIndex(dictionary, node.indexNode);
    }

    private Object valueOfIndex(Object v, IndexNode node) throws RuntimeException {
        if (node == null) {
            if (v instanceof BtList || v instanceof BtDictionary || v instanceof String) {
                return v;
            }
        }

        IndexNode indexNode = node;

        while (indexNode != null) {
            if (indexNode.complex) {
                if (!(v instanceof BtList || v instanceof String)) {
                    throw new RuntimeException(indexNode.position, "Index values are not supported for this type");
                }

                int start;
                int end;
                Object i;

                if (indexNode.start == null) {
                    start = 0;
                } else {
                    i = indexNode.start.accept(this);

                    if (!(i instanceof BtNumber)) {
                        throw new RuntimeException(indexNode.start.position, "Complex index values must be numbers");
                    }

                    start = ((BtNumber) i).toInteger();

                    if (start < 0) {
                        throw new RuntimeException(indexNode.start.position, "Index value less than 0 is not allowed");
                    }
                }

                if (indexNode.end == null) {
                    if (v instanceof String) {
                        end = ((String) v).length();
                    } else {
                        end = ((BtList) v).size();
                    }
                } else {
                    i = indexNode.end.accept(this);

                    if (!(i instanceof BtNumber)) {
                        throw new RuntimeException(indexNode.end.position, "Complex index values must be numbers");
                    }

                    end = ((BtNumber) i).toInteger();

                    if (end < 0) {
                        throw new RuntimeException(indexNode.end.position, "Index value less than 0 is not allowed");
                    }
                }

                int len;

                if (v instanceof String) {
                    String str = (String) v;
                    len = str.length();
                    boolean flag = false;

                    if (start > end) {
                        int temp = end;
                        end = start;
                        start = temp;
                        flag = true;
                    }

                    if (end > len) {
                        throw new RuntimeException(node.position, String.format("String index %d is out of range %d", end, len));
                    }

                    if (start == end) {
                        if (indexNode.start != null && indexNode.end == null) {
                            start = 0;
                            flag = true;
                        }
                    }

                    str = str.substring(start, end);
                    v = flag ? new StringBuffer(str).reverse() : str;
                } else {
                    BtList btList1 = ((BtList) v);
                    len = btList1.size();
                    boolean flag = false;

                    if (start > end) {
                        flag = true;
                        int temp = end;
                        end = start;
                        start = temp;
                    }

                    if (end > len) {
                        throw new RuntimeException(node.position, String.format("List index %d is out of range %d", end, len));
                    }

                    if (start == end) {
                        if (indexNode.start != null && indexNode.end == null) {
                            start = 0;
                            flag = true;
                        }
                    }

                    BtList btList = new BtList();

                    for (int n = start; n < end; n++) {
                        btList.add(btList1.get(n));
                    }

                    if (flag)
                        btList.reverse();
                    v = btList;
                }
            } else {
                if (!(v instanceof BtList || v instanceof String || v instanceof BtDictionary || v instanceof BtByteString)) {
                    throw new RuntimeException(indexNode.position, String.format("Index values are not supported for this type \"%s\"", v.getClass().getSimpleName()));
                }

                Object i = indexNode.start == null ? null : indexNode.start.accept(this);

                if (v instanceof BtDictionary) {
                    if (!(i instanceof String || i == null)) {
                        throw new RuntimeException(indexNode.start != null ? indexNode.start.position : indexNode.position, "BtDictionary index must be a string");
                    }

                    BtDictionary dictionary = ((BtDictionary) v);

                    if (i == null) {
                        if (dictionary.size() == 0) {
                            throw new RuntimeException(node.position, "The dictionary is empty");
                        }

                        v = dictionary.values().toArray(Object[]::new)[dictionary.size() - 1]; // last
                    } else {
                        if (!dictionary.containsKey(i.toString())) {
                            throw new RuntimeException(indexNode.start != null ? indexNode.start.position : node.position, String.format("The key \"%s\" is not defined", i));
                        }

                        v = dictionary.get(i.toString());
                    }
                } else {
                    if (!(i instanceof BtNumber || i == null)) {
                        throw new RuntimeException(indexNode.start != null ? indexNode.start.position : indexNode.position, String.format("%s index must be a number", i.getClass().getSimpleName()));
                    }

                    int len;

                    if (v instanceof BtList) {
                        BtList btList = (BtList) v;
                        len = i == null ? btList.size() - 1 : ((BtNumber) i).toInteger();
                        if (len >= btList.size() || len < 0) {
                            throw new RuntimeException(indexNode.start != null ? indexNode.start.position : indexNode.position, String.format("List index %d is out of range %d", len, btList.size()));
                        }

                        v = btList.get(len);
                    } else if (v instanceof String) {
                        String str = (String) v;
                        len = i == null ? str.length() - 1 : ((BtNumber) i).toInteger();
                        if (len >= str.length()) {
                            throw new RuntimeException(indexNode.start != null ? indexNode.start.position : indexNode.position, String.format("String index %d is out of range %d", len, str.length()));
                        }

                        v = String.valueOf(str.charAt(len));
                    } else {
                        BtByteString str = (BtByteString) v;
                        len = i == null ? str.size() - 1 : ((BtNumber) i).toInteger();
                        if (len >= str.size()) {
                            throw new RuntimeException(indexNode.start != null ? indexNode.start.position : indexNode.position, String.format("ByteString index %d is out of range %d", len, str.size()));
                        }

                        v = str.get(len);
                    }
                }
            }

            indexNode = indexNode.next;
        }

        return v;
    }

    private Object changeValue(Object result, AssignNode node) throws RuntimeException {
        try {
            VariableNode variable = (VariableNode) node.left;
            Object v;

            if (result instanceof BtList) {
                result = ((BtList) result).clone();
            } else if (result instanceof BtDictionary) {
                result = ((BtDictionary) result).clone();
            }

            if (runtime.environment != null && runtime.environment.params.containsKey(variable.name)) {
                if (variable.indexNode == null) {
                    runtime.environment.params.put(variable.name, result);
                } else {
                    v = runtime.environment.params.get(variable.name);
                    IndexNode address = variable.indexNode;

                    while (address.next != null) {
                        IndexNode node1 = address.clone();
                        node1.next = null;
                        v = valueOfIndex(v, node1);
                        address = address.next;
                    }

                    if (address.complex) {
                        throw new RuntimeException(address.position, "Cannot use complex indexes when assigning values");
                    }

                    Object index = address.start == null ? null : address.start.accept(this);

                    if (v instanceof BtList) {
                        if (!(index instanceof BtNumber || index == null)) {
                            throw new RuntimeException(node.position, String.format("%s index must be a number", v.getClass().getSimpleName()));
                        }

                        BtList list = (BtList) v;

                        if (index == null) {
                            list.add(result);
                        } else {
                            int n = ((BtNumber) index).toInteger();

                            if (n > list.size()) {
                                throw new RuntimeException(node.position, String.format("List index %d is out of range %d", n, list.size()));
                            }

                            if (n == list.size()) {
                                list.add(result);
                            } else {
                                list.set(n, result);
                            }
                        }
                    } else if (v instanceof BtDictionary) {
                        if (!(index instanceof String)) {
                            throw new RuntimeException(node.position, String.format("%s index must be a string", v.getClass().getSimpleName()));
                        }

                        ((BtDictionary) v).add(index.toString(), result);
                    } else {
                        throw new RuntimeException(node.position, String.format("the Type \"%s\" does not support assignment in this way", v.getClass().getSimpleName()));
                    }

                    return result;
                }

                return result;
            }

            if (node.isProvide) {
                if (runtime.provideAttributes.containsKey(variable.name)) {
                    throw new DefinedException(DefinedException.DerfinedType.PROVIDE_ATTRIBUTE, variable.name);
                }

                runtime.provideAttributes.put(variable.name, result);
                return result;
            }

            if (!runtime.scope.existsVariable(variable.name)) {
                if (node.createAction) {
                    if (variable.indexNode == null) {
                        runtime.scope.createVariable(variable.name, result);
                        return result;
                    }
                }

                throw new UnderfineException(UnderfineException.UnderfineType.VARIABLE, variable.name);
            } else {
                if (node.createAction) {
                    throw new DefinedException(DefinedException.DerfinedType.VARIABLE, variable.name);
                }

                BtVariable btVariable = runtime.scope.findVariable(variable.name);

                if (variable.indexNode == null) {
                    return btVariable.setValue(result).getValue();
                } else {
                    v = btVariable.getValue();
                    IndexNode address = variable.indexNode;

                    while (address.next != null) {
                        IndexNode node1 = address.clone();
                        node1.next = null;
                        v = valueOfIndex(v, node1);
                        address = address.next;
                    }

                    if (address.complex) {
                        throw new RuntimeException(address.position, "Cannot use complex indexes when assigning values");
                    }

                    Object index = address.start == null ? null : address.start.accept(this);

                    if (v instanceof BtList) {
                        if (!(index instanceof BtNumber || index == null)) {
                            throw new RuntimeException(address.start != null ? address.start.position : address.position, String.format("%s index must be a number", v.getClass().getSimpleName()));
                        }

                        BtList list = (BtList) v;

                        if (index == null) {
                            list.add(result);
                        } else {
                            int n = ((BtNumber) index).toInteger();

                            if (n > list.size()) {
                                throw new RuntimeException(address.start.position, String.format("List index %d is out of range %d", n, list.size()));
                            }

                            if (n == list.size()) {
                                list.add(result);
                            } else {
                                list.set(n, result);
                            }
                        }
                    } else if (v instanceof BtDictionary) {
                        if (!(index instanceof String)) {
                            throw new RuntimeException(address.start != null ? address.start.position : address.position, String.format("%s index must be a string", v.getClass().getSimpleName()));
                        }

                        ((BtDictionary) v).add(index.toString(), result);
                    } else {
                        throw new RuntimeException(address.start != null ? address.start.position : address.position, String.format("The type \"%s\" does not support assignment in this way", v.getClass().getSimpleName()));
                    }
                }
            }

            return result;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.position, e.getMessage());
        } catch (BulletException e) {
            throw new RuntimeException(node.position, e.getMessage());
        }
    }

    private boolean translateBool(Node node) throws RuntimeException {
        Object v = node.accept(this);

        if (v instanceof Boolean) return (boolean) v;
        if (v instanceof String) return ((String) v).length() > 0;
        if (v instanceof BtNumber) return ((BtNumber) v).compare(BtNumber.ZERO) != 0;
        if (v instanceof BtDictionary) return ((BtDictionary) v).size() > 0;
        if (v instanceof BtByte) return ((BtByte) v).getValue() != 0;
        if (v instanceof BtByteString) return ((BtByteString) v).size() > 0;
        if (v instanceof BtList) return ((BtList) v).size() > 0;
        if (v instanceof BtNull) return false;

        throw new RuntimeException(node.position, "Conversion of this type to Boolean is not supported");
    }
}

package org.bullet.interpreter;

import org.bullet.base.components.BtFunction;
import org.bullet.base.components.BtVariable;
import org.bullet.base.components.BtScope;
import org.bullet.base.components.BtCustomFunction;
import org.bullet.base.types.BtArray;
import org.bullet.base.types.BtDictionary;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * @author Huyemt
 */

public class Interpreter extends Visitor {
    public final BulletRuntime runtime;

    public Interpreter(String source, BulletRuntime runtime) throws ParsingException {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
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

        this.runtime = runtime;
    }

    public Interpreter(File file, BulletRuntime runtime) throws ParsingException, FileCorruptingExceiption {
        Lexer lexer = new Lexer(file);
        Parser parser = new Parser(lexer);
        ProgramNode programNode = parser.Parse();

        BlockNode blockNode = new BlockNode();
        blockNode.position = programNode.position;
        blockNode.statements = programNode.statements;
        blockNode.level = 0;

        BtScope scope = new BtScope();
        scope.position = programNode.position;
        scope.from = null;
        scope.node = blockNode;

        runtime.scope = scope;

        this.runtime = runtime;
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
        Object right = node.right.accept(this);
        Object left = node.left.accept(this);

        if (left instanceof BigDecimal) {
            switch (node.operator) {
                case ADD:
                    if (right instanceof BigDecimal) {
                        return ((BigDecimal) left).add((BigDecimal) right);
                    } else if (right instanceof String) {
                        return String.valueOf(left).concat(right.toString());
                    }

                    throw new RuntimeException(node.position, String.format("Addition of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case SUB:
                    if (right instanceof BigDecimal) {
                        return ((BigDecimal) left).subtract((BigDecimal) right);
                    }

                    throw new RuntimeException(node.position, String.format("Subtraction of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case MUL:
                    if (right instanceof BigDecimal) {
                        return ((BigDecimal) left).multiply((BigDecimal) right);
                    } else if (right instanceof String) {
                        return ((String) right).repeat(((BigDecimal) left).intValue());
                    }

                    throw new RuntimeException(node.position, String.format("Multiplication of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case DIV:
                    if (right instanceof BigDecimal) {
                        if (((BigDecimal) right).intValue() == 0) {
                            throw new RuntimeException(node.position, "Cannot divide by zero");
                        }

                        return ((BigDecimal) left).divide((BigDecimal) right, 1, RoundingMode.HALF_EVEN);
                    }

                    throw new RuntimeException(node.position, String.format("Division of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case POW:
                    if (right instanceof BigDecimal) {
                        return ((BigDecimal) left).pow(((BigDecimal) right).intValueExact());
                    }

                    throw new RuntimeException(node.position, String.format("Exponentiation  of type \"%s\" is not supported for numeric types", right.getClass().getSimpleName()));
                case EQUAL:
                case NOT_EQUAL:
                case GREATER:
                case GREATER_OR_EQUAL:
                case LESSER:
                case LESSER_OR_EQUAL:
                    int flag = ((BigDecimal) left).compareTo((BigDecimal) right);

                    if (node.operator == BinaryNode.Operator.EQUAL) {
                        return flag == 0;
                    }

                    if (node.operator == BinaryNode.Operator.NOT_EQUAL) {
                        return flag != 0;
                    }

                    if (node.operator == BinaryNode.Operator.GREATER) {
                        return flag > 0;
                    }

                    if (node.operator == BinaryNode.Operator.GREATER_OR_EQUAL) {
                        return flag > 0 || flag == 0;
                    }

                    if (node.operator == BinaryNode.Operator.LESSER) {
                        return flag < 0;
                    }

                    return flag < 0 || flag == 0;
                default:
                    throw new RuntimeException(node.position, "Unsupported binary operator");
            }
        } else if (left instanceof Boolean && right instanceof Boolean) {
            switch (node.operator) {
                case OR:
                    return ((Boolean) left) || ((Boolean) right);
                case AND:
                    return ((Boolean) left) && ((Boolean) right);
                default:
                    throw new RuntimeException(node.position, "Boolean values cannot perform operations other than \"and\" and \"or\"");
            }
        } else if (left instanceof String && !(right instanceof BtArray)) {
            if (node.operator == BinaryNode.Operator.ADD) {
                return ((String) left).concat(String.valueOf(right));
            }

            if (node.operator == BinaryNode.Operator.MUL && right instanceof BigDecimal) {
                return ((String) left).repeat(((BigDecimal) right).intValueExact());
            }

            throw new RuntimeException(node.position, "String values cannot perform operations other than \"+\" and \"*\"");
        } else if (left instanceof BtArray && right instanceof BtArray) {
            if (node.operator == BinaryNode.Operator.ADD) {
                BtArray array = new BtArray();
                array.vector.addAll(((BtArray) left).vector);
                array.vector.addAll(((BtArray) right).vector);
                return array;
            }

            throw new RuntimeException(node.position, "Arrays cannot perform operations other than \"+\"");
        }

        throw new RuntimeException(node.position, "Unsupported type operation");
    }

    @Override
    public Object goUnary(UnaryNode node) throws RuntimeException {
        Object left = node.left.accept(this);

        if (left instanceof BigDecimal) {
            switch (node.operator) {
                case PLUS:
                    return left;
                case MINUS:
                    return ((BigDecimal) left).negate();
                default:
                    throw new RuntimeException(node.position, "Unsupported unary operator");
            }
        }

        if (left instanceof Boolean) {
            if (node.operator == UnaryNode.Operator.NOT) {
                return !((Boolean) left);
            }

            throw new RuntimeException(node.position, "Boolean values can only be inverted unary");
        }

        throw new RuntimeException(node.left.position, String.format("Data type of operation is not supported:  \"%s\"", left.toString()));
    }

    @Override
    public Object goConstant(ConstantNode node) throws RuntimeException {
        if (node.value instanceof BigDecimal || node.value instanceof Boolean || node.value instanceof String || node.value == null) {
            if (node.value instanceof String) {
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
        Object condition = node.condition.accept(this);

        boolean flag = false;

        if (condition instanceof BigDecimal) {
            flag = ((BigDecimal) condition).intValue() != 0;
        }

        if (condition instanceof Boolean) {
            flag = (Boolean) condition;
        }

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

        boolean condition = (Boolean) node.condition.accept(this);
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

        boolean purpose = (Boolean) node.purpose.accept(this);
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

            for (int i = 0; i < node.args.size(); i++) {
                run = node.args.get(i);
                args.add(run.accept(this));
            }

            return function.invokeFV(args.toArray());
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
    public BtArray goArray(ArrayNode node) throws RuntimeException {
        BtArray array = new BtArray();

        for (int i = 0; i < node.values.size(); i++) {
            array.vector.add(node.values.get(i).accept(this));
        }

        return array;
    }

    @Override
    public BtDictionary goDictionary(DictionaryNode node) throws RuntimeException {
        BtDictionary dictionary = new BtDictionary();

        if (node.vector.size() > 0) {
            Object key;
            Object value;

            for (Map.Entry<Node, Node> entry : node.vector.entrySet()) {
                key = entry.getKey().accept(this);
                value = entry.getValue().accept(this);

                if (key instanceof String) {
                    dictionary.vector.put(key.toString(), value);
                } else {
                    throw new RuntimeException(entry.getValue().position, "It is not a String");
                }
            }
        }

        return dictionary;
    }

    private Object valueOfIndex(Object v, IndexNode node) throws RuntimeException {
        if (node == null) {
            if (v instanceof BtArray || v instanceof BtDictionary || v instanceof String) {
                return v;
            } else if (v instanceof Integer || v instanceof Double || v instanceof Float) {
                return new BigDecimal(v.toString());
            }
        }

        IndexNode indexNode = node;

        while (indexNode != null) {
            if (indexNode.complex) {
                if (!(v instanceof BtArray || v instanceof String)) {
                    throw new RuntimeException(indexNode.position, "Index values are not supported for this type");
                }

                int start;
                int end;
                Object i;

                if (indexNode.start == null) {
                    start = 0;
                } else {
                    i = indexNode.start.accept(this);

                    if (!(i instanceof BigDecimal)) {
                        throw new RuntimeException(indexNode.start.position, "Complex index values must be numbers");
                    }

                    start = ((BigDecimal) i).intValueExact();

                    if (start < 0) {
                        throw new RuntimeException(indexNode.start.position, "Index value less than 0 is not allowed");
                    }
                }

                if (indexNode.end == null) {
                    if (v instanceof String) {
                        end = ((String) v).length();
                    } else {
                        end = ((BtArray) v).vector.size();
                    }
                } else {
                    i = indexNode.end.accept(this);

                    if (!(i instanceof BigDecimal)) {
                        throw new RuntimeException(indexNode.end.position, "Complex index values must be numbers");
                    }

                    end = ((BigDecimal) i).intValueExact();

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

                    str = str.substring(start, end);
                    v = flag ? new StringBuffer(str).reverse() : str;
                } else {
                    BtArray btArray1 = ((BtArray) v);
                    len = btArray1.vector.size();
                    boolean flag = false;

                    if (start > end) {
                        flag = true;
                        int temp = end;
                        end = start;
                        start = temp;
                    }

                    if (end > len) {
                        throw new RuntimeException(node.position, String.format("Array index %d is out of range %d", end, len));
                    }

                    BtArray btArray = new BtArray();

                    for (int n = start; n < end; n++) {
                        Object a = btArray1.vector.get(n);

                        if (a instanceof Integer || a instanceof Double || a instanceof Float) {
                            a = new BigDecimal(a.toString());
                        }

                        btArray.vector.add(a);
                    }

                    if (flag)
                        Collections.reverse(btArray.vector);
                    v = btArray;
                }
            } else {
                if (!(v instanceof BtArray || v instanceof String || v instanceof BtDictionary)) {
                    throw new RuntimeException(indexNode.position, String.format("Index values are not supported for this type \"%s\"", v.getClass().getSimpleName()));
                }

                Object i = indexNode.start == null ? null : indexNode.start.accept(this);

                if (v instanceof BtDictionary) {
                    if (!(i instanceof String || i == null)) {
                        throw new RuntimeException(indexNode.start != null ? indexNode.start.position : indexNode.position, "BtDictionary index must be a string");
                    }

                    BtDictionary dictionary = ((BtDictionary) v);

                    if (i == null) {
                        if (dictionary.vector.size() == 0) {
                            throw new RuntimeException(node.position, "The dictionary is empty");
                        }

                        v = dictionary.vector.values().toArray(Object[]::new)[dictionary.vector.size() - 1]; // last
                    } else {
                        if (!dictionary.vector.containsKey(i)) {
                            throw new RuntimeException(indexNode.start != null ? indexNode.start.position : node.position, String.format("The key \"%s\" is not defined", i));
                        }

                        v = dictionary.vector.get(i);
                    }
                } else {
                    if (!(i instanceof BigDecimal || i == null)) {
                        throw new RuntimeException(indexNode.start != null ? indexNode.start.position : indexNode.position, String.format("%s index must be a number", i.getClass().getSimpleName()));
                    }

                    int len;

                    if (v instanceof BtArray) {
                        BtArray btArray = (BtArray) v;
                        len = i == null ? btArray.vector.size() - 1 : ((BigDecimal) i).intValueExact();
                        if (len >= btArray.vector.size() || len < 0) {
                            throw new RuntimeException(indexNode.start != null ? indexNode.start.position : indexNode.position, String.format("Array index %d is out of range %d", len, btArray.vector.size()));
                        }

                        v = btArray.vector.get(len);
                    } else {
                        String str = (String) v;
                        len = i == null ? str.length() - 1 : ((BigDecimal) i).intValueExact();
                        if (len >= str.length()) {
                            throw new RuntimeException(indexNode.start != null ? indexNode.start.position : indexNode.position, String.format("String index %d is out of range %d", len, str.length()));
                        }

                        v = String.valueOf(str.charAt(len));
                    }
                }
            }

            if (v instanceof Integer || v instanceof Double || v instanceof Float) {
                v = new BigDecimal(v.toString());
            }

            indexNode = indexNode.next;
        }

        return v;
    }

    private Object changeValue(Object result, AssignNode node) throws RuntimeException {
        try {
            VariableNode variable = (VariableNode) node.left;
            Object v;

            if (result instanceof BtArray) {
                result = ((BtArray) result).clone();
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

                    if (v instanceof BtArray) {
                        if (!(index instanceof BigDecimal || index == null)) {
                            throw new RuntimeException(node.position, String.format("%s index must be a number", v.getClass().getSimpleName()));
                        }

                        BtArray array = (BtArray) v;

                        if (index == null) {
                            array.vector.add(result);
                        } else {
                            int n = ((BigDecimal) index).intValueExact();

                            if (n > array.vector.size()) {
                                throw new RuntimeException(node.position, String.format("Array index %d is out of range %d", n, array.vector.size()));
                            }

                            if (n == array.vector.size()) {
                                array.vector.add(result);
                            } else {
                                array.vector.set(n, result);
                            }
                        }
                    } else if (v instanceof BtDictionary) {
                        if (!(index instanceof String)) {
                            throw new RuntimeException(node.position, String.format("%s index must be a string", v.getClass().getSimpleName()));
                        }

                        ((BtDictionary) v).vector.put(index.toString(), result);
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

                    if (v instanceof BtArray) {
                        if (!(index instanceof BigDecimal || index == null)) {
                            throw new RuntimeException(address.start != null ? address.start.position : address.position, String.format("%s index must be a number", v.getClass().getSimpleName()));
                        }

                        BtArray array = (BtArray) v;

                        if (index == null) {
                            array.vector.add(result);
                        } else {
                            int n = ((BigDecimal) index).intValueExact();

                            if (n > array.vector.size()) {
                                throw new RuntimeException(address.start.position, String.format("Array index %d is out of range %d", n, array.vector.size()));
                            }

                            if (n == array.vector.size()) {
                                array.vector.add(result);
                            } else {
                                array.vector.set(n, result);
                            }
                        }
                    } else if (v instanceof BtDictionary) {
                        if (!(index instanceof String)) {
                            throw new RuntimeException(address.start != null ? address.start.position : address.position, String.format("%s index must be a string", v.getClass().getSimpleName()));
                        }

                        ((BtDictionary) v).vector.put(index.toString(), result);
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
}

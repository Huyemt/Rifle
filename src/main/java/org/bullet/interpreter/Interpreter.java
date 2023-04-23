package org.bullet.interpreter;

import org.bullet.base.components.BtFunction;
import org.bullet.base.components.BtInterface;
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

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

                    throw new RuntimeException(node.position, String.format("Addition of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                case SUB:
                    if (right instanceof BigDecimal) {
                        return ((BigDecimal) left).subtract((BigDecimal) right);
                    }

                    throw new RuntimeException(node.position, String.format("Subtraction of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                case MUL:
                    if (right instanceof BigDecimal) {
                        return ((BigDecimal) left).multiply((BigDecimal) right);
                    } else if (right instanceof String) {
                        return ((String) right).repeat(((BigDecimal) left).intValue());
                    }

                    throw new RuntimeException(node.position, String.format("Multiplication of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                case DIV:
                    if (right instanceof BigDecimal) {
                        if (((BigDecimal) right).intValue() == 0) {
                            throw new RuntimeException(node.position, "Cannot divide by zero");
                        }

                        return ((BigDecimal) left).divide((BigDecimal) right, RoundingMode.HALF_EVEN);
                    }

                    throw new RuntimeException(node.position, String.format("Division of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                case POW:
                    if (right instanceof BigDecimal) {
                        return ((BigDecimal) left).pow(((BigDecimal) right).intValueExact());
                    }

                    throw new RuntimeException(node.position, String.format("Exponentiation  of type \"%s\" is not supported for numeric types", right.getClass().getName()));
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
    public Object goConstant(ConstantNode<?> node) throws RuntimeException {
        if (node.value instanceof BigDecimal || node.value instanceof Boolean || node.value instanceof String) {
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

            if (v instanceof BtArray) {
                BtArray array = (BtArray) v;

                if (node.index.size() == 0) {
                    return array;
                }

                Object index;
                Object rr;
                int jj;
                index = node.index.get(0).accept(this);

                if (!(index instanceof BigDecimal)) {
                    throw new RuntimeException(node.index.get(0).position, "Array's index can only be a number");
                }

                jj = ((BigDecimal) index).intValueExact();

                if (array.vector.size() <= jj) {
                    throw new RuntimeException(node.index.get(0).position, String.format("Index %d out of bounds for length %d", jj, array.vector.size()));
                }

                rr = array.vector.get(jj);

                for (int i = 1; i < node.index.size(); i++) {
                    index = node.index.get(i).accept(this);

                    if (rr instanceof BtArray) {

                        if (!(index instanceof BigDecimal)) {
                            throw new RuntimeException(node.index.get(i).position, "Array's index can only be a number");
                        }

                        jj = ((BigDecimal) index).intValueExact();

                        if (((BtArray) rr).vector.size() > jj) {
                            rr = ((BtArray) rr).vector.get(jj);
                            continue;
                        }

                        throw new RuntimeException(node.index.get(i).position, String.format("Index %d out of bounds for length %d", jj, ((BtArray) rr).vector.size()));
                    } else if (rr instanceof BtDictionary) {
                        if (!(index instanceof String)) {
                            throw new RuntimeException(node.index.get(i).position, "Dictionary's index can only be a string");
                        }

                        if (!((BtDictionary) rr).vector.containsKey(index)) {
                            throw new RuntimeException(node.index.get(i).position, String.format("The key \"%s\" is not exists", index));
                        }

                        rr = ((BtDictionary) rr).vector.get(index);
                    }
                }

                return rr;
            }

            if (v instanceof BtDictionary) {
                BtDictionary dictionary = (BtDictionary) v;

                if (node.index.size() == 0) {
                    return v;
                }

                Object index;
                Object rr;
                int jj;
                index = node.index.get(0).accept(this);

                if (!(index instanceof String)) {
                    throw new RuntimeException(node.index.get(0).position, "Dictionary's index can only be a string");
                }

                if (!dictionary.vector.containsKey(index)) {
                    throw new RuntimeException(node.index.get(0).position, String.format("The key \"%s\" is not exists", index));
                }

                rr = dictionary.vector.get(index);

                for (int i = 1; i < node.index.size(); i++) {
                    index = node.index.get(i).accept(this);

                    if (rr instanceof BtArray) {
                        if (!(index instanceof BigDecimal)) {
                            throw new RuntimeException(node.index.get(i).position, "Array's index can only be a number");
                        }

                        jj = ((BigDecimal) index).intValueExact();

                        if (((BtArray) rr).vector.size() > jj) {
                            rr = ((BtArray) rr).vector.get(jj);
                            continue;
                        }

                        throw new RuntimeException(node.index.get(i).position, String.format("Index %d out of bounds for length %d", jj, ((BtArray) rr).vector.size()));
                    } else if (rr instanceof BtDictionary) {
                        if (!(index instanceof String)) {
                            throw new RuntimeException(node.index.get(i).position, "Dictionary's index can only be a string");
                        }

                        if (!((BtDictionary) rr).vector.containsKey(index)) {
                            throw new RuntimeException(node.index.get(i).position, String.format("The key \"%s\" is not exists", index));
                        }

                        rr = ((BtDictionary) rr).vector.get(index);
                    }
                }

                return rr;
            }

            return v;
        } catch (BulletException e) {
            throw new RuntimeException(node.position, e.getMessage());
        }
    }

    @Override
    public Object goAssign(AssignNode node) throws RuntimeException {
        if (node.left instanceof VariableNode) {
            VariableNode variable = (VariableNode) node.left;
            Object result = node.right.accept(this);

            try {
                if (runtime.environment != null && runtime.environment.params.containsKey(variable.name)) {
                    if (variable.index.size() == 0) {
                        runtime.environment.params.put(variable.name, result);
                    } else {
                        Object v = runtime.environment.params.get(variable.name);
                        if (runtime.environment.params.get(variable.name) instanceof BtArray) {
                            BtArray array = (BtArray) v;
                            Object index;
                            Object rr = null;
                            int jj;

                            for (int i = 0; i < variable.index.size() - 1; i++) {
                                index = variable.index.get(i).accept(this);

                                if (!(index instanceof BigDecimal)) {
                                    throw new RuntimeException(variable.index.get(i).position, "Array's index can only be a number");
                                }

                                jj = ((BigDecimal) index).intValueExact();

                                if (rr == null) {
                                    if (array.vector.size() > jj) {
                                        rr = array.vector.get(jj);

                                        if (!(rr instanceof BtArray)) {
                                            throw new RuntimeException(variable.index.get(i).position, "Only arrays can use numeric indexes");
                                        }

                                        continue;
                                    }
                                } else {
                                    if (((BtArray) rr).vector.size() > jj) {
                                        rr = ((BtArray) rr).vector.get(jj);

                                        if (!(rr instanceof BtArray)) {
                                            throw new RuntimeException(variable.index.get(i).position, "Only arrays can use numeric indexes");
                                        }

                                        continue;
                                    }
                                }

                                throw new RuntimeException(node.position, String.format("Index %d out of bounds for length %d", jj, array.vector.size()));
                            }

                            jj = ((BigDecimal) variable.index.get(variable.index.size() - 1).accept(this)).intValueExact();

                            if (result instanceof BtArray) {
                                BtArray array1 = new BtArray();
                                array1.vector = (ArrayList<Object>) ((BtArray) result).vector.clone();
                                result = array1;
                            }

                            if (rr == null) {
                                if (jj + 1 < array.vector.size())
                                    array.vector.set(jj, result);
                                else if (jj == array.vector.size())
                                    array.vector.add(result);
                                else throw new RuntimeException(node.position, String.format("Index %d out of bounds for length %d", jj, array.vector.size()));
                            } else {
                                BtArray array1 = ((BtArray) rr);
                                if (jj + 1 < array1.vector.size())
                                    array1.vector.set(jj, result);
                                else if (jj == array1.vector.size())
                                    array1.vector.add(result);
                                else throw new RuntimeException(node.position, String.format("Index %d out of bounds for length %d", jj, array1.vector.size()));
                            }

                            return array;
                        }

                        throw new RuntimeException(node.position, String.format("Variable \"%s\" is not an array", variable.name));
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

                if (variable.index.size() == 0) {
                    BtVariable variable1 = node.createAction ? runtime.scope.createVariable(variable.name, result) : runtime.scope.changeVariable(variable.name, result);

                    if (node.createAction) {
                        variable1.canChange = node.canChange;
                    }

                    return variable1.getValue();
                } else {
                    if (node.createAction) {
                        throw new RuntimeException(node.position, "Array access cannot be performed when variables are declared");
                    }

                    BtVariable v = runtime.scope.findVariable(variable.name);

                    if (v.getValue() instanceof BtArray) {
                        BtArray array = (BtArray) v.getValue();
                        Object index;
                        Object rr = null;
                        int jj;

                        for (int i = 0; i < variable.index.size() - 1; i++) {
                            index = variable.index.get(i).accept(this);

                            if (rr == null) {
                                if (!(index instanceof BigDecimal)) {
                                    throw new RuntimeException(variable.index.get(i).position, "Array's index can only be a number");
                                }

                                jj = ((BigDecimal) index).intValueExact();

                                if (array.vector.size() > jj) {
                                    rr = array.vector.get(jj);

                                    if (!(rr instanceof BtArray)) {
                                        throw new RuntimeException(variable.index.get(i).position, "Only arrays can use numeric indexes");
                                    }

                                    continue;
                                }
                            } else {
                                if (rr instanceof BtArray) {
                                    if (!(index instanceof BigDecimal)) {
                                        throw new RuntimeException(variable.index.get(i).position, "Array's index can only be a number");
                                    }

                                    jj = ((BigDecimal) index).intValueExact();

                                    if (((BtArray) rr).vector.size() > jj) {
                                        rr = ((BtArray) rr).vector.get(jj);
                                        continue;
                                    }

                                    throw new RuntimeException(variable.index.get(i).position, String.format("Index %d out of bounds for length %d", jj, ((BtArray) rr).vector.size()));
                                } else if (rr instanceof BtDictionary) {
                                    if (!(index instanceof String)) {
                                        throw new RuntimeException(variable.index.get(i).position, "Dictionary's index can only be a string");
                                    }

                                    if (!((BtDictionary) rr).vector.containsKey(index)) {
                                        throw new RuntimeException(variable.index.get(i).position, String.format("The key \"%s\" is not exists", index));
                                    }

                                    rr = ((BtDictionary) rr).vector.get(index);
                                    continue;
                                }
                            }

                            throw new RuntimeException(node.position, String.format("Index %d out of bounds for length %d", i, array.vector.size()));
                        }

                        index = variable.index.get(variable.index.size() - 1).accept(this);

                        if (result instanceof BtArray) {
                            BtArray array1 = new BtArray();
                            array1.vector = (ArrayList<Object>) ((BtArray) result).vector.clone();
                            result = array1;
                        }

                        if (rr == null) {
                            if (!(index instanceof BigDecimal)) {
                                throw new RuntimeException(variable.index.get(variable.index.size() - 1).position, "Array's index can only be a number");
                            }

                            jj = ((BigDecimal) index).intValueExact();

                            if (jj + 1 < array.vector.size())
                                array.vector.set(jj, result);
                            else if (jj == array.vector.size())
                                array.vector.add(result);
                            else throw new RuntimeException(node.position, String.format("Index %d out of bounds for length %d", jj, array.vector.size()));
                        } else {
                            if (rr instanceof BtDictionary) {
                                ((BtDictionary) rr).vector.put(index.toString(), result);
                            } else if (rr instanceof BtArray) {
                                if (!(index instanceof BigDecimal)) {
                                    throw new RuntimeException(variable.index.get(variable.index.size() - 1).position, "Array's index can only be a number");
                                }

                                jj = ((BigDecimal) index).intValueExact();

                                BtArray array1 = ((BtArray) rr);
                                if (jj + 1 < array1.vector.size())
                                    array1.vector.set(jj, result);
                                else if (jj == array1.vector.size())
                                    array1.vector.add(result);
                                else throw new RuntimeException(node.position, String.format("Index %d out of bounds for length %d", jj, array1.vector.size()));
                            }
                        }

                        return array;
                    }

                    if (v.getValue() instanceof BtDictionary) {
                        BtDictionary dictionary = (BtDictionary) v.getValue();
                        Object index;
                        Object rr = null;
                        int jj;

                        for (int i = 0; i < variable.index.size() - 1; i++) {
                            index = variable.index.get(i).accept(this);

                            if (rr == null) {
                                if (!(index instanceof String)) {
                                    throw new RuntimeException(variable.index.get(i).position, "Dictionary's index can only be a string");
                                }

                                if (!dictionary.vector.containsKey(index)) {
                                    throw new RuntimeException(variable.index.get(i).position, String.format("The key \"%s\" is not exists", index));
                                }

                                rr = dictionary.vector.get(index);
                            } else {
                                if (rr instanceof BtArray) {
                                    if (!(index instanceof BigDecimal)) {
                                        throw new RuntimeException(variable.index.get(i).position, "Array's index can only be a number");
                                    }

                                    jj = ((BigDecimal) index).intValueExact();

                                    if (((BtArray) rr).vector.size() > jj) {
                                        rr = ((BtArray) rr).vector.get(jj);
                                        continue;
                                    }

                                    throw new RuntimeException(variable.index.get(i).position, String.format("Index %d out of bounds for length %d", jj, ((BtArray) rr).vector.size()));
                                } else if (rr instanceof BtDictionary) {
                                    if (!(index instanceof String)) {
                                        throw new RuntimeException(variable.index.get(i).position, "Dictionary's index can only be a string");
                                    }

                                    if (!((BtDictionary) rr).vector.containsKey(index)) {
                                        throw new RuntimeException(variable.index.get(i).position, String.format("The key \"%s\" is not exists", index));
                                    }

                                    rr = ((BtDictionary) rr).vector.get(index);
                                }
                            }
                        }

                        index = variable.index.get(variable.index.size() - 1).accept(this);

                        if (result instanceof BtDictionary) {
                            BtDictionary btDictionary = new BtDictionary();
                            btDictionary.vector.putAll((Map<? extends String, ?>) ((BtDictionary) result).vector.clone());
                            result = btDictionary;
                        }

                        if (rr == null) {
                            dictionary.vector.put(index.toString(), result);
                        } else {
                            if (rr instanceof BtDictionary) {
                                ((BtDictionary) rr).vector.put(index.toString(), result);
                            } else if (rr instanceof BtArray) {
                                if (!(index instanceof BigDecimal)) {
                                    throw new RuntimeException(variable.index.get(variable.index.size() - 1).position, "Array's index can only be a number");
                                }

                                jj = ((BigDecimal) index).intValueExact();

                                BtArray array1 = ((BtArray) rr);
                                if (jj + 1 < array1.vector.size())
                                    array1.vector.set(jj, result);
                                else if (jj == array1.vector.size())
                                    array1.vector.add(result);
                                else throw new RuntimeException(node.position, String.format("Index %d out of bounds for length %d", jj, array1.vector.size()));
                            }
                        }

                        return dictionary;
                    }

                    throw new RuntimeException(node.position, String.format("Variable \"%s\" is not an array or a dictionary", variable.name));
                }
            } catch (BulletException e) {
                throw new RuntimeException(node.position, e.getMessage());
            }
        }

        throw new RuntimeException(node.position, "Only variables can be assigned values");
    }

    @Override
    public Object goComplexAssign(ComplexAssignNode node) throws RuntimeException {
        try {
            Object left = node.left.accept(this);
            Object right = node.right.accept(this);

            if (left instanceof BigDecimal) {
                switch (node.operator) {
                    case ADD: {
                        if (right instanceof BigDecimal) {
                            return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).add((BigDecimal) right)).getValue();
                        } else if (right instanceof String) {
                            return runtime.scope.changeVariable(node.left.name, String.valueOf(left) + right).getValue();
                        }

                        throw new RuntimeException(node.position, String.format("Addition of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                    }

                    case SUB: {
                        if (right instanceof BigDecimal) {
                            return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).subtract((BigDecimal) right)).getValue();
                        }

                        throw new RuntimeException(node.position, String.format("Subtraction of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                    }

                    case MUL: {
                        if (right instanceof BigDecimal) {
                            return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).multiply((BigDecimal) right)).getValue();
                        } else if (right instanceof String) {
                            return runtime.scope.changeVariable(node.left.name, ((String) right).repeat(((BigDecimal) left).intValueExact())).getValue();
                        }

                        throw new RuntimeException(node.position, String.format("Multiplication of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                    }

                    case DIV: {
                        if (right instanceof BigDecimal) {
                            if (((BigDecimal) right).intValue() == 0) {
                                throw new RuntimeException(node.position, "Cannot divide by zero");
                            }

                            return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).divide((BigDecimal) right, RoundingMode.HALF_EVEN)).getValue();
                        }

                        throw new RuntimeException(node.position, String.format("Division of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                    }

                    case POW: {
                        if (right instanceof BigDecimal) {
                            return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).pow(((BigDecimal) right).intValueExact())).getValue();
                        }

                        throw new RuntimeException(node.position, String.format("Exponentiation  of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                    }
                }
            } else if (left instanceof String) {
                switch (node.operator) {
                    case ADD: {
                        if (right instanceof BigDecimal || right instanceof String) {
                            return runtime.scope.changeVariable(node.left.name, ((String) left).concat(right.toString())).getValue();
                        }

                        throw new RuntimeException(node.position, String.format("Addition of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                    }

                    case MUL: {
                        if (right instanceof BigDecimal) {
                            return runtime.scope.changeVariable(node.left.name, ((String) left).repeat(((BigDecimal) right).intValueExact())).getValue();
                        }

                        throw new RuntimeException(node.position, String.format("Multiplication of type \"%s\" is not supported for numeric types", right.getClass().getName()));
                    }
                }
            }

            throw new RuntimeException(node.position, "Complex assignment operation is not supported by this type");
        } catch (BulletException e) {
            throw new RuntimeException(node.position, e.getMessage());
        }
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
    public Object goWhile(WhileNode node) throws RuntimeException {
        runtime.loopLevel++;

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
                return runtime.returnValue;
            }

            if (runtime.loopStatus == LoopStatus.BREAK) {
                break;
            }

            if (runtime.loopStatus == LoopStatus.CONTINUE) {
                runtime.loopStatus = LoopStatus.NONE;
                continue;
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

        runtime.loopLevel--;

        return result;
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
    public Object goFunction(FunctionNode node) throws RuntimeException {
        if (runtime.functions.containsKey(node.name)) {
            throw new RuntimeException(node.position, String.format("The function \"%s\" has already been declared", node.name));
        }

        runtime.functions.put(node.name, new BtCustomFunction(this, node));

        return null;
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

        return runtime.returnValue = node.left.accept(this);
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
    public Object goProvide(ProvideNode node) throws RuntimeException {
        if (runtime.provideInterfaces.containsKey(node.name)) {
            throw new RuntimeException(node.position, String.format("Interface \"%s\" has been defined", node.name));
        }

        runtime.provideInterfaces.put(node.name, new BtInterface(this, node));
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
}

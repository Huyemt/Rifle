package org.bullet.interpreter;

import org.bullet.base.components.BtFunction;
import org.bullet.base.components.BtInterface;
import org.bullet.base.components.BtVariable;
import org.bullet.base.components.BtScope;
import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.compiler.ast.nodes.*;
import org.bullet.compiler.lexer.Lexer;
import org.bullet.compiler.parser.Parser;
import org.bullet.exceptions.*;
import org.bullet.exceptions.RuntimeException;
import org.rifle.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

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

    public Interpreter(File file, BulletRuntime runtime) throws ParsingException, IOException {
        this(Utils.readFile(file), runtime);
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
                        return String.valueOf(left) + right;
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
        } else if (left instanceof String) {
            if (node.operator == BinaryNode.Operator.ADD) {
                return left + String.valueOf(right);
            }

            if (node.operator == BinaryNode.Operator.MUL && right instanceof BigDecimal) {
                return ((String) left).repeat(((BigDecimal) right).intValueExact());
            }

            throw new RuntimeException(node.position, "String values cannot perform operations other than \"+\" and \"*\"");
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
            if (runtime.environment != null && runtime.environment.params.containsKey(node.name)) {
                return runtime.environment.params.get(node.name);
            }

            return runtime.scope.findVariable(node.name).getValue();
        } catch (BulletException e) {
            throw new RuntimeException(node.position, e.getMessage());
        }
    }

    @Override
    public Object goAssign(AssignNode node) throws RuntimeException {
        if (node.left instanceof VariableNode) {
            String name = ((VariableNode) node.left).name;
            Object result = node.right.accept(this);

            try {
                if (runtime.environment != null && runtime.environment.params.containsKey(name)) {
                    runtime.environment.params.put(name, result);
                    return result;
                }

                if (node.isProvide) {
                    if (runtime.provideAttributes.containsKey(name)) {
                        throw new DefinedException(DefinedException.DerfinedType.PROVIDE_ATTRIBUTE, name);
                    }

                    runtime.provideAttributes.put(name, result);
                    return result;
                }

                BtVariable variable = node.createAction ? runtime.scope.createVariable(name, result) : runtime.scope.changeVariable(name, result);

                if (node.createAction) {
                    variable.canChange = node.canChange;
                }

                return variable.getValue();
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

            switch (node.operator) {
                case ADD:
                    return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).add((BigDecimal) right)).getValue();
                case SUB:
                    return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).subtract((BigDecimal) right)).getValue();
                case MUL:
                    return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).multiply((BigDecimal) right)).getValue();
                case DIV:
                    if (((BigDecimal) right).intValue() == 0) {
                        throw new RuntimeException(node.position, "Cannot divide by zero");
                    }

                    return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).divide((BigDecimal) right, RoundingMode.HALF_EVEN)).getValue();
                case POW:
                    return runtime.scope.changeVariable(node.left.name, ((BigDecimal) left).pow(((BigDecimal) right).intValueExact())).getValue();
                default:
                    throw new RuntimeException(node.position, "Unsupported complex assignment operator");
            }
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

        runtime.functions.put(node.name, new BtFunction(this, node));

        return null;
    }

    @Override
    public Object goFunctionCall(FunctionCallNode node) throws RuntimeException {
        switch (node.name) {
            case "print":
            case "println": {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < node.args.size(); i++) {
                    builder.append(node.args.get(i).accept(this));

                    if (i + 1 < node.args.size())
                        builder.append("\t");
                }

                if (node.name.equals("print")) {
                    if (runtime.logger == null)
                        System.out.print(builder);
                    else
                        runtime.logger.info(builder);
                } else {
                    if (runtime.logger == null)
                        System.out.println(builder);
                    else
                        runtime.logger.info(builder);
                }
                return null;
            }

            default: {
                try {
                    BtFunction function = runtime.findFunction(node.name);
                    ArrayList<Object> args = new ArrayList<>();

                    for (int i = 0; i < node.args.size(); i++) {
                        args.add(node.args.get(i).accept(this));
                    }

                    return function.invokeFV(args.toArray());
                } catch (BulletException e) {
                    throw new RuntimeException(node.position, e.getMessage());
                }
            }
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
}

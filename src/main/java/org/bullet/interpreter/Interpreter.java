package org.bullet.interpreter;

import org.bullet.base.FunctionEnvironment;
import org.bullet.base.BtScope;
import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.compiler.ast.nodes.*;
import org.bullet.compiler.lexer.Lexer;
import org.bullet.compiler.parser.Parser;
import org.bullet.exceptions.BulletException;
import org.bullet.exceptions.ParsingException;
import org.bullet.exceptions.RuntimeException;
import org.bullet.exceptions.UnderfineException;
import org.rifle.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author Huyemt
 */

public class Interpreter extends Visitor {
    private BtScope scope;
    private FunctionEnvironment environment;
    private final HashMap<String, FunctionNode> functions;
    private final Stack<FunctionEnvironment> environments;
    private Object returnValue;
    private LoopStatus loopStatus;


    public Interpreter(String source) throws ParsingException {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        ProgramNode programNode = parser.Parse();
        functions = new HashMap<>();
        returnValue = null;
        environments = new Stack<>();
        environment = null;
        scope = new BtScope();
        scope.position = programNode.position;
        scope.node = new BlockNode();
        scope.node.statements = programNode.statements;
        scope.node.level = 0;
        scope.node.position = scope.position;
        loopStatus = LoopStatus.NONE;
    }

    public Interpreter(File file) throws ParsingException, IOException {
        this(Utils.readFile(file));
    }

    public Object eval() throws RuntimeException {
        return scope.node.accept(this);
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

        if (left instanceof BigDecimal && right instanceof BigDecimal) {
            switch (node.operator) {
                case ADD:
                    return ((BigDecimal) left).add((BigDecimal) right);
                case SUB:
                    return ((BigDecimal) left).subtract((BigDecimal) right);
                case MUL:
                    return ((BigDecimal) left).multiply((BigDecimal) right);
                case DIV:
                    if (((BigDecimal) right).intValue() == 0) {
                        throw new RuntimeException(node.position, "Cannot divide by zero");
                    }

                    return ((BigDecimal) left).divide((BigDecimal) right, RoundingMode.HALF_EVEN);
                case POW:
                    return ((BigDecimal) left).pow(((BigDecimal) right).intValueExact());
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
        if (node.value instanceof BigDecimal || node.value instanceof Boolean) {
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
            if (environment != null && environment.params.containsKey(node.name)) {
                return environment.params.get(node.name);
            }

            return scope.findVariable(node.name).value;
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
                if (environment != null && environment.params.containsKey(variable.name)) {
                    environment.params.put(variable.name, result);
                    return result;
                }

                return node.createAction ? scope.createVariable(variable.name, result).value : scope.changeVariable(variable.name, result).value;
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
                    return scope.changeVariable(node.left.name, ((BigDecimal) left).add((BigDecimal) right)).value;
                case SUB:
                    return scope.changeVariable(node.left.name, ((BigDecimal) left).subtract((BigDecimal) right)).value;
                case MUL:
                    return scope.changeVariable(node.left.name, ((BigDecimal) left).multiply((BigDecimal) right)).value;
                case DIV:
                    if (((BigDecimal) right).intValue() == 0) {
                        throw new RuntimeException(node.position, "Cannot divide by zero");
                    }

                    return scope.changeVariable(node.left.name, ((BigDecimal) left).divide((BigDecimal) right, RoundingMode.HALF_EVEN)).value;
                case POW:
                    return scope.changeVariable(node.left.name, ((BigDecimal) left).pow(((BigDecimal) right).intValueExact())).value;
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
        if (scope.node != node && node.level > scope.node.level) {
            scope = this.createScope(node);
        }

        Object r = null;

        for (Node statement : scope.node.statements) {
            statement.accept(this);

            if (returnValue != null) {
                r = returnValue;
                break;
            }
        }

        scope = environment != null ? environment.from : scope.from;

        return r;
    }

    @Override
    public Object goWhile(WhileNode node) throws RuntimeException {
        boolean condition = (Boolean) node.condition.accept(this);
        boolean flag = condition;
        Object result = null;

        while (condition) {
            if (node.body != null) {
                result = node.body.accept(this);
            }

            if (returnValue != null) {
                return returnValue;
            }

            condition = (Boolean) node.condition.accept(this);
        }

        /*
        运行到这里说明已经跳出循环了
         */
        if (node.elseBody != null && flag) {
            result = node.elseBody.accept(this);
        }

        return result;
    }

    @Override
    public Object goFor(ForNode node) throws RuntimeException {
        if (node.init != null) {
            node.init.accept(this);
        }

        boolean condition = (Boolean) node.condition.accept(this);
        boolean flag = condition;
        Object result = null;

        while (condition) {
            if (node.body != null) {
                result = node.body.accept(this);
            }

            if (returnValue != null) {
                return returnValue;
            }

            if (node.increase != null) {
                node.increase.accept(this);
            }

            condition = (Boolean) node.condition.accept(this);
        }

        /*
        运行到这里说明已经跳出循环了
         */
        if (node.elseBody != null && flag) {
            result = node.elseBody.accept(this);
        }

        return result;
    }

    @Override
    public Object goUntil(UntilNode node) throws RuntimeException {
        boolean purpose = (Boolean) node.purpose.accept(this);
        boolean flag = purpose;
        Object result = null;

        while (!purpose) {
            if (node.body != null) {
                result = node.body.accept(this);
            }

            if (returnValue != null) {
                return returnValue;
            }

            purpose = (Boolean) node.purpose.accept(this);
        }

        /*
        运行到这里说明已经跳出循环了
         */
        if (node.elseBody != null && !flag) {
            result = node.elseBody.accept(this);
        }

        return result;
    }

    @Override
    public Object goFunction(FunctionNode node) throws RuntimeException {
        if (functions.containsKey(node.name)) {
            throw new RuntimeException(node.position, String.format("The function \"%s\" has already been declared", node.name));
        }

        functions.put(node.name, node);

        return null;
    }

    @Override
    public Object goFunctionCall(FunctionCallNode node) throws RuntimeException {
        try {
            switch (node.name) {
                case "print":
                case "println": {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < node.args.size(); i++) {
                        builder.append(node.args.get(i).accept(this));

                        if (i + 1 < node.args.size())
                            builder.append("\t");
                    }

                    if (node.name.equals("print"))
                        System.out.print(builder);
                    else
                        System.out.println(builder);
                    return null;
                }

                default: {
                    FunctionNode function = this.findFunction(node.name);

                    if (node.args.size() > function.params.size()) {
                        throw new RuntimeException(node.position, String.format("Too many parameters -> %d", node.args.size() - function.params.size()));
                    } else if (node.args.size() < function.params.size()) {
                        throw new RuntimeException(node.position, String.format("Missing parameters -> %d", function.params.size() - node.args.size()));
                    }

                    FunctionEnvironment environment = new FunctionEnvironment();
                    environment.body = function.blockNode;
                    environment.from = scope;

                    for (int i = 0; i < function.params.size(); i++) {
                        environment.params.put(function.params.get(i), node.args.get(i).accept(this));
                    }

                    if (this.environment != null) {
                        environments.push(this.environment);
                    }

                    this.environment = environment;

                    Object r = this.environment.body.accept(this);
                    returnValue = null;

                    this.environment = (environments.size() > 0) ? environments.pop() : null;

                    return r;
                }
            }
        } catch (UnderfineException e) {
            throw new RuntimeException(node.position, e.getMessage());
        }
    }

    @Override
    public Object goReturn(ReturnNode node) throws RuntimeException {
        if (returnValue != null) {
            return returnValue;
        }

        return returnValue = node.left.accept(this);
    }

    @Override
    public Object goBreak(BreakNode node) throws RuntimeException {
        loopStatus = LoopStatus.BREAK;
        return null;
    }

    @Override
    public Object goContinue(ContinueNode node) throws RuntimeException {
        loopStatus = LoopStatus.CONTINUE;
        return null;
    }

    //////////////////////////////////////
    //////////////////////////////////////
    //////////////////////////////////////

    private BtScope createScope(BlockNode node) {
        BtScope btScope = new BtScope();
        btScope.position = node.position;
        btScope.node = node;
        btScope.from = this.scope;

        return btScope;
    }

    private FunctionNode findFunction(String name) throws UnderfineException {
        if (!functions.containsKey(name)) {
            throw new UnderfineException(UnderfineException.UnderfineType.FUNCTION, name);
        }

        return functions.get(name);
    }
}

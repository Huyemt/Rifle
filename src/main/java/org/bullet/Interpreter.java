package org.bullet;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.compiler.ast.nodes.*;
import org.bullet.compiler.lexer.Lexer;
import org.bullet.compiler.parser.Parser;
import org.bullet.exceptions.ParsingException;
import org.bullet.exceptions.RuntimeException;
import org.bullet.exceptions.UnderfineException;
import org.rifle.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * @author Huyemt
 */

public class Interpreter extends Visitor {

    private final ProgramNode programNode;
    private final HashMap<String, Object> variables;
    private final HashMap<String, FunctionNode> functions;
    private BlockNode nowBlock;


    public Interpreter(String source) throws ParsingException {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        programNode = parser.Parse();
        variables = new HashMap<>();
        functions = new HashMap<>();
        nowBlock = null;
    }

    public Interpreter(File file) throws ParsingException, IOException {
        this(Utils.readFile(file));
    }

    public Object eval() throws RuntimeException {
        return programNode.accept(this);
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

                    return ((BigDecimal) left).divide((BigDecimal) right, RoundingMode.FLOOR);
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

        throw new RuntimeException(node.left.position, String.format("Data type of operation is not supported:  \"%s\"", left.toString()));
    }

    @Override
    public Object goConstant(ConstantNode<?> node) throws RuntimeException {
        if (node.value instanceof BigDecimal) {
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
        if (nowBlock != null) {
            try {
                Object r = nowBlock.findVariable(node.name);
                System.out.println(r);
                return r;
            } catch (UnderfineException e) {
                // ignore
            }
        }

        if (variables.containsKey(node.name)) {
            return variables.get(node.name);
        }

        throw new RuntimeException(node.position, String.format("Undefined variable \"%s\"", node.name));
    }

    @Override
    public Object goAssign(AssignNode node) throws RuntimeException {
        if (node.left instanceof VariableNode) {
            VariableNode variable = (VariableNode) node.left;

            Object result = node.right.accept(this);

            if (node.createAction) {
                if (nowBlock != null) {
                    if (!nowBlock.existsVariable(variable.name)) {
                        nowBlock.variables.put(variable.name, result);
                        return result;
                    }
                } else {
                    if (!variables.containsKey(variable.name)) {
                        variables.put(variable.name, result);
                        return result;
                    }
                }
                throw new RuntimeException(node.position, String.format("The variable \"%s\" has already been declared", variable.name));
            } else {
                if (nowBlock != null) {
                    try {
                        return nowBlock.changeVariable(variable.name, result);
                    } catch (UnderfineException e) {
                        // ignore
                    }
                }

                if (variables.containsKey(variable.name)) {
                    variables.put(variable.name, result);
                    return result;
                }
            }

            throw new RuntimeException(node.position, String.format("Undefined variable \"%s\"", variable.name));
        }

        throw new RuntimeException(node.position, "Only variables can be assigned values");
    }

    @Override
    public Object goIf(IfNode node) throws RuntimeException {
        Object condition = node.condition.accept(this);

        if (condition instanceof Boolean && (Boolean) condition) {
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
        if (nowBlock != null) {
            node.lastBlock = nowBlock;
        }

        nowBlock = node;

        Object result = null;

        for (Node node1 : node.statements) {
            result = node1.accept(this);
        }

        return result;
    }

    @Override
    public Object goWhile(WhileNode node) throws RuntimeException {
        Boolean condition = (Boolean) node.condition.accept(this);
        Object result = null;

        while (condition) {
            if (node.body != null) {
                result = node.body.accept(this);
                System.out.println(result);
            }

            condition = (Boolean) node.condition.accept(this);
        }

        /*
        运行到这里说明已经跳出循环了
         */
        if (node.elseBody != null) {
            result = node.elseBody.accept(this);
        }

        return result;
    }

    @Override
    public Object goFor(ForNode node) throws RuntimeException {
        if (node.init != null) {
            node.init.accept(this);
        }

        Boolean condition = (Boolean) node.condition.accept(this);
        Object result = null;

        while (condition) {
            if (node.body != null) {
                result = node.body.accept(this);
            }

            if (node.increase != null) {
                node.increase.accept(this);
            }

            condition = (Boolean) node.condition.accept(this);
        }

        /*
        运行到这里说明已经跳出循环了
         */
        if (node.elseBody != null) {
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
}

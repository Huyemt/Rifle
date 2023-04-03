package org.bullet;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.compiler.ast.nodes.*;
import org.bullet.compiler.lexer.Lexer;
import org.bullet.compiler.parser.Parser;
import org.bullet.exceptions.ParsingException;
import org.bullet.exceptions.RuntimeException;
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


    public Interpreter(String source) throws ParsingException {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        programNode = parser.parse();
        variables = new HashMap<>();
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
                    return ((BigDecimal) left).add(((BigDecimal) right));
                case SUB:
                    return ((BigDecimal) left).subtract(((BigDecimal) right));
                case MUL:
                    return ((BigDecimal) left).multiply(((BigDecimal) right));
                case DIV:
                    if (((BigDecimal) right).intValue() == 0) {
                        throw new RuntimeException(node.position, "Cannot divide by zero");
                    }

                    return ((BigDecimal) left).divide(((BigDecimal) right), RoundingMode.FLOOR);
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
        return node.left.accept(this);
    }

    @Override
    public Object goVariable(VariableNode node) throws RuntimeException {
        if (variables.containsKey(node.name)) {
            return variables.get(node.name);
        }

        throw new RuntimeException(programNode.position, "Undefined variable");
    }

    @Override
    public Object goAssign(AssginNode node) throws RuntimeException {
        if (node.left instanceof VariableNode) {
            Object result = node.right.accept(this);
            variables.put(((VariableNode) node.left).name, result);
            return result;
        }

        throw new RuntimeException(node.position, "Only variables can be assigned values");
    }
}

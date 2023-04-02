package org.bullet.interpreter;

import org.bullet.compiler.ast.Visitor;
import org.bullet.compiler.ast.nodes.BinaryNode;
import org.bullet.compiler.ast.nodes.ConstantNode;
import org.bullet.compiler.ast.nodes.ProgramNode;
import org.bullet.compiler.ast.nodes.UnaryNode;
import org.bullet.compiler.lexer.Lexer;
import org.bullet.compiler.parser.Parser;
import org.bullet.exceptions.ParsingException;
import org.bullet.exceptions.RuntimeException;
import org.rifle.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Huyemt
 */

public class Interpreter extends Visitor {

    private final ProgramNode programNode;


    public Interpreter(String source) throws ParsingException {
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        programNode = parser.parse();
    }

    public Interpreter(File file) throws ParsingException, IOException {
        this(Utils.readFile(file));
    }

    public Result<?> eval() throws RuntimeException {
        return programNode.accept(this);
    }

    @Override
    public Result<?> goProgram(ProgramNode node) throws RuntimeException {
        return node.left.accept(this);
    }

    @Override
    public Result<?> goBinary(BinaryNode node) throws RuntimeException {
        Result<?> right = node.right.accept(this);
        Result<?> left = node.left.accept(this);

        if (left.value instanceof BigDecimal && right.value instanceof BigDecimal) {
            Result<BigDecimal> result = new Result<>();
            switch (node.operator) {
                case ADD:
                    result.value = ((BigDecimal) left.value).add(((BigDecimal) right.value));
                    break;
                case SUB:
                    result.value = ((BigDecimal) left.value).subtract(((BigDecimal) right.value));
                    break;
                case MUL:
                    result.value = ((BigDecimal) left.value).multiply(((BigDecimal) right.value));
                    break;
                case DIV:
                    if (((BigDecimal) right.value).intValue() == 0) {
                        throw new RuntimeException(node.right.position, "Cannot divide by zero");
                    }
                    result.value = ((BigDecimal) left.value).divide(((BigDecimal) right.value), RoundingMode.FLOOR);
                    break;
                default:
                    throw new RuntimeException(node.position, "Unsupported binary operator");
            }

            return result;
        }

        throw new RuntimeException(node.position, "Unsupported type operation");
    }

    @Override
    public Result<?> goUnary(UnaryNode node) throws RuntimeException {
        Result<?> left = node.left.accept(this);

        if (left.value instanceof BigDecimal) {
            Result<BigDecimal> result = new Result<>();

            switch (node.operator) {
                case PLUS:
                    result = (Result<BigDecimal>) left;
                case MINUS:
                    result.value = ((BigDecimal) left.value).negate();
                    break;
                default:
                    throw new RuntimeException(node.position, "Unsupported unary operator");
            }

            return result;
        }

        throw new RuntimeException(node.left.position, String.format("Data type of operation is not supported:  \"%s\"", left.value.toString()));
    }

    @Override
    public Result<?> goConstant(ConstantNode<?> node) throws RuntimeException {
        if (node.value instanceof BigDecimal) {
            Result<BigDecimal> result = new Result<>();
            result.value = (BigDecimal) node.value;
            return result;
        }

        throw new RuntimeException(node.position, String.format("Unsupported data type:  \"%s\"", node.value.toString()));
    }
}

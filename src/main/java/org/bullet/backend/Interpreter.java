package org.bullet.backend;

import org.bullet.frontend.Lexer;
import org.bullet.frontend.Parser;
import org.bullet.frontend.ast.IVisitor;
import org.bullet.frontend.ast.node.BinaryNode;
import org.bullet.frontend.ast.node.ConstantNode;
import org.bullet.frontend.ast.node.ProgramNode;
import org.bullet.frontend.ast.node.UnaryNode;
import org.bullet.frontend.results.Result;
import org.rifle.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Huyemt
 */

public class Interpreter extends IVisitor {
    protected final Lexer lexer;
    protected final Parser parser;
    protected final ProgramNode programAST;

    public Interpreter(String source) {
        lexer = new Lexer(source);
        parser = new Parser(lexer);
        programAST = parser.parse();
    }

    public Interpreter(File file) throws IOException {
        this(Utils.readFile(file));
    }

    public Result<?> evalIt() {
        return programAST.accept(this);
    }

    @Override
    public Result<?> goProgram(ProgramNode node) {
        return node.left.accept(this);
    }

    @Override
    public Result<?> goBinary(BinaryNode node) {
        Result<?> right = node.right.accept(this);
        Result<?> left = node.left.accept(this);

        if (right.value instanceof BigDecimal && left.value instanceof BigDecimal) {
            Result<BigDecimal> result = new Result<>();

            switch (node.operator) {
                case ADD:
                    result.value = ((BigDecimal) left.value).add((BigDecimal) right.value);
                    return result;
                case SUB:
                    result.value = ((BigDecimal) left.value).subtract((BigDecimal) right.value);
                    return result;
                case MUL:
                    result.value = ((BigDecimal) left.value).multiply((BigDecimal) right.value);
                    return result;
                case DIV:
                    if (((BigDecimal) right.value).intValueExact() == 0) {
                        // throws
                        return null;
                    }

                    result.value = ((BigDecimal) left.value).divide((BigDecimal) right.value);
                    return result;
                default:
                    return null;
            }
        }

        return null;
    }

    @Override
    public Result<?> goUnary(UnaryNode node) {
        Result<?> left = node.left.accept(this);
        if (left.value instanceof BigDecimal) {
            Result<BigDecimal> result = new Result<>();
            switch (node.operator) {
                case PLUS:
                    result.value = ((BigDecimal) left.value).multiply(new BigDecimal(1));
                    return result;
                case MINUS:
                    result.value = ((BigDecimal) left.value).multiply(new BigDecimal(-1));
                    return result;
                default:
                    return null;
            }
        }
        return null;
    }

    @Override
    public <T> Result<?> goConstant(ConstantNode<T> node) {
        if (node.value instanceof BigDecimal) {
            Result<BigDecimal> result = new Result<>();
            result.value = (BigDecimal) node.value;
            return result;
        }

        return null;
    }
}

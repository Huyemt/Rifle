package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;
import org.bullet.interpreter.Result;

/**
 * @author Huyemt
 */

public class UnaryNode extends Node {

    public enum Operator {
        PLUS,
        MINUS
    }

    public Operator operator;
    public Node left;

    public UnaryNode() {

    }

    @Override
    public Result<?> accept(Visitor visitor) throws RuntimeException {
        return visitor.goUnary(this);
    }
}

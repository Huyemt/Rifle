package org.bullet.frontend.ast.node;

import org.bullet.frontend.ast.INode;
import org.bullet.frontend.ast.IVisitor;
import org.bullet.frontend.results.Result;

/**
 * @author Huyemt
 */

public class UnaryNode extends INode {
    public enum Operator {
        PLUS,
        MINUS
    }

    public Operator operator;
    public INode left;

    public UnaryNode() {

    }

    @Override
    public Result<?> accept(IVisitor visitor) {
        return visitor.goUnary(this);
    }
}

package org.bullet.frontend.ast.node;

import org.bullet.frontend.ast.INode;
import org.bullet.frontend.ast.IVisitor;
import org.bullet.frontend.results.Result;

/**
 * @author Huyemt
 */

public class BinaryNode extends INode {
    public enum Operator {
        ADD,
        SUB,
        MUL,
        DIV
    }

    public Operator operator;
    public INode left;
    public INode right;

    public BinaryNode() {

    }

    @Override
    public Result<?> accept(IVisitor visitor) {
        return visitor.goBinary(this);
    }
}

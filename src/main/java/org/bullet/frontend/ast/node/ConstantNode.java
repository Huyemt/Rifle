package org.bullet.frontend.ast.node;

import org.bullet.frontend.ast.INode;
import org.bullet.frontend.ast.IVisitor;
import org.bullet.frontend.results.Result;

/**
 * @author Huyemt
 */

public class ConstantNode<T> extends INode {
    public T value;

    public ConstantNode() {

    }

    @Override
    public Result<?> accept(IVisitor visitor) {
        return visitor.goConstant(this);
    }
}

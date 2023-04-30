package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class ConstantNode extends Node {
    public Object value;
    public boolean complex;
    public IndexNode indexNode;

    public ConstantNode() {
        indexNode = null;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goConstant(this);
    }
}

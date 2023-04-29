package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class ConstantNode<T> extends Node {
    public T value;
    public boolean complex;
    public IndexNode indexNode;

    public ConstantNode() {
        indexNode = null;
    }

    public T getValue() {
        return value;
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goConstant(this);
    }
}

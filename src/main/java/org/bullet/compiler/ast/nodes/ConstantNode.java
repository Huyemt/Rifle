package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;
import org.bullet.interpreter.Result;

/**
 * @author Huyemt
 */

public class ConstantNode<T> extends Node {
    public T value;

    public ConstantNode() {

    }

    public T getValue() {
        return value;
    }

    @Override
    public Result<?> accept(Visitor visitor) throws RuntimeException {
        return visitor.goConstant(this);
    }
}

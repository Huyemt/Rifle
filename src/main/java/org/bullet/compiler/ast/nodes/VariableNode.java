package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class VariableNode extends Node {

    public String name;
    public IndexNode indexNode;

    public VariableNode() {
        indexNode = null;
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goVariable(this);
    }
}

package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class VariableNode extends Node {

    public String name;
    public final ArrayList<Node> index;

    public VariableNode() {
        index = new ArrayList<>();
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goVariable(this);
    }
}

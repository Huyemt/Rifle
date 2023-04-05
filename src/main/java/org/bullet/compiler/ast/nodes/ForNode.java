package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class ForNode extends Node {

    public Node init;
    public Node condition;
    public Node increase;
    public Node body;
    public Node elseBody;

    public ForNode() {

    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goFor(this);
    }
}

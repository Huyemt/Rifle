package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class WhileNode extends Node {

    public Node condition;
    public Node body;
    public Node elseBody;

    public WhileNode() {

    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goWhile(this);
    }
}

package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class BreakNode extends Node {

    public BreakNode() {

    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goBreak(this);
    }
}

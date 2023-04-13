package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class ContinueNode extends Node {

    public ContinueNode() {

    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goContinue(this);
    }
}

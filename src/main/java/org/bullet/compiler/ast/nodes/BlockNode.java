package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class BlockNode extends Node {

    public ArrayList<Node> statements;
    public int level;

    public BlockNode() {
        statements = new ArrayList<>();
        level = 0;
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goBlock(this);
    }
}

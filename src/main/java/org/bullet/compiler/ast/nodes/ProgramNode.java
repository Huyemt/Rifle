package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class ProgramNode extends Node {

    public ArrayList<Node> statements;

    public ProgramNode() {
        statements = new ArrayList<>();
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goProgram(this);
    }
}

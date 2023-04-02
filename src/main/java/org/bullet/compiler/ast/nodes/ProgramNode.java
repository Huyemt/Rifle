package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;
import org.bullet.interpreter.Result;

/**
 * @author Huyemt
 */

public class ProgramNode extends Node {

    public Node left;

    public ProgramNode() {

    }

    @Override
    public Result<?> accept(Visitor visitor) throws RuntimeException {
        return visitor.goProgram(this);
    }
}

package org.bullet.frontend.ast.node;

import org.bullet.frontend.ast.INode;
import org.bullet.frontend.ast.IVisitor;
import org.bullet.frontend.results.Result;

/**
 * @author Huyemt
 */

public class ProgramNode extends INode {
    public INode left;

    public ProgramNode() {

    }

    @Override
    public Result<?> accept(IVisitor visitor) {
        return visitor.goProgram(this);
    }
}

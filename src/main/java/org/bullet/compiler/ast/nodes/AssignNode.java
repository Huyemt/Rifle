package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class AssignNode extends Node {

    public Node left;
    public Node right;
    public boolean createAction;
    public boolean canChange;
    public boolean isProvide;
    public int complexLevel;

    public AssignNode() {
        complexLevel = 0;
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goAssign(this);
    }
}

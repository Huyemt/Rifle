package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class ComplexAssignNode extends Node {

    public BinaryNode.Operator operator;
    public VariableNode left;
    public Node right;

    public ComplexAssignNode() {

    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goComplexAssign(this);
    }
}

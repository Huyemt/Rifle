package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class FunctionCallNode extends Node {

    public String name;
    public ArrayList<Node> args;

    public FunctionCallNode() {
        name = "";
        args = new ArrayList<>();
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goFunctionCall(this);
    }
}

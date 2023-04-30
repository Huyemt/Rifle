package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BuiltInFunctionCallNode extends Node {

    public String name;
    public LinkedHashMap<String, Node> args;

    public BuiltInFunctionCallNode() {
        args = new LinkedHashMap<>();
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goBuiltInFunctionCall(this);
    }
}

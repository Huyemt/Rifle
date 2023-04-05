package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class FunctionNode extends Node {

    public String name;
    public ArrayList<String> params;
    public BlockNode blockNode;

    public FunctionNode() {
        params = new ArrayList<>();
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goFunction(this);
    }
}

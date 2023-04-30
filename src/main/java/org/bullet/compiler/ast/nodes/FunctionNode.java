package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class FunctionNode extends Node {
    public String name;
    public LinkedHashMap<String, Node> params;
    public BlockNode blockNode;

    public FunctionNode() {
        name = null;
        params = new LinkedHashMap<>();
        blockNode = null;
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return null;
    }
}

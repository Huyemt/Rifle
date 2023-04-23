package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class DictionaryNode extends Node {

    public LinkedHashMap<Node, Node> vector;

    public DictionaryNode() {
        vector = new LinkedHashMap<>();
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goDictionary(this);
    }

}

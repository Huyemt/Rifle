package org.bullet.compiler.ast.nodes;

import org.bullet.base.types.BtArray;
import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class ArrayNode extends Node {

    public final ArrayList<Node> values;

    public ArrayNode() {
        values = new ArrayList<>();
    }

    @Override
    public BtArray accept(Visitor visitor) throws RuntimeException {
        return visitor.goArray(this);
    }
}

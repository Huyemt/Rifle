package org.bullet.compiler.ast.nodes;

import org.bullet.base.types.BtList;
import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class ListNode extends Node {

    public final ArrayList<Node> values;
    public IndexNode indexNode;

    public ListNode() {
        values = new ArrayList<>();
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goList(this);
    }
}

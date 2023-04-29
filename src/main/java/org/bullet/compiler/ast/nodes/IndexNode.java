package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class IndexNode extends Node {
    public IndexNode next;
    public boolean complex;
    public Node start;
    public Node end;

    public IndexNode() {
        complex = false;
        next = null;
        start = null;
        end = null;
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return null;
    }

    public IndexNode clone() {
        IndexNode node = new IndexNode();
        node.next = next;
        node.complex = complex;
        node.start = start;
        node.end = end;

        return node;
    }
}

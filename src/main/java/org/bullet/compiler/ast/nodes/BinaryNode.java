package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;

/**
 * @author Huyemt
 */

public class BinaryNode extends Node {
    public enum Operator {
        ADD,
        SUB,
        MUL,
        DIV,
        POW,
        EQUAL,
        NOT_EQUAL,
        GREATER,
        GREATER_OR_EQUAL,
        LESSER,
        LESSER_OR_EQUAL,
        AND,
        OR
    }

    public Operator operator;
    public Node left;
    public Node right;

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goBinary(this);
    }
}

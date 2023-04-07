package org.bullet.compiler.ast.nodes;

import org.bullet.compiler.ast.Node;
import org.bullet.compiler.ast.Visitor;
import org.bullet.exceptions.RuntimeException;
import org.bullet.exceptions.UnderfineException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Huyemt
 */

public class BlockNode extends Node {

    public BlockNode from;
    public ArrayList<Node> statements;
    public HashMap<String, Object> variables;
    public int level;

    public BlockNode() {
        from = null;
        statements = new ArrayList<>();
        variables = new HashMap<>();
    }

    public boolean existsVariable(String name) {
        BlockNode block = this;

        while (block != null) {
            if (block.variables.containsKey(name)) {
                return true;
            }

            block = block.from;
        }

        return false;
    }

    public Object findVariable(String name) throws RuntimeException {
        BlockNode block = this;

        while (block != null) {
            if (block.variables.containsKey(name)) {
                return block.variables.get(name);
            }

            block = block.from;
        }

        throw new UnderfineException(position, UnderfineException.UnderfineType.VARIABLE, name);
    }

    public Object changeVariable(String name, Object value) throws UnderfineException {
        BlockNode block = this;

        while (block != null) {
            if (block.variables.containsKey(name)) {
                block.variables.put(name, value);
                return value;
            }

            block = block.from;
        }

        throw new UnderfineException(position, UnderfineException.UnderfineType.VARIABLE, name);
    }

    @Override
    public Object accept(Visitor visitor) throws RuntimeException {
        return visitor.goBlock(this);
    }
}

package org.bullet.base;

import org.bullet.compiler.ast.nodes.BlockNode;
import org.bullet.compiler.lexer.Position;
import org.bullet.exceptions.BulletException;
import org.bullet.exceptions.DefinedException;
import org.bullet.exceptions.UnderfineException;

import java.util.HashMap;

/**
 * @author Huyemt
 */

public class BtScope {
    public final HashMap<String, BtVariable> variables;
    public BlockNode node;
    public BtScope from;
    public Position position;

    public BtScope() {
        variables = new HashMap<>();
        node = null;
        from = null;
        position = null;
    }

    public boolean existsVariable(String name) {
        BtScope block = this;

        while (block != null) {
            if (block.variables.containsKey(name)) {
                return true;
            }

            block = block.from;
        }

        return false;
    }

    public BtVariable findVariable(String name) throws UnderfineException {
        BtScope block = this;

        while (block != null) {
            if (block.variables.containsKey(name)) {
                return block.variables.get(name);
            }

            block = block.from;
        }

        throw new UnderfineException(UnderfineException.UnderfineType.VARIABLE, name);
    }


    public BtVariable changeVariable(String name, Object value) throws BulletException {
        BtScope block = this;

        while (block != null) {
            if (block.variables.containsKey(name)) {
                BtVariable btVariable = block.variables.get(name);
                if (btVariable.canChange) {
                    btVariable.value = value;
                    return btVariable;
                }

                throw new BulletException(String.format("Cannot modify variable \"%s\" of immutable type", name));
            }

            block = block.from;
        }

        throw new UnderfineException(UnderfineException.UnderfineType.VARIABLE, name);
    }

    public BtVariable createVariable(String name, Object value, boolean canChange) throws DefinedException {
        if (this.existsVariable(name)) {
            throw new DefinedException(DefinedException.DerfinedType.VARIABLE, name);
        }

        BtVariable btVariable = new BtVariable();
        btVariable.name = name;
        btVariable.value = value;
        btVariable.canChange = canChange;

        variables.put(name, btVariable);

        return btVariable;
    }

    public BtVariable createVariable(String name, Object value) throws DefinedException {
        return this.createVariable(name, value, true);
    }
}

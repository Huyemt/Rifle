package org.bullet.interpreter;

import org.bullet.compiler.ast.nodes.BlockNode;
import org.bullet.compiler.lexer.Position;
import org.bullet.exceptions.BulletException;
import org.bullet.exceptions.DefinedException;
import org.bullet.exceptions.UnderfineException;

import java.util.HashMap;

/**
 * @author Huyemt
 */

public class Scope {
    public final HashMap<String, Variable> variables;
    public BlockNode node;
    public Scope from;
    public Position position;

    public Scope() {
        variables = new HashMap<>();
        node = null;
        from = null;
        position = null;
    }

    public boolean existsVariable(String name) {
        Scope block = this;

        while (block != null) {
            if (block.variables.containsKey(name)) {
                return true;
            }

            block = block.from;
        }

        return false;
    }

    public Variable findVariable(String name) throws UnderfineException {
        Scope block = this;

        while (block != null) {
            if (block.variables.containsKey(name)) {
                return block.variables.get(name);
            }

            block = block.from;
        }

        throw new UnderfineException(UnderfineException.UnderfineType.VARIABLE, name);
    }


    public Variable changeVariable(String name, Object value) throws BulletException {
        Scope block = this;

        while (block != null) {
            if (block.variables.containsKey(name)) {
                Variable variable = block.variables.get(name);
                if (variable.canChange) {
                    variable.value = value;
                    return variable;
                }

                throw new BulletException(String.format("Cannot modify variable \"%s\" of immutable type", name));
            }

            block = block.from;
        }

        throw new UnderfineException(UnderfineException.UnderfineType.VARIABLE, name);
    }

    public Variable createVariable(String name, Object value, boolean canChange) throws DefinedException {
        if (this.existsVariable(name)) {
            throw new DefinedException(DefinedException.DerfinedType.VARIABLE, name);
        }

        Variable variable = new Variable();
        variable.name = name;
        variable.value = value;
        variable.canChange = canChange;

        variables.put(name, variable);

        return variable;
    }

    public Variable createVariable(String name, Object value) throws DefinedException {
        return this.createVariable(name, value, true);
    }
}

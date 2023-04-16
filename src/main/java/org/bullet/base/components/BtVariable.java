package org.bullet.base.components;

import org.bullet.exceptions.BulletException;

/**
 * @author Huyemt
 */

public class BtVariable {
    public final String name;
    public boolean canChange;
    private Object value;

    public BtVariable(String name, Object value, boolean canChange) {
        this.name = name;
        this.value = value;
        this.canChange = canChange;
    }

    public final BtVariable setValue(Object value) throws BulletException {
        if (!canChange) {
            throw new BulletException(String.format("Cannot modify variable \"%s\" of immutable type", name));
        }

        if (this.value != value) {
            this.value = value;
        }

        return this;
    }

    public final Object getValue() {
        return value;
    }
}

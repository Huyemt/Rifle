package org.bullet.base.types;

/**
 * @author Huyemt
 */

public class BtNull extends BtType {
    public BtNull() {
        // ignore
    }

    @Override
    public final String toString() {
        return "null";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BtNull;
    }
}

package org.bullet.base.types;

import org.bullet.exceptions.BulletException;

/**
 * @author Huyemt
 */

public class BtByte extends BtType {
    private final byte value;

    public BtByte(byte value) {
        this.value = value;
    }

    public BtByte(String value) throws BulletException {
        if (value.length() != 4 || !value.startsWith("\\x")) {
            throw new BulletException(String.format("Error byte expression \"%s\"", value));
        }

        this.value = (byte) Integer.parseInt(value.substring(2), 16);
    }

    public final byte getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("\\x%02X", value);
    }
}

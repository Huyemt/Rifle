package org.bullet.vm.bytecode;

import org.bullet.base.types.BtByte;
import org.bullet.base.types.BtDictionary;
import org.bullet.base.types.BtList;
import org.bullet.base.types.BtNumber;

/**
 * @author Huyemt
 */

public enum BtcType {
    BYTE("byte", (byte) 0x01),
    NULL("null", (byte) 0x00),
    BOOL("bool", (byte) 0x02),
    NUMBER("number", (byte) 0x03),
    STRING("string", (byte) 0x04),
    LIST("list", (byte) 0x05),
    DICTIONARY("dictionary", (byte) 0x06),
    ;

    public static BtcType typeof(Object val) {
        if (val instanceof BtByte) return BYTE;
        if (val == null) return NULL;
        if (val instanceof Boolean) return BOOL;
        if (val instanceof BtNumber) return NUMBER;
        if (val instanceof String) return STRING;
        if (val instanceof BtList) return LIST;
        if (val instanceof BtDictionary) return DICTIONARY;

        return null;
    }

    public static BtcType typeof(byte code) {
        if (code == BYTE.getCode()) return BYTE;
        if (code == NULL.getCode()) return NULL;
        if (code == BOOL.getCode()) return BOOL;
        if (code == NUMBER.getCode()) return NUMBER;
        if (code == STRING.getCode()) return STRING;
        if (code == LIST.getCode()) return LIST;
        if (code == DICTIONARY.getCode()) return DICTIONARY;

        return null;
    }

    private final String name;
    private final byte code;

    BtcType(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }

    public byte getCode() {
        return code;
    }
}

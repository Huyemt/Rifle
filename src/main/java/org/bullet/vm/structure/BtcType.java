package org.bullet.vm.structure;

import org.bullet.base.types.*;

/**
 * @author Huyemt
 */

public enum BtcType {

    /**
     * 空
     */
    NULL("null"),

    /**
     * 布尔
     */
    BOOLEAN("boolean"),

    /**
     * 字节
     */
    BYTE("byte"),

    /**
     * 数字
     */
    NUMBER("number"),

    /**
     * 字符串
     */
    STRING("string"),

    /**
     * 列表
     */
    LIST("list"),

    /**
     * 字典
     */
    DICTIONARY("dictionary"),
    ;

    public final String typeName;

    BtcType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }

    public static BtcType typeOf(Object v) {
        if (v instanceof BtNull || v == null) return NULL;
        if (v instanceof Boolean) return BOOLEAN;
        if (v instanceof String) return STRING;
        if (v instanceof BtByte) return BYTE;
        if (v instanceof BtNumber) return NUMBER;
        if (v instanceof BtList) return LIST;
        if (v instanceof BtDictionary) return DICTIONARY;

        return null;
    }
}

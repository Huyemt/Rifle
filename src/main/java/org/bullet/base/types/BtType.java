package org.bullet.base.types;

import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.List;
import java.util.Map;

/**
 * @author Huyemt
 */

public class BtType {

    public static Object toBType(Object v) throws BulletException {
        if (v == null) {
            return BulletRuntime.BTNULL;
        }

        if (v instanceof Number) {
            if (v instanceof Byte) {
                return new BtByte((byte) v);
            }

            return new BtNumber(v.toString());
        }

        if (v.getClass().isArray()) {
            if (v.getClass().getName().equals("[B")) {
                return new BtByteString((byte[]) v);
            }

            if (v.getClass().getName().equals("[I")) {
                return BtList.parse((int[]) v);
            }

            if (v.getClass().getName().equals("[C")) {
                return BtList.parse((char[]) v);
            }

            if (v.getClass().getName().equals("[F")) {
                return BtList.parse((float[]) v);
            }

            if (v.getClass().getName().equals("[S")) {
                return BtList.parse((short[]) v);
            }

            if (v.getClass().getName().equals("[Z")) {
                return BtList.parse((boolean[]) v);
            }

            return BtList.parse((Object[]) v);
        }

        if (v instanceof Map) {
            return BtDictionary.parse((Map<?, ?>) v);
        }

        if (v instanceof List) {
            return BtList.parse(((List<?>) v).toArray());
        }

        return v;
    }

    public static Object toJType(Object v) {
        if (v instanceof BtList) {
            return ((BtList) v).toArray();
        }

        if (v instanceof BtDictionary) {
            return ((BtDictionary) v).toMap();
        }

        if (v instanceof BtNull) {
            return null;
        }

        if (v instanceof BtByte) {
            return ((BtByte) v).getValue();
        }

        if (v instanceof BtByteString) {
            return ((BtByteString) v).toByteArray();
        }

        return v;
    }
}

package org.bullet.base.types;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Huyemt
 */

public class BtArray {

    public static BtArray parse(Object[] obj) {
        BtArray array = new BtArray();

        for (Object v : obj) {
            if (v instanceof Double || v instanceof Integer || v instanceof Float) {
                array.vector.add(new BigDecimal(v.toString()));
                continue;
            }

            if (v.getClass().isArray()) {
                array.vector.add(parse((Object[]) v));
                continue;
            }

            if (v instanceof Map) {
                array.vector.add(BtDictionary.parse((Map<String, Object>) v));
                continue;
            }

            array.vector.add(v);
        }

        return array;
    }

    public ArrayList<Object> vector;

    public BtArray() {
        vector = new ArrayList<>();
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[");

        int i = 0;

        for (Object obj : vector) {
            if (obj instanceof String) {
                builder.append("\"").append(obj.toString().replace("\t", "\\t").replace("\r", "\\r").replace("\n", "\\n").replace("\"", "\\\"").replace("'", "\\'")).append("\"");
            } else {
                builder.append(obj);
            }

            if (i + 1 < vector.size()) {
                builder.append(",");
            }

            i++;
        }

        builder.append("]");

        return builder.toString();
    }

    public final Object[] toArray() {
        return translate(this);
    }

    private Object[] translate(BtArray array) {
        ArrayList<Object> list = new ArrayList<>();

        for (Object v : array.vector) {
            if (v instanceof BtArray) {
                list.add((translate((BtArray) v)));
                continue;
            }

            if (v instanceof BtDictionary) {
                list.add(((BtDictionary) v).toMap());
                continue;
            }

            list.add(v);
        }

        return list.toArray(Object[]::new);
    }
}

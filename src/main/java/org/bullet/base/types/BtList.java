package org.bullet.base.types;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Huyemt
 */

public class BtList extends BtType {
    public static BtList parse(Object[] obj) {
        BtList list = new BtList();

        for (Object v : obj) {
            list.add(v);
        }

        return list;
    }

    public static BtList parse(int[] obj) {
        BtList list = new BtList();

        for (int v : obj) {
            list.add(new BigDecimal(v));
        }

        return list;
    }

    public static BtList parse(float[] obj) {
        BtList list = new BtList();

        for (float v : obj) {
            list.add(new BigDecimal(v));
        }

        return list;
    }

    public static BtList parse(double[] obj) {
        BtList list = new BtList();

        for (double v : obj) {
            list.add(new BigDecimal(v));
        }

        return list;
    }

    public static BtList parse(byte[] obj) {
        BtList list = new BtList();

        for (byte v : obj) {
            list.add(new BigDecimal(v));
        }

        return list;
    }

    public static BtList parse(short[] obj) {
        BtList list = new BtList();

        for (short v : obj) {
            list.add(new BigDecimal(v));
        }

        return list;
    }

    public static BtList parse(char[] obj) {
        BtList list = new BtList();

        for (char v : obj) {
            list.add(String.valueOf(v));
        }

        return list;
    }

    public static BtList parse(boolean[] obj) {
        BtList list = new BtList();

        for (boolean v : obj) {
            list.add(v);
        }

        return list;
    }

    ///////////////////////////////////
    ///////////////////////////////////
    ///////////////////////////////////

    private ArrayList<Object> vector;

    public BtList() {
        vector = new ArrayList<>();
    }

    @Override
    public String toString() {
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

    public Object add(Object value) {
        vector.add(value = jTypeToBType(value));
        return value;
    }

    public Object set(int index, Object value) {
        return vector.set(index, jTypeToBType(value));
    }

    public void addAll(BtList list) {
        vector.addAll(list.vector);
    }

    public Object remove(Object value) {
        return vector.remove(value);
    }

    public Object get(int index) {
        return vector.get(index);
    }

    public int size() {
        return vector.size();
    }

    public ArrayList<Object> values() {
        return (ArrayList<Object>) vector.clone();
    }

    public void reverse() {
        Collections.reverse(vector);
    }

    public final Object[] toArray() {
        return translate(this);
    }

    private Object[] translate(BtList array) {
        ArrayList<Object> list = new ArrayList<>();

        for (Object v : array.vector) {
            list.add(bTypeToJType(v));
        }

        return list.toArray(Object[]::new);
    }

    public BtList clone() {
        BtList btList = new BtList();
        btList.vector = (ArrayList<Object>) vector.clone();
        return btList;
    }
}

package org.bullet.base.types;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class BtArray {
    private final ArrayList<Object> vector;

    public BtArray() {
        vector = new ArrayList<>();
    }

    public Object add(Object obj) {
        if (!vector.contains(obj)) {
            vector.add(obj);
        }

        return obj;
    }

    public Object remove(Object obj) {
        if (!vector.contains(obj)) {
            return null;
        }

        vector.remove(obj);
        return obj;
    }

    public Object get(int index) {
        if (vector.size() > index) {
            return vector.get(index);
        }

        return null;
    }
}

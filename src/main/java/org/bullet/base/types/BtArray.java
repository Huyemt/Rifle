package org.bullet.base.types;

import java.util.ArrayList;

/**
 * @author Huyemt
 */

public class BtArray {
    public ArrayList<Object> vector;

    public BtArray() {
        vector = new ArrayList<>();
    }

    @Override
    public final String toString() {
        return string(this).replace("\t", "\\t").replace("\r", "\\r").replace("\n", "\\n");
    }

    private String string(BtArray array) {
        StringBuilder builder = new StringBuilder();

        builder.append("[");

        int i = 0;

        for (Object obj : array.vector) {
            if (obj instanceof BtArray) {
                builder.append(string((BtArray) obj));
            } else if (obj instanceof String) {
                builder.append("\"").append(obj).append("\"");
            } else {
                builder.append(obj);
            }

            if (i + 1 < vector.size()) builder.append(",");
            i++;
        }

        builder.append("]");

        return builder.toString();
    }
}

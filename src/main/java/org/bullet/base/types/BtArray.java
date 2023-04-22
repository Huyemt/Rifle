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
}

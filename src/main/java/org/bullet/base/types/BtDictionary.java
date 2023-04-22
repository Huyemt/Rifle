package org.bullet.base.types;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Huyemt
 */

public class BtDictionary {
    public LinkedHashMap<String, Object> vector;

    public BtDictionary() {
        vector = new LinkedHashMap<>();
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");

        int i = 0;
        Map.Entry<String, Object> entry;

        for (Map.Entry<String, Object> stringObjectEntry : vector.entrySet()) {
            entry = stringObjectEntry;
            builder.append("\"").append(entry.getKey().replace("\t", "\\t").replace("\r", "\\r").replace("\n", "\\n").replace("\"", "\\\"").replace("'", "\\'")).append("\":");
            if (entry.getValue() instanceof String) {
                builder.append("\"").append(entry.getValue().toString().replace("\t", "\\t").replace("\r", "\\r").replace("\n", "\\n").replace("\"", "\\\"").replace("'", "\\'")).append("\"");
            } else {
                builder.append(entry.getValue());
            }

            if (i + 1 < vector.size()) {
                builder.append(",");
            }

            i++;
        }

        builder.append("}");

        return builder.toString();
    }
}

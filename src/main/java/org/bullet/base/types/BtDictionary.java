package org.bullet.base.types;

import java.math.BigDecimal;
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

    public static BtDictionary parse(Map map) {
        BtDictionary dictionary = new BtDictionary();

        for (Object key : map.keySet()) {
            Object v = map.get(key);
            if (v instanceof Double || v instanceof Integer || v instanceof Float) {
                dictionary.vector.put(key.toString(), new BigDecimal(v.toString()));
                continue;
            }

            if (v.getClass().isArray()) {
                dictionary.vector.put(key.toString(), BtArray.parse((Object[]) v));
                continue;
            }

            dictionary.vector.put(key.toString(), v);
        }

        return dictionary;
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

    public final Map<String, Object> toMap() {
        return translate(this);
    }

    private Map<String, Object> translate(BtDictionary dictionary) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : dictionary.vector.entrySet()) {
            if (entry.getValue() instanceof BtDictionary) {
                map.put(entry.getKey(), translate((BtDictionary) entry.getValue()));
                continue;
            }

            if (entry.getValue() instanceof BtArray) {
                map.put(entry.getKey(), ((BtArray) entry.getValue()).toArray());
                continue;
            }

            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    public BtDictionary clone() {
        BtDictionary dictionary = new BtDictionary();
        dictionary.vector = (LinkedHashMap<String, Object>) vector.clone();
        return dictionary;
    }
}

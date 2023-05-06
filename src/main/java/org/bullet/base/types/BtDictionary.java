package org.bullet.base.types;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Huyemt
 */

public class BtDictionary extends BtType {
    public static BtDictionary parse(Map<?, ?> map) {
        BtDictionary dictionary = new BtDictionary();

        for (Object key : map.keySet()) {
            dictionary.add(key.toString(), map.get(key));
        }

        return dictionary;
    }

    ///////////////////////////////////
    ///////////////////////////////////
    ///////////////////////////////////

    private LinkedHashMap<String, Object> vector;

    public BtDictionary() {
        vector = new LinkedHashMap<>();
    }

    @Override
    public String toString() {
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
                builder.append(entry.getValue().toString());
            }

            if (i + 1 < vector.size()) {
                builder.append(",");
            }

            i++;
        }

        builder.append("}");

        return builder.toString();
    }

    public Object add(String key, Object value) {
        vector.put(key, value = jTypeToBType(value));
        return value;
    }

    public Object remove(String key) {
        return vector.remove(key);
    }

    public Object get(String key) {
        return vector.get(key);
    }

    public boolean containsKey(String key) {
        return vector.containsKey(key);
    }

    public Set<String> keys() {
        return vector.keySet();
    }

    public Collection<Object> values() {
        return vector.values();
    }

    public Set<Map.Entry<String, Object>> entrys() {
        return vector.entrySet();
    }

    public int size() {
        return vector.size();
    }

    public final Map<String, Object> toMap() {
        return translate(this);
    }

    private Map<String, Object> translate(BtDictionary dictionary) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : dictionary.vector.entrySet()) {
            map.put(entry.getKey(), bTypeToJType(entry.getValue()));
        }

        return map;
    }

    public BtDictionary clone() {
        BtDictionary dictionary = new BtDictionary();
        dictionary.vector = (LinkedHashMap<String, Object>) vector.clone();
        return dictionary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof BtDictionary) {
            BtDictionary dictionary = (BtDictionary) o;

            if (size() != dictionary.size()) return false;

            String[] lkeys = keys().toArray(String[]::new);
            String[] rkeys = dictionary.keys().toArray(String[]::new);

            boolean flag = true;
            String lkey;
            String rkey;
            Object lv;
            Object rv;

            for (int i = 0; i < lkeys.length; i++) {
                if (!flag) break;

                lkey = lkeys[i];
                rkey = rkeys[i];

                if (!lkey.equals(rkey)) return false;

                lv = get(lkey);
                rv = dictionary.get(rkey);

                if (lv == rv) continue;

                flag = lv.equals(rv);
            }

            return flag;
        }

        return false;
    }
}

package org.rifle.library.Http4J.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Huyemt
 */

public class Resource {
    protected Map<String, Object> sources;

    public Resource() {
        sources = new HashMap<>();
    }

    public Resource add(String name, Object value) {
        sources.put(name, value == null ? "" : String.valueOf(value));
        return this;
    }

    public Resource remove(String name) {
        sources.remove(name);
        return this;
    }

    public Resource set(String name, String value) {
        if (sources.containsKey(name))
            sources.put(name, value == null ? "" : String.valueOf(value));

        return this;
    }

    public Resource defaultValue(String name, Object value) {
        if (!sources.containsKey(name))
            sources.put(name, value == null ? "" : String.valueOf(value));

        return this;
    }

    public Resource setAll(HashMap<String, Object> sources) {
        this.sources = sources;
        return this;
    }

    public boolean contains(String name) {
        return sources.containsKey(name);
    }

    public String get(String name) {
        Object r = sources.getOrDefault(name, null);
        return r == null ? null : String.valueOf(r);
    }

    public int size() {
        return sources.size();
    }
}

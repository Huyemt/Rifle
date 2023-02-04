package org.huyemt.http4j.resource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Huyemt
 */

public class Resource extends HttpAttribute {
    protected Map<String, Object> sources;

    public Resource() {
        sources = new LinkedHashMap<>();
    }

    public Resource add(String name, Object value) {
        this.sources.put(name, value == null ? "" : String.valueOf(value));
        return this;
    }

    public Resource remove(String name) {
        this.sources.remove(name);
        return this;
    }

    public Resource set(String name, Object value) {
        if (this.sources.containsKey(name)) {
            this.sources.put(name, value == null ? "" : String.valueOf(value));
        }

        return this;
    }

    public Resource defaultValue(String name, Object value) {
        if (!this.sources.containsKey(name)) {
            this.sources.put(name, value == null ? "" : String.valueOf(value));
        }

        return this;
    }

    public boolean contains(String name) {
        return this.sources.containsKey(name);
    }

    public String get(String name) {
        Object r = this.sources.getOrDefault(name, null);
        return r == null ? null : String.valueOf(r);
    }

    public int size() {
        return this.sources.size();
    }
}

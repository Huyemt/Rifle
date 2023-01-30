package org.huyemt.http4j.resource;

import java.util.List;
import java.util.Map;

/**
 * @author Huyemt
 */

public class Headers extends Resource {
    public Headers() {
        super();
    }

    public Headers(Map<String, List<String>> headers) {
        super();

        if (headers != null && headers.size() != 0) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                StringBuilder builder = new StringBuilder();

                int i = 0;
                for (String value : entry.getValue()) {
                    builder.append(value);
                    if (i + 1 < entry.getValue().size()) {
                        builder.append(";");
                    }
                }

                add(entry.getKey(), builder.toString());
            }
        }
    }

    @Override
    public Headers add(String name, Object value) {
        super.add(name.toLowerCase(), value);
        return this;
    }

    @Override
    public Headers remove(String name) {
        super.remove(name.toLowerCase());
        return this;
    }

    @Override
    public Headers set(String name, Object value) {
        super.set(name.toLowerCase(), value);
        return this;
    }

    @Override
    public String get(String name) {
        return super.get(name.toLowerCase());
    }

    @Override
    public Headers defaultValue(String name, Object value) {
        super.defaultValue(name.toLowerCase(), value);
        return this;
    }

    @Override
    public boolean contains(String name) {
        return super.contains(name.toLowerCase());
    }

    public Map<String, Object> getHeaders() {
        return this.sources;
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, Object> entry : sources.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue() == null ? "" : String.valueOf(entry.getValue()));
            builder.append("\n");
        }

        return builder.toString();
    }
}
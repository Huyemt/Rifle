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

    public Headers add(String name, Object value) {
        super.add(name.toLowerCase(), value);
        return this;
    }

    public Headers remove(String name) {
        super.remove(name.toLowerCase());
        return this;
    }

    public Headers set(String name, Object value) {
        super.set(name.toLowerCase(), value);
        return this;
    }

    public Headers defaultValue(String name, Object value) {
        super.defaultValue(name.toLowerCase(), value);
        return this;
    }

    public Map<String, Object> getHeaders() {
        return this.sources;
    }

    public final String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 0;

        for (Map.Entry<String, Object> entry : sources.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue() == null ? "" : String.valueOf(entry.getValue()));

            if (i + 1 < this.sources.size()) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }
}

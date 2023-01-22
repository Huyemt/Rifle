package org.rifle.library.Http4J.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求的标头载体
 *
 * Header container for network request
 *
 * @author Huyemt
 */

public class Headers extends Resource {

    @Override
    public Headers add(String name, Object value) {
        super.add(name, value);
        return this;
    }

    @Override
    public Headers remove(String name) {
        super.remove(name);
        return this;
    }

    @Override
    public Headers set(String name, String value) {
        super.set(name, value);
        return this;
    }

    @Override
    public Headers defaultValue(String name, Object value) {
        super.defaultValue(name, value);
        return this;
    }

    @Override
    public Headers setAll(HashMap<String, Object> headers) {
        super.setAll(headers);
        return this;
    }

    public Map<String, Object> getHeaders() {
        return sources;
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (Map.Entry<String, Object> header : sources.entrySet()) {
            builder.append(header.getKey()).append(": ").append(header.getValue() == null ? "" : String.valueOf(header.getValue()));
            if ((i + 1) < sources.size())
                builder.append("\n");
            i++;
        }

        return builder.toString();
    }
}

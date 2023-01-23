package org.rifle.library.Http4J.resource;

import java.util.List;
import java.util.Map;

/**
 * 网络请求的标头载体
 *
 * Header container for network request
 *
 * @author Huyemt
 */

public class Headers extends Resource {

    public Headers() {
        super();
    }

    public Headers(Map<String, List<String>> headers) {
        super();

        if (headers == null || headers.size() == 0)
            return;

        StringBuilder builder;
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            if (entry.getKey() == null)
                continue;

            builder = new StringBuilder();

            int i = 0;
            for (String s : entry.getValue()) {
                builder.append(s);
                if ((i + 1) < entry.getValue().size())
                    builder.append(";");
                i++;
            }

            add(entry.getKey(), builder.toString());
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
    public Headers defaultValue(String name, Object value) {
        super.defaultValue(name.toLowerCase(), value);
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
            builder.append(header.getKey()).append("=").append(header.getValue() == null ? "" : String.valueOf(header.getValue()));
            if ((i + 1) < sources.size())
                builder.append("\n");
            i++;
        }

        return builder.toString();
    }
}

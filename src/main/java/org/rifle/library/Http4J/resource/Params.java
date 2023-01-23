package org.rifle.library.Http4J.resource;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * URL参数载体
 *
 * URL data container
 *
 * @author Huyemt
 */

public class Params extends Resource {

    @Override
    public Params add(String name, Object value) {
        super.add(name, value);
        return this;
    }

    @Override
    public Params remove(String name) {
        super.remove(name);
        return this;
    }

    @Override
    public Params set(String name, Object value) {
        super.set(name, value);
        return this;
    }

    @Override
    public Params defaultValue(String name, Object value) {
        super.defaultValue(name, value);
        return this;
    }

    public Map<String, Object> getParams() {
        return sources;
    }

    public final String toString(boolean encode) {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (Map.Entry<String, Object> entry : sources.entrySet()) {
            if (encode)
                builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(entry.getValue() == null ? "" : String.valueOf(entry.getValue()), StandardCharsets.UTF_8));
            else
                builder.append(entry.getKey()).append("=").append(entry.getValue() == null ? "" : String.valueOf(entry.getValue()));

            if ((i + 1) < sources.size())
                builder.append("&");
            i++;
        }

        return builder.toString();
    }

    @Override
    public final String toString() {
        return toString(false);
    }
}

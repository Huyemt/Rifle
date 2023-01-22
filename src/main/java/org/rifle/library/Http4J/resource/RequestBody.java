package org.rifle.library.Http4J.resource;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * POST数据载体
 *
 * POST data container
 *
 * @author Huyemt
 */

public class RequestBody extends Resource {
    @Override
    public RequestBody add(String name, Object value) {
        super.add(name, value);
        return this;
    }

    @Override
    public RequestBody remove(String name) {
        super.remove(name);
        return this;
    }

    @Override
    public RequestBody set(String name, String value) {
        super.set(name, value);
        return this;
    }

    @Override
    public RequestBody defaultValue(String name, Object value) {
        super.defaultValue(name, value);
        return this;
    }

    @Override
    public RequestBody setAll(HashMap<String, Object> body) {
        super.setAll(body);
        return this;
    }

    public Map<String, Object> getRequestBody() {
        return sources;
    }

    public final String toString(Charset charset) {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (Map.Entry<String, Object> entry : sources.entrySet()) {
            if (charset != null)
                builder.append(URLEncoder.encode(entry.getKey(), charset)).append("=").append(URLEncoder.encode(entry.getValue() == null ? "" : String.valueOf(entry.getValue()), charset));
            else
                builder.append(entry.getKey()).append("=").append(entry.getValue() == null ? "" : String.valueOf(entry.getValue()));

            if ((i + 1) < sources.size())
                builder.append("&");
            i++;
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return toString(null);
    }
}

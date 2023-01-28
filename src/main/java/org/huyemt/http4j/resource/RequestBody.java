package org.huyemt.http4j.resource;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author Huyemt
 */

public class RequestBody extends Resource {
    public String json;

    public RequestBody() {
        super();
        json = null;
    }

    public RequestBody add(String name, Object value) {
        super.add(name, value);
        return this;
    }

    public RequestBody remove(String name) {
        super.remove(name);
        return this;
    }

    public RequestBody set(String name, Object value) {
        super.set(name, value);
        return this;
    }

    public RequestBody defaultValue(String name, Object value) {
        super.defaultValue(name, value);
        return this;
    }

    public RequestBody setJson(String json) {
        this.json = json;
        return this;
    }

    public Map<String, Object> getRequestBody() {
        return this.sources;
    }

    public final String toString(Charset charset) {
        StringBuilder builder = new StringBuilder();
        int i = 0;

        if (json == null) {

            for (Map.Entry<String, Object> entry : sources.entrySet()) {
                if (charset != null) {
                    builder.append(URLEncoder.encode(entry.getKey(), charset)).append("=").append(entry.getValue() == null ? "" : URLEncoder.encode(String.valueOf(entry.getValue()), charset));
                } else {
                    builder.append(entry.getKey()).append("=").append(entry.getValue() == null ? "" : String.valueOf(entry.getValue()));
                }

                if ((i + 1) < sources.size()) {
                    builder.append("&");
                }

                i++;
            }
        } else builder.append(json);
        return builder.toString();
    }



    public String toString() {
        return this.toString(null);
    }
}

package org.rifle.library.Http4J;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络请求的响应体 (暂时未封装Cookie，请等待后续支持)
 *
 * Response body of network request (Cookie are not encapsulated for the time being, please wait for subsequent support)
 *
 * @author Huyemt
 */

public class HttpResponse {
    protected final Map<String, List<String>> headers;
    protected final byte[] content;

    public HttpResponse(byte[] content, Map<String, List<String>> headers) {
        this.content = content;

        HashMap<String, List<String>> map = new HashMap<>(headers);
        map.remove(null);
        this.headers = map;
    }

    public final byte[] getContent() {
        return content;
    }

    public final String getHtml() {
        return new String(content, StandardCharsets.UTF_8);
    }

    public final Map<String, List<String>> getHeaders() {
        return headers;
    }
}

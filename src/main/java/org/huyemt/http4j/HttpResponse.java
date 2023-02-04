package org.huyemt.http4j;

import com.google.gson.Gson;
import org.huyemt.http4j.resource.Cookies;
import org.huyemt.http4j.resource.Headers;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Huyemt
 */

public class HttpResponse {
    public final Headers headers;
    public final byte[] content;
    public final Cookies cookies;
    public final String html;
    public final int status_code;
    public final URL url;

    public HttpResponse(int status_code, URL url, byte[] content, Map<String, List<String>> headers) {
        this.status_code = status_code;
        this.url = url;
        this.content = content;
        this.html = new String(content, StandardCharsets.UTF_8);
        Map<String, List<String>> map = new LinkedHashMap<>(headers);
        map.remove(null);
        this.cookies = new Cookies(map.getOrDefault("Set-Cookie", null));
        this.headers = new Headers(map);
    }

    public <T extends Class<?>> T json(T clazz) {
        return new Gson().fromJson(getHtml(), clazz);
    }

    public final int getStatusCode() {
        return this.status_code;
    }

    public final byte[] getContent() {
        return this.content;
    }

    public final String getHtml() {
        return html;
    }

    public final Cookies getCookies() {
        return cookies;
    }

    public final Headers getHeaders() {
        return headers;
    }

    public final URL getUrl() {
        return url;
    }

    @Override
    public final String toString() {
        return headers + "\n" + cookies;
    }
}

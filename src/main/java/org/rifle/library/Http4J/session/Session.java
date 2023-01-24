package org.rifle.library.Http4J.session;

import org.rifle.library.Http4J.HttpRequest;
import org.rifle.library.Http4J.HttpResponse;
import org.rifle.library.Http4J.resource.*;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;

/**
 * 会话保持
 *
 * Session maintenance
 *
 * @author Huyemt
 */

public class Session {
    public Headers headers;
    public Cookies cookies;
    public HttpConfig config;

    public Session() {
        headers = new Headers();
        // init headers
        headers.add("User-Agent", "Http4J/1.0");
        headers.add("Accept-Encoding", "gzip, deflate");
        headers.add("Accept", "*/*");
        headers.add("Connection", "keep-alive");

        cookies = new Cookies();
        config = new HttpConfig()
                .allowsRedirect(true);
    }

    public HttpResponse send(String url, Method method, Headers headers, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        HttpRequest request = new HttpRequest(url);
        HttpResponse response = request.send(method, headers == null ? this.headers : headers, params, requestBody, cookies == null ? this.cookies : cookies, config == null ? this.config : config);

        Map<String, HttpCookie> cookieMap = response.cookies.getCookieMap();
        for (HttpCookie cookie : cookieMap.values()) {
            // 将常规格式的SessionID记录下来
            // Record the SessionID in regular format.
            if (cookie.getName().toLowerCase().contains("sessionid") || cookie.getName().toLowerCase().contains("session_id") || cookie.getName().toLowerCase().contains("session")) {
                if (cookie.hasExpired() && this.cookies.contains(cookie.getName())) {
                    this.cookies.remove(cookie.getName());
                    continue;
                }

                if (!this.cookies.contains(cookie.getName()) && !cookie.hasExpired()) {
                    this.cookies.add(cookie);
                    continue;
                }

                if (this.cookies.contains(cookie.getName()) && !cookie.getValue().equals(this.cookies.get(cookie.getName()).getValue()))
                    this.cookies.set(cookie.getName(), cookie.getValue());
            }
        }
        return response;
    }

    public HttpResponse get(String url) throws IOException {
        return send(url, Method.GET, headers, new Params(), null, cookies, config);
    }

    public HttpResponse get(String url, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, new Params(), null, cookies, config);
    }

    /////////////////////////////////////

    public HttpResponse get(String url, Headers headers) throws IOException {
        return send(url, Method.GET, headers, new Params(), null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, new Params(), null, cookies, config);
    }

    public HttpResponse get(String url, Params params) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Params params, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Cookies cookies) throws IOException {
        return send(url, Method.GET, headers, new Params(), null, cookies, config);
    }

    public HttpResponse get(String url, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, new Params(), null, cookies, config);
    }

    /////////////////////////////////////

    public HttpResponse get(String url, Headers headers, Params params) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Params params, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Cookies cookies) throws IOException {
        return send(url, Method.GET, headers, new Params(), null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, new Params(), null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Headers headers) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Cookies cookies) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    /////////////////////////////////////

    public HttpResponse get(String url, Headers headers, Params params, Cookies cookies) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Cookies cookies, Params params) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Cookies cookies, Headers headers, Params params) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Cookies cookies, Headers headers, Params params, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Cookies cookies, Params params, Headers headers) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Cookies cookies, Params params, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Cookies cookies, Headers headers) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Headers headers, Cookies cookies) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public HttpResponse post(String url) throws IOException {
        return send(url, Method.POST, headers, new Params(), null, cookies, config);
    }

    public HttpResponse post(String url, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), null, cookies, config);
    }

    /////////////////////////////////////

    public HttpResponse post(String url, Headers headers) throws IOException {
        return send(url, Method.POST, headers, new Params(), null, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), null, cookies, config);
    }

    public HttpResponse post(String url, Params params) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, new Params(), null, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), null, cookies, config);
    }

    /////////////////////////////////////

    public HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, Params params) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, Params params) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    /////////////////////////////////////

    public HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, Cookies cookies, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, Params params, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, Cookies cookies, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, Headers headers, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, Cookies cookies) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, Params params, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, Headers headers, RequestBody requestBody) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers, Params params) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers, Params params, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params, Headers headers) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params, Headers headers, HttpConfig config) throws IOException {
        return send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

}

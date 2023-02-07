package org.huyemt.http4j.session;

import org.huyemt.http4j.HttpRequest;
import org.huyemt.http4j.HttpResponse;
import org.huyemt.http4j.resource.*;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;

/**
 * Session会话保持
 *
 * Session session persistence
 *
 * @author Huyemt
 */

public class Session {
    public Headers headers = new Headers();
    public Cookies cookies;
    public HttpConfig config;

    public Session() {
        this.headers.add("User-Agent", "Http4J/1.0");
        this.headers.add("Accept-Encoding", "gzip, deflate");
        this.headers.add("Accept", "*/*");
        this.headers.add("Connection", "keep-alive");
        this.cookies = new Cookies();
        this.config = (new HttpConfig()).allowsRedirect(true);
    }

    public HttpResponse send(String url, Method method, Headers headers, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        if (headers == null) {
            headers = this.headers;
        } else {
            for (Map.Entry<String, Object> entry : this.headers.getHeaders().entrySet()) {
                if (headers.contains(entry.getKey())) {
                    continue;
                }

                headers.add(entry.getKey(), entry.getValue());
            }
        }

        if (cookies == null) {
            cookies = this.cookies;
        } else {
            for (Map.Entry<String, HttpCookie> entry : this.cookies.getCookieMap().entrySet()) {
                if (cookies.contains(entry.getKey()))
                    continue;

                cookies.add(entry.getValue());
            }
        }

        if (config == null) {
            config = this.config;
        }

        HttpRequest request = new HttpRequest(url);
        HttpResponse response = request.send(method, headers, params, requestBody, cookies, config);
        Map<String, HttpCookie> cookieMap = response.cookies.getCookieMap();

        for (HttpCookie cookie : cookieMap.values()) {
            if (!cookie.getName().toLowerCase().contains("sessionid") && !cookie.getName().toLowerCase().contains("session_id") && !cookie.getName().toLowerCase().contains("session")) {
                continue;
            }

            if (cookie.hasExpired() && this.cookies.contains(cookie.getName())) {
                this.cookies.remove(cookie.getName());
                continue;
            }

            if (!this.cookies.contains(cookie.getName()) && !cookie.hasExpired()) {
                this.cookies.add(cookie);
                continue;
            }

            this.cookies.set(cookie.getName(), cookie.getValue());
        }

        return response;
    }

    public HttpResponse get(String url, HttpAttribute ... attributes) throws IOException {
        Headers headers = this.headers;
        Params params = null;
        Cookies cookies = this.cookies;
        HttpConfig config = this.config;

        for (HttpAttribute attribute : attributes) {
            if (attribute instanceof Headers) {
                headers = headers == null ? (Headers) attribute : headers;
            } else if (attribute instanceof Params) {
                params = (Params) attribute;
            }else if (attribute instanceof Cookies) {
                cookies = cookies == null ? (Cookies) attribute : cookies;
            } else if (attribute instanceof HttpConfig) {
                config = config == null ? (HttpConfig) attribute : config;
            }
        }

        return send(url, Method.GET, headers, params, null, cookies, config);
    }

    public HttpResponse post(String url, HttpAttribute ... attributes) throws IOException {
        Headers headers = this.headers;
        Params params = null;
        RequestBody requestBody = null;
        Cookies cookies = this.cookies;
        HttpConfig config = this.config;

        for (HttpAttribute attribute : attributes) {
            if (attribute instanceof Headers) {
                headers = headers == null ? (Headers) attribute : headers;
            } else if (attribute instanceof Params) {
                params = (Params) attribute;
            } else if (attribute instanceof RequestBody) {
                requestBody = requestBody == null ? (RequestBody) attribute : requestBody;
            }else if (attribute instanceof Cookies) {
                cookies = cookies == null ? (Cookies) attribute : cookies;
            } else if (attribute instanceof HttpConfig) {
                config = config == null ? (HttpConfig) attribute : config;
            }
        }

        return send(url, Method.POST, headers, params, null, cookies, config);
    }

    public HttpResponse put(String url, HttpAttribute ... attributes) throws IOException {
        Headers headers = this.headers;
        Params params = null;
        RequestBody requestBody = null;
        Cookies cookies = this.cookies;
        HttpConfig config = this.config;

        for (HttpAttribute attribute : attributes) {
            if (attribute instanceof Headers) {
                headers = headers == null ? (Headers) attribute : headers;
            } else if (attribute instanceof Params) {
                params = (Params) attribute;
            } else if (attribute instanceof RequestBody) {
                requestBody = requestBody == null ? (RequestBody) attribute : requestBody;
            }else if (attribute instanceof Cookies) {
                cookies = cookies == null ? (Cookies) attribute : cookies;
            } else if (attribute instanceof HttpConfig) {
                config = config == null ? (HttpConfig) attribute : config;
            }
        }

        return send(url, Method.PUT, headers, params, null, cookies, config);
    }
}
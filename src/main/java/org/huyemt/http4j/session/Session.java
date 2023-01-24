package org.huyemt.http4j.session;

import org.huyemt.http4j.HttpRequest;
import org.huyemt.http4j.HttpResponse;
import org.huyemt.http4j.resource.*;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;

/**
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
        HttpRequest request = new HttpRequest(url);
        HttpResponse response = request.send(method, headers == null ? this.headers : headers, params, requestBody, cookies == null ? this.cookies : cookies, config == null ? this.config : config);
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

    public HttpResponse get(String url) throws IOException {
        return this.send(url, Method.GET, this.headers, new Params(), (RequestBody)null, this.cookies, this.config);
    }

    public HttpResponse get(String url, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, this.headers, new Params(), (RequestBody)null, this.cookies, config);
    }

    public HttpResponse get(String url, Headers headers) throws IOException {
        return this.send(url, Method.GET, headers, new Params(), (RequestBody)null, this.cookies, this.config);
    }

    public HttpResponse get(String url, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, new Params(), (RequestBody)null, this.cookies, config);
    }

    public HttpResponse get(String url, Params params) throws IOException {
        return this.send(url, Method.GET, this.headers, params, (RequestBody)null, this.cookies, this.config);
    }

    public HttpResponse get(String url, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, this.headers, params, (RequestBody)null, this.cookies, config);
    }

    public HttpResponse get(String url, Cookies cookies) throws IOException {
        return this.send(url, Method.GET, this.headers, new Params(), (RequestBody)null, cookies, this.config);
    }

    public HttpResponse get(String url, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, this.headers, new Params(), (RequestBody)null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Params params) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, this.cookies, this.config);
    }

    public HttpResponse get(String url, Headers headers, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, this.cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Cookies cookies) throws IOException {
        return this.send(url, Method.GET, headers, new Params(), (RequestBody)null, cookies, this.config);
    }

    public HttpResponse get(String url, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, new Params(), (RequestBody)null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Headers headers) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, this.cookies, this.config);
    }

    public HttpResponse get(String url, Params params, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, this.cookies, config);
    }

    public HttpResponse get(String url, Params params, Cookies cookies) throws IOException {
        return this.send(url, Method.GET, this.headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse get(String url, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, this.headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Params params, Cookies cookies) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse get(String url, Headers headers, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse get(String url, Headers headers, Cookies cookies, Params params) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse get(String url, Headers headers, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse get(String url, Cookies cookies, Headers headers, Params params) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse get(String url, Cookies cookies, Headers headers, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse get(String url, Cookies cookies, Params params, Headers headers) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse get(String url, Cookies cookies, Params params, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Cookies cookies, Headers headers) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse get(String url, Params params, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse get(String url, Params params, Headers headers, Cookies cookies) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse get(String url, Params params, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.GET, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse post(String url) throws IOException {
        return this.send(url, Method.POST, this.headers, new Params(), (RequestBody)null, this.cookies, this.config);
    }

    public HttpResponse post(String url, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, new Params(), (RequestBody)null, this.cookies, config);
    }

    public HttpResponse post(String url, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), (RequestBody)null, this.cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), (RequestBody)null, this.cookies, config);
    }

    public HttpResponse post(String url, Params params) throws IOException {
        return this.send(url, Method.POST, this.headers, params, (RequestBody)null, this.cookies, this.config);
    }

    public HttpResponse post(String url, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, params, (RequestBody)null, this.cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, this.headers, new Params(), requestBody, this.cookies, this.config);
    }

    public HttpResponse post(String url, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, new Params(), requestBody, this.cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, this.headers, new Params(), (RequestBody)null, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, new Params(), (RequestBody)null, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, this.config);
    }

    public HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, this.config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, this.config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, (HttpConfig)null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, (HttpConfig)null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, this.cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, (HttpConfig)null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, (HttpConfig)null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, (HttpConfig)null);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, (RequestBody)null, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, this.headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Params params, Cookies cookies, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, Params params, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, Params params, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Headers headers, Cookies cookies, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, Headers headers, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, Headers headers, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, Cookies cookies) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, Params params, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, Headers headers, RequestBody requestBody) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers, Params params) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers, Params params, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params, Headers headers) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, this.config);
    }

    public HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params, Headers headers, HttpConfig config) throws IOException {
        return this.send(url, Method.POST, headers, params, requestBody, cookies, config);
    }
}

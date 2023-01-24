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

public class SaveCookieSession extends Session {
    @Override
    public HttpResponse send(String url, Method method, Headers headers, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        HttpRequest request = new HttpRequest(url);
        HttpResponse response = request.send(method, headers == null ? this.headers : headers, params, requestBody, cookies == null ? this.cookies : cookies, config == null ? this.config : config);
        Map<String, HttpCookie> cookieMap = response.cookies.getCookieMap();

        for (HttpCookie cookie : cookieMap.values()) {
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
}

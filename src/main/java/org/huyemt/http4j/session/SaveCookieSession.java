package org.huyemt.http4j.session;

import org.huyemt.http4j.HttpRequest;
import org.huyemt.http4j.HttpResponse;
import org.huyemt.http4j.resource.*;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;

/**
 * SaveCookieSession与Session的区别在于，后者只会保存会话ID的Cookie，而前者会保存响应体设置所有的Cookie，并且同步更新。
 *
 * The difference between `SaveCookieSession` and `Session` is that the latter only saves the cookies of the session ID, while the former saves all the cookies set by the response body and synchronizes the update.
 *
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
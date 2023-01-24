package org.rifle.library.Http4J.session;

import org.rifle.library.Http4J.HttpRequest;
import org.rifle.library.Http4J.HttpResponse;
import org.rifle.library.Http4J.resource.*;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;

/**
 * 此类与`Session`不同的是，它会保存所有响应设置的Cookie。 (科学性有待测验)
 *
 * This class is different from `Session` in that it saves `Cookie` for all response settings. (scientific nature to be tested)
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

            if (this.cookies.contains(cookie.getName()) && !cookie.getValue().equals(this.cookies.get(cookie.getName()).getValue()))
                this.cookies.set(cookie.getName(), cookie.getValue());
        }
        return response;
    }
}

package org.huyemt.http4j;

import org.huyemt.http4j.session.*;
import org.huyemt.http4j.resource.*;

import java.io.IOException;

/**
 * Rifle为模块开发者们提供的更方便，更高效的网络请求封装方法。
 *
 * Rifle provides a more convenient and efficient network request encapsulation method for module developers.
 *
 * @author Huyemt
 */

public class Http4J {
    // 如果 Headers 传的是特殊值(除了字母和常用报头), 都需要`URLEncoder.encode()`进行编码
    // If Headers pass special values (except letters and common headers), they need to be encoded by `URLEncoder.encode()`

    private Http4J() {

    }

    /**
     * 创建一个会话 (此操作可保持会话ID)
     *
     * Create a session (this will keep Cookies)
     *
     * @return Session
     */
    public static Session session() {
        return new Session();
    }


    /**
     * 创建一个会话 (此操作会将响应体设置所有的Cookie保存)
     *
     *
     *
     * @return SaveCookieSession
     */
    public static SaveCookieSession saveCookieSession() {
        return new SaveCookieSession();
    }

    /**
     * 发送一个GET请求
     *
     * Send a GET request
     *
     * @param url
     * @param attributes
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse get(String url, HttpAttribute ... attributes) throws IOException {
        Headers headers = null;
        Params params = null;
        Cookies cookies = null;
        HttpConfig config = null;

        for (HttpAttribute attribute : attributes) {
            if (attribute instanceof Headers) {
                headers = headers == null ? (Headers) attribute : headers;
            } else if (attribute instanceof Params) {
                params = params == null ? (Params) attribute : params;
            }else if (attribute instanceof Cookies) {
                cookies = cookies == null ? (Cookies) attribute : cookies;
            } else if (attribute instanceof HttpConfig) {
                config = config == null ? (HttpConfig) attribute : config;
            }
        }

        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies, config);
    }

    /**
     * 发送一个POST请求
     *
     * Send a POST request
     *
     * @param url
     * @param attributes
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse post(String url, HttpAttribute ... attributes) throws IOException {
        Headers headers = null;
        Params params = null;
        RequestBody requestBody = null;
        Cookies cookies = null;
        HttpConfig config = null;

        for (HttpAttribute attribute : attributes) {
            if (attribute instanceof Headers) {
                headers = headers == null ? (Headers) attribute : headers;
            } else if (attribute instanceof Params) {
                params = params == null ? (Params) attribute : params;
            } else if (attribute instanceof RequestBody) {
                requestBody = requestBody == null ? (RequestBody) attribute : requestBody;
            }else if (attribute instanceof Cookies) {
                cookies = cookies == null ? (Cookies) attribute : cookies;
            } else if (attribute instanceof HttpConfig) {
                config = config == null ? (HttpConfig) attribute : config;
            }
        }

        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    /**
     * 发送一个PUT请求
     *
     * Send a PUT request
     *
     * @param url
     * @param attributes
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse put(String url, HttpAttribute ... attributes) throws IOException {
        Headers headers = null;
        Params params = null;
        RequestBody requestBody = null;
        Cookies cookies = null;
        HttpConfig config = null;

        for (HttpAttribute attribute : attributes) {
            if (attribute instanceof Headers) {
                headers = headers == null ? (Headers) attribute : headers;
            } else if (attribute instanceof Params) {
                params = params == null ? (Params) attribute : params;
            } else if (attribute instanceof RequestBody) {
                requestBody = requestBody == null ? (RequestBody) attribute : requestBody;
            }else if (attribute instanceof Cookies) {
                cookies = cookies == null ? (Cookies) attribute : cookies;
            } else if (attribute instanceof HttpConfig) {
                config = config == null ? (HttpConfig) attribute : config;
            }
        }

        return new HttpRequest(url).send(Method.PUT, headers, params, requestBody, cookies, config);
    }
}
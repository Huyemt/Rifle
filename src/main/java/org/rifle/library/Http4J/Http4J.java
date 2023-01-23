package org.rifle.library.Http4J;

import org.rifle.library.Http4J.resource.*;

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

    /**
     * 创建一个会话 (此操作可保持Cookies)
     *
     * Create a session (this will keep Cookies)
     *
     * @return Session
     */
    public static Session session() {
        return new Session();
    }

    /**
     * 发送GET请求
     *
     * Send a GET request
     *
     * @param url
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse get(String url) throws IOException {
        return new HttpRequest(url).send(Method.GET);
    }

    public static HttpResponse get(String url, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, config);
    }

    /////////////////////////////////////

    public static HttpResponse get(String url, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, new Params(), null, new Cookies());
    }

    public static HttpResponse get(String url, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, new Params(), null, new Cookies(), config);
    }

    public static HttpResponse get(String url, Params params) throws IOException {
        return new HttpRequest(url).send(Method.GET, new Headers(), params, null, new Cookies());
    }

    public static HttpResponse get(String url, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, new Headers(), params, null, new Cookies(), config);
    }

    public static HttpResponse get(String url, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.GET, new Headers(), new Params(), null, cookies);
    }

    public static HttpResponse get(String url, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, new Headers(), new Params(), null, cookies, config);
    }

    /////////////////////////////////////

    public static HttpResponse get(String url, Headers headers, Params params) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, new Cookies());
    }

    public static HttpResponse get(String url, Headers headers, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, new Cookies(), config);
    }

    public static HttpResponse get(String url, Headers headers, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, new Params(), null, cookies);
    }

    public static HttpResponse get(String url, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, new Params(), null, cookies, config);
    }

    public static HttpResponse get(String url, Params params, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, new Cookies());
    }

    public static HttpResponse get(String url, Params params, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, new Cookies(), config);
    }

    public static HttpResponse get(String url, Params params, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.GET, new Headers(), params, null, cookies);
    }

    public static HttpResponse get(String url, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, new Headers(), params, null, cookies, config);
    }

    /////////////////////////////////////

    public static HttpResponse get(String url, Headers headers, Params params, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies);
    }

    public static HttpResponse get(String url, Headers headers, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies, config);
    }

    public static HttpResponse get(String url, Headers headers, Cookies cookies, Params params) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies);
    }

    public static HttpResponse get(String url, Headers headers, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies, config);
    }

    public static HttpResponse get(String url, Cookies cookies, Headers headers, Params params) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies);
    }

    public static HttpResponse get(String url, Cookies cookies, Headers headers, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies, config);
    }

    public static HttpResponse get(String url, Cookies cookies, Params params, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies);
    }

    public static HttpResponse get(String url, Cookies cookies, Params params, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies, config);
    }

    public static HttpResponse get(String url, Params params, Cookies cookies, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies);
    }

    public static HttpResponse get(String url, Params params, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies, config);
    }

    public static HttpResponse get(String url, Params params, Headers headers, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies);
    }

    public static HttpResponse get(String url, Params params, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, null, cookies, config);
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    /**
     * 发送一个POST请求
     *
     * Send a POST request
     *
     * @param url
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse post(String url) throws IOException {
        return new HttpRequest(url).send(Method.POST);
    }

    public static HttpResponse post(String url, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, config);
    }

    /////////////////////////////////////

    public static HttpResponse post(String url, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), null, new Cookies());
    }

    public static HttpResponse post(String url, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), null, new Cookies(), config);
    }

    public static HttpResponse post(String url, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, null, new Cookies());
    }

    public static HttpResponse post(String url, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, null, new Cookies(), config);
    }

    public static HttpResponse post(String url, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), new Params(), requestBody, new Cookies());
    }

    public static HttpResponse post(String url, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), new Params(), requestBody, new Cookies(), config);
    }

    public static HttpResponse post(String url, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), new Params(), null, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), new Params(), null, cookies, config);
    }

    /////////////////////////////////////

    public static HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies());
    }

    public static HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies(), config);
    }

    public static HttpResponse post(String url, Headers headers, Params params, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies);
    }

    public static HttpResponse post(String url, Headers headers, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies, config);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies());
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies(), config);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Headers headers, Cookies cookies, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies);
    }

    public static HttpResponse post(String url, Headers headers, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies, config);
    }

    public static HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies);
    }

    public static HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies());
    }

    public static HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies(), config);
    }

    public static HttpResponse post(String url, Params params, Headers headers, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies);
    }

    public static HttpResponse post(String url, Params params, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies, config);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies());
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies(), config);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Params params, Cookies cookies, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies);
    }

    public static HttpResponse post(String url, Params params, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies, config);
    }

    public static HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies());
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies(), config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies());
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, new Cookies(), config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, Headers headers, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, Headers headers, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, Params params, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, Params params, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, new RequestBody(), cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, new Params(), requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, new Headers(), params, requestBody, cookies, config);
    }

    /////////////////////////////////////

    public static HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Headers headers, Params params, Cookies cookies, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Headers headers, Params params, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Headers headers, Cookies cookies, Params params, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Headers headers, Cookies cookies, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Headers headers, Cookies cookies, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Params params, Headers headers, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Params params, Headers headers, Cookies cookies, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Params params, Headers headers, Cookies cookies, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Params params, Cookies cookies, Headers headers, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Params params, Cookies cookies, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Params params, Cookies cookies, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Cookies cookies, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, Cookies cookies) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, Cookies cookies, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Cookies cookies, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Headers headers, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Cookies cookies, Params params, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, Headers headers, Params params, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, Headers headers, Params params, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, Headers headers, RequestBody requestBody, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, Params params, Headers headers, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, Params params, Headers headers, RequestBody requestBody, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, Params params, RequestBody requestBody, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Headers headers, Params params, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }

    public static HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies);
    }

    public static HttpResponse post(String url, Cookies cookies, RequestBody requestBody, Params params, Headers headers, HttpConfig config) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, cookies, config);
    }
}

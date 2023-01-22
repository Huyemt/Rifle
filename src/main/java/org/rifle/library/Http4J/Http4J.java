package org.rifle.library.Http4J;

import org.rifle.library.Http4J.resource.Headers;
import org.rifle.library.Http4J.resource.Method;
import org.rifle.library.Http4J.resource.Params;
import org.rifle.library.Http4J.resource.RequestBody;

import java.io.IOException;

/**
 * Rifle为模块开发者们提供的更方便，更高效的网络请求封装方法。
 *
 * Rifle provides a more convenient and efficient network request encapsulation method for module developers.
 *
 * @author Huyemt
 */

public class Http4J {
    public static HttpResponse get(String url, Headers headers, Params params, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, allowsRedirect);
    }

    public static HttpResponse get(String url, Headers headers, Params params) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params);
    }

    public static HttpResponse get(String url, Params params, Headers headers, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params, allowsRedirect);
    }

    public static HttpResponse get(String url, Params params, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, params);
    }

    public static HttpResponse get(String url, Headers headers, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers, allowsRedirect);
    }

    public static HttpResponse get(String url, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.GET, headers);
    }

    public static HttpResponse get(String url, Params params, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.GET, params, allowsRedirect);
    }

    public static HttpResponse get(String url, Params params) throws IOException {
        return new HttpRequest(url).send(Method.GET, params);
    }

    public static HttpResponse get(String url, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.GET, allowsRedirect);
    }

    public static HttpResponse get(String url) throws IOException {
        return new HttpRequest(url).send(Method.GET);
    }

    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////

    public static HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody, allowsRedirect);
    }

    public static HttpResponse post(String url, Headers headers, Params params, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, requestBody);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, requestBody, params, allowsRedirect);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, requestBody, params);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody, headers, params, allowsRedirect);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody, headers, params);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody, params, headers, allowsRedirect);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody, params, headers);
    }

    public static HttpResponse post(String url, Headers headers, Params params, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params, allowsRedirect);
    }

    public static HttpResponse post(String url, Headers headers, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, params);
    }

    public static HttpResponse post(String url, Params params, Headers headers, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, params, headers, allowsRedirect);
    }

    public static HttpResponse post(String url, Params params, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, params, headers);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, params, requestBody, headers, allowsRedirect);
    }
    public static HttpResponse post(String url, Params params, RequestBody requestBody, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, params, requestBody, headers);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, params, requestBody, allowsRedirect);
    }

    public static HttpResponse post(String url, Params params, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, params, requestBody);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody, params, allowsRedirect);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody, params);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, requestBody, allowsRedirect);
    }

    public static HttpResponse post(String url, Headers headers, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, requestBody);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody, headers, allowsRedirect);
    }

    public static HttpResponse post(String url, RequestBody requestBody, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody, headers);
    }

    public static HttpResponse post(String url, Params params, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, params, allowsRedirect);
    }

    public static HttpResponse post(String url, Params params) throws IOException {
        return new HttpRequest(url).send(Method.POST, params);
    }

    public static HttpResponse post(String url, Headers headers, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers, allowsRedirect);
    }

    public static HttpResponse post(String url, Headers headers) throws IOException {
        return new HttpRequest(url).send(Method.POST, headers);
    }

    public static HttpResponse post(String url, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody, allowsRedirect);
    }

    public static HttpResponse post(String url, RequestBody requestBody) throws IOException {
        return new HttpRequest(url).send(Method.POST, requestBody);
    }

    public static HttpResponse post(String url, boolean allowsRedirect) throws IOException {
        return new HttpRequest(url).send(Method.POST, allowsRedirect);
    }

    public static HttpResponse post(String url) throws IOException {
        return new HttpRequest(url).send(Method.POST);
    }
}

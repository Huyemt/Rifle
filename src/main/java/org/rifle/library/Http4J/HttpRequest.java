package org.rifle.library.Http4J;

import org.rifle.library.Http4J.resource.Headers;
import org.rifle.library.Http4J.resource.Method;
import org.rifle.library.Http4J.resource.Params;
import org.rifle.library.Http4J.resource.RequestBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * HTTP请求对象
 *
 * HTTP request object
 *
 * @author Huyemt
 */

public class HttpRequest {
    protected final URL url;

    public HttpRequest(URL url) {
        this.url = url;
    }

    public HttpRequest(String url) throws MalformedURLException {
        this(new URL(url.trim()));
    }

    protected HttpResponse send(String method, Headers headers, Params params, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        URL url;
        if (params != null && params.size() > 0) {
            String strUrl = this.url.toString();
            url = new URL(strUrl.endsWith("?") ? strUrl.concat(params.toString()) : strUrl.concat("?").concat(params.toString()));
        } else
            url = this.url;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setAllowUserInteraction(allowsRedirect);
        connection.setDoInput(true);
        connection.setRequestMethod(method.toUpperCase());

        if (headers == null)
            headers = new Headers();

        headers.defaultValue("User-Agent", "RifleHttp/1.0");
        headers.defaultValue("Accept", "*/*");
        headers.defaultValue("Accept-Language", "zh-CN,zh;");
        headers.defaultValue("Cache-Control", "no-cache");
        headers.defaultValue("Connection", "Keep-Alive");
        headers.defaultValue("Charset", "UTF-8");
        headers.defaultValue("Content-Type", "application/x-www-form-urlencoded");

        if (headers.size() > 0)
            for (Map.Entry<String, Object> entry : headers.getHeaders().entrySet())
                connection.addRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));

        if (!method.equalsIgnoreCase(Method.GET.getMethod()) && requestBody != null && requestBody.size() > 0) {
            connection.setDoOutput(true);

            DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
            stream.writeBytes(requestBody.toString(StandardCharsets.UTF_8));
            stream.flush();

            stream.close();
        }

        connection.connect();

        byte[] result;

        if (connection.getResponseCode() == 200) {
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1)
                outStream.write(buffer, 0, len);

            result = outStream.toByteArray();

            inputStream.close();
            outStream.close();
        } else
            result = new byte[0];

        connection.disconnect();

        return new HttpResponse(result, connection.getHeaderFields());
    }

    protected HttpResponse send(String method, Headers headers, Params params, RequestBody requestBody) throws IOException {
        return send(method, headers, params, requestBody, false);
    }

    protected HttpResponse send(String method, Headers headers, Params params, boolean allowsRedirect) throws IOException {
        return send(method, headers, params, null, allowsRedirect);
    }

    protected HttpResponse send(String method, Headers headers, Params params) throws IOException {
        return send(method, headers, params, null, false);
    }

    protected HttpResponse send(String method, Headers headers, RequestBody requestBody, Params params, boolean allowsRedirect) throws IOException {
        return send(method, headers, params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(String method, Headers headers, RequestBody requestBody, Params params) throws IOException {
        return send(method, headers, params, requestBody, false);
    }

    protected HttpResponse send(String method, RequestBody requestBody, Headers headers, Params params, boolean allowsRedirect) throws IOException {
        return send(method, headers, params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(String method, RequestBody requestBody, Headers headers, Params params) throws IOException {
        return send(method, headers, params, requestBody, false);
    }

    protected HttpResponse send(String method, RequestBody requestBody, Params params, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method, headers, params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(String method, RequestBody requestBody, Params params, Headers headers) throws IOException {
        return send(method, headers, params, requestBody, false);
    }

    protected HttpResponse send(String method, Params params, RequestBody requestBody, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method, headers, params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(String method, Params params, RequestBody requestBody, Headers headers) throws IOException {
        return send(method, headers, params, requestBody, false);
    }

    protected HttpResponse send(String method, Params params, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return send(method, new Headers(), params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(String method, Params params, RequestBody requestBody) throws IOException {
        return send(method, new Headers(), params, requestBody, false);
    }

    protected HttpResponse send(String method, Params params, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method, headers, params, null, allowsRedirect);
    }

    protected HttpResponse send(String method, Params params, Headers headers) throws IOException {
        return send(method, headers, params, null, false);
    }

    protected HttpResponse send(String method, RequestBody requestBody, Params params, boolean allowsRedirect) throws IOException {
        return send(method, new Headers(), params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(String method, RequestBody requestBody, Params params) throws IOException {
        return send(method, new Headers(), params, requestBody, false);
    }

    protected HttpResponse send(String method, Headers headers, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return send(method, headers, null, requestBody, allowsRedirect);
    }

    protected HttpResponse send(String method, Headers headers, RequestBody requestBody) throws IOException {
        return send(method, headers, null, requestBody, false);
    }

    protected HttpResponse send(String method, RequestBody requestBody, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method, headers, null, requestBody, allowsRedirect);
    }

    protected HttpResponse send(String method, RequestBody requestBody, Headers headers) throws IOException {
        return send(method, headers, null, requestBody, false);
    }

    protected HttpResponse send(String method, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method, headers, (Params) null, null, allowsRedirect);
    }

    protected HttpResponse send(String method, Headers headers) throws IOException {
        return send(method, headers, (Params) null, null, false);
    }

    protected HttpResponse send(String method, Params params, boolean allowsRedirect) throws IOException {
        return send(method, new Headers(), params, null, allowsRedirect);
    }

    protected HttpResponse send(String method, Params params) throws IOException {
        return send(method, new Headers(), params, null, false);
    }

    protected HttpResponse send(String method, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return send(method, new Headers(), null, requestBody, allowsRedirect);
    }

    protected HttpResponse send(String method, RequestBody requestBody) throws IOException {
        return send(method, new Headers(), null, requestBody, false);
    }

    protected HttpResponse send(String method, boolean allowsRedirect) throws IOException {
        return send(method, new Headers(), (Params) null, null, allowsRedirect);
    }

    protected HttpResponse send(String method) throws IOException {
        return send(method, new Headers(), (Params) null, null, false);
    }

    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////

    protected HttpResponse send(Method method, Headers headers, Params params, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, Headers headers, Params params, RequestBody requestBody) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, false);
    }

    protected HttpResponse send(Method method, Headers headers, Params params, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, params, null, allowsRedirect);
    }

    protected HttpResponse send(Method method, Headers headers, Params params) throws IOException {
        return send(method.getMethod(), headers, params, null, false);
    }

    protected HttpResponse send(Method method, Headers headers, RequestBody requestBody, Params params, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, Headers headers, RequestBody requestBody, Params params) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, false);
    }

    protected HttpResponse send(Method method, RequestBody requestBody, Headers headers, Params params, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, RequestBody requestBody, Headers headers, Params params) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, false);
    }

    protected HttpResponse send(Method method, RequestBody requestBody, Params params, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, RequestBody requestBody, Params params, Headers headers) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, false);
    }

    protected HttpResponse send(Method method, Params params, RequestBody requestBody, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, Params params, RequestBody requestBody, Headers headers) throws IOException {
        return send(method.getMethod(), headers, params, requestBody, false);
    }

    protected HttpResponse send(Method method, Params params, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, params, null, allowsRedirect);
    }

    protected HttpResponse send(Method method, Params params, Headers headers) throws IOException {
        return send(method.getMethod(), headers, params, null, false);
    }

    protected HttpResponse send(Method method, Params params, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), new Headers(), params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, Params params, RequestBody requestBody) throws IOException {
        return send(method.getMethod(), new Headers(), params, requestBody, false);
    }

    protected HttpResponse send(Method method, RequestBody requestBody, Params params, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), new Headers(), params, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, RequestBody requestBody, Params params) throws IOException {
        return send(method.getMethod(), new Headers(), params, requestBody, false);
    }

    protected HttpResponse send(Method method, Headers headers, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, null, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, Headers headers, RequestBody requestBody) throws IOException {
        return send(method.getMethod(), headers, null, requestBody, false);
    }

    protected HttpResponse send(Method method, RequestBody requestBody, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, null, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, RequestBody requestBody, Headers headers) throws IOException {
        return send(method.getMethod(), headers, null, requestBody, false);
    }

    protected HttpResponse send(Method method, Headers headers, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), headers, (Params) null, null, allowsRedirect);
    }

    protected HttpResponse send(Method method, Headers headers) throws IOException {
        return send(method.getMethod(), headers, (Params) null, null, false);
    }

    protected HttpResponse send(Method method, Params params, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), new Headers(), params, null, allowsRedirect);
    }

    protected HttpResponse send(Method method, Params params) throws IOException {
        return send(method.getMethod(), new Headers(), params, null, false);
    }

    protected HttpResponse send(Method method, RequestBody requestBody, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), new Headers(), null, requestBody, allowsRedirect);
    }

    protected HttpResponse send(Method method, RequestBody requestBody) throws IOException {
        return send(method.getMethod(), new Headers(), null, requestBody, false);
    }

    protected HttpResponse send(Method method, boolean allowsRedirect) throws IOException {
        return send(method.getMethod(), new Headers(), (Params) null, null, allowsRedirect);
    }

    protected HttpResponse send(Method method) throws IOException {
        return send(method.getMethod(), new Headers(), (Params) null, null, false);
    }
}

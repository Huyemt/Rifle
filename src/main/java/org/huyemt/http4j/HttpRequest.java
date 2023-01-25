package org.huyemt.http4j;

import org.huyemt.http4j.resource.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Huyemt
 */

public class HttpRequest {
    public final URL url;

    public HttpRequest(URL url) {
        this.url = url;
    }

    public HttpRequest(String url) throws MalformedURLException {
        this(new URL(url.trim()));
    }

    public URL getURL() {
        return this.url;
    }

    public HttpResponse send(Method method, Headers headers, Params params, RequestBody requestBody, Cookies cookies, HttpConfig config) throws IOException {
        if (headers == null) {
            headers = new Headers();
        }

        if (cookies == null) {
            cookies = new Cookies();
        }

        if (config == null) {
            config = new HttpConfig();
        }

        URL url;
        if (params != null && params.size() > 0) {
            String strUrl = this.url.toString();
            url = new URL(strUrl.endsWith("?") ? strUrl.concat(params.toString()) : strUrl.concat("?").concat(params.toString(config.isTargetUrlEncode())));
        } else {
            url = this.url;
        }

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(config.getTimeout());
        connection.setInstanceFollowRedirects(config.isAllowsRedirect());
        connection.setDoInput(true);
        connection.setRequestMethod(method.getMethod());
        if (config.isAutoHeaders()) {
            headers.defaultValue("User-Agent", "Http4J/1.0");
        }

        headers.defaultValue("Accept", "*/*");
        headers.defaultValue("Connection", "Keep-Alive");
        if (headers.size() > 0) {
            for (String key : headers.getHeaders().keySet()) {
                connection.addRequestProperty(key, headers.get(key));
            }
        }

        if (cookies.size() > 0) {
            connection.addRequestProperty("Cookie", cookies.toString(config.isCookieUrlEncode()));
        }

        if (method == Method.POST && requestBody != null && requestBody.size() > 0) {
            connection.setDoOutput(true);
            OutputStream stream = connection.getOutputStream();
            stream.write(requestBody.toString(config.isDataUrlEncode() ? StandardCharsets.UTF_8 : null).getBytes(StandardCharsets.UTF_8));
            stream.flush();
            stream.close();
        }

        byte[] result;
        int status;
        try {
            connection.connect();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int len;
            while((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }

            result = outStream.toByteArray();
            inputStream.close();
            outStream.close();
        } catch (SocketTimeoutException var18) {
            result = new byte[0];
        } finally {
            connection.disconnect();
            status = connection.getResponseCode();
        }

        return new HttpResponse(status, result, connection.getHeaderFields());
    }

    public HttpResponse send(Method method, Headers headers, Params params, RequestBody requestBody, Cookies cookies) throws IOException {
        return this.send(method, headers, params, requestBody, cookies, new HttpConfig());
    }

    public HttpResponse send(Method method, HttpConfig config) throws IOException {
        return this.send(method, new Headers(), new Params(), null, new Cookies(), config);
    }

    public HttpResponse send(Method method) throws IOException {
        return this.send(method, new Headers(), new Params(), null, new Cookies(), new HttpConfig());
    }
}

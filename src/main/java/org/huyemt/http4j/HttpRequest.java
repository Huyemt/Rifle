package org.huyemt.http4j;

import org.huyemt.http4j.resource.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/**
 * @author Huyemt
 */

public class HttpRequest {
    public URL url;

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
        connection.setInstanceFollowRedirects(false);
        connection.setDoInput(true);
        connection.setRequestMethod(method.getMethod());
        if (config.isAutoHeaders()) {
            headers.defaultValue("User-Agent", "Http4J/1.0");
            headers.defaultValue("Content-Type", "application/x-www-form-urlencoded");
        }

        if (method != Method.GET && requestBody != null && requestBody.json != null) {
            headers.add("Content-Type", "application/json");
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

        if (requestBody != null) {
            byte[] content;
            if (requestBody.json != null) {
                content = requestBody.json.getBytes(StandardCharsets.UTF_8);
            } else {
                content = requestBody.toString(config.isDataUrlEncode() ? StandardCharsets.UTF_8 : null).getBytes(StandardCharsets.UTF_8);
            }
            connection.setDoOutput(true);
            OutputStream stream = connection.getOutputStream();
            stream.write(content);
            stream.flush();
            stream.close();
        }

        byte[] result;
        int status;
        try {
            connection.connect();

            InputStream inputStream;

            if (connection.getContentEncoding() != null && connection.getContentEncoding().equalsIgnoreCase("gzip")) {
                inputStream = new GZIPInputStream(connection.getInputStream());

            } else {
                inputStream = new BufferedInputStream(connection.getInputStream());
            }

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

        HttpResponse response = new HttpResponse(status, connection.getURL(), result, connection.getHeaderFields(), headers);


        if (config.isAllowsRedirect()) {
            // If there is a redirect address
            if (response.headers.contains("Location")) {
                // set url
                this.url = new URL(response.headers.get("Location"));
                response.headers.remove("Location");


                for (HttpCookie cookie : response.cookies.getCookieMap().values()) {
                    cookies.add(cookie);
                }

                return send(method, headers, null, null, cookies, config);
            }
        }

        return response;
    }
}
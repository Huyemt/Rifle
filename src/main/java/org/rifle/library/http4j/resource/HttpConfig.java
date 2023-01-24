package org.rifle.library.http4j.resource;

/**
 * @author Huyemt
 */

public class HttpConfig {
    private int timeout;
    private boolean allowsRedirect;
    private boolean autoHeaders;
    private boolean cookieUrlEncode;
    private boolean targetUrlEncode;

    public HttpConfig() {
        timeout = 20 * 1000;
        allowsRedirect = false;
        autoHeaders = true;
        cookieUrlEncode = true;
        targetUrlEncode = true;
    }

    public boolean isAutoHeaders() {
        return autoHeaders;
    }

    public boolean isAllowsRedirect() {
        return allowsRedirect;
    }

    public boolean isCookieUrlEncode() {
        return cookieUrlEncode;
    }

    public boolean isTargetUrlEncode() {
        return targetUrlEncode;
    }

    public int getTimeout() {
        return timeout;
    }

    public HttpConfig timeout(int timeout) {
        if (timeout > 0)
            this.timeout = timeout;

        return this;
    }

    public HttpConfig allowsRedirect(boolean redirect) {
        allowsRedirect = redirect;
        return this;
    }

    public HttpConfig autoHeaders(boolean auto) {
        autoHeaders = auto;
        return this;
    }

    public HttpConfig cookieUrlEncode(boolean encode) {
        cookieUrlEncode = encode;
        return this;
    }

    public HttpConfig targetUrlEncode(boolean encode) {
        targetUrlEncode = encode;
        return this;
    }
}

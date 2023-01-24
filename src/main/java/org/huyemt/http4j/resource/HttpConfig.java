package org.huyemt.http4j.resource;

/**
 * @author Huyemt
 */

public class HttpConfig {
    private int timeout = 20000;
    private boolean allowsRedirect = false;
    private boolean autoHeaders = true;
    private boolean cookieUrlEncode = true;
    private boolean targetUrlEncode = true;

    public HttpConfig() {
    }

    public boolean isAutoHeaders() {
        return this.autoHeaders;
    }

    public boolean isAllowsRedirect() {
        return this.allowsRedirect;
    }

    public boolean isCookieUrlEncode() {
        return this.cookieUrlEncode;
    }

    public boolean isTargetUrlEncode() {
        return this.targetUrlEncode;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public HttpConfig timeout(int timeout) {
        if (timeout > 0) {
            this.timeout = timeout;
        }

        return this;
    }

    public HttpConfig allowsRedirect(boolean redirect) {
        this.allowsRedirect = redirect;
        return this;
    }

    public HttpConfig autoHeaders(boolean auto) {
        this.autoHeaders = auto;
        return this;
    }

    public HttpConfig cookieUrlEncode(boolean encode) {
        this.cookieUrlEncode = encode;
        return this;
    }

    public HttpConfig targetUrlEncode(boolean encode) {
        this.targetUrlEncode = encode;
        return this;
    }
}

package org.rifle.library.Http4J.resource;

/**
 * 网络请求方式
 *
 * Network request mode
 *
 * @author Huyemt
 */

public enum Method {
    /*
    目前只支持两种常用的请求方式，请等待后续更新。
    At present, only two common request methods are supported, please wait for the subsequent update.
     */
    GET("GET"),
    POST("POST");

    private final String method;

    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return getMethod();
    }
}

package org.huyemt.http4j.resource;

/**
 * @author Huyemt
 */

public enum Method {
    GET("GET"),
    POST("POST");

    private final String method;

    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public String toString() {
        return this.getMethod();
    }
}

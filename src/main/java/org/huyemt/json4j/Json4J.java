package org.huyemt.json4j;

import com.google.gson.Gson;

import java.io.Reader;

/**
 * Json Parser
 *
 * @author Huyemt
 */

public class Json4J {
    public static final Gson gson = new Gson();

    private Json4J() {

    }

    public static <T> T parse(String json, Class<?> serialize) {
        return (T) gson.fromJson(json, serialize);
    }

    public static <T> T parse(Reader reader, Class<?> serialize) {
        return (T) gson.fromJson(reader, serialize);
    }
}
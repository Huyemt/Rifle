package org.huyemt.json4j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;

/**
 * Json Parser
 *
 * @author Huyemt
 */

public class Json4J {
    public static final GsonBuilder gsonBuilder = new GsonBuilder();

    private Json4J() {

    }

    public static <T> T parse(String json, Class<?> serialize) {
        return (T) gsonBuilder.disableHtmlEscaping().create().fromJson(json, serialize);
    }

    public static <T> T parse(Reader reader, Class<?> serialize) {
        return (T) gsonBuilder.disableHtmlEscaping().create().fromJson(reader, serialize);
    }

    public static String toJson(Object object) {
        return gsonBuilder.disableHtmlEscaping().create().toJson(object);
    }
}

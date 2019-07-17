package pl.revolut.util;

import com.google.gson.Gson;

/**
 * The Json helper to handle json mapping
 */
public class JsonHelper {

    private static final Gson gson = new Gson();

    public static <T> T read(String string, Class<T> returnType) {
        return gson.fromJson(string, returnType);
    }

    public static String write(Object object) {
        return gson.toJson(object);
    }
}

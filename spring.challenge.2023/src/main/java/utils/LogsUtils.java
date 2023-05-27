package utils;

public class LogsUtils {

    public static void log(Object value, Object... values) {
        System.err.println(String.format("Log : " + value, values));
    }
}

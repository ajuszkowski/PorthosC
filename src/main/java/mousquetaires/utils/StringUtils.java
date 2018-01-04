package mousquetaires.utils;

public class StringUtils {
    public static String notNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}

package mousquetaires.utils;

public class StringUtils {
    public static String nonNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}

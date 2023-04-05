package util;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> queryParams = new HashMap<>();
        String[] split = queryString.split("&");
        for (String str : split) {
            String param = str.substring(0, str.indexOf('='));
            String value = str.substring(str.indexOf('=') + 1);
            queryParams.put(param, value);
        }
        return queryParams;
    }
}

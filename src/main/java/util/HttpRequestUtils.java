package util;

import http.request.ContentType;

import java.util.Arrays;
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

    public static ContentType findContent(String view) {
        for (ContentType contentType : ContentType.values()) {
            if (view.endsWith(".html") && contentType == ContentType.HTML) {
                return contentType;
            }
        }
        throw new IllegalArgumentException("Contents-Type이 존재하지 않습니다.");
    }
}

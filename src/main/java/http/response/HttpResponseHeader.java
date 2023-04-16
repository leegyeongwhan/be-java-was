package http.response;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {
    private Map<String, String> headers = new HashMap<>();

    public void put(String key, String value) {
        headers.put(key, value);
    }
}

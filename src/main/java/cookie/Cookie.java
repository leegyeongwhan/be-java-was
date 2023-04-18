package cookie;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    private static final String SESSION_ID = "sid";
    private static final String EQUAL = "=";
    private static final String SEMICOLON = ";";
    private static final String BLANK = " ";

    private final String key;
    private final String value;
    private Map<String, String> cookies = new HashMap<>();

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
        this.cookies.put(key, value);
    }

    public void addCookie(String name, String value) {
        this.cookies.put(name, value);
    }

    public void setHttpOnly(String key) {
        cookies.put(key, cookies.get(key) + SEMICOLON + "httpOnly");
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public String parse() {
        return this.cookies.entrySet().stream()
                .map(key -> key.getKey() + EQUAL + key.getValue())
                .collect(Collectors.joining(SEMICOLON + BLANK));
    }

    public String getSessionId() {
        return cookies.get(SESSION_ID);
    }
}

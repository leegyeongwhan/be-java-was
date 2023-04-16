package cookie;

import session.Session;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    private final Map<String, String> cookies;
    private Session session;

    public Cookie(String sessionCookieName, String sessionId) {
        this.cookies = new LinkedHashMap<>();
    }

    public static Cookie newInstance() {
        return new Cookie("", "");
    }

    public void add(String key, String value) {
        cookies.put(key, value);
    }

    public void addAttribute(String key, String addValue) {
        cookies.put(key, cookies.get(key) + "; " + addValue);
    }

    public void setHttpOnly(String key) {
        cookies.put(key, cookies.get(key) + "; " + "HttpOnly");
    }

    public String create() {
        return cookies.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\r\n"));
    }

    public String get(String key) {
        String value = cookies.get(key);
        return value != null ? value : "";
    }
}

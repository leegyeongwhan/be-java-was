package http.response;

import cookie.Cookie;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {
    private Map<String, String> headers = new HashMap<>();
    private Cookie cookies;

    public void put(String key, String value) {
        headers.put(key, value);
    }

    public boolean isEmptyCookie() {
        return this.cookies.isEmpty();
    }

    public void addCookie(String name, String value) {
        this.cookies.addCookie(name, value);
    }

    public String parseCookie() {
        return this.cookies.parse();
    }

    public void addResponseHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void addMySessionCookie(Cookie mySessionCookie) {
        this.cookies = mySessionCookie;
    }
}

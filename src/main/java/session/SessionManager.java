package session;

import cookie.Cookie;
import http.response.HttpResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Map<String, Object> sessionStore = new ConcurrentHashMap<>();
    private final String id;

    public SessionManager() {
        this.id = UUID.randomUUID().toString();
    }

    public void createSession(Object value, HttpResponse response) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie mySessionCookie = new Cookie("SESSION_COOKIE_NAME", sessionId);
        response.addCookie(mySessionCookie);
    }

    public String getId() {
        return this.id;
    }

    public Object getAttribute(final String name) {
        return this.sessionStore.get(name);
    }

    public void setAttribute(final String name, Object value) {
        this.sessionStore.put(name, value);
    }

    public void removeAttribute(final String name) {
        this.sessionStore.remove(name);
    }

    public void invalidate() {
        this.sessionStore.clear();
    }
}

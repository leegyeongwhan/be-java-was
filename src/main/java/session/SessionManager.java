package session;

import application.model.User;
import cookie.Cookie;
import http.response.HttpResponse;
import javassist.compiler.ast.Member;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Map<String, Object> sessionStore = new ConcurrentHashMap<>();
    public static final String SESSION_COOKIE_NAME = "sid";

    public static void createSession(User user, HttpResponse response) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, user);

        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addSessionCookie(mySessionCookie);
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

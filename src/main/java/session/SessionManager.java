package session;

import application.model.User;
import cookie.Cookie;
import http.response.HttpResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Map<String, User> sessionStore = new ConcurrentHashMap<>();
    public static final String SESSION_COOKIE_NAME = "sid";

    public static void createSession(User user, HttpResponse response) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, user);

        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addSessionCookie(mySessionCookie);
    }

    public static String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, user);
        return sessionId;
    }

    public static User getAttribute(final String name) {
        return sessionStore.get(name);
    }

    public void setAttribute(final String name, User value) {
        sessionStore.put(name, value);
    }

    public void removeAttribute(final String name) {
        sessionStore.remove(name);
    }

    public void invalidate() {
        sessionStore.clear();
    }
}

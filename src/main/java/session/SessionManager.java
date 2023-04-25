package session;

import application.model.User;
import cookie.Cookie;
import http.response.HttpResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Map<String, User> sessionStore = new ConcurrentHashMap<>();

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

    public static void removeAttribute(final String name) {
        sessionStore.remove(name);
    }

    public void invalidate() {
        sessionStore.clear();
    }
}

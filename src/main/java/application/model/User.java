package application.model;

import util.HttpRequestUtils;

import java.util.Map;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    //TODO static을 지우자
    public static User createUser(String httpRequest) {
        String queryString = httpRequest.substring(httpRequest.indexOf("?") + 1);
        Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        return user;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }


    public boolean valid(String userId, String password) {
        if (this.userId.equals(userId) && this.password.equals(password)) {
            return true;
        }
        return false;
    }
}

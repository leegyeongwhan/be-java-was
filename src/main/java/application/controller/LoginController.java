package application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.SessionManager;
import webserver.RequestHandler;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import application.db.Database;
import application.model.User;
import http.request.HttpRequest;
import http.response.HttpResponse;
import util.HttpMethod;

import java.util.Map;

@Controller
public class LoginController extends FrontController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @RequestMapping(path = "/users/login", method = HttpMethod.POST)
    public String login(HttpRequest request
            , HttpResponse response) {
        Map<String, String> parameters = request.getHttpRequestBody().getBody();
        log.debug("userId : {}", parameters.get("userId"));
        log.debug("password : {}", parameters.get("password"));

        String userId = parameters.get("userId");
        String password = parameters.get("password");
        User user = Database.findUserById(userId);
        if (user.validUser(userId, password)) {
            SessionManager.createSession(user, response);
            response.addCookieHeader();
            log.debug("user", user);
            return "redirect:/index.html";
        }
        return "/user/login_failed.html";
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.GET)
    public String loginForm(HttpRequest request
            , HttpResponse response) {
        return "/user/login.html";
    }
}

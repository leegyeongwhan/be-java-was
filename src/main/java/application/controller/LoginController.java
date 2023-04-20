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

@Controller
public class LoginController extends FrontController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @RequestMapping(path = "/users/login", method = HttpMethod.POST)
    public String login(HttpRequest request, HttpResponse response) {
        log.debug("userId : {}", request.getParameter("userId"));
        log.debug("password : {}", request.getParameter("password"));

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = Database.findUserById(userId);
        if (user.valid(userId, password)) {
            String session = SessionManager.createSession(user);
            response.addCookie("sid", session);
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

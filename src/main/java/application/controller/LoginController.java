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

import java.util.Optional;

@Controller
public class LoginController extends FrontController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @RequestMapping(path = "/users/login", method = HttpMethod.POST)
    public String login(HttpRequest request, HttpResponse response) {

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = Database.findUserById(userId);
        if (user.valid(userId, password)) {
            String session = SessionManager.createSession(user);
            log.debug("session", session);
            response.addCookie("sid", session);
            response.setModelAttribute("loginId", user.getName());
            log.debug("response", response);
            log.debug("로그인된 회원", user);
            return "redirect:/";
        }
        return "/user/login_failed.html";
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.GET)
    public String loginForm(HttpRequest request
            , HttpResponse response) {
        return "/user/login.html";
    }

    @RequestMapping(path = "/user/logout", method = HttpMethod.GET)
    public String logout(HttpRequest request
            , HttpResponse response) {
        String cookie = request.getCookie();
        SessionManager.removeAttribute(cookie);
        return "redirect:/";
    }

}

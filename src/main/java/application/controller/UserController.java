package application.controller;

import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.response.HttpResponse;
import session.SessionManager;
import util.HttpRequestUtils;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import application.db.Database;
import application.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;
import view.ModelAndView;
import webserver.RequestHandler;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController extends FrontController {
    private static final String INDEX_HTML = "/index.html";
    private static final String REDIRECT = "redirect:/";
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final Database database = new Database();


    @RequestMapping(path = "/users/create", method = HttpMethod.POST)
    public String createUser(HttpRequest request, HttpResponse response) {
        HttpRequestBody httpRequestBody = request.getHttpRequestBody();
        Map<String, String> parameters = httpRequestBody.getBody();
        log.debug("parameters = {}", parameters);

        User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"),
                parameters.get("email"));

        log.debug("생성한유저 = {}", user);
        database.addUser(user);
        return "redirect:/index.html";
    }

    @RequestMapping(path = "/user/form", method = HttpMethod.GET)
    public String userForm(HttpRequest request, HttpResponse response) {
        return "/user/form.html";
    }

    @RequestMapping(path = "/users/list", method = HttpMethod.GET)
    public String userList(HttpRequest request, HttpResponse response) {
        Optional<String> cookie = request.getCookie();
        if (cookie.isEmpty()) {
            return "redirect:/user/login.html";
        }
        String sessionId = HttpRequestUtils.parseSessionId(cookie.orElseThrow());
        User user = SessionManager.getAttribute(sessionId);
        if (user != null) {
            Collection<User> userList = Database.findAll();
            //model에 담아 view에서 출력하도록한다
            response.setModelAttribute("users", userList);
        }
        return "/user/list.html";
    }
}

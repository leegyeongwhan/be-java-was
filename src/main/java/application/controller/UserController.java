package application.controller;

import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.response.HttpResponse;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import application.db.Database;
import application.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;
import view.ModelAndView;
import webserver.RequestHandler;

import java.util.Map;

@Controller
public class UserController extends FrontController {
    private static final String INDEX_HTML = "/index.html";
    private static final String REDIRECT = "redirect:/";
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final Database database = new Database();

    public ModelAndView process(Map<String, String> parameters) {
        return new ModelAndView(REDIRECT + INDEX_HTML);
    }

    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
    public String createUser(HttpRequest request, HttpResponse response) {
        HttpRequestBody httpRequestBody = request.getHttpRequestBody();
        Map<String, String> parameters = httpRequestBody.getBody();
        log.debug("parameters = {}", parameters);

        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        User user = new User(userId, password, name, email);
        log.debug("생성한유저 = {}", user);
        database.addUser(user);
        return "redirect:/";
    }

    @RequestMapping(path = "/user/form", method = HttpMethod.GET)
    public String userForm(HttpRequest request, HttpResponse response) {
        return "/user/form.html";
    }
}

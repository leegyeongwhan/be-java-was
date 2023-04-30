package webserver;

import application.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private String url = "";
    private static final ConcurrentHashMap<String, Controller> controllerMap = new ConcurrentHashMap<>();

    public RequestMapping() {
        controllerMap.put("/", new HomeController());
        controllerMap.put("/index.html", new HomeController());
        controllerMap.put("/user/form.html", new UserController());
        controllerMap.put("/user/list.html", new LoginController());
        controllerMap.put("/user/login.html", new LoginController());
        controllerMap.put("/users/create", new UserController());
        controllerMap.put("/users/login", new LoginController());
        controllerMap.put("/users/list", new UserController());
    }

    public Controller mapping(String url) {
        log.debug("url : {}", url);
        if (controllerMap.get(url) == null) {
            return new DefaultController();
        }
        return controllerMap.get(url);
    }
}

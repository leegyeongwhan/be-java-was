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
        controllerMap.put("/user/form.html", new UserController());
        controllerMap.put("/users/create.html", new UserController());
        controllerMap.put("/users/login.html", new LoginController());
        controllerMap.put("/user/list.html", new LoginController());
    }

    public Controller mapping(String url) {
        log.debug("url : {}", url);
        if (controllerMap.get(url) == null) {
            return new DefaultController();
        }
        return controllerMap.get(url);
    }
}

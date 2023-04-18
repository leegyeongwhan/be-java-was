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
        controllerMap.put("/user/form", new UserController());
        controllerMap.put("/user/create", new UserController());
        controllerMap.put("/user/login", new LoginController());
    }

    public Controller mapping(String url) {
        log.debug("url : {}", url);
        if (controllerMap.get(url) == null) {
            return new DefaultController();
        }
        return controllerMap.get(url);
    }
}

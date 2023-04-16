package webserver;

import application.controller.Controller;
import application.controller.DefaultController;
import application.controller.HomeController;
import application.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private String url = "";
    private static final ConcurrentHashMap<String, Controller> controllerMap = new ConcurrentHashMap<>();

    public RequestMapping() {
        controllerMap.put("/index.html", new HomeController());
        controllerMap.put("/user/form.html", new UserController());
        controllerMap.put("/user/create", new UserController());
    }

    public Controller mapping(String url) {
        log.debug("url : {}", url);
        if (controllerMap.get(url) == null) {
            return new DefaultController();
        }
        return controllerMap.get(url);
    }
}

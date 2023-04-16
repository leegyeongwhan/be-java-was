package application.controller;


import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;
import webserver.RequestHandler;

@Controller
public class HomeController extends FrontController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

//    public ModelAndView doGet(HttpRequest request, HttpResponse response) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setView(new MyView("/index"));
//        return new ModelAndView();
//    }

    @RequestMapping(path = "/index.html", method = HttpMethod.GET)
    public String home() {
        return "/index.html";
    }
}

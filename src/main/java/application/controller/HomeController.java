package application.controller;


import application.model.User;
import http.request.HttpRequest;
import http.response.HttpResponse;
import session.SessionManager;
import util.HttpRequestUtils;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;
import webserver.RequestHandler;

import java.util.Optional;

@Controller
public class HomeController extends FrontController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

//    public ModelAndView doGet(HttpRequest request, HttpResponse response) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setView(new MyView("/index"));
//        return new ModelAndView();
//    }

    @RequestMapping(path = "/", method = HttpMethod.GET)
    public String home(HttpRequest request, HttpResponse response) {
        Optional<String> cookie = request.getCookie();
        String session = cookie.orElseThrow();
        //쿠키가 등록된 회원
        if (!session.isEmpty()) {
            String sessionId = HttpRequestUtils.parseSessionId(cookie.orElseThrow());
            User user = SessionManager.getAttribute(sessionId);

            //로그인한 회원인 경우
            response.setModelAttribute("loginId", user.getUserId());
        }
        response.setModelAttribute("loginId", "비회원");
        return "/index.html";
    }
}

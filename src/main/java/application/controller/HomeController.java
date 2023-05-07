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

    @RequestMapping(path = "/", method = HttpMethod.GET)
    public String home(HttpRequest request, HttpResponse response) {
        //TODO NO 쿠키일때 생각해보자.
        String sessionId = HttpRequestUtils.parseSessionId(request.getCookie());
        log.debug("home request",request);

        //로그인한 상태의 회원일 경우 index.html에 아이디를 표시한다.
        if (SessionManager.getAttribute(sessionId) != null) {
            User user = SessionManager.getAttribute(sessionId);

            //로그인한 회원인 경우
            response.setModelAttribute("loginId", user.getName());
        }
        return "/index.html";
    }

    @RequestMapping(path = "/index", method = HttpMethod.GET)
    public String index(HttpRequest request, HttpResponse response) {
        //TODO NO 쿠키일때 생각해보자.
        log.debug("home request",request);
        String sessionId = HttpRequestUtils.parseSessionId(request.getCookie());
        //로그인한 상태의 회원일 경우 index.html에 아이디를 표시한다.
        if (SessionManager.getAttribute(sessionId) != null) {
            User user = SessionManager.getAttribute(sessionId);

            //로그인한 회원인 경우
            response.setModelAttribute("loginId", user.getName());
        }
        return "/index.html";
    }
}

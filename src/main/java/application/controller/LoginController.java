package application.controller;

import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import application.db.Database;
import application.model.User;
import http.request.HttpRequest;
import http.response.HttpResponse;
import util.HttpMethod;

import java.util.Map;

@Controller
public class LoginController{

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    public String login(Map<String, String> parameters, HttpRequest request
            , HttpResponse response) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");

        User user = Database.findUserById(userId);

        //아이디가 null이면 없는회원
        if (user == null || !user.getPassword().equals(password)) {
            return "/user/login_failed.html";
        }

//        Session session = request.getHttpSession();
//        session.s(user.getUserId(), user);
//        HttpSessionStorage.save(httpSession.getId(), httpSession);
//
//        httpResponse.addCookie(SESSION_ID, httpRequest.getSessionId());
//        httpResponse.addCookie(LOGINED, "true");


        return "/index.html";
    }

}

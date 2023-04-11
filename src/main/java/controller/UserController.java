package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.HttpRequestBody;
import response.HttpResponse;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;


public class UserController extends FrontController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final Database database = new Database();

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
//        log.debug("UserController doPost : {}", request, response);
        HttpRequestBody httpRequestBody = request.getHttpRequestBody();

        Map<String, String> parameter = httpRequestBody.getBody();
        String userId = parameter.get("userId");
        String password = parameter.get("password");
        String name = parameter.get("name");
        String email = parameter.get("email");
        User user = new User(userId, password, name, email);
        log.debug("parameter : {}", parameter);

        database.addUser(user);
        response.sendRedirect("/index.html");
    }

    /**
     * userForm
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            //log.debug("UserController doPost : {}", request, response);
            byte[] body = Files.readAllBytes(new File(request.getTypeDirectory() + request.getUrl()).toPath());
            //       log.debug("UserController + request.getUrl()).toPath() {} :", request.getTypeDirectory() + request.getUrl());
            response.response200Header(body.length, request.getContentTypeHeader());
            response.responseBody(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

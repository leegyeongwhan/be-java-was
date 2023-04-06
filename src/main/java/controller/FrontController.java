package controller;

import request.HttpRequest;
import response.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class FrontController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String method = request.getMethod();

        if (method.equals("GET")) {
            doGet(request, response);
        } else if (method.equals("POST")) {
            doPost(request, response);
        }
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + request.getUrl()).toPath());
            response.response200Header(body.length);
            response.responseBody(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        if (request.getUrl().startsWith("redirect:")) {
            String url = request.getUrl().split("redirect:")[1];
            response.response302Header(url);
        }
    }
}

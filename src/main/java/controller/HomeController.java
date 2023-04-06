package controller;

import request.HttpRequest;
import response.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HomeController extends FrontController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + request.getUrl()).toPath());
            response.response200Header(body.length);
            response.responseBody(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

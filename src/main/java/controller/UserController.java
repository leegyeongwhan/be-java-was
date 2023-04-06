package controller;

import request.HttpRequest;
import response.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UserController extends FrontController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        if (request.getUrl().startsWith("redirect:")) {
            String url = request.getUrl().split("redirect:")[1];
            response.response302Header(url);
        }
    }
}

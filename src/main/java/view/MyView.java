package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MyView {
    private String viewPath;
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    public MyView(String path) {
        this.viewPath = path;
    }

    public void render(HttpRequest request, HttpResponse response) throws IOException {
        byte[] body = Files.readAllBytes(new File(request.getTypeDirectory() + request.getUrl()).toPath());
        response.response200Header(body.length, request.getContentTypeHeader());
        response.responseBody(body);
    }
}

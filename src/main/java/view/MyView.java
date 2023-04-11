package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MyView {
    private String viewPath;
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    public MyView(HttpRequest request, HttpResponse response, String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpRequest request, HttpResponse response) throws IOException {
        log.debug("render request : {}", request.getUrl());
        byte[] body = Files.readAllBytes(new File(request.getTypeDirectory() + request.getUrl()).toPath());
        log.debug("render + request.getUrl()).toPath() : {} ", request.getTypeDirectory() + request.getUrl());
        log.debug("render  request.getContentTypeHeader()) : {} ", request.getContentTypeHeader());

        response.response200Header(body.length, request.getContentTypeHeader());
        response.responseBody(body);
        log.debug("render request : {}", request);
        log.debug("render response : {}", response);
    }
}

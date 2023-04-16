package application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DefaultController extends FrontController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            log.debug("DefaultController + request.getUrl()).toPath() : {} ", request.getTypeDirectory() + request.getUrl());
            byte[] body = Files.readAllBytes(new File(request.getTypeDirectory() + request.getUrl()).toPath());
            response.response200Header(body.length, request.getContentTypeHeader());
            log.debug("DefaultController + request.getContentTypeHeader() : {} ", request.getContentTypeHeader());
            response.responseBody(body);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

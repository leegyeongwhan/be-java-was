package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HomeController extends FrontController {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            log.debug("HomeController request : {}", request.getUrl());
            byte[] body = Files.readAllBytes(new File(request.getTypeDirectory() + request.getUrl()).toPath());
            log.debug("HomeController + request.getUrl()).toPath() : {} ", request.getTypeDirectory() + request.getUrl());

            response.response200Header(body.length, request.getContentTypeHeader());
            response.responseBody(body);
            log.debug("HomeController request : {}", request);
            log.debug("HomeController response : {}", response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

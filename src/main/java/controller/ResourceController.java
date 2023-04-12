package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourceController extends FrontController {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            //log.debug("UserController doPost : {}", request, response);
            log.debug("ResourceController request.getUrl() : {}", request.getUrl());
            byte[] body = Files.readAllBytes(new File(request.getTypeDirectory() + request.getUrl()).toPath());
            log.debug("ResourceController + request.getUrl()).toPath() {} :", request.getTypeDirectory() + request.getUrl());
            log.debug("ResourceController  request.getContentTypeHeader()) : {} ",  request.getContentTypeHeader());

            response.response200Header(body.length, request.getContentTypeHeader());
            response.responseBody(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

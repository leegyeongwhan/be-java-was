package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import webserver.RequestHandler;

public abstract class FrontController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String method = request.getMethod();
        log.debug("FrontController method : {}", request.getMethod());
        if (method.equals("GET")) {
            doGet(request, response);
        } else if (method.equals("POST")) {
            doPost(request, response);
        }
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
    }
}

package view;

import http.response.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.RequestHandler;

import java.io.IOException;

public class MyView {
    private String viewPath;
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    public MyView(String viewName) {
        this.viewPath = viewName;
    }

    //TODO 리스폰스가 render을 할까 view가 render을 할까.

    public String getViewPath() {
        return viewPath;
    }
}

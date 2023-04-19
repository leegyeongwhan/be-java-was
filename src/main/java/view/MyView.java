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
    private HttpRequest request;

    public MyView(String viewName, HttpRequest request) {
        this.viewPath = viewName;
        this.request = request;
    }

    //TODO 리스폰스가 render을 할까 view가 render을 할까.
    public void viewResolver(HttpRequest request, HttpResponse response) throws IOException {
        //리다이렉트 하는경우
        response.setHttpVersion("HTTP/1.1");

        if (viewPath.contains("redirect:")) {
            String viewName = viewPath.replace("redirect:", "");
            MyView view = new MyView(viewName, request);
            log.debug("Location viewName: {} ", viewName);

            response.setStatus(HttpStatus.FOUND)
                    .addHeader("Location", viewName)
                    .setView(view);

            response.render();
            return;
        }

        MyView view = new MyView(viewPath, request);

        response.setStatus(HttpStatus.OK)
                .addHeader("Content-Type", request.getTypeDirectory())
                .setView(view);

        response.render();
    }

    public String getViewPath() {
        return viewPath;
    }

    public HttpRequest getRequest() {
        return request;
    }
}

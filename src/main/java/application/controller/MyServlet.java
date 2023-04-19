package application.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;

public interface MyServlet {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}

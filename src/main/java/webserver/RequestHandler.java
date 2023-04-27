package webserver;

import java.io.*;
import java.net.Socket;

import application.controller.DispatchServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private static final DispatchServlet dispatchServlet = new DispatchServlet();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다. 구현로직만 있는 유틸성 클래스를 구현한다.
            HttpRequest httpRequest = HttpRequest.of(in);

            //TODO HttpResponse로 out을 받는것 이 맞을까?
            HttpResponse httpResponse = new HttpResponse(out);
           // HttpResponse httpResponse =   dispatchServlet.service(httpRequest);
            dispatchServlet.service(httpRequest, httpResponse);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}


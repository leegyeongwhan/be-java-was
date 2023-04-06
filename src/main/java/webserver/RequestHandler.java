package webserver;

import java.io.*;
import java.net.Socket;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;


public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private RequestMapping requestMapping = new RequestMapping();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다. 구현로직만 있는 유틸성 클래스를 구현한다.

            HttpRequest httpRequest = new HttpRequest(in);

            //TODO HttpResponse로 out을 받는것 이 맞을까?
            HttpResponse httpResponse = new HttpResponse(out);

            //TODO 스프링에서는 requestMapping을 통해 맞는 컨트롤러 메서드를 실행해준다 requestMapping을 구현해본다.
            //ToDO 현재 HttpRequest에서 url을 정한다 redirect나 "/" 같은 상황에 대처하며 경로를 편하게 하기위해 requestMapping을 구현한다.\
            log.debug("RequestHandler  HttpRequest : {}", httpRequest);

            //다음 url을 기준으로 정적파일을 만들지 동적을 만들지 구분한다.
            if (httpRequest.getUrl().startsWith("/css") || httpRequest.getUrl().startsWith("/js") || httpRequest.getUrl().startsWith("/fonts")
                    || httpRequest.getUrl().startsWith("/favicon.ico")) {
                httpResponse.handleStaticResource(httpRequest, httpResponse);
            } else {
                Controller controller = requestMapping.mapping(httpRequest.getUrl());
                log.debug("controller : {}", controller);
                controller.service(httpRequest, httpResponse);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }


    //TODO css/bootstrap.min.css   /css/styles.css /js/jquery-2.2.0.min.js  /js/scripts.js  /js/bootstrap.min.js  /favicon.ico
    // 다음과 같은 정적 파일들은 따로 랜더링 한다.


}


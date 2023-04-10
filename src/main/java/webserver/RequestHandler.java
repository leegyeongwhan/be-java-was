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
    //rivate handleStaticResource staticResource = new handleStaticResource();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다. 구현로직만 있는 유틸성 클래스를 구현한다.
            //halder
            HttpRequest httpRequest = new HttpRequest(in);

            //TODO HttpResponse로 out을 받는것 이 맞을까?
            HttpResponse httpResponse = new HttpResponse(out);

            //log.debug("Http static URL {}", httpRequest.getStaticUrl());
            //TODO 스프링에서는 requestMapping을 통해 맞는 컨트롤러 메서드를 실행해준다 requestMapping을 구현해본다.
            //ToDO 현재 HttpRequest에서 url을 정한다 redirect나 "/" 같은 상황에 대처하며 경로를 편하게 하기위해 requestMapping을 구현한다

           // log.debug("httpRequest : {}", httpRequest.toString());
            Controller controller = requestMapping.mapping(httpRequest.getUrl());
            if (controller != null) {
                log.debug("controller : {}", controller);
                controller.service(httpRequest, httpResponse);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}


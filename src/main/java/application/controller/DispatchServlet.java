package application.controller;

import view.MyView;
import webserver.RequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.MappedRequest;
import webserver.RequestHandler;
import webserver.RequestMapping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DispatchServlet implements MyServlet {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final RequestMapping requestMapping = new RequestMapping();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        log.debug("request.getContentTypePath() : {}", request.getContentTypePath());

        Controller controller = requestMapping.mapping(request.getUrl());
        log.debug("controller : {}", controller);

        if (controller instanceof DefaultController) {
            DefaultController defaultController = (DefaultController) controller;
            defaultController.doGet(request, response);
            return;
        }

        //TODO 컨트롤러의 상위 단인 controller를 통해
        try {
            log.debug("request.getMethod(): {}", request.getMethod());
            MappedRequest mappedRequest = new MappedRequest(request.getMethod(), request.getUrl());
            log.debug("mappedRequest: {}", mappedRequest);
            Method controllerMethod = RequestMapper.get(mappedRequest);
            log.debug("controllerMethod: {}", controllerMethod);
            //     String view = (String) controllerMethod.invoke(controller);
            String view = (String) controllerMethod.invoke(controller, request, response);
            MyView myView = new MyView(view, request);
            log.debug("view: {}", view);
            myView.viewResolver(request, response);

        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> createParamMap(HttpRequest request) {
        return request.getHttpRequestBody().getBody();
    }
}

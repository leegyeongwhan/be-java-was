package application.controller;

import view.ViewResolver;
import webserver.RequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.dto.RequestDto;
import webserver.RequestHandler;
import webserver.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DispatchServlet implements MyServlet {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final RequestMapping requestMapping = new RequestMapping();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        log.info("request.getUrl()", request.getUrl());
        Controller controller = requestMapping.mapping(request.getUrl());

        if (controller instanceof DefaultController) {
            DefaultController defaultController = (DefaultController) controller;
            defaultController.doGet(request, response);
            return;
        }

        //TODO 컨트롤러의 상위 단인 controller를 통해
        try {
            RequestDto requestDto = new RequestDto(request.getMethod(), request.getUrl().replace(".html", ""));
            log.info("controller", controller);
            Method controllerMethod = RequestMapper.get(requestDto);
            String path = (String) controllerMethod.invoke(controller, request, response);
            log.debug("view: {}", path);
            ViewResolver.run(path, response);

        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Map<String, String> createParamMap(HttpRequest request) {
        return request.getHttpRequestBody().getBody();
    }
}

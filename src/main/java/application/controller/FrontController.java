package application.controller;

import application.controller.exception.HttpRequestException;
import http.response.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import util.HttpMethod;
import view.ModelAndView;

import static util.HttpMethod.GET;
import static util.HttpMethod.POST;


public class FrontController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(FrontController.class);

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        throw new HttpRequestException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        throw new HttpRequestException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected ModelAndView doPost(HttpRequest request, HttpResponse response) {
        throw new HttpRequestException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    public void findMethod(HttpRequest request, HttpResponse response) {
        HttpMethod method = request.getMethod();
        log.debug("FrontController method : {}", request.getMethod());
        if (method.equals(GET)) {
            doGet(request, response);
        }
        if (method.equals(POST)) {
            doPost(request, response);
        }
        //throw new NotSupportedHttpMethodException();
    }
}

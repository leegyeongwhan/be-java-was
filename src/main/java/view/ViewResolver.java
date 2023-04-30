package view;

import http.request.ContentType;
import http.response.HttpResponse;
import http.response.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import webserver.RequestHandler;

public class ViewResolver {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    public static void run(String viewPath, HttpResponse response) {
        //리다이렉트 하는경우
        response.setHttpVersion("HTTP/1.1");

        if (viewPath.contains("redirect:")) {
            redirect(viewPath, response);
            return;
        }
         forward(viewPath, response);
    }

    private static void forward(String viewPath, HttpResponse response) {
        ContentType contentType = HttpRequestUtils.findContent(viewPath);
        response.setStatus(HttpStatus.OK)
                .addHeader("Content-Type", contentType.getContentTypeHeader())
                .setContentType(contentType)
                .setView(viewPath);

        response.render();
    }

    private static void redirect(String viewPath, HttpResponse response) {
        String viewName = viewPath.replace("redirect:", "");

        response.setStatus(HttpStatus.FOUND)
                .addHeader("Location", viewName)
                .setView(viewName);

        response.render();
    }
}

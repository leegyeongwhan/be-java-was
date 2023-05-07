package http.response;

import application.model.User;
import cookie.Cookie;
import http.request.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;
import view.templateengine.MustacheTemplateEngine;
import view.templateengine.TemplateEngine;

import java.io.*;
import java.nio.file.Files;
import java.util.Collection;

public class HttpResponse {
    private static final String COOKIE_NAME_PATH = "Path";
    private static final String COOKIE_VALUE_PATH = "=/";

    //ToDO  응답 코드와 헤더를 먼저 설정. 이후에는 응답 바디를 만든다.
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    //TODO 사용해보자.
    private HttpStatus httpStatus;
    private String version;
    private ModelAndView modelAndView;
    private HttpResponseHeader httpResponseHeader;
    private ContentType contentType;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.modelAndView = new ModelAndView();
        this.httpResponseHeader = new HttpResponseHeader();
    }

    public HttpResponse response200Header(int lengthOfBodyContent, String contentType) {
        try {
            log.debug("Content-Type: " + contentType + "\r\n");
            log.debug("Content-Lengthe: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("esponse200Header defalut Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return this;
    }

    public HttpResponse responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return this;
    }

    //200
    public void render() {
        try {
            dos.writeBytes("HTTP/1.1 " + this.httpStatus.getCode() + this.httpStatus.getMessage() + " \r\n");
            dos.writeBytes(responseReadHeaderLine());
            //리다이렉트하는 경우 바디가 없다.
            if (hasBody()) {
                byte[] body = Files.readAllBytes(new File(contentType.getTypeDirectory() + modelAndView.getView()).toPath());
                //TODO 동적 웹페이지
                TemplateEngine templateEngine = new MustacheTemplateEngine();
                byte[] compile = templateEngine.compile(body, modelAndView);
                responseBody(compile);
            } else {
                dos.flush();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private boolean hasBody() {
        return contentType != null ? true : false;
    }

    private void response(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
        }
    }

    private String responseReadHeaderLine() throws IOException {
        StringBuffer headerString = new StringBuffer();
        httpResponseHeader.getHeaders().entrySet().stream()
                .forEach(e -> headerString.append(e.getKey()).append(": ").append(e.getValue())
                        .append("\r\n"));

        return headerString.append("\r\n").toString();

    }

    public HttpResponse setHttpVersion(String version) {
        this.version = version;
        return this;
    }

    public HttpResponse setStatus(HttpStatus status) {
        this.httpStatus = status;
        return this;
    }

    public HttpResponse addHeader(String key, String value) {
        this.httpResponseHeader.put(key, value);
        return this;
    }

    public void addCookie(String sid, String session) {
        //Todo 쿠키는 클라이언트 브라우저가
        addHeader("Set-Cookie", sid + "=" + session + "; " + " Domain=localhost; " + COOKIE_NAME_PATH + COOKIE_VALUE_PATH);
    }

    public HttpResponse setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public HttpResponse setView(String view) {
        modelAndView.setView(view);
        return this;
    }

    public void setModelAttribute(String listName, Collection<User> list) {
        modelAndView.setModelAttribute(listName, list);
    }

    public void setModelAttribute(String key, String value) {
        modelAndView.setModelAttribute(key, value);
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpStatus=" + httpStatus +
                ", version='" + version + '\'' +
                ", view=" + modelAndView +
                ", httpResponseHeader=" + httpResponseHeader +
                '}';
    }


}

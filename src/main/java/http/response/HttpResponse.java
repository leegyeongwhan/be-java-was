package http.response;

import cookie.Cookie;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.MyView;

import java.io.*;
import java.nio.file.Files;

public class HttpResponse {
    private static final String COOKIE_NAME_PATH = "Path";
    private static final String COOKIE_VALUE_PATH = "/";

    //ToDO  응답 코드와 헤더를 먼저 설정. 이후에는 응답 바디를 만든다.
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    //TODO 사용해보자.
    private HttpStatus httpStatus;
    private String version;
    private MyView view;
    private HttpResponseHeader httpResponseHeader;

    public HttpResponse(OutputStream out, HttpRequest request) {
        this.dos = new DataOutputStream(out);
        this.httpResponseHeader = new HttpResponseHeader();
    }

    public void sendRedirect(String url) {
        addCookieHeader();
        response302Header(url);
    }

    public HttpResponse response302Header(String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return this;
    }

    public HttpResponse response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + "text/html;charset=utf-8" + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return this;
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

    public HttpStatus getStatus() {
        return httpStatus;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void addSessionCookie(Cookie mySessionCookie) {
        httpResponseHeader.addMySessionCookie(mySessionCookie);
    }

    public void addCookie(String name, String value) {
        this.httpResponseHeader.addCookie(name, value);
    }

    private void addCookieHeader() {
        if (this.httpResponseHeader.isEmptyCookie()) {
            return;
        }

        this.httpResponseHeader.addCookie(COOKIE_NAME_PATH, COOKIE_VALUE_PATH);
        String parseCookie = this.httpResponseHeader.parseCookie();
        log.debug("addCookieHeader : {}", parseCookie);
        addHeader("Set-Cookie", parseCookie);
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "dos=" + dos +
                ", httpStatus=" + httpStatus +
                '}';
    }

    public void render() throws IOException {
        byte[] body = Files.readAllBytes(new File(view.getRequest().getTypeDirectory() + view.getViewPath()).toPath());
        // getResponseHeader();
        response200Header(body.length, view.getRequest().getContentTypeHeader());
        addCookieHeader();
        responseBody(body);
    }

    private void setContentLength(int length) {
        httpResponseHeader.put("Content-Length", String.valueOf(length));
    }

    private byte[] getResponseHeader() {
        String headLine = version + " " + getHttpStatus().getCode() + " " + getStatus().getMessage();
        return (headLine + "\r\n" + httpResponseHeader).getBytes();
    }

    public HttpResponse setHttpVersion(String version) {
        this.version = version;
        return this;
    }

    public HttpResponse setStatus(HttpStatus status) {
        this.httpStatus = status;
        return this;
    }

    public HttpResponse setView(MyView view) {
        this.view = view;
        return this;
    }

    public HttpResponse addHeader(String key, String value) {
        this.httpResponseHeader.put(key, value);
        return this;
    }
}

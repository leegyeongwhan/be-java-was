package http.response;

import cookie.Cookie;
import http.request.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import view.ModelAndView;
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
    private ModelAndView modelAndView;
    private HttpResponseHeader httpResponseHeader;
    private ContentType contentType;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
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

    private void response(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
        }
    }


    public void addSessionCookie(Cookie mySessionCookie) {
        httpResponseHeader.addMySessionCookie(mySessionCookie);
    }

    public void addCookieHeader() {
        if (this.httpResponseHeader.isEmptyCookie()) {
            return;
        }
        String parseCookie = this.httpResponseHeader.parseCookie();
        this.httpResponseHeader.addCookie(COOKIE_NAME_PATH, COOKIE_VALUE_PATH);
        addHeader("Set-Cookie", parseCookie);
        log.debug("addCookieHeader : {}", parseCookie);
        log.debug("httpResponseHeader : {}", httpResponseHeader);
    }

    //200
    public void render() {
        try {
            dos.writeBytes("HTTP/1.1 " + this.httpStatus.getCode() + this.httpStatus.getMessage() + " \r\n");
            dos.writeBytes(responseReadHeaderLine());
            //Set-cookie: sid=f50ed4f1-c07c-4a6d-b2d6-9d25f2aec460; Path=/
//            if (this.body != null) {
//                dos.write(this.body, 0, body.length);
//            }
            //    if (hasResponseBody()) {
            //modelAndView.getRequest().getTypeDirectory() + modelAndView.getViewPath()
            byte[] body = Files.readAllBytes(new File(contentType.getTypeDirectory() + modelAndView.getView()).toPath());
            responseBody(body);
            //    }
            dos.flush();
        } catch (IOException e) {
            e.getMessage();
        }

//        responseReadStatusLine();
//        responseReadHeaderLine();
//        if (hasResponseBody()) {
//            byte[] body = Files.readAllBytes(new File(view.getRequest().getTypeDirectory() + view.getViewPath()).toPath());
//            responseBody(body);
//        }
    }

    private void response(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
        }
    }

    private boolean hasResponseBody() {
        return httpResponseHeader.getContentLength();
    }

    private String responseReadHeaderLine() throws IOException {
        StringBuffer headerString = new StringBuffer();
        httpResponseHeader.getHeaders().entrySet().stream()
                .forEach(e -> headerString.append(e.getKey()).append(": ").append(e.getValue())
                        .append("\r\n"));

        return headerString.append("\r\n").toString();

//        for (Map.Entry<String, String> entry : httpResponseHeader.getHeaders().entrySet()) {
//            dos.writeBytes(
//                    entry.getKey() + ": " + entry.getValue() + System.lineSeparator());
//        }
//        return this;
    }

    public HttpResponse setHttpVersion(String version) {
        this.version = version;
        return this;
    }

    public HttpResponse setStatus(HttpStatus status) {
        this.httpStatus = status;
        return this;
    }

    public HttpResponse setView(String view) {
        this.modelAndView = new ModelAndView(view);
        return this;
    }

    public HttpResponse addHeader(String key, String value) {
        this.httpResponseHeader.put(key, value);
        return this;
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

    public void addCookie(String sid, String session) {
        Cookie mySessionCookie = new Cookie(sid, session);
        addSessionCookie(mySessionCookie);
    }

    public HttpResponse setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }
}

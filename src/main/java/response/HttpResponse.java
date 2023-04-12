package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class HttpResponse {
    //ToDO  응답 코드와 헤더를 먼저 설정. 이후에는 응답 바디를 만든다.
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    //TODO 사용해보자.
    private HttpStatus httpStatus;
//    private String contentType;
//    private byte[] responseBody;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void sendRedirect(String url) {
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

    public void setStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;

public class HttpResponse {
    //ToDO  응답 코드와 헤더를 먼저 설정. 이후에는 응답 바디를 만든다.
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static DataOutputStream dos;
    //TODO 사용해보자.
    private HttpStatus httpStatus;
    private String contentType;
    private byte[] responseBody;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void sendRedirect(String url) {
        response302Header(url);
    }

    public void response302Header(String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + "text/html;charset=utf-8" + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void response200Header(int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            log.debug("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
            log.error("dos : {}", dos);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpStatus=" + httpStatus +
                ", contentType='" + contentType + '\'' +
                ", responseBody=" + Arrays.toString(responseBody) +
                '}';
    }

    public void handleStaticResource(HttpRequest request, HttpResponse response) throws IOException {
        String resourcePath = request.getUrl(); // 앞에 '/' 제거
        log.debug("resourcePath : {}", resourcePath);

        // 요청한 파일이 존재하는지 확인
        File file = new File("src/main/resources/static" + resourcePath);

        if (!file.exists()) {
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            return;
        }

        //TODO  차후 request 에서 컨텐트 타입을 만든다. 파일 타입 확인
        String contentType;
        if (resourcePath.startsWith("/html")) {
            contentType = "text/html;charset=utf-8";
        } else if (resourcePath.startsWith("/css")) {
            contentType = "text/css";
        } else if (resourcePath.startsWith("/js")) {
            contentType = "application/javascript";
        } else {
            contentType = "application/octet-stream";
        }

        // 파일 읽기
        byte[] body = Files.readAllBytes(new File("src/main/resources/static" + resourcePath).toPath());

        log.debug("resourcePath파일읽은 후 : {}", resourcePath);

        // HTTP 응답 전송
        response.setHttpStatus(HttpStatus.OK);
        response.setContentType(contentType);
        log.debug("HTTP 응답 전송 : {}", response);
        response.response200Header(body.length, contentType);
        response.responseBody(body);
    }
}

package http.request;

import cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;
import webserver.RequestHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private HttpRequestStartLine httpRequestStartLine;
    //header
    private HttpRequestHeader httpRequestHeader;
    //body
    private HttpRequestBody httpRequestBody;

    //TODO 정적 팩토리 메서드(Static Factory Method)
    public static HttpRequest of(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        //TODO startLine, header ,body 으로 구분한다.
        HttpRequestStartLine requestLine = HttpRequestStartLine.of(br);
        HttpRequestHeader requestHeader = HttpRequestHeader.of(br);
        HttpRequestBody requestBody = HttpRequestBody.of(br, requestHeader.getContentLength());

        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    private HttpRequest(HttpRequestStartLine httpRequestStartLine, HttpRequestHeader httpRequestHeader, HttpRequestBody httpRequestBody) {
        this.httpRequestStartLine = httpRequestStartLine;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequestStartLine getHttpRequestStartLine() {
        return httpRequestStartLine;
    }

    public HttpRequestHeader getHttpRequestHeader() {
        return httpRequestHeader;
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }

    public String getUrl() {
        return httpRequestStartLine.getUrl();
    }

    public String getTypeDirectory() {
        return getHttpRequestHeader().getContentType().getTypeDirectory();
    }

    public String getContentTypeHeader() {
        return getHttpRequestHeader().getContentType().getContentTypeHeader();
    }

    public String getContentTypePath() {
        return getHttpRequestHeader().getContentType().getTypePath();
    }

    public HttpMethod getMethod() {
        return httpRequestStartLine.getMethod();
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "httpRequestStartLine=" + httpRequestStartLine +
                ", httpRequestHeader=" + httpRequestHeader +
                ", httpRequestBody=" + httpRequestBody +
                '}';
    }

    public String getParameter(String key) {
        return httpRequestBody.getValue(key);
    }

    public Optional<String> getCookie() {
        return httpRequestHeader.getCookie();
    }
}

package http.dto;


import util.HttpMethod;

import java.util.Objects;

public class RequestDto {
    private HttpMethod httpMethod;
    private String path;

    public RequestDto() {
    }

    public RequestDto(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RequestDto that = (RequestDto) o;
        return httpMethod == that.httpMethod &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path);
    }

    @Override
    public String toString() {
        return "MappedRequest{" +
                "httpMethod=" + httpMethod +
                ", path='" + path + '\'' +
                '}';
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }
}


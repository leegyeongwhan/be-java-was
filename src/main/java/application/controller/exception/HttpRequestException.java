package application.controller.exception;

import http.response.HttpStatus;

public class HttpRequestException extends RuntimeException {

    public HttpRequestException(HttpStatus httpStatus) {
        super(httpStatus.getCode() + httpStatus.getMessage());
    }
}

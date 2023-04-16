package http.response;

public enum HttpStatus {

    OK(200, "OK"),
    FOUND(302, "FOUND"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    private final int code;
    private final String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
            return code;
    }

    public String getMessage() {
        return message;
    }

    public static HttpStatus valueOf(int statusCode) {
        for (HttpStatus status : values()) {
            if (status.code == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }
}

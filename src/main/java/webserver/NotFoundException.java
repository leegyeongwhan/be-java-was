package webserver;

public class NotFoundException extends Throwable {
    public NotFoundException(ReflectiveOperationException e) {
        super(e);
    }
}

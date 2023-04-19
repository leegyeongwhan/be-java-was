package application.controller.exception;

public class NotFindController extends RuntimeException {

    public NotFindController(Object[] e) {
        super(e + "컨트롤러를 찾을 수 없습니다.");
    }
}

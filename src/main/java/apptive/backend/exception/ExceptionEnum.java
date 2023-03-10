package apptive.backend.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {

    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    //로그인 관련 에러
    PWD_CONDITION_EXCEPTION(HttpStatus.BAD_REQUEST, "E0002", "비밀번호가 같지 않습니다."),
    SAME_MEMBER_NAME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0003", "같은 닉네임이 존재합니다.");

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

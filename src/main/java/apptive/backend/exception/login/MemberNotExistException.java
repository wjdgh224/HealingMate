package apptive.backend.exception.login;

import apptive.backend.exception.ExceptionEnum;
import lombok.Getter;

@Getter
public class MemberNotExistException extends RuntimeException{

    private final ExceptionEnum error;

    public MemberNotExistException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}

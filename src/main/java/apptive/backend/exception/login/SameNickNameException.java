package apptive.backend.exception.login;

import apptive.backend.exception.ExceptionEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SameNickNameException extends RuntimeException{

    private final ExceptionEnum error;

    public SameNickNameException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}

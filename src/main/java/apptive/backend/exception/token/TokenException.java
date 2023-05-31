package apptive.backend.exception.token;

import apptive.backend.exception.ExceptionEnum;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {
    private final ExceptionEnum error;

    public TokenException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}

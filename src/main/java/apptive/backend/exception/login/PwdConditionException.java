package apptive.backend.exception.login;

import org.springframework.http.HttpStatus;

public class PwdConditionException extends LoginException{

    public PwdConditionException(String message) {

        super(message, HttpStatus.BAD_REQUEST);
    }
}

package apptive.backend.exception.login;

import org.springframework.http.HttpStatus;

public class LoginException extends RuntimeException{

    private final HttpStatus httpStatus;

    public LoginException(final String message, final HttpStatus httpStatus) {

        super(message);
        this.httpStatus = httpStatus;
    }

}

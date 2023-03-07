package apptive.backend.exception.login;

import org.springframework.http.HttpStatus;

public class SameNickNameException extends LoginException{

    private static final String MESSAGE = "중복된 닉네임이 존재합니다.";

    public SameNickNameException() {

        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}

package apptive.backend.exception;

import apptive.backend.exception.login.LoginException;
import apptive.backend.exception.login.PwdConditionException;
import apptive.backend.exception.login.SameNickNameException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler({LoginException.class})
    public ResponseEntity<ExceptionEntity> exceptionHandler(HttpServletRequest request, final LoginException e) {
        //e.printStackTrace();
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ExceptionEntity.builder()
                        .errorCode(e.getError().getCode())
                        .errorMessage(e.getError().getMessage())
                        .build());
    }

    @ExceptionHandler({PwdConditionException.class})
    public ResponseEntity<ExceptionEntity> exceptionHandler(HttpServletRequest request, final PwdConditionException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.PWD_NOT_SAME_EXCEPTION.getStatus())
                .body(ExceptionEntity.builder()
                        .errorCode(ExceptionEnum.PWD_NOT_SAME_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({SameNickNameException.class})
    public ResponseEntity<ExceptionEntity> exceptionHandler(HttpServletRequest request, final SameNickNameException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.SAME_MEMBER_NAME_EXCEPTION.getStatus())
                .body(ExceptionEntity.builder()
                        .errorCode(ExceptionEnum.SAME_MEMBER_NAME_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    /*@ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ExceptionEntity> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionEntity> exceptionHandler(HttpServletRequest request, final Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }*/
}

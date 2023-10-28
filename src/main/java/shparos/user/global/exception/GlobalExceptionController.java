package shparos.user.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<ErrorResponse> customExHandle(CustomException e) {
        log.error("[exceptionHandle] ex", e);
        return ErrorResponse.toResponseEntity(e.getResponseCode());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> loginExHandle(BadCredentialsException e) {
        return ErrorResponse.toResponseEntity(ResponseCode.LOGIN_FAIL);
    }


}

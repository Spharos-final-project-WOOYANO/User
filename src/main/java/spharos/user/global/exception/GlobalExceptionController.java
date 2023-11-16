package spharos.user.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spharos.user.global.common.response.ErrorResponse;
import spharos.user.global.common.response.ResponseCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<?> customExHandle(CustomException e) {
        log.error("[exceptionHandle] ex", e);
        return ErrorResponse.toResponseEntity(e.getResponseCode());
    }

//    @ExceptionHandler(value = { CustomException.class })
//    public ResponseEntity<ErrorResponse> customExHandle(CustomException e) {
//        log.error("[exceptionHandle] ex", e);
//        return ErrorResponse.toResponseEntity(e.getResponseCode());
//    }

    /*
        아이디가 없거나 비밀번호가 틀린 경우 AuthenticationManager 에서 발생
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> loginExHandle(BadCredentialsException e) {
        return ErrorResponse.toResponseEntity(ResponseCode.LOGIN_FAIL);
    }


}

package shparos.user.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

//    USER_ID_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
//    USER_NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호를 확인해 주세요."),
    WITHDRAW_USER(HttpStatus.BAD_REQUEST, "탈퇴한 회원입니다."),
    DORMANT_USER(HttpStatus.BAD_REQUEST, "휴면 회원입니다.");
    ;


    private final HttpStatus httpStatus;
    private final String detail;


}

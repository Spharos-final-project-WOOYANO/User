package shparos.user.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(HttpStatus.OK,true, 200, "요청에 성공하였습니다."),


    /**
     * 에러 코드
     */
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, false, 1010, "아이디 또는 비밀번호를 확인해 주세요."),
    WITHDRAW_USER(HttpStatus.BAD_REQUEST, false, 1020,"탈퇴한 회원입니다."),
    DORMANT_USER(HttpStatus.BAD_REQUEST, false, 1030,"휴면 회원입니다."),
    NOT_EXISTS_USER_EMAIL(HttpStatus.BAD_REQUEST, false, 1040,"해당하는 유저 이메일이 존재하지 않습니다."),
    CANNOT_FIND_USER(HttpStatus.BAD_REQUEST, false, 1050,"유저 정보를 찾을 수 없습니다."),
    CANNOT_FIND_ADDRESS(HttpStatus.BAD_REQUEST, false, 1060,"주소 정보를 찾을 수 없습니다."),
    CANNOT_DELETE_DEFAULT_ADDRESS(HttpStatus.BAD_REQUEST, false, 1070,"대표주소는 삭제 할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final boolean success;
    private final int code;
    private final String message;

}

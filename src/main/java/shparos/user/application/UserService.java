package shparos.user.application;

import shparos.user.dto.UserSignUpDto;
import shparos.user.vo.UserLoginIn;
import shparos.user.vo.UserLoginOut;
import shparos.user.vo.UserSignUpIn;
import shparos.user.vo.UserSignUpOut;

public interface UserService {

    // 이메일 중복 체크
    Boolean checkEmail(String email);
    // 닉네임 중복 체크
    Boolean checkNickname(String nickname);
    // 회원가입
    UserSignUpOut join(UserSignUpIn userSignUpIn);
    // 로그인
    UserLoginOut login(UserLoginIn userLoginIn);

}

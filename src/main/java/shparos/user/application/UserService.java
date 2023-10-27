package shparos.user.application;

import shparos.user.dto.UserSignUpDto;
import shparos.user.vo.*;

public interface UserService {

    // 이메일 중복 체크
    Boolean checkEmail(String email);
    // 닉네임 중복 체크
    Boolean checkNickname(String nickname);
    // 회원가입
    UserSignUpOut join(UserSignUpIn userSignUpIn);
    // 로그인
    UserLoginOut login(UserLoginIn userLoginIn);
    // 아이디(이메일) 찾기
    String findEmail(String username, String phone);
    // 비밀번호 변경
    void modifyPassword(UserChangePasswordIn userChangePasswordIn);

}

package shparos.user.users.application;

import shparos.user.users.domain.User;
import shparos.user.users.vo.*;

public interface UserService {

    // 이메일 중복 체크
    Boolean checkEmailExist(String email);
    // 닉네임 중복 체크
    Boolean checkNickname(String nickname);
    // 회원가입
    UserSignUpResponse join(UserSignUpRequest userSignUpRequest);
    // 로그인
    UserLoginOut login(UserLoginIn userLoginIn);
    // 아이디(이메일) 찾기
    UserFindEmailResponse findEmail(String username, String phone);
    // 비밀번호 변경
    void modifyPassword(UserChangePasswordRequest userChangePasswordRequest);
    // 토큰속 이메일로 유저정보 찾기
    User getUserFromToken(String token);

}

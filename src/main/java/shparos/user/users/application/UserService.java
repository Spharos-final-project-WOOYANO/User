package shparos.user.users.application;

import shparos.user.users.domain.User;
import shparos.user.users.dto.UserModifyDto;
import shparos.user.users.dto.UserPasswordChangeDto;
import shparos.user.users.dto.UserPasswordCheckDto;
import shparos.user.users.dto.UserWithdrawCheckDto;
import shparos.user.users.vo.*;

public interface UserService {

    // 이메일 중복 체크
    Boolean checkEmailExist(String email);
    // 닉네임 중복 체크
    Boolean checkNickname(String nickname);
    // 회원가입
    UserSignUpResponse join(UserSignUpRequest userSignUpRequest);
    // 로그인
    UserLoginResponse login(UserLoginRequest userLoginRequest);
    // 아이디(이메일) 찾기
    UserFindEmailResponse findEmail(String username, String phone);
    // 비밀번호 변경
    void modifyPassword(UserPasswordChangeDto userPasswordChangeDto);
    // 이메일로 유저정보 찾기
    User getUserFromEmail(String email);
    // 이름과 이메일로 해당하는 유저가 존재하는지 체크
    Boolean checkExistEmailByNameAndEmail(String username, String email);
    // 이메일과 비밀번호로 유저 확인
    Boolean checkPassword(UserPasswordCheckDto userPasswordCheckDto);
    // 회원정보조회
    UserInformationResponse getUserInformation(String email);
    // 회원정보 수정
    void modifyUserInformation(UserModifyDto userModifyDto);
    // 회원탈퇴전 회원확인
    Boolean checkUserBeforeWithdraw(UserWithdrawCheckDto userWithdrawCheckDto);
    // 회원탈퇴
    void withdrawUser(String email);

}

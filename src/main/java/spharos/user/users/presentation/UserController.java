package spharos.user.users.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.user.global.common.response.BaseResponse;
import spharos.user.users.application.UserService;
import spharos.user.users.dto.UserPasswordChangeDto;
import spharos.user.users.vo.request.UserChangePasswordRequest;
import spharos.user.users.vo.request.UserLoginRequest;
import spharos.user.users.vo.request.UserSignUpRequest;
import spharos.user.users.vo.response.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /*
        이메일 중복 체크
     */
    @Operation(summary = "이메일 중복 체크",
            description = "해당 이메일이 데이터베이스에 존재하는지 체크",
            tags = { "User SignUp" })
    @GetMapping("/email/check")
    public BaseResponse<?> checkEmail(@RequestParam("email") String email) {

        // 이메일이 존재하는지 체크
        Boolean checkResult = userService.checkEmailExist(email);

        UserEmailCheckResponse response = UserEmailCheckResponse.builder()
                .checkResult(checkResult)
                .build();

        return new BaseResponse<>(response);
    }

    /*
        회원가입시 닉네임 중복확인
     */
    @Operation(summary = "회원가입시 닉네임 중복확인",
            description = "회원가입시 중복되는 닉네임이 있는지 확인",
            tags = { "User SignUp" })
    @GetMapping("/nickname/check")
    public BaseResponse<?> checkNickname(@RequestParam("nickname") String nickname) {

        Boolean checkResult = userService.checkNickname(nickname);

        UserNicknameCheckResponse response = UserNicknameCheckResponse.builder()
                .checkResult(checkResult)
                .build();

        return new BaseResponse<>(response);
    }

    /*
        회원가입
     */
    @Operation(summary = "회원가입", description = "회원가입", tags = { "User SignUp" })
    @PostMapping("/join")
    public BaseResponse<?> join(@RequestBody UserSignUpRequest userSignUpRequest) {
        UserSignUpResponse userSignUpResponse = userService.join(userSignUpRequest);
        return new BaseResponse<>(userSignUpResponse);
    }

    /*
        로그인
     */
    @Operation(summary = "로그인", description = "로그인", tags = { "User Login" })
    @PostMapping("/login")
    public BaseResponse<?> loginIn(@RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResponse userLoginResponse = userService.login(userLoginRequest);
        return new BaseResponse<>(userLoginResponse);
    }

    /*
        간편 로그인
     */

    /*
        아이디 찾기
     */
    @Operation(summary = "아이디 찾기",
            description = "이름과 휴대폰 번호를 입력하여 아이디(이메일)을 찾음",
            tags = { "User FindEmail" })
    @GetMapping("/email/find")
    public BaseResponse<?> findEmail(@RequestParam("username") String username,
                                            @RequestParam("phone") String phone) {
        // 아이디 찾기
        UserFindEmailResponse response = userService.findEmail(username, phone);
        return new BaseResponse<>(response);
    }

    /*
        이름과 이메일로 해당하는 유저가 존재하는지 체크
     */
    @Operation(summary = "이름과 이메일로 해당하는 유저가 존재하는지 체크",
            description = "비밀번호 변경 인증 메일 전송 전 이름과 이메일로 해당하는 유저가 존재하는지 체크",
            tags = { "User ChangePassword" })
    @GetMapping("/email/exist/check")
    public BaseResponse<?> checkExistEmailByNameAndEmail(@RequestParam("username") String username,
                                                         @RequestParam("email") String email) {

        // 이름과 이메일로 해당하는 유저가 존재하는지 체크
        Boolean checkResult = userService.checkExistEmailByNameAndEmail(username, email);
        UserEmailExistCheckResponse response = UserEmailExistCheckResponse.builder()
                .checkResult(checkResult)
                .build();
        return new BaseResponse<>(response);
    }

    /*
        비밀번호 변경
     */
    @Operation(summary = "비밀번호 변경",
            description = "비밀번호 변경",
            tags = { "User ChangePassword" })
    @PutMapping("/password")
    public BaseResponse<?> changePassword(@RequestBody UserChangePasswordRequest userChangePasswordRequest) {

        // 비밀번호 변경
        UserPasswordChangeDto dto = UserPasswordChangeDto.builder()
                .email(userChangePasswordRequest.getEmail())
                .password(userChangePasswordRequest.getPassword())
                .build();
        userService.modifyPassword(dto);
        return new BaseResponse<>();
    }

}

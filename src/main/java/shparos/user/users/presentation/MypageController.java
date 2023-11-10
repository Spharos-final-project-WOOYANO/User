package shparos.user.users.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shparos.user.global.common.response.BaseResponse;
import shparos.user.users.application.UserService;
import shparos.user.users.dto.UserPasswordCheckDto;
import shparos.user.users.vo.UserPasswordCheckRequest;
import shparos.user.users.vo.UserPasswordCheckResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/mypage")
public class MypageController {

    private final UserService userService;

    /*
        비밀번호확인
     */
    @Operation(summary = "비밀번호확인",
            description = "마이페이지에 들어가기전 비밀번호확인",
            tags = { "User Mypage" })
    @PostMapping("/password/check")
    public BaseResponse<?> checkPassword(@RequestHeader("email") String email,
                                         @RequestBody UserPasswordCheckRequest userPasswordCheckRequest) {

        UserPasswordCheckDto dto = UserPasswordCheckDto.builder()
                .email(email)
                .password(userPasswordCheckRequest.getPassword())
                .build();

        // 비밀번호 일치 확인
        Boolean checkResult = userService.checkPassword(dto);

        UserPasswordCheckResponse response = UserPasswordCheckResponse.builder()
                .checkResult(checkResult)
                .build();

        return new BaseResponse<>(response);
    }


    /*
        회원정보조회
     */

    /*
        닉네임 중복확인
     */

    /*
        회원정보 수정
     */

    /*
        비밀번호변경
     */

    /*
        회원확인
     */

    /*
        회원탈퇴
     */

}

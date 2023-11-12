package shparos.user.users.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shparos.user.global.common.response.BaseResponse;
import shparos.user.users.application.UserService;
import shparos.user.users.dto.UserModifyDto;
import shparos.user.users.dto.UserPasswordChangeDto;
import shparos.user.users.dto.UserPasswordCheckDto;
import shparos.user.users.vo.*;

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
    @Operation(summary = "회원정보조회", description = "회원정보조회", tags = { "User Mypage" })
    @GetMapping("/info")
    public BaseResponse<?> getUserInformation(@RequestHeader("email") String email) {

        // 회원정보 조회
        UserInformationResponse response = userService.getUserInformation(email);

        return new BaseResponse<>(response);
    }

    /*
        회원정보 수정
     */
    @Operation(summary = "회원정보 수정", description = "회원정보 수정", tags = { "User Mypage" })
    @PutMapping("/info")
    public BaseResponse<?> modifyUserInformation(@RequestHeader("email") String email,
                                                 @RequestBody UserInformationModifyRequest request)
    {
        // 회원정보 수정
        UserModifyDto userModifyDto = UserModifyDto.builder()
                .email(email)
                .username(request.getUsername())
                .birthday(request.getBirthday())
                .nickname(request.getNickname())
                .phone(request.getPhone())
                .build();
        userService.modifyUserInformation(userModifyDto);
        return new BaseResponse<>();
    }

    /*
        비밀번호변경
     */
    @Operation(summary = "비밀번호변경", description = "마이페이지에서 비밀번호변경", tags = { "User Mypage" })
    @PutMapping("/password")
    public BaseResponse<?> modifyUserInformation(@RequestHeader("email") String email,
                                                 @RequestBody UserMypageChangePasswordRequest request) {

        // 비밀번호변경
        UserPasswordChangeDto dto = UserPasswordChangeDto.builder()
                .email(email)
                .password(request.getPassword())
                .build();
        userService.modifyPassword(dto);
        return new BaseResponse<>();
    }

    /*
        회원확인
     */

    /*
        회원탈퇴
     */

}

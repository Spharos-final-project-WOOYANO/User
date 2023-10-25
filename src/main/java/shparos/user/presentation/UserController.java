package shparos.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shparos.user.application.EmailService;
import shparos.user.application.UserService;
import shparos.user.vo.UserSignUpIn;
import shparos.user.vo.UserSignUpOut;


//@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    /*
        회원가입시 이메일 중복체크
     */
    @Operation(summary = "회원가입시 이메일 중복체크",
            description = "회원가입시 중복되는 이메일이 있는지 확인",
            tags = { "User SignUp" })
    @GetMapping("/email/check")
    public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email) {
        Boolean checkResult = userService.checkEmail(email);
        return new ResponseEntity<>(checkResult, HttpStatus.OK);
    }

    /*
        회원가입시 이메일인증
     */
    @Operation(summary = "회원가입시 이메일인증",
            description = "유저가 입력한 이메일이 유효한 이메일인지 확인하기 위해코드를 발송",
            tags = { "User SignUp" })
    @GetMapping("/email/auth")
    public ResponseEntity<String> sendEmailCode(@RequestParam("name") String name,
                                                @RequestParam("email") String email) {

        // TODO 이메일전송 오류인 경우 예외처리 추가
        emailService.sendCheckMail(name,email);

        return new ResponseEntity<>("이메인 인증 요청 완료", HttpStatus.OK);
    }

    /*
        이메일 코드 확인
     */
    @Operation(summary = "이메일 코드 확인",
            description = "이메일 코드가 유효한지 확인",
            tags = { "User SignUp" })
    @GetMapping("/certnum/check")
    public ResponseEntity<Boolean> certifyEmailCode(@RequestParam("email") String email,
                                                    @RequestParam("code") String code) {
        Boolean checkResult = emailService.certifyEmailCode(email, code);
        return new ResponseEntity<>(checkResult, HttpStatus.OK);
    }


    /*
        회원가입시 닉네임 중복확인
     */
    @Operation(summary = "회원가입시 닉네임 중복확인",
            description = "회원가입시 중복되는 닉네임이 있는지 확인",
            tags = { "User SignUp" })
    @GetMapping("/nickname/check")
    public ResponseEntity<Boolean> checkNickname(@RequestParam("nickname") String nickname) {
        Boolean checkResult = userService.checkNickname(nickname);
        return new ResponseEntity<>(checkResult, HttpStatus.OK);
    }

    /*
        회원가입
     */
    @Operation(summary = "회원가입", description = "회원가입", tags = { "User SignUp" })
    @PostMapping("/join")
    public ResponseEntity<UserSignUpOut> join(@RequestBody UserSignUpIn userSignUpIn) {
        UserSignUpOut userSignUpOut = userService.join(userSignUpIn);
        return new ResponseEntity<>(userSignUpOut, HttpStatus.OK);
    }

    /*
        로그인
     */
    @Operation(summary = "로그인", description = "로그인", tags = { "User" })
    @PostMapping("/login")
    public ResponseEntity<String> loginIn() {

        return new ResponseEntity<>("로그인성공", HttpStatus.OK);
    }

}

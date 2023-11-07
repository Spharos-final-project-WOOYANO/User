package shparos.user.users.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shparos.user.address.application.AddressService;
import shparos.user.address.dto.AddressRegisterDto;
import shparos.user.address.dto.AddressRegisterResultDto;
import shparos.user.global.common.response.ResponseCode;
import shparos.user.users.domain.User;
import shparos.user.global.config.security.JwtTokenProvider;
import shparos.user.global.exception.CustomException;
import shparos.user.users.infrastructure.UserRepository;
import shparos.user.users.vo.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AddressService addressService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // 이메일 중복 체크
    @Override
    public Boolean checkEmailExist(String email) {

        Optional<User> user = userRepository.findByEmail(email);

        // 해당 이메일이 DB에 존재하면 체크 결과를 true로 리턴
        if(user.isPresent()){
            return Boolean.TRUE;
        }

        // 해당 이메일이 DB에 존재하지 않으면 체크 결과를 false로 리턴
        return Boolean.FALSE;
    }

    // 닉네임 중복 체크
    @Override
    public Boolean checkNickname(String nickname) {

        Optional<User> user = userRepository.findByNickname(nickname);

        // 해당 닉네임이 DB에 존재하면 체크 결과를 true로 리턴
        if(user.isPresent()){
            return Boolean.TRUE;
        }

        // 해당 닉네임이 DB에 존재하지 않으면 체크 결과를 false로 리턴
        return Boolean.FALSE;
    }

    // 회원가입
    @Override
    @Transactional
    public UserSignUpResponse join(UserSignUpRequest request) {

        // 비밀번호 암호화
        String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        // 유저 등록
        User user = User.createUser(request.getEmail(), hashedPassword, request.getBirthday(), request.getUsername(),
                request.getNickname(), request.getPhone(), 0);
        userRepository.save(user);

        // 주소 등록
        AddressRegisterDto addressRegisterDto = AddressRegisterDto.builder()
                .user(user)
                .localAddress(request.getLocalAddress())
                .extraAddress(request.getExtraAddress())
                .defaultAddress(Boolean.TRUE)
                .localCode(request.getLocalCode())
                .build();
        AddressRegisterResultDto resultDto = addressService.registerAddress(addressRegisterDto);

        // 리턴값 지정하기
        return UserSignUpResponse.builder()
                .email(user.getEmail())
                .username(user.getName())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .localAddress(resultDto.getLocalAddress())
                .extraAddress(resultDto.getExtraAddress())
                .build();
    }

    // 로그인
    @Override
    public UserLoginOut login(UserLoginIn userLoginIn) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginIn.getEmail(),
                        userLoginIn.getPassword()
                )
        );

        // 유저 확인
        User user = userRepository.findByEmail(userLoginIn.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_FAIL));

        // 유저 상태 확인
        if(user.getStatus() == 1) {
            // 탈퇴 유저인 경우
            throw new CustomException(ResponseCode.WITHDRAW_USER);
        } else if(user.getStatus() == 2) {
            // 휴면 유저인 경우
            throw new CustomException(ResponseCode.DORMANT_USER);
        }

        // 토큰발급
        String accessToken = jwtTokenProvider.generateToken(user);
        // 리프레시 토큰 발급 TODO
//        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return UserLoginOut.builder()
                .token(accessToken)
                .build();
    }

    // 아이디(이메일) 찾기
    @Override
    public UserFindEmailResponse findEmail(String username, String phone) {

        // 휴대폰 번호로 일치하는 유저 정보 조회
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_EXISTS_USER_EMAIL));

        // 이름이 다른 경우
        if(!user.getName().equals(username)) {
            throw new CustomException(ResponseCode.NOT_EXISTS_USER_EMAIL);
        }

        // 유저 상태가 탈퇴인 경우
        if(user.getStatus() == 1) {
            throw new CustomException(ResponseCode.NOT_EXISTS_USER_EMAIL);
        }

        return UserFindEmailResponse.builder()
                .email(user.getEmail())
                .build();
    }

    // 비밀번호 변경
    @Override
    @Transactional
    public void modifyPassword(UserChangePasswordRequest userChangePasswordRequest) {

        // 비밀번호를 변경할 유저 확인
        User user = userRepository.findByEmail(userChangePasswordRequest.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_USER));

        // 비밀번호 변경
        user.setPassword(new BCryptPasswordEncoder().encode(userChangePasswordRequest.getPassword()));
    }

    // 토큰속 이메일로 유저정보 찾기
    @Override
    public User getUserFromToken(String token) {
        String email = jwtTokenProvider.getUserId(token.substring(7));

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_USER));
    }


}

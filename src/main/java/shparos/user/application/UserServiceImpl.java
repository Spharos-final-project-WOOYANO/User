package shparos.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shparos.user.domain.Address;
import shparos.user.domain.User;
import shparos.user.global.config.security.JwtTokenProvider;
import shparos.user.global.exception.CustomException;
import shparos.user.global.exception.ResponseCode;
import shparos.user.infrastructure.AddressRepository;
import shparos.user.infrastructure.UserRepository;
import shparos.user.vo.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // 이메일 중복 체크
    @Override
    public Boolean checkEmail(String email) {

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
    public UserSignUpOut join(UserSignUpIn userSignUpIn) {

        // 비밀번호 암호화
        String hashedPassword = new BCryptPasswordEncoder().encode(userSignUpIn.getPassword());

        // 유저 등록
        User user = User.builder()
                .email(userSignUpIn.getEmail())
                .password(hashedPassword)
                .birthday(userSignUpIn.getBirthday())
                .username(userSignUpIn.getUsername())
                .nickname(userSignUpIn.getNickname())
                .phone(userSignUpIn.getPhone())
                .status(0)
                .build();
        userRepository.save(user);

        // 주소 등록
        Address address = Address.builder()
                .user(user)
                .localAddress(userSignUpIn.getLocalAddress())
                .extraAddress(userSignUpIn.getExtraAddress())
                .defaultAddress(Boolean.TRUE)
                .localCode(userSignUpIn.getLocalCode())
                .build();
        addressRepository.save(address);

        return UserSignUpOut.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .localAddress(address.getLocalAddress())
                .extraAddress(address.getExtraAddress())
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
    public String findEmail(String username, String phone) {

        // 유저 이름과 휴대폰 번호로 유저정보 찾기
        User user = userRepository.findByUsernameAndPhone(username, phone)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_EXISTS_USER));

        return user.getEmail();
    }

    // 비밀번호 변경
    @Override
    @Transactional
    public void modifyPassword(UserChangePasswordIn userChangePasswordIn) {

        // 비밀번호를 변경할 유저 확인
        User user = userRepository.findByEmail(userChangePasswordIn.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_USER));

        // 비밀번호 변경
        user.setPassword(new BCryptPasswordEncoder().encode(userChangePasswordIn.getPassword()));
    }

    // 토큰속 이메일로 유저정보 찾기
    @Override
    public User getUserFromToken(String token) {
        String email = jwtTokenProvider.getUserId(token.substring(7));

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_USER));
    }


}

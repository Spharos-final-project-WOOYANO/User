package shparos.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import shparos.user.domain.Address;
import shparos.user.domain.User;
import shparos.user.infrastructure.AddressRepository;
import shparos.user.infrastructure.UserRepository;
import shparos.user.vo.UserSignUpIn;
import shparos.user.vo.UserSignUpOut;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

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
}

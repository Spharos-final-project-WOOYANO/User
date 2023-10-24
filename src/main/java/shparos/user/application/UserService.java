package shparos.user.application;

import shparos.user.dto.UserSignUpDto;
import shparos.user.vo.UserSignUpIn;
import shparos.user.vo.UserSignUpOut;

public interface UserService {

    Boolean checkEmail(String email);

    Boolean checkNickname(String nickname);

    UserSignUpOut join(UserSignUpIn userSignUpIn);

}

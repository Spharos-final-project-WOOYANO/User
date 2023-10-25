package shparos.user.vo;

import lombok.Getter;

@Getter
public class UserSignUpIn {

    private String email;
    private String password;
    private String username;
    private String nickname;
    private String birthday;
    private String phone;
    private String localAddress;
    private String extraAddress;
    private Integer localCode;

}

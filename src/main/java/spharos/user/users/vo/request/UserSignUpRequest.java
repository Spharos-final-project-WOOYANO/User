package spharos.user.users.vo.request;

import lombok.Getter;

@Getter
public class UserSignUpRequest {

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

package spharos.user.users.vo.request;

import lombok.Getter;

@Getter
public class UserChangePasswordRequest {

    private String email;
    private String password;

}

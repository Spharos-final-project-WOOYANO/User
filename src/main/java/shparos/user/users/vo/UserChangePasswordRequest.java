package shparos.user.users.vo;

import lombok.Getter;

@Getter
public class UserChangePasswordRequest {

    private String email;
    private String password;

}

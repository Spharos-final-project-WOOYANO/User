package spharos.user.users.vo;

import lombok.Getter;

@Getter
public class UserWithdrawCheckRequest {

    private String username;
    private String email;
    private String password;

}

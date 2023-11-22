package spharos.user.users.vo.request;

import lombok.Getter;

@Getter
public class UserWithdrawCheckRequest {

    private String username;
    private String email;
    private String password;

}

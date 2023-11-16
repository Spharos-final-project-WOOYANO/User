package spharos.user.users.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpResponse {

    private String email;
    private String username;
    private String nickname;
    private String phone;
    private String localAddress;
    private String extraAddress;

}

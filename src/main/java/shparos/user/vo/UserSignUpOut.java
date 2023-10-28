package shparos.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpOut {

    private String email;
    private String username;
    private String nickname;
    private String phone;
    private String localAddress;
    private String extraAddress;

}

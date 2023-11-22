package spharos.user.users.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

    private String token;
    private String email;
    private String username;
    private String address;
    private String profileImageUrl;

}

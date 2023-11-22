package spharos.user.users.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationResponse {

    private String email;
    private String username;
    private String birthday;
    private String nickname;
    private String phone;

}

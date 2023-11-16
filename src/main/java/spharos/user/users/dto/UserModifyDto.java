package spharos.user.users.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModifyDto {

    private String email;
    private String username;
    private String birthday;
    private String nickname;
    private String phone;

}

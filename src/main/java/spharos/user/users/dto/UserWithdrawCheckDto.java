package spharos.user.users.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithdrawCheckDto {

    private String loginEmail;
    private String inputEmail;
    private String password;
    private String username;

}

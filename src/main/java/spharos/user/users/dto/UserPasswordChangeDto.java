package spharos.user.users.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordChangeDto {

    private String email;
    private String password;

}

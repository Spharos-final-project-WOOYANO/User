package shparos.user.users.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordCheckDto {

    private String email;
    private String password;

}

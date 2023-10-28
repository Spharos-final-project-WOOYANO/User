package shparos.user.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDto {

    private String email;
    private String password;
    private String username;
    private String nickname;
    private String birthday;
    private String phone;
    private String localAddress;
    private String extraAddress;
    private Integer localCode;

}

package spharos.user.address.dto;

import lombok.*;
import spharos.user.users.domain.User;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRegisterDto {

    private User user;
    private String localAddress;
    private String extraAddress;
    private Boolean defaultAddress;
    private Integer localCode;

}

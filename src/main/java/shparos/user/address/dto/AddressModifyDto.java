package shparos.user.address.dto;

import lombok.*;
import shparos.user.users.domain.User;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressModifyDto {

    private Long addressId;
    private User user;
    private String localAddress;
    private String extraAddress;
    private Integer localCode;

}

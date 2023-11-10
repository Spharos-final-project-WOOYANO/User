package shparos.user.address.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRegisterResultDto {

    private String localAddress;
    private String extraAddress;
    private Boolean defaultAddress;
    private Integer localCode;

}

package spharos.user.address.vo;

import lombok.Getter;

@Getter
public class AddressRegisterRequest {

    private String localAddress;
    private String extraAddress;
    private Boolean defaultAddress;
    private Integer localCode;

}

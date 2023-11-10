package shparos.user.address.vo;

import lombok.Getter;

@Getter
public class AddressModifyRequest {

    private Long addressId;
    private String localAddress;
    private String extraAddress;
    private Integer localCode;

}

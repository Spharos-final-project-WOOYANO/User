package shparos.user.address.vo;

import lombok.Getter;

@Getter
public class AddressModifyIn {

    private Long addressId;
    private String localAddress;
    private String extraAddress;
    private Boolean defaultAddress;
    private Integer localCode;

}

package shparos.user.vo;

import lombok.Getter;

@Getter
public class AddressRegisterIn {

    private String localAddress;
    private String extraAddress;
    private Boolean defaultAddress;
    private Integer localCode;

}

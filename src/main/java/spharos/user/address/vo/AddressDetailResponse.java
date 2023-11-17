package spharos.user.address.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailResponse {

    private Long id;
    private String localAddress;
    private String extraAddress;

}

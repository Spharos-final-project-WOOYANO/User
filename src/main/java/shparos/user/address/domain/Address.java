package shparos.user.address.domain;

import jakarta.persistence.*;
import lombok.*;
import shparos.user.users.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100, name = "local_address")
    private String localAddress;
    @Column(nullable = false, length = 100, name = "extra_address")
    private String extraAddress;
    @Column(nullable = false, name = "local_code")
    private Integer localCode;

    private Address(String localAddress, String extraAddress, Integer localCode) {
        this.localAddress = localAddress;
        this.extraAddress = extraAddress;
        this.localCode = localCode;
    }

    // 주소 등록
    public static Address createAddress(String localAddress, String extraAddress, Integer localCode) {
        return new Address(localAddress, extraAddress, localCode);
    }

    // 주소 수정
    public void update(String localAddress, String extraAddress, Integer localCode) {
        this.localAddress = localAddress;
        this.extraAddress = extraAddress;
        this.localCode = localCode;
    }

}

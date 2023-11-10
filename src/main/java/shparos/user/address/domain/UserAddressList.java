package shparos.user.address.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shparos.user.users.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_address_list")
public class UserAddressList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Address address;
    @Column(nullable = false, name = "default_address", columnDefinition = "boolean default false")
    private Boolean defaultAddress;

    private UserAddressList(User user, Address address, Boolean defaultAddress) {
        this.user = user;
        this.address = address;
        this.defaultAddress = defaultAddress;
    }

    // 유저 주소 중간 테이블 등록
    public static UserAddressList createUserAddressList(User user, Address address, Boolean defaultAddress) {
        return new UserAddressList(user, address, defaultAddress);
    }


    // 대표주소 변경
    public void setDefaultAddress(Boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

}

package shparos.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;
    @Column(nullable = false, length = 100, name = "local_address")
    private String localAddress;
    @Column(nullable = false, length = 100, name = "extra_address")
    private String extraAddress;
    @Column(nullable = false, name = "default_address", columnDefinition = "boolean default false")
    private Boolean defaultAddress;
    @Column(nullable = false, name = "local_code")
    private Integer localCode;

    public void setDefaultAddress(Boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }



}

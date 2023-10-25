package shparos.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shparos.user.global.common.domain.BaseEntity;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50, name = "email")
    private String email;
    @Column(nullable = false, name = "password")
    private String password;
    @Column(nullable = false, length = 6, name = "birthday")
    private String birthday;
    @Column(nullable = false, length = 30, name = "username")
    private String username;
    @Column(nullable = false, length = 10, name = "nickname")
    private String nickname;
    @Column(nullable = false, length = 11, name = "phone")
    private String phone;
    @Column(nullable = false, name = "status", columnDefinition = "tinyint default 0")
    private Integer status;

}

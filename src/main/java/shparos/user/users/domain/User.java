package shparos.user.users.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shparos.user.global.common.domain.BaseEntity;

import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails  {

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

    private User(String email, String password, String birthday, String username, String nickname,
                 String phone, Integer status) {
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.username = username;
        this.nickname = nickname;
        this.phone = phone;
        this.status = status;
    }

    // 유저 생성
    public static User createUser(String email, String password, String birthday, String username, String nickname,
                                  String phone, Integer status) {
        return new User(email, password, birthday, username, nickname, phone, status);
    }

    public String getName() {
        return username;
    }
    public void setPassword(String password) { this.password = password; }


    // 시큐리티 관련
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

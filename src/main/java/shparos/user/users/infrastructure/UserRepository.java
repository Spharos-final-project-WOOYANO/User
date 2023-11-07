package shparos.user.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import shparos.user.users.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByPhone(String phone);


}

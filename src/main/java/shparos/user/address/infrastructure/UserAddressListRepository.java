package shparos.user.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import shparos.user.address.domain.UserAddressList;
import shparos.user.users.domain.User;

public interface UserAddressListRepository extends JpaRepository<UserAddressList, Long> {

    UserAddressList findByUserAndDefaultAddress(User user, Boolean defaultAddress);

}

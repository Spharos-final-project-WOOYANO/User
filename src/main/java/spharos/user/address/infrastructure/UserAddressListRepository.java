package spharos.user.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.user.address.domain.Address;
import spharos.user.address.domain.UserAddressList;
import spharos.user.users.domain.User;

import java.util.List;

public interface UserAddressListRepository extends JpaRepository<UserAddressList, Long> {

    UserAddressList findByUserAndDefaultAddress(User user, Boolean defaultAddress);
    List<UserAddressList> findByUser(User user);
    UserAddressList findByAddress(Address address);

}

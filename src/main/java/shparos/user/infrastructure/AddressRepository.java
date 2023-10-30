package shparos.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import shparos.user.domain.Address;
import shparos.user.domain.User;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);
    Address findByUserAndDefaultAddress(User user, Boolean defaultAddress);

}

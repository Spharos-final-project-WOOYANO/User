package shparos.user.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import shparos.user.address.domain.Address;
import shparos.user.users.domain.User;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

}

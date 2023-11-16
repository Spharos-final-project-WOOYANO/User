package spharos.user.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.user.address.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}

package shparos.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import shparos.user.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

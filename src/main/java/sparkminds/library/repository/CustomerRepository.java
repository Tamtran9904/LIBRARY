package sparkminds.library.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail (String email);

}

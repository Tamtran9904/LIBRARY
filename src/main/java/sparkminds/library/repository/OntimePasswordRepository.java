package sparkminds.library.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.OneTimePassword;

public interface OntimePasswordRepository extends JpaRepository<OneTimePassword, Long> {

    Optional<OneTimePassword> findByOtp(String otp);

}

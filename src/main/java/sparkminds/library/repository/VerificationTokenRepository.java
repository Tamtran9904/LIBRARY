package sparkminds.library.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

}

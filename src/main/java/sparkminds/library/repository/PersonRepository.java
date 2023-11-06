package sparkminds.library.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail (String email);
}

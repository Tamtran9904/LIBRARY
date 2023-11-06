package sparkminds.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

}

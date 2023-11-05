package sparkminds.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}

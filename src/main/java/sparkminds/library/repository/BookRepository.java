package sparkminds.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}

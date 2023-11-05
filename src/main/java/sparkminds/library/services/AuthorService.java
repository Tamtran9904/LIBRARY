package sparkminds.library.services;

import org.springframework.data.domain.Page;
import sparkminds.library.entities.Author;

public interface AuthorService {

    Author insert();

    Page<Author> findAll(Integer number, Integer size);

    Boolean delete(Author author);

    Author update(Author author);
}

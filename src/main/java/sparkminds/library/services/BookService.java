package sparkminds.library.services;

import org.springframework.data.domain.Page;
import sparkminds.library.entities.Book;

public interface BookService {

    Book insert();

    Page<Book> findAll(Integer number, Integer size);

    Boolean delete(Book book);

    Book update(Book book);
}

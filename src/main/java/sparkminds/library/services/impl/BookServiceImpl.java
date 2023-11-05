package sparkminds.library.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Book;
import sparkminds.library.repository.BookRepository;
import sparkminds.library.services.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book insert() {
        return null;
    }

    @Override
    public Page<Book> findAll(Integer number, Integer size) {
        Pageable pageable = PageRequest.of(number, size);
        Page<Book> page = bookRepository.findAll(pageable);
        return page;
    }

    @Override
    public Boolean delete(Book book) {
        return null;
    }

    @Override
    public Book update(Book book) {
        return null;
    }
}

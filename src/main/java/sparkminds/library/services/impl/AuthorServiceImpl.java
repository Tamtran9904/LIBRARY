package sparkminds.library.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Author;
import sparkminds.library.repository.AuthorRepository;
import sparkminds.library.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author insert() {
        return null;
    }

    @Override
    public Page<Author> findAll(Integer number, Integer size) {
        return null;
    }

    @Override
    public Boolean delete(Author author) {
        return null;
    }

    @Override
    public Author update(Author author) {
        return null;
    }
}

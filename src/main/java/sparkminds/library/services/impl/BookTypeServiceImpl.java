package sparkminds.library.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Category;
import sparkminds.library.repository.BookTypeRepository;
import sparkminds.library.services.BookTypeService;

@Service
public class BookTypeServiceImpl implements BookTypeService {

    @Autowired
    private BookTypeRepository bookTypeRepository;

    @Override
    public Category insert() {
        return null;
    }

    @Override
    public Page<Category> findAll(Integer number, Integer size) {
        return null;
    }

    @Override
    public Boolean delete(Category category) {
        return null;
    }

    @Override
    public Category update(Category category) {
        return null;
    }
}

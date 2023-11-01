package sparkminds.library.services;

import org.springframework.data.domain.Page;
import sparkminds.library.entities.Category;

public interface BookTypeService {

    Category insert();

    Page<Category> findAll(Integer number, Integer size);

    Boolean delete(Category category);

    Category update(Category category);
}

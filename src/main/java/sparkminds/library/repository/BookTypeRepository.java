package sparkminds.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.Category;

public interface BookTypeRepository extends JpaRepository<Category, Long> {

}

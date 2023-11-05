package sparkminds.library.services;


import java.util.Optional;
import org.springframework.data.domain.Page;
import sparkminds.library.entities.Person;

public interface PersonService {

    Person insert();

    Page<Person> findAll(Integer number, Integer size);

    Optional<Person> finddById (Long id);

    Boolean delete(Person Person);

    Person update(Person Person);
}

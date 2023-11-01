package sparkminds.library.services.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Customer;
import sparkminds.library.entities.Person;
import sparkminds.library.repository.PersonRepository;
import sparkminds.library.services.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public Person insert() {
        return null;
    }

    @Override
    public Page<Person> findAll(Integer number, Integer size) {
        return null;
    }

    @Override
    public Optional<Person> finddById(Long id) {
       return personRepository.findById(id);
    }

    @Override
    public Boolean delete(Person Person) {
        return null;
    }

    @Override
    public Person update(Person Person) {
        return null;
    }
}

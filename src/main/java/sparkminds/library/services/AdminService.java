package sparkminds.library.services;

import org.springframework.data.domain.Page;
import sparkminds.library.entities.Admin;

public interface AdminService {

    Admin insert(Admin admin);

    Page<Admin> findAll(Integer number, Integer size);

    Boolean delete(Admin Admin);

    Admin update(Admin admin);
}

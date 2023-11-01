package sparkminds.library.services;

import org.springframework.data.domain.Page;
import sparkminds.library.entities.RoleUser;

public interface RoleService {

    RoleUser insert();

    Page<RoleUser> findAll(Integer number, Integer size);

    Boolean delete(RoleUser roleUser);

    RoleUser update(RoleUser roleUser);
}

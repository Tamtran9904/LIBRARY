package sparkminds.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.RoleUser;
import sparkminds.library.enums.Role;

public interface RoleRepository extends JpaRepository<RoleUser, Long> {

    RoleUser findByRole (Role role);

}

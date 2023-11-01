package sparkminds.library.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparkminds.library.entities.Admin;

public interface AdminRepository extends JpaRepository <Admin, Long> {

    Admin findByEmail (String email);
}

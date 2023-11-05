package sparkminds.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparkminds.library.entities.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {

}

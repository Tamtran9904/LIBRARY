package sparkminds.library.services;

import org.springframework.data.domain.Page;
import sparkminds.library.entities.Record;

public interface RecordService {

    Record insert();

    Page<Record> findAll(Integer number, Integer size);

    Boolean delete(Record record);

    Record update(Record record);
}

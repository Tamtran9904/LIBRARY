package sparkminds.library.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Record;
import sparkminds.library.repository.RecordRepository;
import sparkminds.library.services.RecordService;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public Record insert() {
        return null;
    }

    @Override
    public Page<Record> findAll(Integer number, Integer size) {
        return null;
    }

    @Override
    public Boolean delete(Record record) {
        return null;
    }

    @Override
    public Record update(Record record) {
        return null;
    }
}

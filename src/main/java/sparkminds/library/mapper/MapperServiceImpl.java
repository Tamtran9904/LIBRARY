package sparkminds.library.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements MapperService {

    private final ModelMapper modelMapper;

    public MapperServiceImpl() {
        this.modelMapper = new ModelMapper();
    }


    @Override
    public <T, U> T convertToEntity(U dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    @Override
    public <T, U> T convertToDto(U entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}

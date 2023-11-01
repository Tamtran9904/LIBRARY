package sparkminds.library.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperServiceImpl implements MapperService {

    private final ModelMapper modelMapper;

    @Override
    public <T, U> T convertToEntity(U dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    @Override
    public <T, U> T convertToDto(U entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}

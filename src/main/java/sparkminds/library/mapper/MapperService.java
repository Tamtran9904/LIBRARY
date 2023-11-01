package sparkminds.library.mapper;

public interface MapperService {

    public <T, U> T  convertToEntity(U dto, Class<T> entityClass);

    public <T, U> T convertToDto(U entity, Class<T> dtoClass);

}

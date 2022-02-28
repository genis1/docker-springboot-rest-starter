package genis.learning.docker.customers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerVo toVo(CustomerEntity o);

	@Mapping(target = "id", ignore = true)
	CustomerEntity toEntity(CustomerDataVo o);

}

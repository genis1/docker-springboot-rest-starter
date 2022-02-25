package genis.learning.docker.mappers;

import genis.learning.docker.entities.CustomerEntity;
import genis.learning.docker.vo.CustomerDataVo;
import genis.learning.docker.vo.CustomerVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerVo toVo(CustomerEntity o);

	@Mapping(target = "id", ignore = true)
	CustomerEntity toEntity(CustomerDataVo o);

}

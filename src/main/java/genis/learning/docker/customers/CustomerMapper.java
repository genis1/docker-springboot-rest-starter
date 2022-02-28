package genis.learning.docker.customers;

import genis.learning.docker.customers.vo.CustomerDataVo;
import genis.learning.docker.customers.vo.CustomerVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerVo toVo(CustomerEntity o);

	@Mapping(target = "id", ignore = true)
	CustomerEntity toEntity(CustomerDataVo o);

}

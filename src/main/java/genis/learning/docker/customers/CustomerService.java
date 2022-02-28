package genis.learning.docker.customers;

import genis.learning.docker.common.pagination.PageRequest;
import org.springframework.data.domain.Page;

public interface CustomerService {
	String CUSTOMER_NAME_CANNOT_BE_EMTPY = "Customer name cannot be emtpy.";
	String CUSTOMER_ID_DOES_NOT_EXIST = "Customer id does not exist.";
	String CUSTOMER_CANNOT_BE_SORTED_BY_THE_SPECIFIED_PROPERTY = "Customer cannot be sorted by the specified property.";

	CustomerVo create(CustomerDataVo customerVo);

	Page<CustomerVo> read(CustomerDataVo filter, PageRequest pageRequest);

	CustomerVo read(Integer id);

	CustomerVo update(Integer id, CustomerDataVo customerDataVo);

	void delete(Integer id);
}

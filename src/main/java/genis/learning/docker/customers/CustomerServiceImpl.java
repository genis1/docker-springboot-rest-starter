package genis.learning.docker.customers;

import genis.learning.docker.common.exception.IllegalUserInputException;
import genis.learning.docker.common.pagination.PageRequest;
import genis.learning.docker.customers.vo.CustomerDataVo;
import genis.learning.docker.customers.vo.CustomerVo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import java.util.function.Function;

@Service
public class CustomerServiceImpl implements CustomerService{

	private static final Function<Integer, String> CUSTOMER_ID_DOES_NOT_EXIST_MESSAGE_BUILDER = (Integer id) -> CUSTOMER_ID_DOES_NOT_EXIST + " Id used was: " + id;
	private static final Function<String, String> CUSTOMER_CANNOT_BE_SORTED_BY_THE_SPECIFIED_PROPERTY_WITH_MESSAGE = (String message) -> CUSTOMER_CANNOT_BE_SORTED_BY_THE_SPECIFIED_PROPERTY + " Error details: " + message;
	private final CustomerMapper mapper;
	private final CustomerRepository repository;

	public CustomerServiceImpl(CustomerMapper mapper, CustomerRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
	}

	@Override
	public CustomerVo create(CustomerDataVo customerVo) {
		validateCustomerName(customerVo.getName());
		return mapper.toVo(repository.save(mapper.toEntity(customerVo)));
	}

	@Override
	public Page<CustomerVo> read(CustomerDataVo filter, PageRequest pageRequest) {
		final CustomerEntity enttityFilter = mapper.toEntity(filter);
		try {
			return repository.findAll(Example.of(enttityFilter), pageRequest.build())
					.map(mapper::toVo);
		} catch (PropertyReferenceException e) {
			throw new IllegalUserInputException(CUSTOMER_CANNOT_BE_SORTED_BY_THE_SPECIFIED_PROPERTY_WITH_MESSAGE.apply(e.getMessage()));
		}
	}

	@Override
	public CustomerVo read(Integer id) {
		return repository.findById(id)
				.map(mapper::toVo)
				.orElseThrow(() -> new IllegalUserInputException(CUSTOMER_ID_DOES_NOT_EXIST_MESSAGE_BUILDER.apply(id)));
	}

	@Override
	public CustomerVo update(Integer id, CustomerDataVo customerDataVo){
		validateCustomerName(customerDataVo.getName());

		final CustomerEntity entity = repository.getById(id);

		//Update fields
		try {
			entity.setName(customerDataVo.getName());
		} catch (EntityNotFoundException e) {
			throw new IllegalUserInputException(CUSTOMER_ID_DOES_NOT_EXIST_MESSAGE_BUILDER.apply(id));
		}

		return mapper.toVo(repository.save(entity));
	}

	@Override
	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new IllegalUserInputException(CUSTOMER_ID_DOES_NOT_EXIST_MESSAGE_BUILDER.apply(id));
		}
	}

	private void validateCustomerName(String customerName) {
		if (ObjectUtils.isEmpty(customerName))
			throw new IllegalUserInputException(CUSTOMER_NAME_CANNOT_BE_EMTPY);
	}
}
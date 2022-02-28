package genis.learning.docker.versioning;

import genis.learning.docker.common.exception.IllegalUserInputException;
import genis.learning.docker.common.pagination.PageRequest;
import genis.learning.docker.versioning.vo.VersionDataVo;
import genis.learning.docker.versioning.vo.VersionVo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import java.util.function.Function;

@Service
public class VersionServiceImpl implements VersionService{

	private static final Function<Integer, String> VERSION_ID_DOES_NOT_EXIST_MESSAGE_BUILDER = (Integer id) -> VERSION_ID_DOES_NOT_EXIST + " Id used was: " + id;
	private static final Function<String, String> VERSION_CANNOT_BE_SORTED_BY_THE_SPECIFIED_PROPERTY_WITH_MESSAGE = (String message) -> VERSION_CANNOT_BE_SORTED_BY_THE_SPECIFIED_PROPERTY + " Error details: " + message;
	private final VersionMapper mapper;
	private final VersionRepository repository;

	public VersionServiceImpl(VersionMapper mapper, VersionRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
	}

	@Override
	public VersionVo create(VersionDataVo versionVo) {
		validateVersionName(versionVo.getName());
		return mapper.toVo(repository.save(mapper.toEntity(versionVo)));
	}

	@Override
	public Page<VersionVo> read(VersionDataVo filter, PageRequest pageRequest) {
		final VersionEntity enttityFilter = mapper.toEntity(filter);
		try {
			return repository.findAll(Example.of(enttityFilter), pageRequest.build())
					.map(mapper::toVo);
		} catch (PropertyReferenceException e) {
			throw new IllegalUserInputException(VERSION_CANNOT_BE_SORTED_BY_THE_SPECIFIED_PROPERTY_WITH_MESSAGE.apply(e.getMessage()));
		}
	}

	@Override
	public VersionVo read(Integer id) {
		return repository.findById(id)
				.map(mapper::toVo)
				.orElseThrow(() -> new IllegalUserInputException(VERSION_ID_DOES_NOT_EXIST_MESSAGE_BUILDER.apply(id)));
	}

	@Override
	public VersionVo update(Integer id, VersionDataVo versionDataVo){
		validateVersionName(versionDataVo.getName());

		final VersionEntity entity = repository.getById(id);

		//Update fields
		try {
			entity.setName(versionDataVo.getName());
		} catch (EntityNotFoundException e) {
			throw new IllegalUserInputException(VERSION_ID_DOES_NOT_EXIST_MESSAGE_BUILDER.apply(id));
		}

		return mapper.toVo(repository.save(entity));
	}

	@Override
	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new IllegalUserInputException(VERSION_ID_DOES_NOT_EXIST_MESSAGE_BUILDER.apply(id));
		}
	}

	private void validateVersionName(String versionName) {
		if (ObjectUtils.isEmpty(versionName))
			throw new IllegalUserInputException(VERSION_NAME_CANNOT_BE_EMTPY);
	}
}

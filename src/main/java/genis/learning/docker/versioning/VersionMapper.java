package genis.learning.docker.versioning;

import genis.learning.docker.versioning.vo.VersionDataVo;
import genis.learning.docker.versioning.vo.VersionVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VersionMapper {

	VersionVo toVo(VersionEntity o);

	@Mapping(target = "id", ignore = true)
	VersionEntity toEntity(VersionDataVo o);

}

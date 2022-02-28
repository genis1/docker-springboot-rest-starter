package genis.learning.docker.versioning;

import genis.learning.docker.common.pagination.PageRequest;
import genis.learning.docker.versioning.vo.VersionDataVo;
import genis.learning.docker.versioning.vo.VersionVo;
import org.springframework.data.domain.Page;

public interface VersionService {
	String VERSION_ID_DOES_NOT_EXIST = "Version id does not exist.";
	String VERSION_CANNOT_BE_SORTED_BY_THE_SPECIFIED_PROPERTY = "Version cannot be sorted by the specified property.";

	VersionVo create(VersionDataVo versionVo);

	Page<VersionVo> read(VersionDataVo filter, PageRequest pageRequest);

	VersionVo read(Integer id);

	VersionVo update(Integer id, VersionDataVo versionDataVo);

	void delete(Integer id);
}

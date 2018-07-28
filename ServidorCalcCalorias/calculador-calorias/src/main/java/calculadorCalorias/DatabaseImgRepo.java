package calculadorCalorias;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "img", path = "img")
public interface DatabaseImgRepo extends PagingAndSortingRepository<DatabaseImg, Long> {

	List<DatabaseImg> findById(@Param("id") long id);

	List<DatabaseImg> findBySaveName(@Param("saveName") String savename);
	
}

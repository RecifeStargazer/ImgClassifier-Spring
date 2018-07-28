package calculadorCalorias;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "menu", path = "meu")
public interface MenuRepo extends PagingAndSortingRepository<Menu, Long> {

	List<Menu> findById(@Param("id") long id);
	
}

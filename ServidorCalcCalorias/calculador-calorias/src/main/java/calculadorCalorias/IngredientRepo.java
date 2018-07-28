package calculadorCalorias;


import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "ingredient", path = "ingredient")
public interface IngredientRepo extends PagingAndSortingRepository<Ingredient, Long> {

	List<Ingredient> findById(@Param("id") long id);
	
}


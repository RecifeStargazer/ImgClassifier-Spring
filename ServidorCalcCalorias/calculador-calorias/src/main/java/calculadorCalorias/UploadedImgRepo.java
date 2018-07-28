package calculadorCalorias;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "uploaded_img", path = "uploaded_img")
public interface UploadedImgRepo extends PagingAndSortingRepository<UploadedImg, Long> {

	List<UploadedImg> findById(@Param("id") long id);
	
}



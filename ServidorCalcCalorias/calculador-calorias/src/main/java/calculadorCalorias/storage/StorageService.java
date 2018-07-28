package calculadorCalorias.storage;

import org.opencv.core.Mat;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();
    
    void storeORBDescriptor(Mat descriptor, String number );
    
    void storeHistDescriptor(Mat descriptor, String number );

    void storeDB(MultipartFile file, String number);
    
    void storeUp(MultipartFile file, String number);

    Stream<Path> loadAllORBDescriptors();
    
    Stream<Path> loadAllHistDescriptors();
    
    Stream<Path> loadAllDB();
    
    Stream<Path> loadAllUp();

    Path loadORBDescriptor(String filename);
    
    Path loadHistDescriptor(String filename);
    
    Path loadDB(String filename);
    
    Path loadUp(String filename);
    
    Resource loadAsResourceORBDescriptor(String filename);
    
    Resource loadAsResourceHistDescriptor(String filename);

    Resource loadAsResourceDB(String filename);
    
    Resource loadAsResourceUp(String filename);

    void deleteAllDB();
    
    void deleteAllUp();
    
    void deleteAllORBDescriptors();
    
    void deleteAllHistDescriptors();
}

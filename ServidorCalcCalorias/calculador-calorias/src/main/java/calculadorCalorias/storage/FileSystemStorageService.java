package calculadorCalorias.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {
   
	private final Path dbLocation;
    
    private final Path upLocation;
    
    private final Path oRBDescriptorLocation;
    
    private final Path histDescriptorLocation;
    
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.dbLocation = Paths.get(properties.getDbLocation());
        this.upLocation = Paths.get(properties.getUpLocation());
        this.oRBDescriptorLocation = Paths.get(properties.getoRBDescriptorLocation());
        this.histDescriptorLocation = Paths.get(properties.getoRBDescriptorLocation());
    }
    
    public void storeORBDescriptor(Mat descriptor, String number ) {
    	String path = number+".bin";
        if (descriptor==null) {
		    throw new StorageException("Failed to store empty file " + path);
		}
		if (path.contains("..")) {
		    // This is a security check
		    throw new StorageException(
		            "Cannot store file with relative path outside current directory "
		                    + path);
		}
		//Código para converter mat para char[]
		File file = new File(oRBDescriptorLocation.toString(), path);
        int length = (int) (descriptor.total() * descriptor.elemSize());
        byte buffer[] = new byte[length];
        descriptor.get(0, 0, buffer);
        try
            {
                FileUtils.writeByteArrayToFile(file, buffer);
            } catch (IOException e)
            {
            	System.out.println("erro");
                e.printStackTrace();
            }
    }
    
    public void storeHistDescriptor(Mat descriptor, String number ) {
    	String path = number+".bin";
        if (descriptor==null) {
		    throw new StorageException("Failed to store empty file " + path);
		}
		if (path.contains("..")) {
		    // This is a security check
		    throw new StorageException(
		            "Cannot store file with relative path outside current directory "
		                    + path);
		}
		//Código para converter mat para char[]
		File file = new File(histDescriptorLocation.toString(), path);
        int length = (int) (descriptor.total() * descriptor.elemSize());
        byte buffer[] = new byte[length];
        descriptor.get(0, 0, buffer);
        try
            {
                FileUtils.writeByteArrayToFile(file, buffer);
            } catch (IOException e)
            {
            	System.out.println("erro");
                e.printStackTrace();
            }
		
    }
   
    @Override
    public void storeDB(MultipartFile file, String number) {
        String path = "img_"+number+".jpg";
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + path);
            }
            if (path.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + path);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.dbLocation.resolve(path)
                    );
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + path, e);
        }
    }
    
    @Override
    public void storeUp(MultipartFile file, String number) {
        String path = "img_"+number+".jpg";
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + path);
            }
            if (path.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + path);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.upLocation.resolve(path)
                    );
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + path, e);
        }
    }
    
    @Override
    public Stream<Path> loadAllORBDescriptors() {
        try {
            return Files.walk(this.oRBDescriptorLocation, 1)
                .filter(path -> !path.equals(this.oRBDescriptorLocation))
                .map(this.oRBDescriptorLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }
    
    @Override
    public Stream<Path> loadAllHistDescriptors() {
        try {
            return Files.walk(this.histDescriptorLocation, 1)
                .filter(path -> !path.equals(this.histDescriptorLocation))
                .map(this.histDescriptorLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }
    
    @Override
    public Stream<Path> loadAllDB() {
        try {
            return Files.walk(this.dbLocation, 1)
                .filter(path -> !path.equals(this.dbLocation))
                .map(this.dbLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }
    
    @Override
    public Stream<Path> loadAllUp() {
        try {
            return Files.walk(this.upLocation, 1)
                .filter(path -> !path.equals(this.upLocation))
                .map(this.upLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }
    
    @Override
    public Path loadORBDescriptor(String path) {
        return oRBDescriptorLocation.resolve(path);
    }
    
    @Override
    public Path loadHistDescriptor(String path) {
        return histDescriptorLocation.resolve(path);
    }
    
    @Override
    public Path loadDB(String path) {
        return dbLocation.resolve(path);
    }
    
    @Override
    public Path loadUp(String path) {
        return upLocation.resolve(path);
    }

    @Override
    public Resource loadAsResourceORBDescriptor(String path) {
        try {
            Path file = loadDB(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + path);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + path, e);
        }
    }
    
    @Override
    public Resource loadAsResourceHistDescriptor(String path) {
        try {
            Path file = loadDB(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + path);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + path, e);
        }
    }
    
    @Override
    public Resource loadAsResourceDB(String path) {
        try {
            Path file = loadDB(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + path);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + path, e);
        }
    }
    
    @Override
    public Resource loadAsResourceUp(String path) {
        try {
            Path file = loadUp(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + path);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + path, e);
        }
    }
    
    @Override
    public void deleteAllORBDescriptors() {
        FileSystemUtils.deleteRecursively(oRBDescriptorLocation.toFile());
    }
    
    @Override
    public void deleteAllHistDescriptors() {
        FileSystemUtils.deleteRecursively(histDescriptorLocation.toFile());
    }


    @Override
    public void deleteAllDB() {
        FileSystemUtils.deleteRecursively(dbLocation.toFile());
    }
    
    @Override
    public void deleteAllUp() {
        FileSystemUtils.deleteRecursively(upLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(dbLocation);
            Files.createDirectories(upLocation);
            Files.createDirectories(oRBDescriptorLocation);
            Files.createDirectories(histDescriptorLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}

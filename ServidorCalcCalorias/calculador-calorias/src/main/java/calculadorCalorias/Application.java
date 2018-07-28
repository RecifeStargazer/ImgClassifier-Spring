package calculadorCalorias;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import calculadorCalorias.storage.StorageProperties;
import calculadorCalorias.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
    	
        SpringApplication.run(Application.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
        	//storageService.deleteAllDB(); //Deleta todo o conteúdo do repositório
            //storageService.deleteAllUp();
            //storageService.deleteAllORBDescriptors();
            //storageService.deleteAllHistDescriptors();
            storageService.init();
        };
    }
}

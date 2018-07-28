package calculadorCalorias;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import calculadorCalorias.storage.StorageFileNotFoundException;
import calculadorCalorias.storage.StorageService;
import calculadorCalorias.DatabaseImg;
import calculadorCalorias.DatabaseImgRepo;
import calculadorCalorias.UploadedImg;
import calculadorCalorias.UploadedImgRepo;

@Controller
public class MainController {
	
	@Autowired
    private final StorageService storageService;
	
	@Autowired
    private DatabaseImgRepo databaseImgRepo;
	
	@Autowired
    private UploadedImgRepo uploadedImgRepo;
	
    @Autowired
    public MainController(StorageService storageService) {
        this.storageService = storageService;
    }
    /**
     * lista database, sem fotos, mas apenas URL/endereço relativo das fotos
     * @return
     */
    @GetMapping(path="/listDatabase")
    @ResponseBody
	public Iterable<DatabaseImg> getAllImg() {
		// retorna json com database mas não fotos
		return databaseImgRepo.findAll();
	}
    /**
     * Rota para cadastro de imagem no database. Falta retornar o json os dados da imagem
     * @return 
     * @return 
     * @throws IOException 
     */
    @PostMapping("/addInDatabase")
    @ResponseBody
    public DatabaseImg addInDatabase(@RequestParam("file") MultipartFile file,  
            int calories, String[] ingredients) throws IOException {
    	
    	//Instancia DatabaseImg
    	DatabaseImg img = new DatabaseImg();
    	    	
    	//Instancia o a classe que reescala a img
    	//ImgResizer resizer = new ImgResizer();
    	
    	//Numero para salvar a imagem
    	Long imgNumber = databaseImgRepo.count()+1;
    	
    	//Salva o arquivo de foto 
    	storageService.storeDB(file, Long.toString(imgNumber));
    	    	
    	//Cria um set com os ingredientes, para evitar ingredientes repetidos
    	Set <Ingredient> setOfIngredient = new HashSet<Ingredient>();
    	ingredients = new HashSet<String>(Arrays.asList(ingredients)).toArray(new String[0]);
    	for(int i=0; i<ingredients.length; i++) {
    		Ingredient ing = new Ingredient();
    		ing.setIngredient(ingredients[i]);
    		setOfIngredient.add(ing);
    	}
    	
    	//Seta ingredientes na imagem
    	img.setIngredients(setOfIngredient);
    	
    	//Seta calorias na imagem
    	img.setCalories(calories);
		
    	//String nome do arquivo
		String saveName = "img_"+imgNumber+".jpg";
		String descriptorSaveName ="img_"+imgNumber;
		
		//Seta caminho do arquivo
		img.setSaveName(saveName);
		
		//Seta o endereço do descritor da imagem
		img.setDescriptorName(descriptorSaveName);
			
		//Salva imagem na database	
    	databaseImgRepo.save(img);  
    	
    	//Altera resolução da imagem salva na database
    	//Desativado por deformar imagens com aspect ratio diferente
    	//resizer.resize(img);
		
		//Cria descritor ORB
		ORBDescriptor oRBDescriptorGen = new ORBDescriptor(img);
		Mat oRBDescriptor = oRBDescriptorGen.getDescriptor();
							
		//guarda o descritor ORB da imagem
		storageService.storeORBDescriptor(oRBDescriptor, descriptorSaveName);
		
		//Cria descritor HIST
		//HistDescriptor histDescriptorGen = new HistDescriptor(img);
		//List histDescriptor = histDescriptorGen.getrGBHist();
		
		//Guarda descritor RGB
		//
		
		//Testa canal
		//System.out.println(((Mat) histDescriptor.get(0)).dump());
		
		//Stream<Path> descritores = storageService.loadAllORBDescriptors();
		//Object[] desc = descritores.toArray();
				
        return img;
        
        
        
    }
    /**
     * Rota para calcular calorias. Falta retornar o json mostrando as calorias
     * @throws IOException 
     */
    @PostMapping("/calories")
    @ResponseBody
    public Result handleFileUpload(@RequestParam("file") MultipartFile file
            ) throws IOException {
    	
    	//Instancia o resultado
    	Result resultado = new Result();
    	
    	//Instancia a imagem 
    	UploadedImg img = new UploadedImg();
    	    	
    	//Instancia o a classe que reescala a img
    	//ImgResizer resizer = new ImgResizer();
    	
    	//Numero para salvar a imagem
    	Long imgNumber = uploadedImgRepo.count()+1;
    	
    	//Salva o arquivo de foto 
    	storageService.storeUp(file, Long.toString(imgNumber));
    	
    	//String nome do arquivo salvo
    	String saveName = "img_"+imgNumber+".jpg";
    	
    	//Seta string nome
		img.setSaveName(saveName);
		
		//Salva imagem no database	
    	uploadedImgRepo.save(img);
    	
    	//Altera resolução da imagem salva na database
    	//Desativado por deformar imagens comaspect ratio diferente
    	//resizer.resize(img);
    	    	
    	//Cria descritor ORB
    	ORBDescriptor oRBDescriptorGen = new ORBDescriptor(img);
    	Mat oRBDescriptor = oRBDescriptorGen.getDescriptor();
    	//System.out.println(oRBDescriptor.dump());
    	
    	//Carrega arquivos de descritores
    	Stream<Path> descritores = storageService.loadAllORBDescriptors();
    	Object[] desc = descritores.toArray();
    	
    	//K do knn, seta em resultado
    	int k =5;
    	resultado.setK(k);
    	
    	//Instancia o classificador
    	Classifier classificador = new Classifier();
    	
    	//Seta no resultado os matches
    	resultado.setdBImgMatches(classificador.estimate(desc, oRBDescriptor, k));
    	
    	//Recupera objetos no database para tirar a media
    	int calorias = 0;
    	Object[] kmatches= resultado.getdBImgMatches().keySet().toArray();
    	for (int i = 0; i<kmatches.length; i++) {
    		calorias += databaseImgRepo.findBySaveName((String)kmatches[i]).get(0).getCalories();
    	}
    	calorias = calorias/k;
    	
    	//Seta calorias estimadas
    	resultado.setEstimatedCal(calorias);
    	
    	return resultado;   
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}

package calculadorCalorias;
import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.Imgcodecs;
import java.io.Serializable;


@SuppressWarnings("serial")
public class ORBDescriptor implements Serializable {
	/*
	 * Classe retorna MAT contendo descritor em grayscale
	 * @databaseImg image
	 */
	private Mat descriptor;
	
	@SuppressWarnings("deprecation")
	public ORBDescriptor(DatabaseImg img)  {
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		nu.pattern.OpenCV.loadShared();
		//Carrega a imagem
		Mat imgGray = Imgcodecs.imread("db-dir//"+img.getSaveName(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		//Declara/inicializa as matrizes de keypoints
		MatOfKeyPoint keypoints = new MatOfKeyPoint();
		Mat descriptorGray = new Mat();
		//Declara os detectores e extratores de keypoints
		FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB); 
		DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		//Detectar pontos chave
		detector.detect(imgGray, keypoints);
		//Extrair descritor
		extractor.compute(imgGray, keypoints, descriptorGray);
		this.descriptor  = descriptorGray;
		//System.out.println(descriptorGray.dump());
	}
	
	@SuppressWarnings("deprecation")
	public ORBDescriptor(UploadedImg img)  {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//Carrega a imagem
		Mat imgGray = Imgcodecs.imread("uploaded-dir//"+img.getSaveName(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		//Declara/inicializa as matrizes de keypoints
		MatOfKeyPoint keypoints = new MatOfKeyPoint();
		Mat descriptorGray = new Mat();
		//Declara os detectores e extratores de keypoints
		FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB); 
		DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		//Detectar pontos chave
		detector.detect(imgGray, keypoints);
		//Extrair descritor
		extractor.compute(imgGray, keypoints, descriptorGray);
		this.descriptor  = descriptorGray;
		//System.out.println(descriptorGray.dump());
	}

	public Mat getDescriptor() {
		return descriptor;
	}
}

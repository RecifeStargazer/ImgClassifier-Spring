package calculadorCalorias;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class HistDescriptor {
	
	private List<Mat> rGBHist = new ArrayList<>();

	public HistDescriptor(DatabaseImg img) {
		
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		nu.pattern.OpenCV.loadShared();
		Mat image =  Imgcodecs.imread("db-dir//"+img.getSaveName(), Imgcodecs.CV_LOAD_IMAGE_COLOR);
		//Separa em 3 planos rgb
		List<Mat> bgrPlanes = new ArrayList<>();
        Core.split(image, bgrPlanes);
        //
        int histSize = 256;
        //Seta range
        float[] range = {0, 256}; //the upper boundary is exclusive
        MatOfFloat histRange = new MatOfFloat(range);
        //Desliga acumulador
        boolean accumulate = false;
        //Calcula os 3 histogramas, r g e b
        Mat bHist = new Mat(), gHist = new Mat(), rHist = new Mat();
        Imgproc.calcHist(bgrPlanes, new MatOfInt(0), new Mat(), bHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(bgrPlanes, new MatOfInt(1), new Mat(), gHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(bgrPlanes, new MatOfInt(2), new Mat(), rHist, new MatOfInt(histSize), histRange, accumulate);
		//adiciona os histogramas na lista de histogramas
        this.rGBHist.add(rHist);
        this.rGBHist.add(gHist);
        this.rGBHist.add(bHist);  
	}

	public List<Mat> getrGBHist() {
		return rGBHist;
	}
	
	
	
	
	
	

}

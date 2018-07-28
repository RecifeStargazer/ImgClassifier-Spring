package calculadorCalorias;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImgResizer {

	public void resize(DatabaseImg img) throws IOException {

		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		nu.pattern.OpenCV.loadShared();
		Mat original =  Imgcodecs.imread("db-dir//"+img.getSaveName(), Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat resized = new Mat();
		Size size = new Size(1024, 768);
		Imgproc.resize( original, resized, size);
		Imgcodecs.imwrite("db-dir//"+img.getSaveName(), resized);
	}

	public void resize(UploadedImg img) throws IOException {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat original =  Imgcodecs.imread("uploaded-dir//"+img.getSaveName(), Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat resized = new Mat();
		Size size = new Size(1024, 768);
		Imgproc.resize( original, resized, size);
		Imgcodecs.imwrite("up-dir//"+img.getSaveName(), resized);	
	}
}

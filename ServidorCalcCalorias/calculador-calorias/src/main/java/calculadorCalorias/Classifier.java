package calculadorCalorias;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DescriptorMatcher;

public class Classifier {

	List<Mat> oRBDescriptorList = new ArrayList();

	Map<String, Integer> setOfMatched = new HashMap<String, Integer>();

	public Map<String, Integer> estimate(Object[] oRBDescriptorArray, Mat oRBDescriptor, int k) {
		int matchCount = 0;

		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
		MatOfDMatch matches = new MatOfDMatch();

		//Percorre todo os descritores da DB e compara com o descritor da imagem
		for(int i=0; i< oRBDescriptorArray.length; i++) {

			//Código para carregar um descritor
			File fileo = new File("oRBDescriptor-dir//", oRBDescriptorArray[i].toString());
			byte[] buffer = new byte[0];
			try
			{
				buffer = FileUtils.readFileToByteArray(fileo);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			Mat mat = new Mat(500, 32, CvType.CV_8UC1);
			mat.put(0, 0, buffer);
	
			//Verifica se os descritores tem a mesma dimensão (vai que...)
			if (oRBDescriptor.cols() == mat.cols()) {
				matcher.match(mat , oRBDescriptor ,matches);

				// Check matches of keypoints
				DMatch[] match = matches.toArray();
				double max_dist = 0; double min_dist = 1000;

				for (int j = 0; j < oRBDescriptor.rows(); j++) { 
					double dist = match[j].distance;
					if( dist < min_dist ) min_dist = dist;
					if( dist > max_dist ) max_dist = dist;
				}
				// Escolhe as distancias consideradas match (<10)
				for (int j = 0; j < oRBDescriptor.rows(); j++) {
					if (match[j].distance <= 60) {
						matchCount++;
					}
				}
				//Guarda no set de matched, muda a extensão
				if (matchCount!=0) {
					setOfMatched.put(oRBDescriptorArray[i].toString().replaceAll(".bin", ".jpg")+"  ", matchCount);
				}
				//Reinicia contagem de matches
				matchCount =0;
			}

		}
		
		//Ordena o set de matches com os que deram mais matches primeiro
		Map<String, Integer> sortedMap = 
			     setOfMatched.entrySet().stream()
			    .sorted(Entry.<String, Integer>comparingByValue().reversed())
			    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
			                              (e1, e2) -> e2, LinkedHashMap::new));
		
		//Separa os k mais proximos
		Map<String, Integer> knn = new HashMap<>();
		Object[] s = sortedMap.keySet().toArray();
		for (int i =0; i<k; i++) {
			knn.put((String) s[i], sortedMap.get(s[i]));
		};
		
		//Reordena os k mais proximos
		Map<String, Integer> kNearest = 
			     knn.entrySet().stream()
			    .sorted(Entry.<String, Integer>comparingByValue().reversed())
			    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
			                              (e1, e2) -> e2, LinkedHashMap::new));
		return kNearest;
	}
}

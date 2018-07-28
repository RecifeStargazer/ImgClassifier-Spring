package calculadorCalorias;

import java.util.List;
import java.util.Map;

public class Result {
	
	private int k;
	private List<String> ingredientsMatched;
	private Map<String , Integer> dBImgMatches;
	private int estimatedCal;
	
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public List<String> getIngredientsMatched() {
		return ingredientsMatched;
	}
	public void setIngredientsMatched(List<String> ingredientsMatched) {
		this.ingredientsMatched = ingredientsMatched;
	}	
	public Map<String , Integer> getdBImgMatches() {
		return dBImgMatches;
	}
	public void setdBImgMatches(Map<String , Integer> dBImgMatches) {
		this.dBImgMatches = dBImgMatches;
	}
	public int getEstimatedCal() {
		return estimatedCal;
	}
	public void setEstimatedCal(int estimatedCal) {
		this.estimatedCal = estimatedCal;
	}
}
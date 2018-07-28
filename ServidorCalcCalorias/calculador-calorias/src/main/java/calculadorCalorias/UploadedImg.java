package calculadorCalorias;

import javax.persistence.Entity;

@Entity
public class UploadedImg extends ImagePlate{
	
	private int estimatedCalories;
	
	private String[] estimatedIngredients;
	
	private String[] nearestNeighbors;
		
	public int getEstimatedCalories() {
		return estimatedCalories;
	}

	public void setEstimatedCalories(int estimatedCalories) {
		this.estimatedCalories = estimatedCalories;
	}

	public String[] getEstimatedIngredients() {
		return estimatedIngredients;
	}

	public void setEstimatedIngredients(String[] estimatedIngredients) {
		this.estimatedIngredients = estimatedIngredients;
	}

	public String[] getNearestNeighbors() {
		return nearestNeighbors;
	}

	public void setNearestNeighbors(String[] nearestNeighbors) {
		this.nearestNeighbors = nearestNeighbors;
	}

}

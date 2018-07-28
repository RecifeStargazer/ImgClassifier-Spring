package calculadorCalorias;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class DatabaseImg extends ImagePlate{
	
	private int calories;
	
	@OneToMany
	(cascade = CascadeType.ALL)
	@JoinColumn
	(name="img_Id", referencedColumnName="img_Id")
    private Set<Ingredient> ingredients;
	
	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
}

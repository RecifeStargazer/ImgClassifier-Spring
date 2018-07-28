package calculadorCalorias;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class ImagePlate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "img_Id", nullable=false)
	private long id;
	
	private String saveName;
	
	private String DescriptorName;
	
	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String savePath) {
		this.saveName = savePath;
	}

	public String getDescriptorName() {
		return DescriptorName;
	}

	public void setDescriptorName(String string) {
		this.DescriptorName = string+".bin";
	}
}

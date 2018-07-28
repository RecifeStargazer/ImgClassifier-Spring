package calculadorCalorias.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Pasta para salvar arquivos
     */
    private String dbLocation = "db-dir";
    
    private String upLocation = "uploaded-dir";
    
    private String oRBDescriptorLocation = "oRBDescriptor-dir";
    
    private String histDescriptorLocation = "histDescriptor-dir";

	public String getDbLocation() {
		return dbLocation;
	}

	public void setDbLocation(String dbLocation) {
		this.dbLocation = dbLocation;
	}

	public String getUpLocation() {
		return upLocation;
	}

	public void setUpLocation(String upLocation) {
		this.upLocation = upLocation;
	}

	public String getoRBDescriptorLocation() {
		return oRBDescriptorLocation;
	}

	public void setoRBDescriptorLocation(String oRBDescriptorLocation) {
		this.oRBDescriptorLocation = oRBDescriptorLocation;
	}

	public String getHistDescriptorLocation() {
		return histDescriptorLocation;
	}

	public void setHistDescriptorLocation(String histDescriptorLocation) {
		this.histDescriptorLocation = histDescriptorLocation;
	}

	

    

}

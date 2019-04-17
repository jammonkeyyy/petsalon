package njuics.demos.petsalon.service;

public enum ServiceCategory {
	Bath("clean"),
	OPerate("operate"),
	Cut("Cut");


	private String type;
	
	private ServiceCategory(String type) {
		this.type = type;
	}
	public String gettype() {
		return type;
	}
}

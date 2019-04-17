package njuics.demos.petsalon.pet;

public enum PetType {
	Dog("Dog"),
	Cat("Cat"),
	Bird("Bird");
	
	private String type;
	
	private PetType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}

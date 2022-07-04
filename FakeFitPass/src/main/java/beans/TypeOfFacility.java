package beans;

public enum TypeOfFacility {
	Gym("Gym"),
	Pool("Pool"),
	SportCentre("SportCentre"),
	DanceStudio("DanceStudio");
	
	private String value;
	
	TypeOfFacility(String value) {
		this.value = value;
	}
	
	public static TypeOfFacility fromString(String value) {
		for(TypeOfFacility p : TypeOfFacility.values()) {
			if(p.value.equals(value)) {
				return p;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
}

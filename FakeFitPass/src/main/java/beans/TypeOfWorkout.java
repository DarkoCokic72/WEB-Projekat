package beans;

public enum TypeOfWorkout {
	Group("Group"), 
	Personal("Personal"), 
	Gym("Gym");
	
	private String value;
	
	TypeOfWorkout(String value){
		this.value = value;
	}
	
	public static TypeOfWorkout fromString(String value) {
		for(TypeOfWorkout p : TypeOfWorkout.values()) {
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
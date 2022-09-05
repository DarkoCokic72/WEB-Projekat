package dto;

public class CustomerAndWorkoutDTO {

	private String customerId;
	private String workoutId;
	
	public CustomerAndWorkoutDTO(String customerId, String workoutId) {
		super();
		this.customerId = customerId;
		this.workoutId = workoutId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getWorkoutId() {
		return workoutId;
	}

	public void setWorkoutId(String workoutId) {
		this.workoutId = workoutId;
	}
	
}

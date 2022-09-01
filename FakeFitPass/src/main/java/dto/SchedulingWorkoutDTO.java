package dto;

public class SchedulingWorkoutDTO {

	
	private String id;
	private String dateTimeOfWorkout;
	
	public SchedulingWorkoutDTO(String id, String dateTimeOfWorkout) {
		super();
		this.id = id;
		this.dateTimeOfWorkout = dateTimeOfWorkout;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateTimeOfWorkout() {
		return dateTimeOfWorkout;
	}

	public void setDateTimeOfWorkout(String dateTimeOfWorkout) {
		this.dateTimeOfWorkout = dateTimeOfWorkout;
	}
	
}

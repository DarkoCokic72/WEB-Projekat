package beans;

import java.util.Date;

public class Coach extends User{
	private WorkoutHistory workoutHistory;

	public Coach(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			WorkoutHistory workoutHistory) {
		super(username, password, name, surname, gender, dateOfBirth, Role.Coach);
		this.workoutHistory = workoutHistory;
	}

	public WorkoutHistory getWorkoutHistory() {
		return workoutHistory;
	}

	public void setWorkoutHistory(WorkoutHistory workoutHistory) {
		this.workoutHistory = workoutHistory;
	}
	
	
}

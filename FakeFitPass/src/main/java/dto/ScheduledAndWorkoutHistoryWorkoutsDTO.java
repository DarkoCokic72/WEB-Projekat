package dto;

import java.time.LocalDateTime;

import beans.Workout;

public class ScheduledAndWorkoutHistoryWorkoutsDTO implements Comparable<ScheduledAndWorkoutHistoryWorkoutsDTO>{

	private LocalDateTime dateTimeOfWorkout;
	private Workout workout;
	
	public ScheduledAndWorkoutHistoryWorkoutsDTO(LocalDateTime dateTimeOfWorkout, Workout workout) {
		super();
		this.dateTimeOfWorkout = dateTimeOfWorkout;
		this.workout = workout;
	}

	public LocalDateTime getDateTimeOfWorkout() {
		return dateTimeOfWorkout;
	}

	public void setDateTimeOfWorkout(LocalDateTime dateTimeOfWorkout) {
		this.dateTimeOfWorkout = dateTimeOfWorkout;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}
	
	@Override
	public int compareTo(ScheduledAndWorkoutHistoryWorkoutsDTO e) {
		return this.getDateTimeOfWorkout().compareTo(e.getDateTimeOfWorkout());
	}
	
}

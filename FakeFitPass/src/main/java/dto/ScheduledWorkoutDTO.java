package dto;

import java.time.LocalDateTime;

import beans.Workout;

public class ScheduledWorkoutDTO {

	private String id;
	private String username;
	private String name;
	private String surname;
	private Workout workout;
	private LocalDateTime dateTimeOfWorkout;
	private boolean isVisible;
	
	public ScheduledWorkoutDTO(String id, String username, String name, String surname, Workout workout, LocalDateTime dateTimeOfWorkout) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.workout = workout;
		this.dateTimeOfWorkout = dateTimeOfWorkout;
		this.isVisible = false;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

	public LocalDateTime getDateTimeOfWorkout() {
		return dateTimeOfWorkout;
	}

	public void setDateTimeOfWorkout(LocalDateTime dateTimeOfWorkout) {
		this.dateTimeOfWorkout = dateTimeOfWorkout;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}

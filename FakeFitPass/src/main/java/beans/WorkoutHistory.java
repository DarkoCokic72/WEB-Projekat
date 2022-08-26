package beans;

import java.time.LocalDateTime;

public class WorkoutHistory {
	private String id;
	private LocalDateTime timeOfCheckIn;
	private Workout workout;
	private Customer customer;
	private Coach coach;
	
	public WorkoutHistory() {}
	
	public WorkoutHistory(String id, LocalDateTime timeOfCheckIn, Workout workout, Customer customer, Coach coach) {
		super();
		this.id = id;
		this.timeOfCheckIn = timeOfCheckIn;
		this.workout = workout;
		this.customer = customer;
		this.coach = coach;
	}
	
	public WorkoutHistory(WorkoutHistory workoutHistory) {
		this.id = workoutHistory.getId();
		this.timeOfCheckIn = workoutHistory.getTimeOfCheckIn();
		this.workout = workoutHistory.getWorkout();
		this.customer = workoutHistory.getCustomer();
		this.coach = workoutHistory.getCoach();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getTimeOfCheckIn() {
		return timeOfCheckIn;
	}

	public void setTimeOfCheckIn(LocalDateTime timeOfCheckIn) {
		this.timeOfCheckIn = timeOfCheckIn;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	
	
}

package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import beans.Coach;
import beans.SportFacility;
import beans.TypeOfWorkout;
import beans.Workout;
import repository.CoachRepository;
import repository.LogicalEntity;
import repository.SportFacilityRepository;
import repository.WorkoutRepository;

public class WorkoutService {
	
	private WorkoutRepository workoutRepository = new WorkoutRepository();
	private SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
	private CoachRepository coachRepository = new CoachRepository();

	public boolean add() {
		Workout workout = new Workout("2", "Kardio trening", TypeOfWorkout.Personal, sportFacilityRepository.getOne("SuperGym"), LocalDateTime.of(2022, 8, 24, 0, 30), coachRepository.getOne("trener"), "gsgfgdfdfg", null);
		return workoutRepository.addOne(new Workout(workout));
	}
	
	public List<Workout> getAllWorkoutsForCoach(String username){
		List<Workout> workouts = new ArrayList<Workout>();
		for(Workout workout: workoutRepository.getAll()) {
			if(workout.getCoach().getUsername().equals(username)) {
				if(workout.getType().equals(TypeOfWorkout.Personal) || workout.getType().equals(TypeOfWorkout.Group)) {
					for(LogicalEntity<SportFacility> sportFacility: sportFacilityRepository.getAllLogical()) {
						if(workout.getSportFacility().getName().equals(sportFacility.entity.getName()) && sportFacility.deleted == false) {
							workouts.add(workout);
						}
					}
				}
			}
		}
		return workouts;
	}
	
	public List<Workout> getAllWorkoutsForManager(String sportFacilityName){
		List<Workout> workouts = new ArrayList<Workout>();
		for(Workout workout: workoutRepository.getAll()) {
			if(workout.getSportFacility().getName().equals(sportFacilityName)) {
				for(LogicalEntity<SportFacility> sportFacility: sportFacilityRepository.getAllLogical()) {
					if(workout.getSportFacility().getName().equals(sportFacility.entity.getName()) && sportFacility.deleted == false) {
						workouts.add(workout);
					}
				}
			}
		}
		return workouts;
	}
	
	public List<Workout> getAllPersonalWorkouts(){
		List<Workout> workouts = new ArrayList<Workout>();
		for(Workout workout: workoutRepository.getAll()) {
			if(workout.getType().equals(TypeOfWorkout.Personal)) {
				for(LogicalEntity<SportFacility> sportFacility: sportFacilityRepository.getAllLogical()) {
					if(workout.getSportFacility().getName().equals(sportFacility.entity.getName()) && sportFacility.deleted == false) {
						workouts.add(workout);
					}
				}
			}
		}
		return workouts;
	}
	
	public Workout findWorkoutById(String id) {
		for(Workout workout: workoutRepository.getAll()) {
			if(workout.getId().equals(id)) {
				for(LogicalEntity<SportFacility> sportFacility: sportFacilityRepository.getAllLogical()) {
					if(workout.getSportFacility().getName().equals(sportFacility.entity.getName()) && sportFacility.deleted == false) {
						return workout;
					}
				}
			}
		}
		return null;
	}
	
	public String findCoachOfWorkoutById(String id) {
		for(Workout workout: workoutRepository.getAll()) {
			if(workout.getId().equals(id)) {
				for(LogicalEntity<SportFacility> sportFacility: sportFacilityRepository.getAllLogical()) {
					if(workout.getSportFacility().getName().equals(sportFacility.entity.getName()) && sportFacility.deleted == false) {
						return workout.getCoach().getUsername();
					}
				}
			}
		}
		return null;
	}
	
	public List<Coach> getCoachesBySportFacilityName(String sportFacilityName){
		List<Coach> coaches = new ArrayList<Coach>();
		for(Workout workout: workoutRepository.getAll()) {
			if(workout.getSportFacility().getName().equals(sportFacilityName)) {
				if(isCoachDuplicated(workout.getCoach().getUsername(), coaches)) {
					for(LogicalEntity<SportFacility> sportFacility: sportFacilityRepository.getAllLogical()) {
						if(workout.getSportFacility().getName().equals(sportFacility.entity.getName()) && sportFacility.deleted == false) {
							coaches.add(workout.getCoach());
						}
					}
				}
			}
		}
		return coaches;
	}
	
	private boolean isCoachDuplicated(String username, List<Coach> coaches) {
		for(Coach coach: coaches) {
			if(coach.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public List<Workout> getWorkoutsBySportFacilityName(String sportFacilityName){
		List<Workout> workouts = new ArrayList<Workout>();
		for(Workout workout: workoutRepository.getAll()) {
			if(workout.getSportFacility().getName().equals(sportFacilityName)) {
				for(LogicalEntity<SportFacility> sportFacility: sportFacilityRepository.getAllLogical()) {
					if(workout.getSportFacility().getName().equals(sportFacility.entity.getName()) && sportFacility.deleted == false) {
						workouts.add(workout);
					}
				}
			}
		}
		return null;
	}
	
	public boolean deleteWorkout(String id) {
		for(Workout workout: workoutRepository.getAll()) {
			if(workout.getId().equals(id)) {
				workoutRepository.delete(id);
				return true;
			}
		}
		return false;
	}
	
	public void addNewWorkout(String sportFacilityName, Workout workout) {
		workoutRepository.addOne(new Workout(workout.getId(), workout.getName(), workout.getType(), sportFacilityRepository.getOne(sportFacilityName), workout.getDuration(), workout.getCoach(), workout.getDescription(), workout.getImage()));
	}
	
	public void editWorkout(String sportFacilityName, Workout workout) {
		for(Workout w: workoutRepository.getAll()) {
			if(w.getName().equals(workout.getName())) {
				workoutRepository.update(w.getId(), workout);
			}
		}
	}
}

package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import beans.TypeOfWorkout;
import beans.Workout;
import repository.CoachRepository;
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
					workouts.add(workout);
				}
			}
		}
		return workouts;
	}
}

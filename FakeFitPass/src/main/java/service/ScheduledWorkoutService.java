package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import beans.IdGenerator;
import beans.WorkoutHistory;
import dto.ScheduledWorkoutDTO;
import repository.CustomerRepository;
import repository.ScheduledWorkoutRepository;
import repository.WorkoutHistoryRepository;

public class ScheduledWorkoutService {

	private ScheduledWorkoutRepository scheduledWorkoutRepository = new ScheduledWorkoutRepository();
	private WorkoutHistoryRepository workoutHistoryRepository = new WorkoutHistoryRepository();
	private CustomerRepository customerRepository = new CustomerRepository();
	private IdGenerator idGenerator = new IdGenerator();
	
	public void addScheduledWorkout(ScheduledWorkoutDTO scheduledWorkoutDTO) {
		scheduledWorkoutRepository.addOne(scheduledWorkoutDTO);
	}
	
	public boolean findWorkoutByCoachAndTime(String username, LocalDateTime dateTimeOfWorkout) {
		for(ScheduledWorkoutDTO scheduledWorkoutDTO: scheduledWorkoutRepository.getAll()) {
			if(scheduledWorkoutDTO.getWorkout().getCoach().getUsername().equals(username) && scheduledWorkoutDTO.getDateTimeOfWorkout().equals(dateTimeOfWorkout)) {
				return true;
			}
		}
		return false;
	}
	
	public List<ScheduledWorkoutDTO> getAllScheduledWorkouts(String username){
		List<ScheduledWorkoutDTO> scheduledWorkouts = new ArrayList<ScheduledWorkoutDTO>();
		findScheduledWorkoutsForDelete();
		for(ScheduledWorkoutDTO scheduledWorkout: scheduledWorkoutRepository.getAll()) {
			if(scheduledWorkout.getWorkout().getCoach().getUsername().equals(username)) {
				if(scheduledWorkout.getDateTimeOfWorkout().minusDays(2).compareTo(LocalDateTime.now()) < 0) {
					scheduledWorkout.setIsVisible(true);
				}
				scheduledWorkouts.add(scheduledWorkout);
			}
		}
		return scheduledWorkouts;
	}
	
	private void findScheduledWorkoutsForDelete() {
		for(ScheduledWorkoutDTO scheduledWorkout: scheduledWorkoutRepository.getAll()) {
			if(scheduledWorkout.getDateTimeOfWorkout().isBefore(LocalDateTime.now()) || scheduledWorkout.getDateTimeOfWorkout().equals(LocalDateTime.now())) {
				workoutHistoryRepository.addOne(new WorkoutHistory(idGenerator.generateRandomKey(4), scheduledWorkout.getDateTimeOfWorkout(), scheduledWorkout.getWorkout(), customerRepository.getOne(scheduledWorkout.getUsername()), scheduledWorkout.getWorkout().getCoach()));
				cancelWorkout(scheduledWorkout.getId());
			}
		}
	}
	
	public boolean cancelWorkout(String id) {
		for(ScheduledWorkoutDTO scheduledWorkout: scheduledWorkoutRepository.getAll()) {
			if(scheduledWorkout.getId().equals(id)) {
				scheduledWorkoutRepository.delete(id);
				return true;
			}
		}
		return false;
	}
}

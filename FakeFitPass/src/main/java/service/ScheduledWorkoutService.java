package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dto.ScheduledWorkoutDTO;
import repository.ScheduledWorkoutRepository;

public class ScheduledWorkoutService {

	private ScheduledWorkoutRepository scheduledWorkoutRepository = new ScheduledWorkoutRepository();
	
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

package service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import beans.TypeOfWorkout;
import beans.Workout;
import beans.WorkoutHistory;
import repository.CoachRepository;
import repository.CustomerRepository;
import repository.SportFacilityRepository;
import repository.WorkoutHistoryRepository;

public class WorkoutHistoryService {

	private WorkoutHistoryRepository workoutHistoryRepository = new WorkoutHistoryRepository();
	private CoachRepository coachRepository = new CoachRepository();
	private CustomerRepository customerRepository = new CustomerRepository();
	private SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
	
	
	public boolean add() {
		WorkoutHistory workoutHistory = new WorkoutHistory("3", LocalDateTime.now(), new Workout("4", "Push trening", TypeOfWorkout.Group, sportFacilityRepository.getOne("ProGym"), LocalDateTime.now(), coachRepository.getOne("trener"), "gsgfgdfdfg", null), customerRepository.getOne("kupac12"), coachRepository.getOne("trener"));
		return workoutHistoryRepository.addOne(new WorkoutHistory(workoutHistory));
	}
	
	public List<WorkoutHistory> getAllWorkoutsInLastMonth(String username){
		List<WorkoutHistory> historyOfWorkoutsInLastMonth = new ArrayList<WorkoutHistory>();
		for(WorkoutHistory workoutHistory : workoutHistoryRepository.getAll()) {
			if(workoutHistory.getCustomer().getUsername().equals(username)) {
				if(LocalDateTime.now().minusMonths(1).compareTo(workoutHistory.getTimeOfCheckIn()) <= 0) {
					historyOfWorkoutsInLastMonth.add(workoutHistory);
				}
			}
		}
		return historyOfWorkoutsInLastMonth;
	}
}

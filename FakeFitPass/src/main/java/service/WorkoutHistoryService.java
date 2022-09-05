package service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import beans.Customer;
import beans.IdGenerator;
import beans.TypeOfFacility;
import beans.TypeOfWorkout;
import beans.Workout;
import beans.WorkoutHistory;
import dto.ScheduledAndWorkoutHistoryWorkoutsDTO;
import dto.ScheduledWorkoutDTO;
import repository.CoachRepository;
import repository.CustomerRepository;
import repository.ScheduledWorkoutRepository;
import repository.SportFacilityRepository;
import repository.WorkoutHistoryRepository;

public class WorkoutHistoryService {

	private WorkoutHistoryRepository workoutHistoryRepository = new WorkoutHistoryRepository();
	private CoachRepository coachRepository = new CoachRepository();
	private CustomerRepository customerRepository = new CustomerRepository();
	private SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
	private ScheduledWorkoutRepository scheduledWorkoutRepository = new ScheduledWorkoutRepository();
	private IdGenerator idGenerator = new IdGenerator();
	private int counter = 0;
	
	
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
	
	public List<Customer> getCustomersBySportFacilityName(String sportFacilityName){
		List<Customer> customers = new ArrayList<Customer>();
		for(WorkoutHistory workoutHistory: workoutHistoryRepository.getAll()) {
			if(workoutHistory.getWorkout().getSportFacility().getName().equals(sportFacilityName)) {
				if(isCustomerAlreadyInCustomers(workoutHistory.getCustomer().getUsername(), customers)) {
					customers.add(workoutHistory.getCustomer());
				}
			}
		}
		return customers;
	}
	
	private boolean isCustomerAlreadyInCustomers(String username, List<Customer> customers) {
		for(Customer customer: customers) {
			if(username.equals(customer.getUsername())) {
				return false;
			}
		}
		return true;
	}
	
	public List<ScheduledAndWorkoutHistoryWorkoutsDTO> getAllScheduledAndWorkoutHistoryWorkouts(){
		List<ScheduledAndWorkoutHistoryWorkoutsDTO> sh = new ArrayList<ScheduledAndWorkoutHistoryWorkoutsDTO>();
		for(WorkoutHistory wh: workoutHistoryRepository.getAll()) {
			sh.add(new ScheduledAndWorkoutHistoryWorkoutsDTO(wh.getTimeOfCheckIn(), wh.getWorkout()));
		}
		for(ScheduledWorkoutDTO sw: scheduledWorkoutRepository.getAll()) {
			sh.add(new ScheduledAndWorkoutHistoryWorkoutsDTO(sw.getDateTimeOfWorkout(), sw.getWorkout()));
		}
		return sh;
	}
	
	public List<ScheduledAndWorkoutHistoryWorkoutsDTO> searchWorkouts(List<ScheduledAndWorkoutHistoryWorkoutsDTO> scheduledAndWorkoutHistoryWorkouts, String sportFacilityNameSearch, String dateFromStr, String dateToStr) throws ParseException {
		Date dateFrom;
		Date dateTo;
		try {
			dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(dateFromStr);
			dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(dateToStr);
			
		} catch (ParseException e) {
		
			dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse("1980-01-01");
			dateTo = new SimpleDateFormat("yyyy-MM-dd").parse("2100-01-01");
		}
		List<ScheduledAndWorkoutHistoryWorkoutsDTO> searchedWorkouts = new ArrayList<ScheduledAndWorkoutHistoryWorkoutsDTO>();
		for(ScheduledAndWorkoutHistoryWorkoutsDTO sw: scheduledAndWorkoutHistoryWorkouts) {
			Date date = Date.from(sw.getDateTimeOfWorkout().atZone(ZoneId.systemDefault()).toInstant());
			if(sw.getWorkout().getSportFacility().getName().toLowerCase().startsWith(sportFacilityNameSearch.toLowerCase()) && (date.getTime() >= dateFrom.getTime() && date.getTime() <= dateTo.getTime())) {
				searchedWorkouts.add(sw);
			}
		}
		return searchedWorkouts;
	}
	
	public List<ScheduledAndWorkoutHistoryWorkoutsDTO> filterWorkouts(List<ScheduledAndWorkoutHistoryWorkoutsDTO> scheduledAndWorkoutHistoryWorkouts, String typeFacilityFilter, String typeWorkoutFilter){
		List<ScheduledAndWorkoutHistoryWorkoutsDTO> filteredWorkouts = new ArrayList<ScheduledAndWorkoutHistoryWorkoutsDTO>();
		for(ScheduledAndWorkoutHistoryWorkoutsDTO sw: scheduledAndWorkoutHistoryWorkouts) {
			if(sw.getWorkout().getSportFacility().getType().equals(TypeOfFacility.fromString(typeFacilityFilter)) && typeWorkoutFilter == "") {
				filteredWorkouts.add(sw);
			}else if(sw.getWorkout().getType().equals(TypeOfWorkout.fromString(typeWorkoutFilter)) && typeFacilityFilter == "") {
				filteredWorkouts.add(sw);
			}else if(sw.getWorkout().getSportFacility().getType().equals(TypeOfFacility.fromString(typeFacilityFilter)) && sw.getWorkout().getType().equals(TypeOfWorkout.fromString(typeWorkoutFilter))) {
				filteredWorkouts.add(sw);
			}
		}
		return filteredWorkouts;
	}
	
	public List<ScheduledAndWorkoutHistoryWorkoutsDTO> sortWorkoutsByDateTime(){
		List<ScheduledAndWorkoutHistoryWorkoutsDTO> scheduledAndWorkoutHistoryWorkouts = getAllScheduledAndWorkoutHistoryWorkouts();
		++counter;
		if(counter == 1) {
			Collections.sort(scheduledAndWorkoutHistoryWorkouts);
		}else if(counter == 2) {
			Collections.sort(scheduledAndWorkoutHistoryWorkouts, Collections.reverseOrder());
			counter = 0;
		}
		return scheduledAndWorkoutHistoryWorkouts;
	}
	
	public boolean defineTerm(Customer customer, Workout workout) {
		return workoutHistoryRepository.addOne(new WorkoutHistory(idGenerator.generateRandomKey(4), LocalDateTime.now(), workout, customer, workout.getCoach()));
	}
}

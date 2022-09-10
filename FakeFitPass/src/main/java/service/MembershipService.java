package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import beans.Customer;
import beans.IdGenerator;
import beans.Membership;
import beans.PromoCode;
import beans.TypeOfMembership;
import beans.WorkoutHistory;
import dto.CheckPromoCodeDTO;
import dto.MembershipDTO;
import dto.ScheduledWorkoutDTO;
import repository.CustomerRepository;
import repository.MembershipDTORepository;
import repository.MembershipRepository;
import repository.PromoCodeRepository;
import repository.ScheduledWorkoutRepository;
import repository.WorkoutHistoryRepository;

public class MembershipService {

	private MembershipRepository membershipRepository = new MembershipRepository();
	private CustomerRepository customerRepository = new CustomerRepository();
	private ScheduledWorkoutRepository scheduledWorkoutRepository = new ScheduledWorkoutRepository();
	private WorkoutHistoryRepository workoutHistoryRepository = new WorkoutHistoryRepository();
	private PromoCodeRepository promoCodeRepository = new PromoCodeRepository();
	private MembershipDTORepository membershipDTORepository = new MembershipDTORepository();
	private IdGenerator idGenerator = new IdGenerator();
	
	public String addMembership(String username, MembershipDTO membershipDTO) {
		String id = idGenerator.generateRandomKey(4);
		Membership membership = findMembershipById(username);
		if(membership == null) {
			if(membershipDTO.getType().equals(TypeOfMembership.Monthly)) {
				membershipRepository.addOne(new Membership(id, membershipDTO.getType(), new Date(), LocalDateTime.now().plusMonths(1), membershipDTO.getPrice(), true, membershipDTO.getNumberOfAppointments()));
			}else {
				membershipRepository.addOne(new Membership(id, membershipDTO.getType(), new Date(), LocalDateTime.now().plusYears(1), membershipDTO.getPrice(), true, membershipDTO.getNumberOfAppointments()));
			}
		}else {
			if(membershipDTO.getType().equals(TypeOfMembership.Monthly)) {
				membershipRepository.update(membership.getMembershipID(), new Membership(id, membershipDTO.getType(), new Date(), LocalDateTime.now().plusMonths(1),
						membershipDTO.getPrice(), true, membershipDTO.getNumberOfAppointments()));
			}else {
				membershipRepository.update(membership.getMembershipID(), new Membership(id, membershipDTO.getType(), new Date(), LocalDateTime.now().plusYears(1),
						membershipDTO.getPrice(), true, membershipDTO.getNumberOfAppointments()));
			}
		}
		return id;
	}
	
	public Membership findMembershipById(String username) {
		String membershipId = null;
		for(Customer customer: customerRepository.getAll()) {
			if(customer.getUsername().equals(username)) {
				membershipId = customer.getMembershipId(); 
			}
		}
		for(Membership membership: membershipRepository.getAll()) {
			if(membership.getMembershipID().equals(membershipId)) {
				return membership;
			}
		}
		return null;
	}
	
	public boolean changeStatus() {
		for(Membership membership: membershipRepository.getAll()) {
			if(membership.getNumberOfAppointments() == 0 || membership.getPeriodOfValidity().compareTo(LocalDateTime.now()) <= 0) {
				membership.setStatus(false);
				membershipRepository.update(membership.getMembershipID(), membership);
				return true;
			}
		}
		return false;
	}
	
	public boolean decreaseNumberOfAppointmentsByOne() {
		for(ScheduledWorkoutDTO scheduledWorkout: scheduledWorkoutRepository.getAll()) {
			if(scheduledWorkout.getDateTimeOfWorkout().isBefore(LocalDateTime.now()) || scheduledWorkout.getDateTimeOfWorkout().equals(LocalDateTime.now())) {
				workoutHistoryRepository.addOne(new WorkoutHistory(idGenerator.generateRandomKey(4), scheduledWorkout.getDateTimeOfWorkout(), scheduledWorkout.getWorkout(), customerRepository.getOne(scheduledWorkout.getUsername()), scheduledWorkout.getWorkout().getCoach()));
				cancelWorkout(scheduledWorkout.getId());
				decreaseNumberOfAppointments(findMembershipById(scheduledWorkout.getUsername()));
				return true;
			}
		}
		return false;
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
	
	public void decreaseNumberOfAppointments(Membership membership) {
		membership.setNumberOfAppointments(membership.getNumberOfAppointments() - 1);
		membershipRepository.update(membership.getMembershipID(), membership);
	}
	
	public MembershipDTO checkPromoCode(CheckPromoCodeDTO checkPromoCodeDTO) {
		int cnt = 0;
		double discount = 0;
		String promoCodeId = "";
		for(PromoCode promoCode: promoCodeRepository.getAll()) {
			if (promoCode.getId().equals(checkPromoCodeDTO.getPromoCode()) && promoCode.getStartDate().compareTo(LocalDate.now()) <= 0 && promoCode.getEndDate().compareTo(LocalDate.now()) >= 0 && promoCode.getQuantity() > 0) {
				cnt++;
				discount = promoCode.getDiscountPercentage();
				promoCodeId = promoCode.getId();
				break;
			}
		}
		
		if (cnt == 0) {
			return null;
		}else {
			MembershipDTO membershipDTO = membershipDTORepository.getOne(checkPromoCodeDTO.getMembershipId());
			membershipDTO.setPrice(membershipDTO.getPrice()*((100 - discount)/100));
			
			PromoCode promoCode = promoCodeRepository.getOne(promoCodeId);
			promoCode.setQuantity(promoCode.getQuantity() - 1);
			promoCodeRepository.update(promoCodeId, promoCode);
			
			return new MembershipDTO(membershipDTO.getMembershipID(), membershipDTO.getType(), membershipDTO.getPrice(), membershipDTO.getNumberOfAppointments());
		}
	}
}

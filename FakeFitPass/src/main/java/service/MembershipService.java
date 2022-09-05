package service;

import java.time.LocalDateTime;
import java.util.Date;

import beans.Customer;
import beans.IdGenerator;
import beans.Membership;
import beans.TypeOfMembership;
import dto.MembershipDTO;
import repository.CustomerRepository;
import repository.MembershipRepository;

public class MembershipService {

	private MembershipRepository membershipRepository = new MembershipRepository();
	private CustomerRepository customerRepository = new CustomerRepository();
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
	
	private Membership findMembershipById(String username) {
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
}

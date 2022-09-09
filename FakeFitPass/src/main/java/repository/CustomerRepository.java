package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.*;
import service.MembershipService;

public class CustomerRepository extends Repository<Customer, String>{
	
	private MembershipRepository membershipRepository = new MembershipRepository();
	
	@Override
	protected String getKey(Customer customer) {
		return customer.getUsername();
	}
	@Override
	protected Type getTokenType() {
		return new TypeToken<ArrayList<LogicalEntity<Customer>>>() {}.getType();
	}
	
	public boolean addMembershipId(String username, String membershipId) {
		for(Customer customer: getAll()) {
			if(customer.getUsername().equals(username)) {
				customer.setMembership(membershipId);
				update(username, customer);
				return true;
			}
		}
		return false;
	}
	
	public void setTypeOfCustomer(int numberOfUsedTerms, Customer customer) {
		if(numberOfUsedTerms < (numberOfUsedTerms + findMembershipById(customer.getUsername()).getNumberOfAppointments()) / 3) {
			customer.setCollectedPoints(customer.getCollectedPoints() - (findMembershipById(customer.getUsername()).getPrice() / (1000 * 133 * 4)));
			update(customer.getUsername(), customer);
		}else {
			customer.setCollectedPoints(customer.getCollectedPoints() + (findMembershipById(customer.getUsername()).getPrice() / (1000 * numberOfUsedTerms)));
			update(customer.getUsername(), customer);
		}
	}
	
	private Membership findMembershipById(String username) {
		String membershipId = null;
		for(Customer customer: getAll()) {
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

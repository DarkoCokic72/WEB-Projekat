package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.*;

public class CustomerRepository extends Repository<Customer, String>{
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
}

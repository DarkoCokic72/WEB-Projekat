package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.*;
import repository.LogicalEntity;
import repository.Repository;

public class CustomerRepository extends Repository<Customer, String>{
	@Override
	protected String getKey(Customer customer) {
		return customer.getUsername();
	}
	@Override
	protected Type getTokenType() {
		return new TypeToken<ArrayList<LogicalEntity<Customer>>>() {}.getType();
	}
}

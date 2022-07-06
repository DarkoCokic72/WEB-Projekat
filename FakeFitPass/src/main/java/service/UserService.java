package service;

import beans.User;

import java.util.ArrayList;
import java.util.List;

import beans.Administrator;
import beans.Coach;
import beans.Customer;
import beans.Manager;
import repository.AdministratorRepository;
import repository.CoachRepository;
import repository.CustomerRepository;
import repository.ManagerRepository;

public class UserService {
	private CustomerRepository customerRepository = new CustomerRepository();
	private CoachRepository coachRepository =  new CoachRepository();
	private ManagerRepository managerRepository = new ManagerRepository();
	private AdministratorRepository administratorRepository = new AdministratorRepository();
	
	public User findByID(String username) {
		User user;
		user = customerRepository.getOne(username);
		if (user != null) {
			return user;
		}
		user = coachRepository.getOne(username);
		if (user != null) {
			return user;
		}
		
		user = managerRepository.getOne(username);
		if (user != null) {
			return user;
		}
		
		user = administratorRepository.getOne(username);
		if (user != null) {
			return user;
		}
		return user;
	}
	
	public boolean register(User user) {
		switch(user.getRoleName()) {
		case "Administrator":
			return administratorRepository.addOne(new Administrator(user));
		case "Coach":
			return coachRepository.addOne(new Coach(user));
		case "Customer":
			return customerRepository.addOne(new Customer(user));
		case "Manager":
			return managerRepository.addOne(new Manager(user));
		default:
			return false;
		}
	}
	
	public void update(User user) {
		switch(user.getRole()) {
		case Administrator:
			administratorRepository.update(user.getUsername(), (Administrator) user);
			break;
		case Coach:
			coachRepository.update(user.getUsername(), (Coach) user);
			break;
		case Customer:
			customerRepository.update(user.getUsername(), (Customer) user);
			break;
		case Manager:
			managerRepository.update(user.getUsername(), (Manager) user);
			break;
		default:
			break;
		}
	}
	
	public List<User> getAll() {
		List<User> retVal = new ArrayList<User>();
		for(Administrator administrator: administratorRepository.getAll()) {
			retVal.add((User) administrator);
		}
		for(Coach coach: coachRepository.getAll()) {
			retVal.add((User) coach);
		}
		for(Customer customer: customerRepository.getAll()) {
			retVal.add((User) customer);
		}
		for(Manager manager: managerRepository.getAll()) {
			retVal.add((User) manager);
		}
		return retVal;
	}
}

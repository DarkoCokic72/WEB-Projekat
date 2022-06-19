package service;

import beans.User;
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
}

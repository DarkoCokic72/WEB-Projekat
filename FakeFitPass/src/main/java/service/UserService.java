package service;

import beans.User;
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
}

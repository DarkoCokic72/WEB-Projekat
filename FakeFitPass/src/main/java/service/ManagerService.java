package service;

import beans.Manager;
import repository.ManagerRepository;

public class ManagerService {

	private ManagerRepository managerRepository = new ManagerRepository();
	
	public String findSportFacilityByManager(String username) {
		for(Manager manager: managerRepository.getAll()) {
			if(manager.getUsername().equals(username)) {
				return manager.getSportFacility().getName();
			}
		}
		return null;
	}
	
}

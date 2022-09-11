package service;

import beans.Manager;
import beans.SportFacility;
import repository.LogicalEntity;
import repository.ManagerRepository;
import repository.SportFacilityRepository;

public class ManagerService {

	private ManagerRepository managerRepository = new ManagerRepository();
	private SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
	
	public String findSportFacilityByManager(String username) {
		for(Manager manager: managerRepository.getAll()) {
			if(manager.getUsername().equals(username)) {
				for(LogicalEntity<SportFacility> sportFacility: sportFacilityRepository.getAllLogical()) {
					if(manager.getSportFacility().getName().equals(sportFacility.entity.getName()) && sportFacility.deleted == false) {
						return manager.getSportFacility().getName();
					}
				}
			}
		}
		return null;
	}
	
}

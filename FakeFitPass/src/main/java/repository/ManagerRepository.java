package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import beans.Manager;
import beans.SportFacility;
import beans.Workout;
import repository.Repository;
import repository.LogicalEntity;

public class ManagerRepository extends Repository<Manager, String> {
	@Override
	protected String getKey(Manager manager) {
		return manager.getUsername();
	}
	@Override
	protected Type getTokenType() {
		return new TypeToken<ArrayList<LogicalEntity<Manager>>>() {}.getType();
	}
	
	public List<String> getFreeManagersUsernames() {
		List<String> retVal = new ArrayList<String>();
		for (Manager manager: getAll()) {
			if(manager.getSportFacility() == null) {
				retVal.add(manager.getUsername());
			}
		}
		return retVal;
	}
	
	public void setFacilityForManagerUsername(String facilityName, Manager manager) {
		SportFacility sportFacility = new SportFacilityRepository().getOne(facilityName);
		manager.setSportFacility(sportFacility);
		update(manager.getUsername(), manager);
	}
	
	public void setFacilityForManagerUsername(SportFacility sportFacility, String managerUsername) {
		Manager manager = getOne(managerUsername);
		manager.setSportFacility(sportFacility);
		update(managerUsername, manager);
	}
	
	public String getFacilityName(String username) {
		Manager menadzer = getOne(username);
		return menadzer.getSportFacility().getName();
	}
	
	public List<Workout> getContentByUsername(String username){
		Manager manager = getOne(username);
		return manager.getSportFacility().getContent();
	}
}

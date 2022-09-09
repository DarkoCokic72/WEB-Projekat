package service;

import java.util.ArrayList;
import java.util.List;

import beans.SportFacility;
import beans.TypeOfFacility;
import repository.SportFacilityRepository;

public class SportFacilityService {
	
	private SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
	
	public List<SportFacility> filterFacilities(List<SportFacility> unfiltered, String nameSearch, String locationSearch, String scoreSearch, String typeSearch){
		List<SportFacility> filtered = new ArrayList<SportFacility>();
		double scoreLowerBound = scoreSearch.equals("") ? -1 : Double.parseDouble(scoreSearch.split("-")[0]);
		
		for(SportFacility facility: unfiltered) 
		{
			if(facility.getName().toLowerCase().startsWith(nameSearch.toLowerCase()) &&
				(facility.getLocation().getCity().toLowerCase().startsWith(locationSearch.toLowerCase()) || facility.getLocation().getStreet().toLowerCase().startsWith(locationSearch.toLowerCase()))
				&& facility.getAverageScore() >= scoreLowerBound && (facility.getType().equals(TypeOfFacility.fromString(typeSearch)) || TypeOfFacility.fromString(typeSearch) == null)) 
			{
				filtered.add(facility);
			}
		}
		return filtered;
	}
	
	public boolean deleteFacility(String sportFacilityName) {
		for(SportFacility facility: sportFacilityRepository.getAll()) {
			if(facility.getName().equals(sportFacilityName)) {
				sportFacilityRepository.delete(sportFacilityName);
				return true;
			}
		}
		return false;
	}
}

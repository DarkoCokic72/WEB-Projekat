package service;

import java.util.ArrayList;
import java.util.List;

import beans.SportFacility;
import beans.TypeOfFacility;

public class SportFacilityService {
	public List<SportFacility> filterFacilities(List<SportFacility> unfiltered, String nameSearch, String locationSearch, String scoreSearch, String typeSearch){
		List<SportFacility> filtered = new ArrayList<SportFacility>();
		double scoreLowerBound = scoreSearch.equals("") ? -1 : Double.parseDouble(scoreSearch.split("-")[0]);
		
		for(SportFacility facility: unfiltered) {
			if(facility.getName().toLowerCase().startsWith(nameSearch.toLowerCase()) &&
				facility.getLocation().getCity().startsWith(locationSearch.toLowerCase()) &&
				(facility.getType().name().equals(typeSearch) || nameSearch == null) &&
				facility.getAverageScore() >= scoreLowerBound) {
				
				filtered.add(facility);
			}
		}
		
		return filtered;
	}
}

package repository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;


import beans.SportFacility;
import beans.Workout;
import repository.LogicalEntity;
import repository.Repository;

public class SportFacilityRepository  extends Repository<SportFacility, String>{

	@Override
	protected String getKey(SportFacility sportFacility) {
		return sportFacility.getName();
	}
	@Override
	protected Type getTokenType() {
		return new TypeToken<ArrayList<LogicalEntity<SportFacility>>>() {}.getType();
	}


	public Workout getWorkout(String facilityName, String workoutName) {
		return getOne(facilityName).getWorkout(workoutName);
	}
	
	public void addWorkoutToContent(String facilityName, Workout workout) {
		SportFacility facility = getOne(facilityName);
		facility.addWorkout(workout);
		update(facility.getName(), facility);
	}
	
	public List<Workout> getContentByFacilityName(String name){
		SportFacility facility = getOne(name);
		return facility.getContent();
	}
	
	public Workout getByContentName(String facilityName,String contentName) {
		SportFacility facility = getOne(facilityName);
		for(Workout w: facility.getContent()) {
			if(w.getName().equals(contentName)) {
				return w;
			}
		}
		
		return null;
	}
	
	public void putEditedContent(String facilityName, Workout workout) {
		SportFacility facility = getOne(facilityName);
		for(int i=0; i < facility.getContent().size(); i++) {
			Workout fromContent = facility.getContent().get(i);
			if(fromContent.getName().equals(workout.getName())) {
				facility.getContent().set(i, workout);
				break;
			}
		}
		
		update(facility.getName(), facility);
	}

}

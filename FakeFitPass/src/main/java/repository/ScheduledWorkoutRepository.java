package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import dto.ScheduledWorkoutDTO;

public class ScheduledWorkoutRepository extends Repository<ScheduledWorkoutDTO, String> {

	@Override
	protected String getKey(ScheduledWorkoutDTO scheduledWorkoutDTO) {
		
		return scheduledWorkoutDTO.getId();
	}

	@Override
	protected Type getTokenType() {
		
		return new TypeToken<ArrayList<LogicalEntity<ScheduledWorkoutDTO>>>(){}.getType();
	}
}

package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.Workout;

public class WorkoutRepository extends Repository<Workout, String> {

	@Override
	protected String getKey(Workout workout) {
		
		return workout.getId();
	}

	@Override
	protected Type getTokenType() {
		
		return new TypeToken<ArrayList<LogicalEntity<Workout>>>(){}.getType();
	}
}

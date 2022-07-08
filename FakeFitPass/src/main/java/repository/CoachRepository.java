package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import beans.Coach;
import repository.LogicalEntity;
import repository.Repository;

public class CoachRepository extends Repository<Coach, String>{
	@Override
	protected String getKey(Coach coach) {
		return coach.getUsername();
	}
	@Override
	protected Type getTokenType() {
		return new TypeToken<ArrayList<LogicalEntity<Coach>>>() {}.getType();
	}
	
	public List<String> getAllCoachesUsernames(){
		List<String> ret = new ArrayList<String>();
		for(Coach c: getAll()) {
			ret.add(c.getUsername());
		}
		return ret;
	}
	
	public Coach getCoachByUsername(String username) {
		return getOne(username);
	}
}

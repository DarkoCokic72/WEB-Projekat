package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.SportFacility;

public class SportFacilityRepository  extends Repository<SportFacility, String>{

	@Override
	protected String getKey(SportFacility sportFacility) {
		// TODO Auto-generated method stub
		return sportFacility.getName();
	}

	@Override
	protected Type getTokenType() {
		// TODO Auto-generated method stub
		return new TypeToken<ArrayList<LogicalEntity<SportFacility>>>() {}.getType();
	}

}

package repository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.reflect.TypeToken;


import beans.SportFacility;
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


	
	

}

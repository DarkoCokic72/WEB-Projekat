package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.Administrator;
import repository.LogicalEntity;

public class AdministratorRepository extends Repository<Administrator, String> {

	@Override
	protected String getKey(Administrator administrator) {
		
		return administrator.getUsername();
	}

	@Override
	protected Type getTokenType() {
		
		return new TypeToken<ArrayList<LogicalEntity<Administrator>>>() {}.getType();
	}

}

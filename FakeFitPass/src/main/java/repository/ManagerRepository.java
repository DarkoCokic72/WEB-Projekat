package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.Manager;

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
}

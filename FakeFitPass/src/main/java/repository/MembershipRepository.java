package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.Membership;

public class MembershipRepository extends Repository<Membership, String>{

	@Override
	protected String getKey(Membership membership) {
		
		return membership.getMembershipID();
	}

	@Override
	protected Type getTokenType() {
		
		return new TypeToken<ArrayList<LogicalEntity<Membership>>>(){}.getType();
	}
}

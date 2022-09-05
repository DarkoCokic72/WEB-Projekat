package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import dto.MembershipDTO;

public class MembershipDTORepository extends Repository<MembershipDTO, String>{

	@Override
	protected String getKey(MembershipDTO membershipDTO) {
		
		return membershipDTO.getMembershipID();
	}

	@Override
	protected Type getTokenType() {
		
		return new TypeToken<ArrayList<LogicalEntity<MembershipDTO>>>(){}.getType();
	}
}

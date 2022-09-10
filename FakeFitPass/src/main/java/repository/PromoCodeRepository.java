package repository;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import beans.PromoCode;

public class PromoCodeRepository extends Repository<PromoCode, String>{

	@Override
	protected String getKey(PromoCode promoCode) {
		
		return promoCode.getId();
	}

	@Override
	protected Type getTokenType() {
		
		return new TypeToken<ArrayList<LogicalEntity<PromoCode>>>(){}.getType();
	}
}

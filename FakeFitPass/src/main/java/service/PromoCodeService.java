package service;

import beans.PromoCode;
import repository.PromoCodeRepository;

public class PromoCodeService {

	private PromoCodeRepository promoCodeRepository = new PromoCodeRepository();
	
	public boolean addNewPromoCode(PromoCode promoCode) {
		return promoCodeRepository.addOne(promoCode);
	}
}

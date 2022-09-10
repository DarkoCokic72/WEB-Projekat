package dto;

public class CheckPromoCodeDTO {

	private String promoCode;
	private String membershipId;
	
	public CheckPromoCodeDTO() {}

	public CheckPromoCodeDTO(String promoCode, String membershipId) {
		super();
		this.promoCode = promoCode;
		this.membershipId = membershipId;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
}

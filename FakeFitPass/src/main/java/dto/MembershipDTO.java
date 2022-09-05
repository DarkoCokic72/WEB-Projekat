package dto;

import beans.TypeOfMembership;

public class MembershipDTO {

	private String membershipID;
	private TypeOfMembership type;
	private double price;
	private int numberOfAppointments;
	
	public MembershipDTO(String membershipID, TypeOfMembership type, double price, int numberOfAppointments) {
		super();
		this.membershipID = membershipID;
		this.type = type;
		this.price = price;
		this.numberOfAppointments = numberOfAppointments;
	}

	public String getMembershipID() {
		return membershipID;
	}

	public void setMembershipID(String membershipID) {
		this.membershipID = membershipID;
	}

	public TypeOfMembership getType() {
		return type;
	}

	public void setType(TypeOfMembership type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumberOfAppointments() {
		return numberOfAppointments;
	}

	public void setNumberOfAppointments(int numberOfAppointments) {
		this.numberOfAppointments = numberOfAppointments;
	}
}

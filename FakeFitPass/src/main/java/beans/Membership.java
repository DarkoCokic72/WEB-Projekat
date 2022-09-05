package beans;

import java.time.LocalDateTime;
import java.util.Date;

public class Membership {
	private String membershipID;
	private TypeOfMembership type;
	private Date dateOfPayment;
	private LocalDateTime periodOfValidity;
	private double price;
	private boolean status;
	private int numberOfAppointments;
	
	public Membership() {}
	
	public Membership(String membershipID, TypeOfMembership type, Date dateOfPayment, LocalDateTime periodOfValidity,
			double price, boolean status, int numberOfAppointments) {
		super();
		this.membershipID = membershipID;
		this.type = type;
		this.dateOfPayment = dateOfPayment;
		this.periodOfValidity = periodOfValidity;
		this.price = price;
		this.status = status;
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

	public Date getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public LocalDateTime getPeriodOfValidity() {
		return periodOfValidity;
	}

	public void setPeriodOfValidity(LocalDateTime periodOfValidity) {
		this.periodOfValidity = periodOfValidity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getNumberOfAppointments() {
		return numberOfAppointments;
	}

	public void setNumberOfAppointments(int numberOfAppointments) {
		this.numberOfAppointments = numberOfAppointments;
	}
	
	
}

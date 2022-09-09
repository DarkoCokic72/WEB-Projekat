package dto;

import beans.Customer;
import beans.SportFacility;

public class CommentDTO {

	private Customer customer;
	private SportFacility sportFacility;
	private String text;
	private int score;
	private boolean isAproved;
	private boolean isDenied;
	
	public CommentDTO(Customer customer, SportFacility sportFacility, String text, int score, boolean isAproved,
			boolean isDenied) {
		super();
		this.customer = customer;
		this.sportFacility = sportFacility;
		this.text = text;
		this.score = score;
		this.isAproved = isAproved;
		this.isDenied = isDenied;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SportFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportFacility sportFacility) {
		this.sportFacility = sportFacility;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getIsAproved() {
		return isAproved;
	}

	public void setAproved(boolean isAproved) {
		this.isAproved = isAproved;
	}

	public boolean getIsDenied() {
		return isDenied;
	}

	public void setDenied(boolean isDenied) {
		this.isDenied = isDenied;
	}
	
	
}

package beans;

import dto.CommentDTO;

public class Comment {
	private String id;
	private Customer customer;
	private SportFacility sportFacility;
	private String text;
	private int score;
	private boolean isAproved;
	private boolean isDenied;
	
	public Comment() {}
	
	public Comment(String id, Customer customer, SportFacility sportFacility, String text, int score, boolean isAproved, boolean isDenied) {
		super();
		this.id = id;
		this.customer = customer;
		this.sportFacility = sportFacility;
		this.text = text;
		this.score = score;
		this.isAproved = isAproved;
		this.isDenied = isDenied;
	}
	
	public Comment(String id, CommentDTO commentDTO) {
		super();
		this.id = id;
		this.customer = commentDTO.getCustomer();
		this.sportFacility = commentDTO.getSportFacility();
		this.text = commentDTO.getText();
		this.score = commentDTO.getScore();
		this.isAproved = commentDTO.getIsAproved();
		this.isDenied = commentDTO.getIsDenied();
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

	public void setIsAproved(boolean isAproved) {
		this.isAproved = isAproved;
	}

	public boolean getIsDenied() {
		return isDenied;
	}

	public void setIsDenied(boolean isDenied) {
		this.isDenied = isDenied;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}

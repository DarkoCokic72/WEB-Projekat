package beans;

public class Comment {
	private Customer customer;
	private SportFacility sportFacility;
	private String text;
	private int score;
	
	public Comment() {}
	
	public Comment(Customer customer, SportFacility sportFacility, String text, int score) {
		super();
		this.customer = customer;
		this.sportFacility = sportFacility;
		this.text = text;
		this.score = score;
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
	
	
}

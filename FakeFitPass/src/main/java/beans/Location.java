package beans;

public class Location {
	private double longitude;
	private double latitude;
	private String street;
	private String number;
	private String city;
	private String postalCode;
	
	public Location() {}
	
	public Location(double longitude, double latitude, String street, String number, String city, String postalCode) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.street = street;
		this.number = number;
		this.city = city;
		this.postalCode = postalCode;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	
}

package beans;

import java.time.LocalDateTime;

public class SportFacilityTemp {

	private String name;
	private TypeOfFacility type;
	private Content content;
	private boolean status;
	private Location location;
	private String image;
	private double averageScore;
	private String startTime;
	private String endTime;
	
	public SportFacilityTemp() {
		averageScore = 0;
	}
	
	public SportFacilityTemp(String name, TypeOfFacility type, Content content, boolean status, Location location,
			String image, double averageScore, String startTime, String endTime) {
		super();
		this.name = name;
		this.type = type;
		this.content = content;
		this.status = status;
		this.location = location;
		this.image = image;
		this.averageScore = averageScore;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeOfFacility getType() {
		return type;
	}

	public void setType(TypeOfFacility type) {
		this.type = type;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}

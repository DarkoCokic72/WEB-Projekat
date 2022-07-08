package beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SportFacility {
	private String name;
	private TypeOfFacility type;
	private List<Workout> content;
	private boolean status;
	private Location location;
	private String image;
	private double averageScore;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	public SportFacility() {
		averageScore = 0;
		this.content = new ArrayList<Workout>();
	}
	
	public SportFacility(String name, TypeOfFacility type, boolean status, Location location,
			String image, double averageScore, LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.name = name;
		this.type = type;
		this.content = new ArrayList<Workout>();
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

	public List<Workout> getContent() {
		return content;
	}

	public void setContent(List<Workout> content) {
		this.content = content;
	}

	public boolean getStatus() {
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

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	
	
	public Workout getWorkout(String workoutName) {
		if(content.isEmpty())
			return null;
		for (Workout w: content) {
			if(w.getName().equals(workoutName)) {
				return w;
			}
		}
		
		return null;
	}
	
	public void addWorkout(Workout workout) {
		content.add(workout);
	}
	
}

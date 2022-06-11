package beans;

import java.time.LocalDateTime;

public class Workout {
	private String name;
	private TypeOfWorkout type;
	private SportFacility sportFacility;
	private LocalDateTime duration;
	private Coach coach;
	private String description;
	private String image;
	
	public Workout() {}
	
	public Workout(String name, TypeOfWorkout type, SportFacility sportFacility, LocalDateTime duration, Coach coach,
			String description, String image) {
		super();
		this.name = name;
		this.type = type;
		this.sportFacility = sportFacility;
		this.duration = duration;
		this.coach = coach;
		this.description = description;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeOfWorkout getType() {
		return type;
	}

	public void setType(TypeOfWorkout type) {
		this.type = type;
	}

	public SportFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportFacility sportFacility) {
		this.sportFacility = sportFacility;
	}

	public LocalDateTime getDuration() {
		return duration;
	}

	public void setDuration(LocalDateTime duration) {
		this.duration = duration;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}

package dto;

import beans.SportFacility;
import beans.TypeOfWorkout;

public class WorkoutDTO {

	private String name;
	private TypeOfWorkout type;
	private SportFacility sportFacility;
	private String duration;
	private String coach;
	private String description;
	private String image;
	
	public WorkoutDTO(String name, TypeOfWorkout type, SportFacility sportFacility, String duration, String coach,
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
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

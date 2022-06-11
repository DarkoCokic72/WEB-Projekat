package beans;

import java.util.Date;

public class Manager extends User{
	private SportFacility sportFacility;

	public Manager(String username, String password, String name, String surname, Gender gender, Date dateOfBirth) {
		super(username, password, name, surname, gender, dateOfBirth, Role.Manager);
		// TODO Auto-generated constructor stub
	}

	public SportFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportFacility sportFacility) {
		this.sportFacility = sportFacility;
	}
	
	
}

package beans;

import java.util.Date;

public class Administrator extends User{

	public Administrator(String username, String password, String name, String surname, Gender gender, Date dateOfBirth) {
		super(username, password, name, surname, gender, dateOfBirth, Role.Administrator);
		// TODO Auto-generated constructor stub
	}
	
	public Administrator(User user) {
		super(user);
	}
	
}

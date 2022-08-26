package dto;

public class LoggedInUserDTO {

	private String jwt;
	private String role;

	
	public LoggedInUserDTO() {
		super();
	}

	public LoggedInUserDTO(String jwt, String role) {
		super();
		this.jwt = jwt;
		this.role = role;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}

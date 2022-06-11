package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends User{
	private Membership membership;
	private List<SportFacility> visitedFacilities;
	private double collectedPoints;
	private TypeOfCustomer type;
	
	public Customer() {}
	
	public Customer(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			Membership membership,
			TypeOfCustomer type) {
		super(username, password, name, surname, gender, dateOfBirth, Role.Customer);
		this.membership = membership;
		this.visitedFacilities = new ArrayList<SportFacility>();
		this.collectedPoints = 0;
		this.type = new TypeOfCustomer(type.getTypeName());
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public List<SportFacility> getVisitedFacilities() {
		return visitedFacilities;
	}

	public void setVisitedFacilities(List<SportFacility> visitedFacilities) {
		this.visitedFacilities = visitedFacilities;
	}

	public double getCollectedPoints() {
		return collectedPoints;
	}

	public void setCollectedPoints(double collectedPoints) {
		this.collectedPoints = (collectedPoints > 0 ? collectedPoints : 0);
		setType(new TypeOfCustomer(collectedPoints));
	}

	public TypeOfCustomer getType() {
		return type;
	}

	public void setType(TypeOfCustomer type) {
		this.type = type;
	}
	
	
}

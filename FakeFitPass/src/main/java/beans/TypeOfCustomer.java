package beans;

public class TypeOfCustomer {
	private TypeName typeName;
	private double discount;
	private int requiredPoints;
	
	public TypeOfCustomer() {}
	
	public TypeOfCustomer(TypeName typeName) {
		super();
		this.typeName = typeName;
		
		if(typeName == TypeName.Bronze) {
			discount = 0.0;
			requiredPoints = 0;
		} else if(typeName == TypeName.Silver) {
			discount = 5;
			requiredPoints = 1000;
		} else {
			discount = 10;
			requiredPoints = 2000;
		}
	}
	
	public TypeOfCustomer(double collectedPoints) {
		if(collectedPoints >= 2000) {
			this.typeName = TypeName.Gold;
			this.discount = 10;
			this.requiredPoints = 2000;
		}  else if (collectedPoints >= 1000) {
			this.typeName = TypeName.Silver;
			this.discount = 5;
			this.requiredPoints = 1000;
		} else {
			this.typeName = TypeName.Bronze;
			this.discount = 0;
			this.requiredPoints = 0;
		}
	}

	public TypeName getTypeName() {
		return typeName;
	}

	public void setTypeName(TypeName typeName) {
		this.typeName = typeName;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(int requiredPoints) {
		this.requiredPoints = requiredPoints;
	}
	
	
}

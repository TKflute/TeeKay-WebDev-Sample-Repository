
public class Owner {
	
	private String firstName;
	private String lastName;
	Portfolio portfolio;
	
	public Owner(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	//**ACCESSORS**
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	//**METHODS**
	public void assignPortfolioToOwner(Portfolio p) {
		portfolio = p;
	}
	
	
	public String toString() {
		
		String ownerName = getLastName() + ", " + getFirstName();
		return ownerName;
		
	}
}

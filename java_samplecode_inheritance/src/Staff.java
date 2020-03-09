
public class Staff extends Employee {

	private int paygrade;
	
	public Staff(String name, int employeeId, String employeeDepartment, int paygrade) {
		super(name, employeeId, employeeDepartment);
		setPaygrade(paygrade);
	}
	
	public Staff() {
		super();
		setPaygrade(1);
	}
	
	public int getPaygrade() {
		return paygrade;
	}

	public void setPaygrade(int paygrade) {
		if(paygrade < 1 || paygrade > 20) {
			throw new IllegalArgumentException();
		}	
		this.paygrade = paygrade;
	}
	
	public boolean equals(Staff otherStaff) {
		return super.equals(otherStaff);	
	}

	public void writeOutput() {
		super.writeOutput();
		System.out.println("Paygrade: " + paygrade);
	}
}

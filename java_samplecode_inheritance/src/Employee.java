
public abstract class Employee extends Person{

	private int employeeId;
	private String employeeDepartment;
	
	public Employee(String name, int employeeId, String employeeDepartment) {
		super(name);
		setEmployeeId(employeeId);
		setEmployeeDepartment(employeeDepartment);
	}
	
	public Employee() {
		super();
		setEmployeeId(0);
		setEmployeeDepartment("Unknown department");
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		if(employeeId < 0) {
			System.out.println("Error, Employee class: employeeId must be positive");
		}
		this.employeeId = employeeId;
	}

	public String getEmployeeDepartment() {
		return employeeDepartment;
	}

	public void setEmployeeDepartment(String employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}
	
	public boolean equals(Employee otherEmployee) {
		return getEmployeeId() == otherEmployee.getEmployeeId();
	}
	
	public void writeOutput() {
		super.writeOutput();
		System.out.println("Employee id: " + employeeId);
		System.out.println("Employee department: " + employeeDepartment);
	}
}

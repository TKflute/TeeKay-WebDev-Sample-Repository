
public class Faculty extends Employee {

	private String title;
	
	public Faculty(String name, int employeeId, String department, String title) {
		super(name, employeeId, department);
		setTitle(title);
	}
	
	public Faculty() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean equals(Faculty otherFaculty) {
		return super.equals(otherFaculty);	
	}
	
	public void writeOutput() {
		super.writeOutput();
		System.out.println("Title: " + title);
	}
}

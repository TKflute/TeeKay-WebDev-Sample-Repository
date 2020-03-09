
public class Student extends Person implements Comparable<Student>{

	private int studentNumber;
	
	public Student(String name, int studentNumber) {
		super(name);
		setStudentNumber(studentNumber);
	}
	
	public Student() {
		setStudentNumber(0);
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		if(studentNumber < 0) {
			System.out.println("Error, Student class: studentNumber must be positive");
			System.exit(0);
		}
		this.studentNumber = studentNumber;
	}
	
	public void reset(String name, int studentNumber) {
		setName(name);
		setStudentNumber(studentNumber);		
	}
	
	public boolean equals(Student otherStudent) {
		return super.hasSameName(otherStudent) && 
				this.studentNumber == otherStudent.studentNumber; 	
	}
		
	//compareTo via studentNumber	
	//@Override
	public int compareTo(Student otherStudent) {
		if(!(otherStudent instanceof Student)) {
			System.out.println("Error: compareTo method in Student class! Object not a student");
			System.exit(0);
		}
		
		return studentNumber - otherStudent.getStudentNumber();
	}
	
	/*
	//compareTo via name lexicographically
	public int compareTo(Student otherStudent) {
		
		if(!(otherStudent instanceof Student)) {
			System.out.println("Error: compareTo method in Student class! Object not a student");
			System.exit(0);
		}
		
		return getName().compareTo(otherStudent.getName());
	}*/
	
	public void writeOutput() {
		super.writeOutput();
		System.out.println("Student Number: " + studentNumber);
	}

}

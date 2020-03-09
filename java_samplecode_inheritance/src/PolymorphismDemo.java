
import java.util.Arrays;

public class PolymorphismDemo {

	public static void main(String[] args) {
		
		Person[] people = new Person[6];
		people[0] = new Undergraduate("Cotty, Manny", 4910, 1);
		people[1] = new Undergraduate("Kick, Anita", 9931, 2);
		people[2] = new Student("DeBanque, Robin", 8812);
		people[3] = new Undergraduate("Bugg, June", 9901, 4);
		people[4] = new Faculty("Bob", 1234, "Economics", "Professor of Economics");
		people[5] = new Staff("Susan", 2345, "Administration", 10);
		
		for(Person p : people) {
			p.writeOutput();
			System.out.println();
		}
		
		Student[] students = new Student[5];
		students[0] = new Student("Franklin, Ben", 5555);
		students[1] = new Student("Smith, George", 3333);
		students[2] = new Student("Williams, Tommy", 1111);
		students[3] = new Student("Collins, Sharon", 4444);
		students[4] = new Student("Langley, Jennifer", 2222);
		
		//Both sorting methods
		Arrays.sort(students);
		//bubbleSort(students);
		
		for(Student s : students) {
			s.writeOutput();
		}
		System.out.println();
		
		//Test for negative paygrade in Staff class
		Staff fakeSue;
		try{
			fakeSue = new Staff("White, Sue", 6666, "Cleaning", -10);
		}catch(IllegalArgumentException e) {
			System.out.println("Exception in Staff class - paygrade must be a positive number");
		}
		
		//Test for default constructor
		Student noName = new Student();
		noName.writeOutput();	
			
	}
	
	private static void bubbleSort(Student[] students) {
		
		boolean foundSwap = true;
		int counter = 0;
		
		while(foundSwap) {
			counter++;
			foundSwap = false;
			for(int i = 0; i < students.length - counter; i++) {
				if(students[i].getStudentNumber() > students[i+1].getStudentNumber()) {
					Student copy = students[i];
					students[i] = students[i+1];
					students[i+1] = copy;
					foundSwap = true;
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

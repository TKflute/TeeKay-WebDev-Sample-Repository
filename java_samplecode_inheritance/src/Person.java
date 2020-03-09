
public abstract class Person {

	private String name;
	
	public Person(String name) {
		setName(name);
	}
	
	public Person() {
		this("Unknown");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void writeOutput() {
		System.out.println("Name: " + name);
	}
	
	public boolean hasSameName(Person otherPerson) {		
		return this.name.contentEquals(otherPerson.name);
	}
}


public class Undergraduate extends Student {

	private int level;	
	
	public Undergraduate(String name, int studentNumber, int level) {
		super(name, studentNumber);
		setLevel(level);
	}
	
	public Undergraduate() {
		super();
		level = 1;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		if(level < 1 || level > 4) {
			System.out.println("Error, Undergraduate class: level must be between 1 and 4 (inclusive)");
			System.exit(0);
		}
		this.level = level;
	}
	
	public void reset(String name, int studentNumber, int newLevel) {
		super.reset(name, studentNumber);
		setLevel(newLevel);		
	}
	
	public boolean equals(Undergraduate otherUndergraduate) {
		return super.equals(otherUndergraduate) &&
				this.level == otherUndergraduate.level;
	}
	
	public void writeOutput() {
		super.writeOutput();
		System.out.println("Student level: " + level);
	}
	
	
	
}

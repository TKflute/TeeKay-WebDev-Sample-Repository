import static org.junit.Assert.*;

import org.junit.Test;

public class PolymorphismTest {

	@Test
	public void testFacultyEquals() {
		//Test 1
		Faculty bob = new Faculty("Bob", 1111, "Marketing", "Professor of Marketing");
		Faculty bob2 = new Faculty("Bob", 1111, "Marketing", "Professor of Marketing");
		
		assertEquals(true, bob.equals(bob2));
		
		//Test 2
		Faculty sue = new Faculty("Sue", 2222, "Computer Programming", "Professor of Programming");
		Faculty george = new Faculty("George", 2222, "Law", "Professor of Law");
		
		assertEquals(true, sue.equals(george));
		
		//Test 3
		assertEquals(false, sue.equals(bob));
	}
	
	@Test
	public void testStaffPayGrade(){
		
		//Test 1
		Staff sharon = new Staff("Sharon", 3333, "IT", 20);
		assertEquals(20, sharon.getPaygrade());
		
		//Test 2
		Staff sharon2 = new Staff("Sharon", 3333, "IT", 1);
		assertEquals(1, sharon2.getPaygrade());
		
		//test for negative paygrade in PolymorphismDemo class
	}

}

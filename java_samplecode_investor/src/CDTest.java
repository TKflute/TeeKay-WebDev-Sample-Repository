import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

class CDTest {

	@Test
	public void testCalcMaturityDate() {
		
		CD cd = new CD(new Date (119, 01, 15), 12);
		
		//doesn't account for leap years so off by one day/leap year
		assertEquals(new Date(120, 01, 15), cd.calcMaturityDate());
	}
	
	@Test
	public void testCalcYearsHeld() {
		
		CD cd = new CD(new Date (119, 01, 15));
		
		assertEquals(1.00, cd.calcYearsHeld(new Date(120, 01, 15)));
	}
	
	
	@Test
	public void testCalcInterestEarned() {
		
		CD cd = new CD("CD_01", 100000, new Date(115, 0, 01), 12, 0.015, CD.CD_COMPOUND_ANNUALLY);
		
		assertEquals(1500, cd.calcInterestEarned(new Date(116, 0, 01)));
	}
	
	@Test
	public void testCalcValue() {
		
		CD cd = new CD("CD_02", 100000, new Date(115, 02, 15), 60, 0.0235, CD.CD_COMPOUND_ANNUALLY);
		Date withdrawlDate = new Date(116, 05, 01);
		
		
		assertEquals(100000 + cd.calcInterestEarned(withdrawlDate), cd.calcValue(withdrawlDate));
	}
	
	@Test
	public void testCalcValueAtMaturity() {
		
		CD cd = new CD("CD_03", 100000, new Date(115, 02, 15), 60, 0.0235, CD.CD_COMPOUND_ANNUALLY);
		assertEquals(112330, cd.calcValueAtMaturity());
	}

}

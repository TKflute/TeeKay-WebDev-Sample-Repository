import java.util.Date;

public class Broker {


	public static void main(String[] args) {
		
		//Testing associations
		//first have to make owner(s), then portfolio, then CD(s)
		Owner bob = new Owner("Bob", "Smith");
		Owner susie = new Owner("Susie", "Smith");
		Owner[] bobAndSusie = {bob, susie};
		
		Portfolio p1 = new Portfolio(bobAndSusie);
		
		CD p1cd1 = new CD(p1, "CD_01", 100000, new Date(115, 02, 15), 60, 0.0235, CD.CD_COMPOUND_ANNUALLY);
		CD p1cd2 = new CD(p1, "CD_02", 120000, new Date(119, 04, 25), 12, 0.05, CD.CD_COMPOUND_MONTHLY);
		CD p1cd3 = new CD(p1, "CD_03", 100000, new Date(115, 02, 15), 24, 0.0235, CD.CD_COMPOUND_ANNUALLY);
		
		bob.getPortfolio().generatePortfolioSummaryReport();
		
		System.out.println(p1.getCdByName("CD_01") + "\n");
		System.out.println(p1.findCDMaturingSoonest() + "\n");
		System.out.println(p1.findCDWithMaxMaturityValue());
					
	}

}

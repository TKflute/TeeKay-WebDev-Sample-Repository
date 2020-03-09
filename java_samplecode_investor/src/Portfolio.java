import java.util.ArrayList;

public class Portfolio {
	
	private ArrayList<Owner> ownerList;
	private ArrayList<CD> cdList;
	
	public Portfolio(Owner o) {
		ownerList = new ArrayList<Owner>();
		cdList = new ArrayList<CD>();
		
		addOwnerToPortfolio(o);
		o.assignPortfolioToOwner(this);
	}
	
	public Portfolio(Owner[] owners) {
		ownerList = new ArrayList<Owner>();
		cdList = new ArrayList<CD>();
		
		for(Owner o : owners) {
			addOwnerToPortfolio(o);
			o.assignPortfolioToOwner(this);
		}
	}

	public ArrayList getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(ArrayList ownerList) {
		this.ownerList = ownerList;
	}
	
	public ArrayList getCdList() {
		return cdList;
	}

	public void setCdList(ArrayList cdList) {
		this.cdList = cdList;
	}
	
	//**METHODS**
	public void addOwnerToPortfolio(Owner o) {
		if(!ownerList.contains(o)) {
			ownerList.add(o);
		}
	}
	
	public void addCdToPortfolio(CD cd) {
		if(!cdList.contains(cd)) {
			cdList.add(cd);
		}
	}
	
	public CD getCdByName(String cdName) {
		for(CD cd : cdList) {
			if(cd.getCDName().equals(cdName)) {
				return cd;
			}
		}
		return null;
	}
	
	public void generatePortfolioSummaryReport() {
		
		System.out.println(this.toString());
	}
	
	public CD findCDWithMaxMaturityValue() {
		
		int maxIndex = 0;
		
		for(int i = 1; i < cdList.size(); i++) {
			if(cdList.get(i).calcValueAtMaturity() > cdList.get(maxIndex).calcValueAtMaturity()) {
				maxIndex = i;
			}
		}
		return cdList.get(maxIndex);
	}
	
	public CD findCDMaturingSoonest() {
		
		int minIndex = 0;
		
		for(int i = 1; i < cdList.size(); i++) {
			if(cdList.get(i).calcMaturityDate().before(cdList.get(minIndex).calcMaturityDate())) {
				minIndex = i;
			}
		}
		
		return cdList.get(minIndex);
	}
	
	public String toString() {
		
		String portfolioString = "***Portfolio Summary Report***\n\n***Owners***\n";
		
		for(int i = 0; i < ownerList.size(); i++) {
			portfolioString += "Owner " + (i+1) + ": " + ownerList.get(i).toString() + "\n";
		}
		
		portfolioString += "\n***CDs***\n";
		
		
		for(CD cd : cdList) {
			portfolioString += cd.toString() + "\n\n";
		}
		return portfolioString;
	}
	
	

}

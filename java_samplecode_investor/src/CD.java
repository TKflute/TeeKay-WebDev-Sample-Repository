import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CD {
	
	public static final int CD_COMPOUND_ANNUALLY = 1;
	public static final int CD_COMPOUND_DAILY = 365;
	public static final int CD_COMPOUND_MONTHLY = 12;
	
	public static final long MS_IN_ONE_YEAR = 31536000000L;
	
	Portfolio portfolio;
	private String cdName;
	private long amountInCents;
	private Date purchaseDate;
	private int months;
	private double annualRate;
	private int compoundingType;
	
	//**CONSTUCTOR**//
	
	public CD(Portfolio p, String cdName, long amountInCents, Date purchaseDate, int months, double annualRate,
			int compoundingType) {

		portfolio = p;
		setCDName(cdName);
		this.amountInCents = amountInCents;
		this.purchaseDate = purchaseDate;
		this.months = months;
		this.annualRate = annualRate;
		this.compoundingType = compoundingType;
		
		portfolio.addCdToPortfolio(this);
	}

	//CONSTRUCTORS FOR TESTING ONLY 
	public CD(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public CD(Date purchaseDate, int months) {
		this.purchaseDate = purchaseDate;
		this.months = months;
	}
	
	public CD(String cdName, long amountInCents, Date purchaseDate, int months, double annualRate,
			int compoundingType) {

		setCDName(cdName);
		this.amountInCents = amountInCents;
		this.purchaseDate = purchaseDate;
		this.months = months;
		this.annualRate = annualRate;
		this.compoundingType = compoundingType;
	}
	
	//**ACCESSORS**/
	public String getCDName() {
		return cdName;
	}
	
	public void setCDName(String cdName) {
		this.cdName = cdName;
	}
	
	//**METHODS**//
	public Date calcMaturityDate() {
		
		int startYear = purchaseDate.getYear();
		int maturityYear= startYear + (months/12);
		
		Date maturityDate = new Date(maturityYear, purchaseDate.getMonth(), purchaseDate.getDate());
		return maturityDate;
	}
	
	public long calcInterestEarned(Date withdrawlDate) {
		
		//Precondition:	
			
		//Given date should be greater than purchase date
		//If the given date is prior to the CDs purchase date, no interest is earned.
		if(withdrawlDate.getTime() <= purchaseDate.getTime()) {
			return 0;
		}
			
		//If the given date exceeds the CDs term, assume no additional interest is earned.
		double yearsHeld;
		if(withdrawlDate.getTime() > calcMaturityDate().getTime()) {
			yearsHeld = calcYearsHeld(calcMaturityDate());
		}else {
			yearsHeld = calcYearsHeld(withdrawlDate);
		}
		
		double formula = Math.pow(1 + annualRate/compoundingType, compoundingType * yearsHeld);
		double amountInCentsDouble = (double)amountInCents;
		double futureValue = Math.round(amountInCentsDouble * formula);
		long interestEarned = (long)(futureValue - amountInCents);
		
		return interestEarned;
	}
	
	
	public long calcValue(Date withdrawlDate) {	
		//Preconditions covered in calcInterestEarned()
		
		long value = amountInCents + calcInterestEarned(withdrawlDate);
		return value;
	}
	
	public double calcYearsHeld(Date withdrawlDate) {

		double purchaseDateMS = purchaseDate.getTime();
		double withdrawlDateMS = withdrawlDate.getTime();
		double msHeld = withdrawlDateMS - purchaseDateMS;
		
		double yearsHeld = msHeld/MS_IN_ONE_YEAR;
		return yearsHeld;
	}
	
	public long calcValueAtMaturity() {
		
		Date maturityDate = calcMaturityDate();
		long valueAtMaturity = calcValue(maturityDate);
		return valueAtMaturity;
	}
	
	
	public String toString() {
		
		//format amount
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
	   	String amountInDollars = numberFormat.format(amountInCents/100);
		
		//format dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy");
		String purchaseDateString = dateFormat.format(purchaseDate);
		Date maturityDate = calcMaturityDate();
		String maturityDateString = dateFormat.format(maturityDate);
		
		//format annual rate
		String rateString = Double.toString(annualRate * 100) + "%";
		
		//compoundingType from int to String
		String compoundingTypeString = "";
		
		switch(compoundingType) {
		  	case 1:
			   compoundingTypeString = "Annually";
			    break;
			case 12:
				compoundingTypeString = "Monthly";
			    break;
			case 365:
			    compoundingTypeString = "Daily";
			    break;
		}
		
		//value at Maturity
		String valueAtMaturity = numberFormat.format(calcValueAtMaturity()/100);
		
		//interest at Maturity
		String interestAtMaturity = numberFormat.format(calcInterestEarned(maturityDate)/100);
				
		
		String CDSummary = "CD Name: " + getCDName() + "\nAmount Invested: " + amountInDollars +
		"\nPurchase Date: " + purchaseDateString + "\nTerm(Months) " + months + "\nAnnual Rate: " + rateString +
		"\nCompounding Type: " + compoundingTypeString + "\nMaturity Date: " + maturityDateString + "\nValue at Maturity: " + 
		valueAtMaturity + "\nInterest Earned at Maturity: " + interestAtMaturity;

		return CDSummary;
	}
	
}

package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL008_NumberOfHoldings extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Portfolio";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Portfolio - Validating 'Number of Holdings' widget";
		testDescription = "Validating 'Number of Holdings' for applicable funds";
	}
	 
	@Test(groups="FundPortfolio")
	public void validateNumberOfHoldingsWidget() throws InterruptedException {		
		
		try {
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickAnyFundFromAssetClass(getLocalePropertyValue("lblPpssAssetClaassFixedIncome"))				
			.clickPortfolioTab()
			.verifyHoldingsDateFormat()		
			.verifyFootNoteCaveat();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}


}

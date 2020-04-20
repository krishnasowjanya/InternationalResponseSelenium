package internationalTests.Luxembourg;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL005_GeographicalAllocation extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Portfolio";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Portfolio - Validating 'Geographical Allocation' widget";
		testDescription = "Validating 'Geographical Allocation' for applicable funds";
	}
	
	@Test(groups="FundPortfolio")
	public void validateGeographicalAllocationWidget() throws InterruptedException {		
		
		try {
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickAnyFundFromAssetClass(getLocalePropertyValue("lblPpssAssetClaassEquity"))				
			.clickPortfolioTab()
			.verifyGeographicalAllocationDateFormat()		
			.verifyFootNoteCaveat();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}


}

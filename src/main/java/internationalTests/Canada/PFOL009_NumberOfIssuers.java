package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL009_NumberOfIssuers extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Portfolio";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Portfolio - Validating 'Number of Issuers' widget";
		testDescription = "Validating 'Number of Issuers' for applicable funds";
	}
	
	@Test(groups="FundPortfolio")
	public void validateNumberOfIssuersWidget() throws InterruptedException {		
		
		try{
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickAnyFundFromAssetClass(getLocalePropertyValue("lblPpssAssetClaassEquity"))				
			.clickPortfolioTab()
			.verifyNumberOfIssuersDateFormat()		
			.verifyFootNoteCaveat();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}


}

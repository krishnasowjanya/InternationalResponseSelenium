package internationalTests.Luxembourg;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW013_Overview_FindAFund extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Overview - Find A Fund Search";
		testDescription = "Validating Fund Overview Page - Find a Fund functionality";
	}
	
	@Test(groups="FundOverview")
	public void validateOverviewFindAFund() throws InterruptedException {
		
		try {		 
			String fundName=new UKHomePage(driver, test)
					.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getRandomFundNameFromPerformanceList();
			
			new PriceAndPerformancePage(driver, test)
			.clickFirstFundFromPerformanceList()
			.verifyFindAFundButtonExist()
			.clickFindAFundButtonAndVerifyPopupTitle()		
			.enterTheFundToSearch(fundName)
			.verifySearchResult(fundName);	
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}


}

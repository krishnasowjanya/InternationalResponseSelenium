package internationalTests.Singapore;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PPSS001_PageNavigationAndOverview extends ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "PPSS Page Navigation and Overview";
		testDescription = "Validating PPSS page sections like filters and its default values";
	}
	
	@Test()
	public void validatePPSSPageNavigation() throws InterruptedException, FileNotFoundException, IOException {		
		
		try {
			int totalFundCount=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()			
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()		
			.getPerformanceTabFundCount();
			
			new PriceAndPerformancePage(driver, test)		
			.verifyHeadingWithFundCount(totalFundCount)
			.verifyQuickOrFindAFundSearch()
			.verifyPPSSPageFiltersAndItsDefaultValues()
			.verifyMainTabs()
			.verifyDefaultTabSelected()		
			.verifyAnnualizedPerformanceSelectedByDefault();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}

}

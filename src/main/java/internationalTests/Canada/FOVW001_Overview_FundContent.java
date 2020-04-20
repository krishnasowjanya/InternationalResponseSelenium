package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW001_Overview_FundContent extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan"; 
		category = "Smoke";
		testCaseName = "Fund Overview - Fund Content";		
		testDescription = "Validating Fund Overview - Fund Content";
	}
	
	@Test(groups="FundOverview")
	public void validateSummaryOfFundObjective() throws InterruptedException {			
		try {
			
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"FOVW009");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickOnTab(getLocalePropertyValue("lblOverviewTab"))
			.validateFundContent(data);	
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	}


}

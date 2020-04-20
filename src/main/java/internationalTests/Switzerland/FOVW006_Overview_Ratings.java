package internationalTests.Switzerland;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW006_Overview_Ratings extends ProjectMethods{
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Overview- Ratings Section";
		testDescription = "Validating Fund Overview - Ratings Section";
	}
	
	@Test(groups="FundOverview")
	public void validateRatings() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"FOVW006");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())
			.clickOnTab(getLocalePropertyValue("lblOverviewTab"))
			.validateRatingsSections(data);
		
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}



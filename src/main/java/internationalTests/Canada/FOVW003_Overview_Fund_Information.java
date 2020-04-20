package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW003_Overview_Fund_Information extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Overview- Sales Charge Section";
		testDescription = "Validating Fund Overview - Sales Charge Section";
	}
	
	@Test(groups="FundOverview")
	public void validateSalesCharge() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"FOVW004");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickOnTab(getLocalePropertyValue("lblOverviewTab"))
			.validateSalesChargeSection(data);		
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	}
}

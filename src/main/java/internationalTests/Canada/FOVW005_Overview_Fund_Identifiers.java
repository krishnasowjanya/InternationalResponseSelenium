package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW005_Overview_Fund_Identifiers extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Overview- Fund Identifiers Section";
		testDescription = "Validating Fund Overview - Fund Identifiers Section";
	}
	
	@Test(groups="FundOverview")
	public void validateFundIdentifiers() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"FOVW005");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickOnTab(getLocalePropertyValue("lblOverviewTab"))
			.validateFundIdentifiersSection(data);	
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

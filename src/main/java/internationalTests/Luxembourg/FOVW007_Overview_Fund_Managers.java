package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW007_Overview_Fund_Managers extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		//excelSheetName="Luxembourg";
		excelSheetName=sCountryName;
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Overview- Fund Managers Section";
		testDescription = "Validating Fund Overview - Fund Managers Section";
	}
	
	@Test(groups="FundOverview")
	public void validateFundManagers() throws InterruptedException {	
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"FOVW007");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())
			.clickOnTab(getLocalePropertyValue("lblOverviewTab"))
			.validateFundManagersSection(data);	
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);			
		}
		
	}
}

package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW009_Overview_PerfSnpDiscrete_Performance extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Overview- Performance Snapshot-Discrete Performance Section";
		testDescription = "Validating Fund Overview - Performance Snapshot-Discrete Performance Section";
	}
	
	@Test(groups="FundOverview")
	public void validatePerfSnpshtDiscretePerformance() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"FOVW011");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickOnTab(getLocalePropertyValue("lblOverviewTab"))
			.validatePerfSnpshtAnnualizedPerfSection(data);
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
} 

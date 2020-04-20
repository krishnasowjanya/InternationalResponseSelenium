package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF009_PerformanceAnnualisedPerformanceMonthEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Annualised Performance MonthEnd Components";
		testDescription = "Validating Annualised Performance MonthEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceAnnualisedPerformanceMonthEnd() throws InterruptedException {		
		
		try {		
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF009");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())		
			.clickPerformanceTab()
			.clickAnnualisedPerformanceTab()
			.clickAnnualisedPerformanceTimeToggleMonthEnd()
			.verifyAnnualisedPerformanceTableAgainstTimeToggle("MonthEnd")		
			.verifyAnnualisedPerformanceTableMonthEndHeadings()	
			.verifyAnnualisedPerformanceTableMonthEndData(data);	
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}


}

package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF004_PerformanceCumulativePerformanceMonthEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Cumulative Performance MonthEnd Components";
		testDescription = "Validating Cumulative Performance MonthEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCumulativePerformanceMonthEnd() throws InterruptedException {		
		try {
			
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF004");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())		
			.clickPerformanceTab()
			.clickCumulativePerformanceTab()
			.clickCumulativePerformanceTimeToggleMonthEnd()
			.verifyCumulativePerformanceTableAgainstTimeToggle("MonthEnd")		
			.verifyCumulativePerformanceTableMonthEndHeadings()		
			.verifyCumulativePerformanceTableMonthEndData(data);
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}


}

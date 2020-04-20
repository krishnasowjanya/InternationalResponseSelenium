package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF005_PerformanceCumulativePerformanceQuarterEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Cumulative Performance Quarter End Components";
		testDescription = "Validating Cumulative Performance Quarter End Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCumulativePerformanceQuarterEnd() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF005");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())		
			.clickPerformanceTab()
			.clickCumulativePerformanceTab()
			.clickCumulativePerformanceTimeToggleQuarterEnd()
			.verifyCumulativePerformanceTableAgainstTimeToggle("QuarterEnd")		
			.verifyCumulativePerformanceTableQuarterEndHeadings()		
			.verifyCumulativePerformanceTableQuarterEndData(data);	
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	
	}


}

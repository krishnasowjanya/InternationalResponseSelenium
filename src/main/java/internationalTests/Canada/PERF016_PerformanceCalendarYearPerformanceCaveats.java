package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF016_PerformanceCalendarYearPerformanceCaveats extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Calendar Year Performance Caveats Components";
		testDescription = "Validating Calendar Year Performance Caveats under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCalendarYearPerformanceCaveats() throws InterruptedException {		
		try {
			
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF016");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPerformanceTab()
			.clickCalendarYearPerformanceTab()
			.verifyCalendarYearPerformanceTabCaveats();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	
	}


}

package internationalTests.Luxembourg;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF013_PerformanceCalendarYearPerformanceTab extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Calendar Year Performance Tab Components";
		testDescription = "Validating Calendar Year Performance Tab Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCalendarYearPerformanceTab() throws InterruptedException {		
		
		try {
			
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickFirstFundFromPerformanceList()		
			.clickPerformanceTab()
			.clickCalendarYearPerformanceTab()
			.verifyCalendarYearPerformanceHeadingAndDate()
			.validateTimePeriodToggle();	
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	
	}


}

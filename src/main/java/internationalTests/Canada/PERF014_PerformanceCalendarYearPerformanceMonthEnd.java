package internationalTests.Canada;

import java.text.ParseException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF014_PerformanceCalendarYearPerformanceMonthEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Calendar Year Performance MonthEnd Components";
		testDescription = "Validating Annualised Calendar Year MonthEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCalendarYearPerformanceMonthEnd() throws InterruptedException, ParseException {		
		try
		{
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF014");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPerformanceTab()
			.clickCalendarYearPerformanceTab()			
			.verifyCalendarYearPerformanceTableMonthEndHeadings()
			.verifyCalendarYearPerformanceTableMonthEndData(data);

		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}
}

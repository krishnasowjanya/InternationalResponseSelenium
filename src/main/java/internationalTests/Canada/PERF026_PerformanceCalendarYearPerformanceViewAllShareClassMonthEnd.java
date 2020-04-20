package internationalTests.Canada;

import java.text.ParseException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF026_PerformanceCalendarYearPerformanceViewAllShareClassMonthEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Calendar Year Performance View All ShareClas MonthEnd Components";
		testDescription = "Validating Calendar Year Performance View All ShareClas MonthEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCalendarYearPerformanceViewAllShareClasMonthEnd() throws InterruptedException, ParseException {		
		try
		{
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF026");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPerformanceTab()
			.clickCalendarYearPerformanceTab()
			.clickCalendarYearViewPerformanceForAllShareclassLink()
			.verifyCalendarYearPerformanceViewAllShareClassMonthEndHeadings()
			.verifyCalendarYearPerformanceViewAllShareClassMonthEndData(data)		
			.clickFirstFundOverviewLinkFromCalYearViewAllShareClassTable(data)
			.VerifyCalendarYearFundOverviewLink(data);			

		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}		

	}
}

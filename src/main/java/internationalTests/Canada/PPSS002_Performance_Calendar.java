package internationalTests.Canada;

import java.util.Calendar;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import utils.dateFunctions;
import wdMethods.ProjectMethods;

public class PPSS002_Performance_Calendar extends ProjectMethods{
	
	@BeforeClass
	public void setValues() {		
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Performance Tab Columns by Calendar";
		testDescription = "Validating Columnm names when show performance as 'Calendar' under Perfromance Tab";
	}
	
	@Test()
	public void validatePerformanceTabColumnsByCumulative() throws InterruptedException {
		
		try {
			System.out.println(testDescription);
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.selectPerformanceAsCalendar()
			.selecterTimeToggleAsQuarterEnd()
			.verifyCalendarColumnNamesForQuarterEnd()
			.selecterTimeToggleAsMonthEnd()
			.verifyCalendarColumnNamesForMonthEnd();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}

}

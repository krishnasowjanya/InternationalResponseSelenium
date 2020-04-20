package internationalTests.Canada;

import java.text.ParseException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF019_PerformanceDiscreteAnnualPerformanceMonthEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Discrete Annual Performance MonthEnd Components";
		testDescription = "Validating Annualised Discrete Annual MonthEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceDiscreteAnnualPerformanceMonthEnd() throws InterruptedException, ParseException {		
		try {
			
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF019");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPerformanceTab()
			.clickDiscreteAnnualPerformanceTab()
			.clickDiscreteAnnualPerformanceTimeToggleMonthEnd()
			.verifyDiscreteAnnaulPerformanceTableAgainstTimeToggle("MonthEnd")
			.verifyDiscreteAnnualPerformanceTableMonthEndHeadings()			
			.verifyDiscreteAnnualPerformanceTableMonthEndData(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	
	}


}

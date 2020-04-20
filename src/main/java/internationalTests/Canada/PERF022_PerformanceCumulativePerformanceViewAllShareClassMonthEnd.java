package internationalTests.Canada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF022_PerformanceCumulativePerformanceViewAllShareClassMonthEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Cumulative Performance View All ShareClass MonthEnd Components";
		testDescription = "Validating Cumulative Performance View All ShareClas MonthEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCumulativePerformanceViewAllShareClassMonthEnd() throws InterruptedException, FileNotFoundException, IOException {		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF022");
			
			new PriceAndPerformancePage(driver, test)			
			.clickFundWithFilters(data)		
			.clickPerformanceTab()
			.clickCumulativePerformanceTab()
			.clickCumulativePerformanceTimeToggleMonthEnd()		
			.clickCumulativeViewPerformanceForAllShareclassLink()
			.verifyCumulativePerformanceViewAllShareClassTableAgainstTimeToggle("MonthEnd")
			.verifyCumulativePerformanceViewAllShareClassMonthEndHeadings()		
			.verifyCumulativePerformanceViewAllShareClassMonthEndData(data)
			.clickFirstFundOverViewLinkFromCummPerViewAllShareClassTable(data)
			.VerifyCumulativeFundOverviewLink(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	
	}


}

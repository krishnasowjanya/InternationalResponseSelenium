package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF023_PerformanceCumulativePerformanceViewAllShareClassQuarterEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Cumulative Performance View All ShareClass QuarterEnd Components";
		testDescription = "Validating Cumulative Performance View All ShareClas QuarterEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCumulativePerformanceViewAllShareClassQuarterEnd() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF023");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPerformanceTab()
			.clickCumulativePerformanceTab()
			.clickCumulativePerformanceTimeToggleQuarterEnd()	
			.clickCumulativeViewPerformanceForAllShareclassLink()
			.verifyCumulativePerformanceViewAllShareClassTableAgainstTimeToggle("QuarterEnd")
			.verifyCumulativePerformanceViewAllShareClassQuarterEndHeadings()		
			.verifyCumulativePerformanceViewAllShareClassQuarterEndData(data)
			.clickFirstFundOverViewLinkFromCummPerViewAllShareClassTable(data)
			.VerifyCumulativeFundOverviewLink(data);
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}


}

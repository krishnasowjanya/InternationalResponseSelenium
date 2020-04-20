package internationalTests.Canada;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF002_PerformanceCumulativePerformanceTab extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Cumulative Performance Tab Components";
		testDescription = "Validating Cumulative Performance Tab Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCumulativePerformanceTab() throws InterruptedException {		
		try {
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickFirstFundFromPerformanceList()		
			.clickPerformanceTab()
			.clickCumulativePerformanceTab()
			.verifyCumulativePerformanceHeadingAndDate()
			.validateTimePeriodToggle();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	
	}


}

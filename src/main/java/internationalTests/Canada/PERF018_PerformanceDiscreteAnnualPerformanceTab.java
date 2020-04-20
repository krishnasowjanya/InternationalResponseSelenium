package internationalTests.Canada;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF018_PerformanceDiscreteAnnualPerformanceTab extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Discrete Annual Performance Tab Components";
		testDescription = "Validating Discrete Annualr Performance Tab Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceDiscreteAnnualPerformanceTab() throws InterruptedException {		
		try {
			
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickFirstFundFromPerformanceList()		
			.clickPerformanceTab()
			.clickDiscreteAnnualPerformanceTab()
			.verifyDiscreteAnnaulPerformanceHeadingAndDate()
			.validateTimePeriodToggle();	
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	
	}


}

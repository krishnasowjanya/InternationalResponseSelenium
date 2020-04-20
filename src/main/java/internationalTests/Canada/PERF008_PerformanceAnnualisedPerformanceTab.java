package internationalTests.Canada;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF008_PerformanceAnnualisedPerformanceTab extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Annualised Performance Tab Components";
		testDescription = "Validating Annualised Performance Tab Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceAnnualisedPerformanceTab() throws InterruptedException {		
		
		try {
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickFirstFundFromPerformanceList()		
			.clickPerformanceTab()
			.clickAnnualisedPerformanceTab()
			.verifyAnnualisedPerformanceHeadingAndDate()
			.validateTimePeriodToggle();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	
	}


}

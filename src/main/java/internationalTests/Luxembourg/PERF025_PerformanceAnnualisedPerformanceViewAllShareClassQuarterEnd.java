package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF025_PerformanceAnnualisedPerformanceViewAllShareClassQuarterEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Annualised Performance View All ShareClass QuarterEnd Components";
		testDescription = "Validating Annualised Performance View All ShareClass QuarterEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceAnnualisedPerformanceViewAllShareClassQuarterEnd() throws InterruptedException {		
		
		try {		
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF025");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())		
			.clickPerformanceTab()
			.clickAnnualisedPerformanceTab()
			.clickAnnualisedPerformanceTimeToggleQuarterEnd()
			.clickAnnualisedViewPerformanceForAllShareclassLink()
			.verifyAnnualisedPerformanceViewAllShareClassTableAgainstTimeToggle("QuarterEnd")		
			.verifyAnnualisedPerformanceViewAllShareClassTableQuarterEndHeadings()	
			.verifyAnnualisedPerformanceViewAllShareClassTableQuarterEndData(data)
			.clickFirstFundOverViewLinkFromAnnualisedViewAllShareClassTable(data)
			.VerifyAnnualisedFundOverviewLink(data);
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}


}
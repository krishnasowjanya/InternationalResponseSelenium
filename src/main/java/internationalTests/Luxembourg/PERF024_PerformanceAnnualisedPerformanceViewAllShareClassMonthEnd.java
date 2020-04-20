package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF024_PerformanceAnnualisedPerformanceViewAllShareClassMonthEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Annualised Performance View All ShareClass MonthEnd Components";
		testDescription = "Validating Annualised Performance View All ShareClass MonthEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceAnnualisedPerformanceViewAllShareClassMonthEnd() throws InterruptedException {		
		
		try {		
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF024");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())		
			.clickPerformanceTab()
			.clickAnnualisedPerformanceTab()
			.clickAnnualisedPerformanceTimeToggleMonthEnd()
			.clickAnnualisedViewPerformanceForAllShareclassLink()
			.verifyAnnualisedPerformanceViewAllShareClassTableAgainstTimeToggle("MonthEnd")		
			.verifyAnnualisedPerformanceViewAllShareClassTableMonthEndHeadings()	
			.verifyAnnualisedPerformanceViewAllShareClassTableMonthEndData(data)
			.clickFirstFundOverViewLinkFromAnnualisedViewAllShareClassTable(data)
			.VerifyAnnualisedFundOverviewLink(data);
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}


}

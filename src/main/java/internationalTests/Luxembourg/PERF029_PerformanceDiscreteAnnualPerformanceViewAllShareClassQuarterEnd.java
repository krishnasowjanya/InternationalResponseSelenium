package internationalTests.Luxembourg;

import java.text.ParseException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF029_PerformanceDiscreteAnnualPerformanceViewAllShareClassQuarterEnd extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Discrete Annual Performance View All ShareClass QuarterEnd Components";
		testDescription = "Validating Discrete Annual - View All ShareClass (QuarterEnd) Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceDiscreteAnnualPerformanceViewAllShareClassQuarterEnd() throws InterruptedException, ParseException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF029");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())		
			.clickPerformanceTab()
			.clickDiscreteAnnualPerformanceTab()
			.clickDiscreteAnnualPerformanceTimeToggleQuarterEnd()
			.clickDiscreteAnnualViewPerformanceForAllShareclassLink()
			.verifyDiscreteAnnualPerformanceViewAllShareClassTableAgainstTimeToggle("QuarterEnd")
			.verifyDiscreteAnnualPerformanceTableViewAllShareClassQuarterEndHeadings()			
			.verifyDiscreteAnnualPerformanceTableViewAllShareClassQuarterEndData(data)
			.clickFirstFundOverViewLinkFromDiscreteAnnaulViewAllShareClassTable(data)
		.VerifyDiscreteAnnualFundOverviewLink(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	
	}


}

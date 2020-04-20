package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF006_PerformanceCumulativePerformanceCaveats extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Cumulative Performance Caveats Components";
		testDescription = "Validating Cumulative Performance Caveats under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceCumulativePerformanceCaveats() throws InterruptedException {		
		
		try{
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF006");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPerformanceTab()
			.clickCumulativePerformanceTab()
			.verifyCumulativePerformanceTabCaveats();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	
	}


}

package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF021_PerformanceDiscreteAnnualPerformanceCaveats extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Discrete Annual Performance Caveats Components";
		testDescription = "Validating Discrete Annual Performance Caveats under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceDiscreteAnnualPerformanceCaveats() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF021");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())		
			.clickPerformanceTab()
			.clickDiscreteAnnualPerformanceTab()
			.verifyDiscreteAnnualPerformanceTabCaveats();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	
	}


}

package internationalTests.Luxembourg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF020_PerformanceDiscreteAnnualPerformanceQuarterEnd extends ProjectMethods {
	

	@BeforeClass
	public void setValues() {	
		
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		//excelSheetName=CountryName;
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Performance - Discrete Annual Performance QuarterEnd Components";
		testDescription = "Validating Annualised Discrete Annual QuarterEnd Components under Performance Page";
	}
	
	@Test(groups="Performance")
	public void validatePerformanceDiscreteAnnualPerformanceQuarterEnd() throws InterruptedException, ParseException, FileNotFoundException, IOException {		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF020");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())		
			.clickPerformanceTab()
			.clickDiscreteAnnualPerformanceTab()
			.clickDiscreteAnnualPerformanceTimeToggleQuarterEnd()
			.verifyDiscreteAnnaulPerformanceTableAgainstTimeToggle("QuarterEnd")
			.verifyDiscreteAnnualPerformanceTableQuarterEndHeadings()			
			.verifyDiscreteAnnualPerformanceTableQuarterEndData(data);	
	
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}


}

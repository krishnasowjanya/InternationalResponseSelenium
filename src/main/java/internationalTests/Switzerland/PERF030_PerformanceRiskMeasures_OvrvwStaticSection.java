package internationalTests.Switzerland;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF030_PerformanceRiskMeasures_OvrvwStaticSection extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan"; 
		category = "Smoke";
		testCaseName = "Performance - Risk Measures - Overview & Static Section";
		testDescription = "Validating Performance - Risk Measures - Overview & Static Section";
	}
	
	@Test(groups="FundOverview")
	public void validatePerformanceRiskMeasuresOverviewAndStatic() throws InterruptedException, FileNotFoundException, IOException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF030");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").trim())
			.clickPerformanceTab()
			.clickDiscreteAnnualPerformanceTab()
			.validatePerformanceRiskMeasuresOverviewAndStaticSection(data);	
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	
	}
}
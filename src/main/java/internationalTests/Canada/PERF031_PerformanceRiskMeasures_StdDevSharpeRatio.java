package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PERF031_PerformanceRiskMeasures_StdDevSharpeRatio extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan"; 
		category = "Smoke";
		testCaseName = "Performance - Risk Measures - Standard Deviation & Sharpe Ratio";
		testDescription = "Validating Performance - Risk Measures - Standard Deviation & Sharpe Ratio";
	}
	
	@Test(groups="FundOverview")
	public void validatePerformanceRiskMeasuresStandardDeviationSharpeRatio() throws InterruptedException {		
		
		try {			
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor("Financial Advisor")
		
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PERF031");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPerformanceTab()
//			.clickDiscreteAnnualPerformanceTab()
			.validatePerformanceRiskMeasuresStandardDeviationSharpeRatioSectionsCanada(data);		
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	
	}
}
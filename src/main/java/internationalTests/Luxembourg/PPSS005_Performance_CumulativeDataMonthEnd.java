package internationalTests.Luxembourg;

import java.util.HashMap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PPSS005_Performance_CumulativeDataMonthEnd extends ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Performance Tab - Cumulative Data vaidation by MonthEnd";
		testDescription = "Validating data when show performance as 'Cumulative' & Time Toggle as 'MonthEnd' under Perfromance Tab";
	}
	
	@Test()
	public void validatePerformanceTabCumulativeDataByMonthEnd() throws InterruptedException {
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.selectPerformanceAsCumulative()
			.selecterTimeToggleAsMonthEnd()
			.getTestData(sCountryName,"PPSS004");
			
			new PriceAndPerformancePage(driver, test)		
			.verifyDateAndNAVValuesFormates(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}

}

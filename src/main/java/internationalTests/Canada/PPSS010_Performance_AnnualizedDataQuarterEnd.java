package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PPSS010_Performance_AnnualizedDataQuarterEnd extends ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Performance Tab - Annualized Data vaidation by QuarterEnd";
		testDescription = "Validating data when show performance as 'Annualized' & Time Toggle as 'QuarterEnd' under Perfromance Tab";
	}
	
	@Test()
	public void validatePerformanceTabDiscreteAnnaulDataByQuarterEnd() throws InterruptedException {
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.selectPerformanceAsAnnualized()
			.selecterTimeToggleAsQuarterEnd()
			.getTestData(sCountryName,"PPSS004");
			
			new PriceAndPerformancePage(driver, test)		
			.verifyDateAndNAVValuesFormates(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}

}

package internationalTests.Singapore;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PPSS003_Performance_Annualized extends ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Performance Tab Columns by Annualized";
		testDescription = "Validating Columnm names when show performance as 'Annualized' under Perfromance Tab";
	}
	
	@Test()
	public void validatePerformanceTabColumnsByDiscreteAnnaul() throws InterruptedException {
		
		try {
			System.out.println(testDescription);
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.selectPerformanceAsAnnualized()
			.selecterTimeToggleAsQuarterEnd()
			.verifyAnnualizedColumnNamesForQuarterEnd()
			.selecterTimeToggleAsMonthEnd()
			.verifyAnnualizedColumnNamesForMonthEnd();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}

}

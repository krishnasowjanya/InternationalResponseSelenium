package internationalTests.Canada;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import utils.dateFunctions;
import wdMethods.ProjectMethods;

public class PPSS003_Performance_DiscreteAnnaul extends ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Performance Tab Columns by Discrete Annual";
		testDescription = "Validating Columnm names when show performance as 'DiscreteAnnaul' under Perfromance Tab";
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
			.selectPerformanceAsDiscreteAnnual()
			.selecterTimeToggleAsQuarterEnd()
			.verifyDiscreteAnnualColumnNamesForQuarterEnd()
			.selecterTimeToggleAsMonthEnd()
			.verifyDiscreteAnnualColumnNamesForMonthEnd();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}

}

package internationalTests.Canada;

import java.util.Calendar;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import utils.dateFunctions;
import wdMethods.ProjectMethods;

public class PPSS002_Performance_Cumulative extends ProjectMethods{
	
	@BeforeClass
	public void setValues() {		
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Performance Tab Columns by Cumulative";
		testDescription = "Validating Columnm names when show performance as 'Cumulative' under Perfromance Tab";
	}
	
	@Test()
	public void validatePerformanceTabColumnsByCumulative() throws InterruptedException {
		
		try {
			System.out.println(testDescription);
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.selectPerformanceAsCumulative()
			.selecterTimeToggleAsQuarterEnd()
			.verifyCumulativeColumnNamesByQuarterEnd()
			.selecterTimeToggleAsMonthEnd()
			.verifyCumulativeColumnNamesByMonthEnd();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}

}

package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL020_PortfolioStatistics_Equity extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan"; 
		category = "Smoke";
		testCaseName = "Portfolio - Validating Portfolio Statistics - Statistics (Equity)";
		testDescription = "Validating Portfolio Statistics - Statistics (Equity)";
	}
	
	@Test(groups="FundOverview")
	public void validatePortfolioStatisticsEquity() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PFOL020");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPortfolioTab()
			.validatePortfolioStatisticsEquitySection(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	
	}
}
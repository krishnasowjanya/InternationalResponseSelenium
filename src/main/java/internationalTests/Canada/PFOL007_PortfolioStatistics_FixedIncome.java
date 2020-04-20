package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL007_PortfolioStatistics_FixedIncome extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan"; 
		category = "Smoke";
		testCaseName = "Portfolio - Validating Portfolio Statistics - Statistics (Fixed Income)";
		testDescription = "Validating Portfolio Statistics - Statistics (Fixed Income)";
	}
	
	@Test(groups="FundOverview")
	public void validatePortfolioStatisticsFixedIncome() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PFOL007");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPortfolioTab()
			.validatePortfolioStatisticsFixedIncomeSection(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}
	
}
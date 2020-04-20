package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL011_PortfolioStrategyBreakdown extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Portfolio";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Portfolio - Validating Strategy Breakdown widget";
		testDescription = "Validating Strategy Breakdown widget for applicable funds";
	} 
	
	@Test(groups="FundPortfolio")
	public void validateStragetyBreakdownWidget() throws InterruptedException {		
		
		try {
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.goToK2AlternativeFundPage()		
			.clickPortfolioTab()
			.verifyStrategyBreakdownDateFormat()		
			.verifyFootNoteCaveat();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}


}

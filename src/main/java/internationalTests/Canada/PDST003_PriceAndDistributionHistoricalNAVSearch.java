package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PDST003_PriceAndDistributionHistoricalNAVSearch extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan"; 
		category = "Smoke";
		testCaseName = "Price And Distribution - Historical NAV Search";
		testDescription = "Validating Price And Distribution - Historical NAV Search Section";
	}
	
	@Test(groups="PriceAndDistribution")
	public void validatePriceAndDistributionHistoricaNAVSearch() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PDST003");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFundWithFilters(data)		
			.clickPriceAndDistributionTab()
			.validatePriceAndDistributionHistoricaNAVSearchSection(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	
	}
}
package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PDST005_PriceAndDistributionDividends extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Dividends Section";
		testDescription = "Validating Dividends section under Price And Distribution Page";
	}
	
	@Test(groups="PriceAndDistribution")
	public void validatePriceAndDistributionDividendss() throws InterruptedException {		
		
		try {
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PDST005");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name"))
			.clickPriceAndDistributionTab()
			.validateDividendPerShareSectionHeadings()
			.validateDividendPerShareSectionDetails(data);
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	
	}


}

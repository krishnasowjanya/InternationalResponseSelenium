package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PDST007_PriceAndDistributionNAVPrices extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating NAV/Share Prices Section";
		testDescription = "Validating NAV / Share Prices section under Price And Distribution Page";
	}
	
	@Test(groups="PriceAndDistribution")
	public void validatePriceAndDistributionNAVPrices() throws InterruptedException {	
		
		try {
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PDST007");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name"))
			.clickPriceAndDistributionTab()
			.validateDailyFundPricesHeadingAndDate()
			.validateSharePricesSectionDetails();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}		
	
	}


}

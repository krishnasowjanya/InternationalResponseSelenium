package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.FundOverviewPage;
import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PDST004_PriceAndDistributionHighestLowestNAV extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Highest and Lowest NAV Section";
		testDescription = "Validating Highest and Lowest NAV section under Price And Distribution Page";
	}
	
	@Test(groups="PriceAndDistribution")
	public void validatePriceAndDistributionHighestLowestNAV() throws InterruptedException {	
		
		try {		
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()			
			.getTestData(sCountryName,"PDST004");
			
			String shrClassInceptionDate=new PriceAndPerformancePage(driver, test)	
					.clickFundWithFilters(data)		
			.getShareClassInceptionDate();
			
			new FundOverviewPage(driver, test,data.get("Fund Name"))
			.clickPriceAndDistributionTab()
			.validateHeadingAndDateCanada()
			.validateHighestLowestNAVSectionDetailsCanada(shrClassInceptionDate);
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	
	}


}

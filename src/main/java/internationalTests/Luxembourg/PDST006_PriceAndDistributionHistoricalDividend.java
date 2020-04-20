package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PDST006_PriceAndDistributionHistoricalDividend extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating Historical Dividend Section";
		testDescription = "Validating Historical Dividend section under Price And Distribution Page";
	}
	
	@Test(groups="PriceAndDistribution")
	public void validatePriceAndDistributionHistoricalDividend() throws InterruptedException {		
		
		try {	

			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PDST006");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name"))
			.clickPriceAndDistributionTab()
			.verifyViewHistoricalDividendLinkExpand()
			.verifyViewHistoricalDividendDataComponents()
			.validateViewHistoricalDividendDataSectionDetails(data)
			.verifyViewHistoricalDividendLinkCollapse();	
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	
	}


}

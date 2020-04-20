package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL006_MarketCapitalisation extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Portfolio";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Portfolio - Validating 'Market Capitalisation' widget";
		testDescription = "Validating 'Market Capitalisation' for applicable funds";
	}
	
	@Test(groups="FundPortfolio")
	public void validateMarketCapitalisationWidget() throws InterruptedException {		
		
		try {
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickAnyFundFromAssetClass(getLocalePropertyValue("lblPpssAssetClaassEquity"))				
			.clickPortfolioTab()
			.verifyMarketCapitalisationDateFormat()		
			.verifyFootNoteCaveat();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}


}

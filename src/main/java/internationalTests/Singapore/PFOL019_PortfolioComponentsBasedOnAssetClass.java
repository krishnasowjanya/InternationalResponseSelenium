package internationalTests.Singapore;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL019_PortfolioComponentsBasedOnAssetClass extends internationalTests.Luxembourg.PFOL019_PortfolioComponentsBasedOnAssetClass{

	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Portfolio";
		//excelSheetName="Luxembourg";
		excelSheetName=sCountryName;
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Portfolio - Page Components Asset Class Wise";
		testDescription = "Validating Components based on Asset Class under Portfolio Tab";
	}
	
	@Test(groups="FundPortfolio")
	public void validatePortfolioComponentsAssetClassWise() throws InterruptedException {		
		
		try {
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestDataAsCollection(excelName,excelSheetName);
			
			new PriceAndPerformancePage(driver, test)
			
			.clickAnyFundFromAssetClass(getLocalePropertyValue("lblPpssAssetClaassEquity"))
			.clickPortfolioTab()
			.verifyPortfolioComponentsForEquity(data)
			.goToPriceAndPerformancePage()
			.clickAnyFundFromAssetClass(getLocalePropertyValue("lblPpssAssetClaassFixedIncome"))
			.clickPortfolioTab()
			.verifyPortfolioComponentsForFixedIncome(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}


}

package internationalTests.Luxembourg;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL018_PortfolioComponentsBasedOnFund extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Portfolio";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Portfolio - Page Components Fund Wise";
		testDescription = "Validating Components based on Fund under Portfolio Tab";
	}
	
	@Test(groups="FundPortfolio")
	public void validatePortfolioComponentsFundWise() throws InterruptedException {		
		
		try {
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestDataAsCollection(excelName,sCountryName);
			
			new PriceAndPerformancePage(driver, test)
			.goToK2AlternativeFundPage()
			.clickPortfolioTab()
			.verifyPortfolioComponentsForK2AlternativeFunds(data)
			.goToPriceAndPerformancePage()
			.goToK2GlobalMacroOpportunitiesFundPage()
			.clickPortfolioTab()
			.verifyPortfolioComponentsForK2GlobalMacroOpportunitiesFund(data)
			.goToPriceAndPerformancePage()
			.goToK2LongShortCreditFundPage()
			.clickPortfolioTab()
			.verifyPortfolioComponentsForK2LongShortCreditFund(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}


}

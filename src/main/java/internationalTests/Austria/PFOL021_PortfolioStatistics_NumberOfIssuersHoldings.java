package internationalTests.Austria;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL021_PortfolioStatistics_NumberOfIssuersHoldings extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan"; 
		category = "Smoke";
		testCaseName = "Portfolio - Portfolio Statistics - Number of Issuers and Number of Holdings";
		testDescription = "Validating Portfolio Statistics - Number of Issuers and Number of Holdings";
	}
	
	@Test(groups="FundOverview")
	public void validatePortfolioStatisticsNumberOfIssuersHoldings() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PFOL021");
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").split(",")[0])
			.clickPortfolioTab()
			.validatePortfolioStatisticsNumberOfIssuersSections(data)
			.clickAtHomePageLinkInBreadCrumb()
			.clickAtPriceAndPerfLinkFromPreisePageAustria();
			
			
			new PriceAndPerformancePage(driver, test)	
			.clickFund(data.get("Fund Name").split(",")[1])
			.clickPortfolioTab()
			.validatePortfolioStatisticsNumberOfHoldingsSections(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}
}
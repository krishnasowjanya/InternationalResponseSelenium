package internationalTests.Luxembourg;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW018_Overview_ImportantLegalInformation extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Overview - Important Legal Information";
		testDescription = "Validating Fund Overview Page - Important Legal Information component exist and displayed";
	}
	
	@Test(dataProvider="fetchData",groups="FundOverview")
	public void validateOverviewPageLegalInfo(String fundsName,String xmlURL) throws InterruptedException {	
		
		try {		
			String fundName=new UKHomePage(driver, test)
					.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getRandomFundNameFromPerformanceList();
			
			new PriceAndPerformancePage(driver, test)
			.clickFund(fundName)
			.verifyLegalInormationWidget();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}


}

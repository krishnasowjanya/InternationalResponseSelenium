package internationalTests.Canada;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW016_Overview_ShareClass extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Overview - Share Class";
		testDescription = "Validating Fund Overview Page - Share class should displayed according to fund selected in PPSS Page";
	}
	
	@Test(dataProvider="fetchData",groups="FundOverview")
	public void validateOverviewPageSareClass(String fundsName,String xmlURL) throws InterruptedException {	
		
		try {		
			String fundName=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getRandomFundNameFromPerformanceList();
			
			new PriceAndPerformancePage(driver, test)
			.clickFund(fundName)
			.verifyShareClassBasedOnFundSelected(fundName);	
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	}


}

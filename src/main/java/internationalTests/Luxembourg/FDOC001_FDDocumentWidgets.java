package internationalTests.Luxembourg;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FDOC001_FDDocumentWidgets extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Document";
		testDescription = "Validating Fund Document Page";
	}
	
	@Test(dataProvider="fetchData",groups="FundDocuments")
	public void validateOverviewPageForFund(String fundName,String xmlURL) throws InterruptedException {		
		
		try {			

			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickFund(fundName)
			.clickFundDocumentTab()
			.VerifyHeader()		
			.verifyDownloads();	
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	}


}

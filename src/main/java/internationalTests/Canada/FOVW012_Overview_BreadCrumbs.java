package internationalTests.Canada;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class FOVW012_Overview_BreadCrumbs extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		System.out.println("From Test - @BefroreClass");
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Overview - Bread Crumbs";
		testDescription = "Validating Fund Overview - Bread Crumb";
	}
	
	@Test(groups="FundOverview")
	public void validateOverviewPageBreadCrumbs() throws InterruptedException, FileNotFoundException, IOException {		
		try {				
			System.out.println("Test Case: " + testDescription);
			new UKHomePage(driver, test)	
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickFirstFundFromPerformanceList()
			.VerifyBreadCrumbs();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
	}


}

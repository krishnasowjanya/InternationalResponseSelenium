package internationalTests.Canada.Accounts;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Accounts.CanadaAccountsLoginPage;
import wdMethods.ProjectMethods;

public class ASST001_Assets_Overview extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke"; 
		testCaseName = "Asset & Sales - Overview";
		testDescription = "Validating Asset & Sales - Overview";
	}
	
	@Test
	public void validateAssetSalesOverview() throws InterruptedException, FileNotFoundException, IOException {
		try {
			System.out.println("Test Case: " + testDescription);
			new CanadaAccountsLoginPage(driver, test)
			.accountsLogin()
			.validateHomePageDisplayScreenLayout()
			.selectOverviewFromAssetSalesDropdown()
			.validateOverviewPageDisplayScreenLayout()
			.selectAssetsFromLeftPane()
			.validateAssetsByFundPageDisplayScreenLayout()
			.clickOnClientNumberLinkFirstClient()
			.clickOnClientonSecondRow()
			.validateClientsSummaryPageDisplayScreenLayout()
			.clickOnViewTipsonSecondRow()
			.validateClientsSummaryPageDisplayScreenLayout();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
			
	}


}

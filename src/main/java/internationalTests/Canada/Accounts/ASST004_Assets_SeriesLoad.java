package internationalTests.Canada.Accounts;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Accounts.CanadaAccountsAssetsByFundPage;
import internationalPages.Accounts.CanadaAccountsLoginPage;
import wdMethods.ProjectMethods;

public class ASST004_Assets_SeriesLoad extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke"; 
		testCaseName = "Asset & Sales - Series Load";
		testDescription = "Validating Asset & Sales - Series Load";
	}
	
	@Test
	public void validateAssetSalesSeriesLoad() throws InterruptedException, FileNotFoundException, IOException {
		try {
			System.out.println("Test Case: " + testDescription);
			new CanadaAccountsLoginPage(driver, test)
			.accountsLogin()
			.selectAssetsFromAssetSalesDropdown()
			.selectSeriesLoadFromAssetsLeftPane()
			.validateSeriesLoadPageDisplayScreenLayout()
			.clickOnClientNumberLinkFirstClient()
			.validateListofClientAccountsTableHeaders();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
			
	}


}

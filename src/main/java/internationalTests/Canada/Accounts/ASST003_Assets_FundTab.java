package internationalTests.Canada.Accounts;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Accounts.CanadaAccountsAssetsByFundPage;
import internationalPages.Accounts.CanadaAccountsLoginPage;
import wdMethods.ProjectMethods;

public class ASST003_Assets_FundTab extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke"; 
		testCaseName = "Asset & Sales - Asset Class";
		testDescription = "Validating Asset & Sales - Asset Class";
	}
	
	@Test
	public void validateAssetSalesAssetClass() throws InterruptedException, FileNotFoundException, IOException {
		try {
			System.out.println("Test Case: " + testDescription);
			new CanadaAccountsLoginPage(driver, test)
			.accountsLogin()
			.selectAssetsFromAssetSalesDropdown()
			.selectAssetsClassFromAssetsLeftPane()
			.validateAssetsByClassPageDisplayScreenLayout()
			.clickOnClientNumberLinkFirstClient()
			.validateListofClientAccountsTableHeaders();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
			
	}


}

package internationalTests.Canada.Accounts;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Accounts.CanadaAccountsAssetsByFundPage;
import internationalPages.Accounts.CanadaAccountsLoginPage;
import wdMethods.ProjectMethods;

public class ASST005_Assets_AccountType extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke"; 
		testCaseName = "Asset & Sales - Account Type";
		testDescription = "Validating Asset & Sales -  Account Type";
	}
	
	@Test
	public void validateAssetSalesAccountType() throws InterruptedException, FileNotFoundException, IOException {
		try {
			System.out.println("Test Case: " + testDescription);
			new CanadaAccountsLoginPage(driver, test)
			.accountsLogin()
			.selectAssetsFromAssetSalesDropdown()
			.selectAccountTypeFromAssetsLeftPane()
			.validateAccountTypePageDisplayScreenLayout()
			.clickOnClientNumberLinkFirstClient()
			.validateListofClientAccountsTableHeaders();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
			
	}


}

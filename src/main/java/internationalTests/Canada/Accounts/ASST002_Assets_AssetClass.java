package internationalTests.Canada.Accounts;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Accounts.CanadaAccountsLoginPage;
import wdMethods.ProjectMethods;

public class ASST002_Assets_AssetClass extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke"; 
		testCaseName = "Asset & Sales - Fund Tab";
		testDescription = "Validating Asset & Sales - Fund Tab";
	}
	
	@Test
	public void validateAssetSalesFundTab() throws InterruptedException, FileNotFoundException, IOException {
		try {
			System.out.println("Test Case: " + testDescription);
			new CanadaAccountsLoginPage(driver, test)
			.accountsLogin()
			.selectAssetsFromAssetSalesDropdown()
			.validateAssetsByFundPageDisplayScreenLayout()
			.clickOnClientNumberLinkFirstClient()
			.validateListofClientAccountsTableHeaders();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
			
	}


}

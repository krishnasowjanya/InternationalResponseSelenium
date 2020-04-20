package internationalTests.Canada.Accounts;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Accounts.CanadaAccountsAssetsByFundPage;
import internationalPages.Accounts.CanadaAccountsLoginPage;
import wdMethods.ProjectMethods;

public class ASST006_Assets_ProductType extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke"; 
		testCaseName = "Asset & Sales - Product Type";
		testDescription = "Validating Asset & Sales -  Product Type";
	}
	
	@Test
	public void validateAssetSalesProductType() throws InterruptedException, FileNotFoundException, IOException {
		try {
			System.out.println("Test Case: " + testDescription);
			new CanadaAccountsLoginPage(driver, test)
			.accountsLogin()
			.selectAssetsFromAssetSalesDropdown()
			.selectProductTypeFromAssetsLeftPane()
			.validateProductTypePageDisplayScreenLayout()
			.clickOnClientNumberLinkFirstClient()
			.validateListofClientAccountsTableHeaders();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
			
	}


}

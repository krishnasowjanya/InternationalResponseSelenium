package internationalTests.Canada.Accounts;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Accounts.CanadaAccountsAssetsByFundPage;
import internationalPages.Accounts.CanadaAccountsLoginPage;
import wdMethods.ProjectMethods;

public class RPRT001_Reports_AccountReport extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke"; 
		testCaseName = "Report Builder - Account Report";
		testDescription = "Validating Report Builder - Account Report";
	}
	
	@Test
	public void validateReportBuilderAccountReport() throws InterruptedException, FileNotFoundException, IOException {
		try {
			System.out.println("Test Case: " + testDescription);
			new CanadaAccountsLoginPage(driver, test)
			.accountsLogin()
			.selectBuildNewReportsFromReportsDropdown()
			.validateAccountReportPageScreenLayout()
			.validateConsolidateViewReport();
			
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
			
	}


}

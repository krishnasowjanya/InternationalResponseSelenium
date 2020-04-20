package internationalTests.Canada.Accounts;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Accounts.CanadaAccountsAssetsByFundPage;
import internationalPages.Accounts.CanadaAccountsLoginPage;
import wdMethods.ProjectMethods;

public class RPRT002_Reports_FundReport extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke"; 
		testCaseName = "Report Builder - Fund Report";
		testDescription = "Validating Report Builder - Fund Report";
	}
	
	@Test
	public void validateReportBuilderFundReport() throws InterruptedException, FileNotFoundException, IOException {
		try {
			System.out.println("Test Case: " + testDescription);
			new CanadaAccountsLoginPage(driver, test)
			.accountsLogin()
			.selectBuildNewReportsFromReportsDropdown()
			.selectFundReportFromLeftPane()
			.validateConsolidateViewReport();
			
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
			
	}


}

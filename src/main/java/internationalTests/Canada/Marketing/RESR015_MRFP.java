package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR015_MRFP extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - What is Management Report of Fund Performance?";
		testDescription = "Validating Resources What is Management Report of Fund Performance?";
	}
	
	@Test
	public void validateManagementReportofFundPerformance() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickMRFPsQuarterlyReports()
			.verifyPageBreadCrumb()
			.verifyPageLayoutCanada()
			.clickMRPFTab()
			.verifyTabs()
			.verifyFundDocumentsPageLayoutCanada()
			.verifyDropdownValuesCanada()
			.verifyEditColumnsTabCanada()
			.verifySaveViewResetButton()
			.verifyEditColumnsFunctionality();

			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

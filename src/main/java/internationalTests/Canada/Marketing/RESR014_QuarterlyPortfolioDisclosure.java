package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR014_QuarterlyPortfolioDisclosure extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - What is Quarterly Portfolio Disclosure?";
		testDescription = "Validating Resources What is Quarterly Portfolio Disclosure?";
	}
	
	@Test
	public void validateQuarterlyPortfolioDisclosure() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickMRFPsQuarterlyReports()
			.verifyPageBreadCrumb()
			.verifyPageLayoutCanada()
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

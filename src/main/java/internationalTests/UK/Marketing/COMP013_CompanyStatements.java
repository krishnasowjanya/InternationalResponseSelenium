package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP013_CompanyStatements extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - Company Statements";
		testDescription = "Validating Our Company Company Statements";
	}
	
	@Test
	public void validateCompanyStatements() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickCompanyStatements()
			.verifyPageBreadCrumb()
			.verifyCompanyStmtsPageLayout();
		

				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

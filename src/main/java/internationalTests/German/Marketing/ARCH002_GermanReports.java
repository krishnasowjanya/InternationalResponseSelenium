package internationalTests.German.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class ARCH002_GermanReports extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - German Reports";
		testDescription = "Validating German Reports";
	}
	
	@Test
	public void validateGermanReports() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			HashMap<String,String> testData = getTestData("ARCH002");
			
			new UKHomePage(driver, test)
			.selectActor("Institutional Investor")
			.clickInvestmentOpportunities()
			.clickInvstOppReports()
			.verifyPageBreadCrumb()
			.clickOnLoginInstReports()
			.loginAsInstitutional()
			.verifyPageLayout(testData);
	
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

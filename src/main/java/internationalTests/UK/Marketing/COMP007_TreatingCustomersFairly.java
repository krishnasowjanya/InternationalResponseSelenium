package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP007_TreatingCustomersFairly extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - Treating Customers Fairly";
		testDescription = "Validating Our Company Treating Customers Fairly";
	}
	
	@Test
	public void validateTreatingCustomersFairly() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickTreatCustomerFairly()
			.verifyPageBreadCrumb()
			.verifyTreatingCustFairlyPageLayout()
			.verifyOurFirmSection();
				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class HOME001_RoleSelector extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - HOME - Role Selector";
		testDescription = "Validating - HOME - Role Selector";
	}
	
	@Test
	public void validateRoleSelector() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.validateRoleSelectorForPassedActor("Individual Investor")
			.validateRoleSelectorForPassedActor("Financial Advisor")
			.validateRoleSelectorForPassedActor("Institutions");
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

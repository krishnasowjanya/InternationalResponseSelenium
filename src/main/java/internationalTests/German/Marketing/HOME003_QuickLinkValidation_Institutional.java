package internationalTests.German.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class HOME003_QuickLinkValidation_Institutional extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - HOME - Quick Link Validation - Institutional";
		testDescription = "Validating - HOME - Quick Link Validation - Institutional";
	}
	
	@Test
	public void validateQuickLinkInstitutional() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor("Institutional Investor")
			.validateQuickLinksGerman();
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

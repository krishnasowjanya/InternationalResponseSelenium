package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR013_IndependentReviewCommitee extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Independent Review Comitee";
		testDescription = "Validating Resources Independent Review Comitee";
	}
	
	@Test
	public void validateIndependentReviewComitee() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickIndependentReviewCommitee()
			.verifyPageBreadCrumb()
			.verifyIRCPageLayout();
			

			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

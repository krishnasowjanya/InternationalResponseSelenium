package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR009_IntroductionToMutualFunds extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Introduction to Mutual Funds";
		testDescription = "Validating Resources Introduction to Mutual Funds";
	}
	
	@Test
	public void validateIntroductionMutualFunds() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickIntroToMF()
			.verifyPageBreadCrumb()
			.verifyIntroToMFPageLayout()
			.verifyLandingPageLearnMoreLinks();
			
		
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

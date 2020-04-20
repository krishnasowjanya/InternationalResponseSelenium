package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR001_Literature extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Literature";
		testDescription = "Validating Resources Literature";
	}
	
	@Test
	public void validateLiterature() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickLiterature()
			.verifyPageBreadCrumb()
			.verifyLiteratureBannerTitle()
			.verifyLiteraturePageLayout()
			.verifyLiteratureLinks();
				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

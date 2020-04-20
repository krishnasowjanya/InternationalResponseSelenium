package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR005_ApplicationForms extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Application and Forms";
		testDescription = "Validating Resources Application and Forms";
	}
	
	@Test
	public void validateApplicationForms() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickApplicationFormsDocs()
			.verifyPageBreadCrumb()
			.verifyBannerTitle()
			.verifyApplFormsDocsPageLinksCanada();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

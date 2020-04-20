package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR011_ApplicationFormsDocs extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Application Forms & Documents";
		testDescription = "Validating Resources Application Forms & Documents";
	}
	
	@Test
	public void validateApplicationFormsDocuments() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickApplicationFormsDocs()
			.verifyPageBreadCrumb()
			.verifyApplFormsDocsPageLayout();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

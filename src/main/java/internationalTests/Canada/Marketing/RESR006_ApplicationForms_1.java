package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR006_ApplicationForms_1 extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Application and Forms - 1";
		testDescription = "Validating Resources Application and Forms - 1";
	}
	
	@Test
	public void validateApplicationForms1() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickApplicationFormsSubLink()
			.verifyPageBreadCrumb()
			.verifyApplAndFormsPageLayoutCanada();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

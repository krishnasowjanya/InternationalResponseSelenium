package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR007_ApplicationForms_2 extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Application and Forms - 2";
		testDescription = "Validating Resources Application and Forms - 2";
	}
	
	@Test
	public void validateApplicationForms2() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickApplicationFormsSubLink()
			.verifyPageBreadCrumb()
			.verifyApplFormsSectionLinksCanada();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

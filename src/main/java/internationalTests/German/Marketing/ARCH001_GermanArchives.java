package internationalTests.German.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class ARCH001_GermanArchives extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - German Archives";
		testDescription = "Validating German Archives";
	}
	
	@Test
	public void validateGermanArchives() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			HashMap<String,String> testData = getTestData("ARCH001");
			
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickDistributionTaxes()
			.verifyPageBreadCrumb()
			.verifyPageLayout(testData);
	
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

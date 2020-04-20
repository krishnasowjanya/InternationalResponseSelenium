package internationalTests.German.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PTAX001_German_PriceTaxPage extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - German Price & Tax Page";
		testDescription = "Validating German Price & Tax Page";
	}
	
	@Test
	public void validateGermanPriceTaxCurrent() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			HashMap<String,String> testData = getTestData("PTAX001");
			
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceTaxes()
			.verifyPageBreadCrumb()
			.verifyPageLayout(testData);
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

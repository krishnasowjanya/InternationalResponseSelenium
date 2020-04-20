package internationalTests.German.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PTAX002_German_HistoricalPriceTaxPage extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - German Historical Price & Tax Page";
		testDescription = "Validating German Historical Price & Tax Page";
	}
	
	@Test
	public void validateGermanHistoricalPriceTaxCurrent() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
		
			
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickHistoricalPriceTaxes()
			.verifyPageBreadCrumb()
			.verifyPageLayout();
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

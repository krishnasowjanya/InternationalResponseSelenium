package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR011_PointOfSaleFundFactDocuments extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Point of Sale Fund Fact Documents";
		testDescription = "Validating Resources Point of Sale Fund Fact Documents";
	}
	
	@Test
	public void validatePointofSaleFundFactDocuments() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickPointofSaleFundFactDocuments()
			.validatePointOfSaleLinkAndLayout();

			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

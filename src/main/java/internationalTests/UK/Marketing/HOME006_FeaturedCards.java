package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class HOME006_FeaturedCards extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - HOME - Featured Cards";
		testDescription = "Validating - HOME - Featured Cards";
	}
	
	@Test
	public void validateFeaturedCards() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.validateFeaturedCards();
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

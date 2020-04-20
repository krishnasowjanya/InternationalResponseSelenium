package internationalTests.UK.Smoke;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class SMOK001_HomePage extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek & Mohan";
		category = "Smoke";
		testCaseName = "Home Page Test Case";
		testDescription = "Validating Home Page";
	}
	
	@Test
	public void validateHomePage() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.validateFooter();
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

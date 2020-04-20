package internationalTests.UK.Smoke;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class SMOK003_SiteSearch extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek & Mohan";
		category = "Smoke";
		testCaseName = "Site Search Validation";
		testDescription = "Site Search Validation";
	}
	
	@Test
	public void validatePPSSPage() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
				new UKHomePage(driver, test)
					.selectActor(sActor)
					.validateSearch();
		
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

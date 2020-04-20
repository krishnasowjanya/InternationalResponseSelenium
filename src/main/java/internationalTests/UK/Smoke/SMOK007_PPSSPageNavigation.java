package internationalTests.UK.Smoke;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class SMOK007_PPSSPageNavigation extends ProjectMethods{

	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "PPSS Page Navigation";
		testDescription = "Validating PPSS Page Navigation through Mega Menu";
	}
	
	@Test
	public void validatePPSSPage() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
				new UKHomePage(driver, test)
					.selectActor(sActor)
					.clickProducts()
					.clickPriceAndPerformance()
					.waitUntilAllFundsLoaded()
					.verifyPageNavigation();				
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}


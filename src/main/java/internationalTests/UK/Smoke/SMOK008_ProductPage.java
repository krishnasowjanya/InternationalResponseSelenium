package internationalTests.UK.Smoke;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class SMOK008_ProductPage extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek & Mohan";
		category = "Smoke";
		testCaseName = "Product Page Caveats Validation";
		testDescription = "Product Page Footnote & Important Legal Information Validation";
	}
	
	@Test
	public void validatePPSSPage() throws InterruptedException, FileNotFoundException, IOException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
				new UKHomePage(driver, test)					
					.navigateToProductsPageDirectURL()					
					.validateProductPage();
		
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

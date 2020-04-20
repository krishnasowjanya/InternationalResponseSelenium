package internationalTests.Canada.Smoke;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class SMOK006_FundDocuments extends ProjectMethods {
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Documents Validation - Filter Tab";
		testDescription = "Fund Documents Page level Validation - Filter Tab";
	}
	
	@Test
	public void validateFundDocumentsPage() throws InterruptedException, FileNotFoundException, IOException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
				new UKHomePage(driver, test)
				.selectActor(sActor)
				.clickProducts()
				.clickFundDocuments()
				.verifyPageBreadCrumb()
				.verifyTabs()
				.verifyFundDocumentsPageLayout_Canada();
				//.verifyFundDocuments_Canada();		
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}


}

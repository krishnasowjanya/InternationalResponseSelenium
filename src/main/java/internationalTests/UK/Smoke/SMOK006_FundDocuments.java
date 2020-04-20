package internationalTests.UK.Smoke;

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
		authors = "Prateek & Mohan";
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
				.clickResources()
				.clickFundDocuments()
				.verifyPageBreadCrumb()
				.verifyTabs()
				.verifyFundDocumentsPageLayout();
				//.verifyFundDocuments(); 
				
/*				As discussed with ramya, commenting below checkpoints & Removing SMOK006_FundDocuments_2 Test Script which are not applicable for Smoke. Fund Document scenario covered in SMOK006
				.verifyFundDocumentsPageLayout()
				.verifyDropdownValues()  
				.verifySaveViewResetButton();
*/		
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

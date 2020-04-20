package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR004_FundDocuments_3 extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Fund Documents - Edit Column - Save View";
		testDescription = "Validating Resources Fund Documents - Edit Column - Save View";
	}
	
	@Test
	public void validateFundDocuments3() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickFundDocuments()
			.verifyPageBreadCrumb()
			.verifyTabs()
			.verifyEditColumnsTab()
			.verifySaveViewResetFunctionality();

				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

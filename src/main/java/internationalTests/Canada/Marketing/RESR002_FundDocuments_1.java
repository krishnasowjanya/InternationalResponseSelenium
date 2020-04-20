package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR002_FundDocuments_1 extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Fund Documents - Filter Tab ";
		testDescription = "Validating Resources Fund Documents - Filter Tab Components";
	}
	
	@Test
	public void validateFundDocuments1() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickFundDocuments()
			.verifyPageBreadCrumb()
			.verifyTabs()
			.verifyFundDocumentsPageLayout()
			.verifyDropdownValues()
			.verifySaveViewResetButton();

				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

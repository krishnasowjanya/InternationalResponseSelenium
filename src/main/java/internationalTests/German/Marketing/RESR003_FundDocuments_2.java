package internationalTests.German.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR003_FundDocuments_2 extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Fund Documents - Edit Tab";
		testDescription = "Validating Resources Fund Documents - Edit Tab Components";
	}
	
	@Test
	public void validateFundDocuments2() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickFundDocuments()
			.verifyPageBreadCrumb()
			.verifyTabs()
			.verifyEditColumnsTab();

				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

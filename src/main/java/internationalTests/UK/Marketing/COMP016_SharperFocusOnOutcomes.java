package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP016_SharperFocusOnOutcomes extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - A Sharper focus on your outcomes";
		testDescription = "Validating Our Company A Sharper focus on your outcomes";
	}
	
	@Test
	public void validateSharperFocus() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickSharperFocus()
			.verifySharperFocusPageLayout();
				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

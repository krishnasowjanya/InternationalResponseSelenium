package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP003_History extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - History";
		testDescription = "Validating Our Company History";
	}
	
	@Test
	public void validateHistory() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickHistory()
			.verifyPageBreadCrumb()
			.verifyHistoryPageLayout()
			.verifyHistoryPageTabsContent()
			.verifyOurFirmSection();
			
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

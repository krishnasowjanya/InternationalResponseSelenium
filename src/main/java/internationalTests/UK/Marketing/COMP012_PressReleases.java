package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP012_PressReleases extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - Press Releases";
		testDescription = "Validating Our Company Press Releases";
	}
	
	@Test
	public void validatePressReleases() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickPressReleases()
			.verifyPageBreadCrumb()
			.verifyPressReleasesPageLayout()
			.verifyUKProfilePageOurCompanySection();

				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP011_MediaContacts extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - Media Contacts";
		testDescription = "Validating Our Company Media Contacts";
	}
	
	@Test
	public void validateMediaContacts() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickMediaContacts()
			.verifyPageBreadCrumb()
			.verifyMediaContactsPageLayout()
			.verifyUKProfilePageOurCompanySection();

				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class HOME013_Contacts extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - HOME - Contacts";
		testDescription = "Validating - HOME - Contacts";
	}
	
	@Test
	public void validateContacts() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.validateContacts()
			.verifyPageBreadCrumb()
			.verifyContactUsPageLayout()
			.verifyByPhoneFaxTabData()
			.verifyByPostTabData()
			.verifyByEmailTabData()
			.verifyContactUsOnlineTabData()
			.verifyUKProfilePageOurCompanySection();
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

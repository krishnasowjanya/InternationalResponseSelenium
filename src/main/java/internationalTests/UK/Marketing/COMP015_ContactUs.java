package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP015_ContactUs extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - Contact Us";
		testDescription = "Validating Our Company Contact Us";
	}
	
	@Test
	public void validateContactUs() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickContactUs()
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

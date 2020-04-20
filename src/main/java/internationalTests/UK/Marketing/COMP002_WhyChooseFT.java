package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP002_WhyChooseFT extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - Why Choose FT";
		testDescription = "Validating Our Company Why Choose FT";
	}
	
	@Test
	public void validateWhyChooseFT() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickWhyChooseFT()
			.verifyPageBreadCrumb()
			.verifyWhyChooseFTBannerTitle()
			.verifyWhyFTPageLayout()
			.verifyOurFirmSection()
			.verifyTabs();
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

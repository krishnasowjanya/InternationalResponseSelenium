package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP008_RegulatoryInformation extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - Regulatory Information";
		testDescription = "Validating Our Company Regulatory Information";
	}
	
	@Test
	public void validateRegulatoryInformation() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickRegulatoryInformation()
			.verifyPageBreadCrumb()
			.verifyRegulatoryInfoPageLayout()
			.verifyOurFirmSection();
				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

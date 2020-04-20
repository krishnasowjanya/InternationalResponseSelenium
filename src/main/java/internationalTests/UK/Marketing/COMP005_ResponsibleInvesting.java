package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class COMP005_ResponsibleInvesting extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Our Company - Responsible Investing";
		testDescription = "Validating Our Company Responsible Investing";
	}
	
	@Test
	public void validateResponsibleInvesting() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickOurCompany()
			.clickResponsibleInvesting()
			.verifyPageBreadCrumb()
			.verifyResponsibleInvestingBannerTitle()
			.verifyRespInvstPageLayout()
			.verifyRespInvstPageAfflMbshpSection()
			.verifyRespInvstPagePoliciesSection()
			.verifyRespInvstPageProxyVotingSection()
			.verifyOurFirmSection();
				
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

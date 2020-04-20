package internationalTests.UK.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class INST001_GlobalInvestmentOutlook extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Insights - Global Investment Outllook";
		testDescription = "Validating Insights Global Investment Outllook ";
	}
	
	@Test
	public void validateGIO() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickInsights()
			.clickGlobalInvestmentOutllook()
			.verifyPageBreadCrumb()
			.verifyInsightsGIOBannerTitle()
			.verifyInsightsGIOSPageLayout()
			.verifyContent()
			.verifyResourcesContent()
			.verifyOutlookContributorsContent()
			.verifyCaveats();
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

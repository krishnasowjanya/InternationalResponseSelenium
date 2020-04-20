package internationalTests.German.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class INST003_OurInsights extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Insights - Our Insights";
		testDescription = "Validating Insights Our Insights";
	}
	
	@Test
	public void validateOurInsights() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickInsights()
			.clickOurInsights()
			.verifyPageBreadCrumb()
		//	.verifyOurInsightsBannerTitle()
			.verifyOurInsightsPageLayoutGerman()
			.verifyLandingPageFirstBlog();
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

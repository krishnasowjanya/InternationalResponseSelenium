package internationalTests.UK.Smoke;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class SMOK005_OurInsights extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek & Mohan";
		category = "Smoke";
		testCaseName = "Our Insights Validation";
		testDescription = "Our Insights Validation";
	}
	
	@Test
	public void validateOurInsightsPage() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
				new UKHomePage(driver, test)
					.selectActor(sActor)
					.clickInsights()					
					.clickOurInsights()
					//.verifyPageBreadCrumb()
					//.verifyOurInsightsBannerTitle()
					.verifyOurInsightsPageLayout()
					.verifyLoadMoreButton()
					.verifyLandingPageFirstBlog();
		
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

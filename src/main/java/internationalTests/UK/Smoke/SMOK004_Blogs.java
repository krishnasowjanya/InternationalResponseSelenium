package internationalTests.UK.Smoke;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class SMOK004_Blogs extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Prateek & Mohan";
		category = "Smoke";
		testCaseName = "Blogs Validation";
		testDescription = "Blogs Validation";
	}
	
	@Test
	public void validateBlogsPage() throws InterruptedException {		
		
		try {
			System.out.println("Test Case: " + testDescription);
				new UKHomePage(driver, test)
					.selectActor(sActor)
					.clickInsights()
					.clickBlogs()
					.verifyPageBreadCrumb()
					.verifyInsightsBlogsBannerTitle()
					.verifyInsightsBlogsPageLayout()
					.verifyLoadMoreButton()
					.verifyLandingPageFirstBlog();
		
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

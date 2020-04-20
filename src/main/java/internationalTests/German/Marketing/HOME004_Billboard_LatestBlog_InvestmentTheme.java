package internationalTests.German.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class HOME004_Billboard_LatestBlog_InvestmentTheme extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - HOME - Billboard_LatestBlog_InvestmentTheme Validation - Investor";
		testDescription = "Validating - HOME - Billboard_LatestBlog_InvestmentTheme Validation - Investor";
	}
	
	@Test
	public void validateBillboardLatestBlogInvestmentThemeInvestor() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor("Individual Investor")
			.validateBillboardBlogsInvestmentThemesGermany();
			
			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

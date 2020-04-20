package internationalPages.Marketing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

import internationalPages.Products.UKHomePage;
import net.bytebuddy.implementation.bytecode.constant.LongConstant;
import wdMethods.ProjectMethods;
import utils.ExcelDataProvider;
import utils.dateFunctions;

public class OurInsightsPage extends ProjectMethods{
	
	public static String tid;
	public OurInsightsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblOurInsightsPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public OurInsightsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Our Insights Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Our Insights", getLocalePropertyValue("lblOurInsightsPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public OurInsightsPage verifyOurInsightsBannerTitle()
	{
		verifyBannerImageAndHeading(getLocalePropertyValue("lblInsightsPageBannerStyle"), getLocalePropertyValue("lblOurInsightsPageBannerHeadingActual"));
		return this;
	}
	
	
	public OurInsightsPage verifyOurInsightsPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of our Insights Page", "INFO");
		List <WebElement> dropdowns=locateElements("xpath", getLocalePropertyValue("objBlogsPageDropdowns"));	
		
		if(dropdowns.size()!=3)
			reportStep("One or more of the 3 Dropdowns{Asset Class,Region, Content} are not displayed for our Insights Page", "INFO",true);
		
		reportStep("Validate values in asset class dropdown", "INFO");
		click(dropdowns.get(0),true);
		Select assetClassDropdown  = new Select(dropdowns.get(0));
		
		if(dropdowns.get(0).findElements(By.tagName("option")).size()!=getLocalePropertyValue("lblAssetClassDropdownValuesOurInsightPage").split("\\|").length)
			reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblAssetClassDropdownValuesOurInsightPage")+"} are not displayed for Asset Class Filter","FAIL");	
		
		
		for(WebElement ele:assetClassDropdown.getOptions()) {
			if(!ele.getText().matches(getLocalePropertyValue("lblAssetClassDropdownValuesOurInsightPage")))
			
				reportStep(ele.getText()+" value is not present in asset class dropdown as expected", "FAIL",true);
		}
		
		reportStep("All the Expected Values {"+getLocalePropertyValue("lblAssetClassDropdownValuesOurInsightPage")+"} are  displayed for Asset Class Filter","PASS");
		
		reportStep("Validate values in Region dropdown", "INFO");
		click(dropdowns.get(1),true);
		Select regionDropdown  = new Select(dropdowns.get(1));
		
		if(dropdowns.get(1).findElements(By.tagName("option")).size()!=getLocalePropertyValue("lblRegionDropdownValuesOurInsightPage").split("\\|").length)
			reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblRegionDropdownValuesOurInsightPage")+"} are not displayed for Region Filter","FAIL");	
		
		
		for(WebElement ele:regionDropdown.getOptions()) {
			if(!ele.getText().matches(getLocalePropertyValue("lblRegionDropdownValuesOurInsightPage")))
			
				reportStep(ele.getText()+" value is not present in Region dropdown as expected", "FAIL",true);
		}
		
		reportStep("All the Expected Values {"+getLocalePropertyValue("lblRegionDropdownValuesOurInsightPage")+"} are  displayed for Region Filter","PASS");
		
		reportStep("Validate values in Content dropdown", "INFO");
		click(dropdowns.get(2),true);
		Select contentType  = new Select(dropdowns.get(2));

		if(dropdowns.get(2).findElements(By.tagName("option")).size()!=getLocalePropertyValue("lblContentDropdownValuesOurInsightPage").split("\\|").length)
			reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblContentDropdownValuesOurInsightPage")+"} are not displayed for Content Type Filter","FAIL");	
		
		for(WebElement ele:contentType.getOptions()) {
			if(!ele.getText().matches(getLocalePropertyValue("lblContentDropdownValuesOurInsightPage")))
				
				reportStep(ele.getText()+" value is not present in Content Type dropdown as expected", "FAIL",true);
		}
		
		reportStep("All the Expected Values {"+getLocalePropertyValue("lblContentDropdownValuesOurInsightPage")+"} are  displayed for Content Type Filter","PASS");
		
		verifyElementExist("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn"), "Load More Button");
		
		return this;
	}
	
	public OurInsightsPage verifyOurInsightsPageLayoutGerman() throws InterruptedException
	{
		reportStep("Validate Layout of our Insights Page", "INFO");
		
		verifyElementExist("xpath", "//h1[text()='ETF Einblicke']","Insights Page Header");
		
		
		
		return this;
	}
	
	public OurInsightsPage verifyLoadMoreButton() throws InterruptedException
	{
		reportStep("Validate that Load More button is enabled for Our Insights Page", "INFO");
		List <WebElement> blogs=locateElements("xpath", getLocalePropertyValue("objBlogsPageLinks"));	
		
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn")));
		
		if(blogs.size()==20) {
			if(locateElement("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn")).isEnabled()) {
				click(locateElement("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn")),true);
				blogs=locateElements("xpath", getLocalePropertyValue("objBlogsPageLinks"));
				if(blogs.size()>20)
					reportStep("Additional Blogs are loaded on clicking Load More button as expected", "PASS",true);		
				else
					reportStep("Additional Blogs are not loaded on clicking Load More button", "FAIL",true);		
					
			}
			else
				reportStep("Only 20 Blogs are present to display in blogs Page, So Load More button is disabled", "PASS",true);		
			
		}	
			else
				reportStep("Less than 20 Blogs are displayed in Blogs Page so Load More button is not Enabled", "PASS",true);		
		
		
		return this;
		}
	
	
	public OurInsightsPage verifyLandingPageFirstBlog() throws InterruptedException
	{
		reportStep("Validate First Our Insights link is navigating to correct page", "INFO");
		List <WebElement> blogs=locateElements("xpath", getLocalePropertyValue("objBlogsPageLinks"));	
		
		String blogHeading = blogs.get(0).getText();
		
		click(blogs.get(0));
		
		verifyPartialText(locateElement("xpath","//h1"), blogHeading.toUpperCase() , true);
		
		return this;
		}
	
	public UKHomePage clickBenLogo() throws FileNotFoundException, IOException, InterruptedException
	{
		reportStep("Navigating to Home Page", "INFO");
		click(locateElement("xpath",localeProp.getProperty("objBenlogo")));
		
		return new UKHomePage(driver,test);
	}
	
}
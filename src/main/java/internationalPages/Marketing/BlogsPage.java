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


import net.bytebuddy.implementation.bytecode.constant.LongConstant;
import wdMethods.ProjectMethods;
import utils.ExcelDataProvider;
import utils.dateFunctions;

public class BlogsPage extends ProjectMethods{
	
	public static String tid;
	public BlogsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblBlogsPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public BlogsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Blogs Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Blogs", getLocalePropertyValue("lblBlogsPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public BlogsPage verifyInsightsBlogsBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/common/billboards/I4WN-banner.jpg", getLocalePropertyValue("lblBlogsPageBannerHeading"));
		return this;
	}
	
	
	public BlogsPage verifyInsightsBlogsPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Insights Blogs Page", "INFO");
		List <WebElement> dropdowns=locateElements("xpath", getLocalePropertyValue("objBlogsPageDropdowns"));	
		
		if(dropdowns.size()!=2)
			reportStep("One or more of the 2 Dropdownss{Asset Class,Region} are not displayed for Blogs Page", "INFO",true);
		
		reportStep("Validate values in asset class dropdown", "INFO");
		click(dropdowns.get(0),true);
		Select assetClassDropdown  = new Select(dropdowns.get(0));
		

		if(dropdowns.get(0).findElements(By.tagName("option")).size()!=5)
			reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblAssetClassDropdownValuesBlogsPage")+"} are not displayed for Asset Class Filter","FAIL");	
		
		
		for(WebElement ele:assetClassDropdown.getOptions()) {
			if(!ele.getText().matches(getLocalePropertyValue("lblAssetClassDropdownValuesBlogsPage"))) 
			
				reportStep(ele.getText()+" value is not present in asset class dropdown as expected", "FAIL",true);
		}
		
		reportStep("All the Expected Values {"+getLocalePropertyValue("lblAssetClassDropdownValuesBlogsPage")+"} are  displayed for Asset Class Filter","PASS");		
		
		reportStep("Validate values in Region dropdown", "INFO");
		click(dropdowns.get(1),true);
		Select regionDropdown  = new Select(dropdowns.get(1));
		
		if(dropdowns.get(1).findElements(By.tagName("option")).size()!=7)
			reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblRegionDropdownValuesBlogsPage")+"} are not displayed for Region Filter","FAIL");	
		
		for(WebElement ele:regionDropdown.getOptions()) {
			if(!ele.getText().matches(getLocalePropertyValue("lblRegionDropdownValuesBlogsPage")))
			
				reportStep(ele.getText()+" value is not present in Region dropdown as expected", "FAIL",true);
		}
		
		reportStep("All the Expected Values {"+getLocalePropertyValue("lblRegionDropdownValuesBlogsPage")+"} are  displayed for Region Filter","PASS");
		
		verifyElementExist("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn"), "Load More Button",true);
		
		return this;
	}
	
	public BlogsPage verifyLandingPageFirstBlog() throws InterruptedException
	{
		reportStep("Validate First blog link is navigating to correct page", "INFO");
		List <WebElement> blogs=locateElements("xpath", getLocalePropertyValue("objBlogsPageLinks"));	
		
		String blogHeading = blogs.get(0).getText();
		
		click(blogs.get(0));
		
		verifyPartialText(locateElement("xpath","//h1"), blogHeading.toUpperCase() , true);
		
		return this;
		}
	

	public BlogsPage verifyLoadMoreButton() throws InterruptedException
	{
		reportStep("Validate that Load More button is enabled for Blogs Page", "INFO");
		List <WebElement> blogs=locateElements("xpath", getLocalePropertyValue("objBlogsPageLinks"));	
		
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn")));
		if(blogs.size()==8) {
			if(locateElement("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn")).isEnabled()) {
				click(locateElement("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn")),true);
				blogs=locateElements("xpath", getLocalePropertyValue("objBlogsPageLinks"));
				if(blogs.size()>8)
					reportStep("Additional Blogs are loaded on clicking Load More button as expected", "PASS",true);		
				else
					reportStep("Additional Blogs are not loaded on clicking Load More button", "FAIL",true);		
					
			}
			else
				reportStep("Only 8 Blogs are present to display in blogs Page, So Load More button is disabled", "PASS",true);		
			
		}	
			else
				reportStep("Less than 8 Blogs are displayed in Blogs Page so Load More button is not Enabled", "PASS",true);		
		
		
		return this;
		}
	}


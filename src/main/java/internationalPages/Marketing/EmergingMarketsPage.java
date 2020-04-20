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

public class EmergingMarketsPage extends ProjectMethods{
	
	public static String tid;
	public EmergingMarketsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblEmergingMarketsPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public EmergingMarketsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Emerging Markets Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Emerging Markets", getLocalePropertyValue("lblEmergingMarketsPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public EmergingMarketsPage verifyEmergingMarketsBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/common/billboards/sample.jpg", getLocalePropertyValue("lblEmergingMarketsPageBannerHeading"));
		return this;
	}
	
	
	public EmergingMarketsPage verifyEmergingMarketsPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of EmergingMarkets Page", "INFO");
		List <WebElement> dropdowns=locateElements("xpath", getLocalePropertyValue("objBlogsPageDropdowns"));	
		
		if(dropdowns.size()!=2)
			reportStep("One or more of the 2 Dropdowns are not displayed for Emerging Markets Page", "INFO",true);
		

		click(dropdowns.get(0),true);
		Select assetClassDropdown  = new Select(dropdowns.get(0));
		for(WebElement ele:assetClassDropdown.getOptions()) {
			if(ele.getText().matches(getLocalePropertyValue("lblAssetClassDropdownValuesEmMrksPage")))
				reportStep(ele.getText()+" value is present in asset class dropdown as expected", "PASS");
			else
				reportStep(ele.getText()+" value is not present in asset class dropdown as expected", "FAIL",true);
		}
		
		click(dropdowns.get(1),true);
		Select contentType  = new Select(dropdowns.get(1));
		for(WebElement ele:contentType.getOptions()) {
			if(ele.getText().matches(getLocalePropertyValue("lblContentDropdownValuesEmMrksPage")))
				reportStep(ele.getText()+" value is present in Content Type dropdown as expected", "PASS");
			else
				reportStep(ele.getText()+" value is not present in Content Type dropdown as expected", "FAIL",true);
		}
		
	verifyElementExist("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn"), "Load More Button");
		
		return this;
	}
	
	
}

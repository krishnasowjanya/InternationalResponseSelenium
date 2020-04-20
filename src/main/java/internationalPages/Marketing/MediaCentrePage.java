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

public class MediaCentrePage extends ProjectMethods{
	
	public static String tid;
	public MediaCentrePage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblMediaCentrePageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public MediaCentrePage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Media Centre Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Media Centre", getLocalePropertyValue("lblMediaCentrePageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public MediaCentrePage verifyMediaCentreBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/common/billboards/Businesspartners_1170x186.jpg", getLocalePropertyValue("lblMediaCentrePageBannerHeading"));
		return this;
	}
	
	
	public MediaCentrePage verifyMediaCentrePageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Media Centre Page", "INFO");
		List <WebElement> headers=locateElements("xpath", "//h2");	
		
		if(headers.size()!=5)
			reportStep("One or more of the 5 Headers are not displayed for Media Centre Page", "INFO",true);
		
		reportStep("Validate Headers in Page", "INFO");
		verifyExactText(headers.get(0), getLocalePropertyValue("lblMediaCentrePageHeader1"), "Header");
		verifyExactText(headers.get(1), getLocalePropertyValue("lblMediaCentrePageHeader2"), "Header");
		verifyExactText(headers.get(2), getLocalePropertyValue("lblMediaCentrePageHeader3"), "Header");
		verifyExactText(headers.get(3), getLocalePropertyValue("lblMediaCentrePageHeader4"), "Header"); 
		verifyExactText(headers.get(4), getLocalePropertyValue("lblMediaCentrePageHeader5"), "Header"); 
		
		return this;
	}
	
	
	public MediaCentrePage verifyMediaCentreLinks() throws InterruptedException
	{
		reportStep("Validate Media Center links navigationg in Page", "INFO");
	
	
		if(locateElements("xpath", "//h2/a").size()!=4)
			reportStep("The Expected media centre links count is not matching in page", "FAIL",true);
		
		String urlsArray[] = {getLocalePropertyValue("lblMediaCenterLinksLandingPageHeader1"),getLocalePropertyValue("lblMediaCenterLinksLandingPageHeader2"),getLocalePropertyValue("lblMediaCenterLinksLandingPageHeader3"),getLocalePropertyValue("lblMediaCenterLinksLandingPageHeader4")};
		int i=0;
		int size =locateElements("xpath", "//h2/a").size();
		for(int j=0;j<size ;j++) {
			String resTitle =  locateElements("xpath", "//h2/a").get(j).getText();
			reportStep("Clicking the Resource with Title :"+resTitle, "INFO");
			click(locateElements("xpath", "//h2/a").get(j),true);
			verifyExactText(locateElement("xpath","//h1"), urlsArray[i]);
			i++;
			driver.navigate().back();
		}	
		
	return this;	
	}
	
	public MediaCentrePage verifyUKProfilePageOurCompanySection() throws InterruptedException
	{
		reportStep("Validate Layout of Our Company Section", "INFO");
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("lblUKProfilePageSectionlnks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblUKProfilePageOurCompanylink1"), "link",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblUKProfilePageOurCompanylink2"), "link",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblUKProfilePageOurCompanylink3"), "link",false);
		verifyExactText(lnks.get(3), getLocalePropertyValue("lblUKProfilePageOurCompanylink4"), "link",false);
		

		return this;
	}
	
	
	
}

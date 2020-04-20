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

public class LiteraturePage extends ProjectMethods{
	
	public static String tid;
	public LiteraturePage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblLiteraturePageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public LiteraturePage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Literature Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Literature", getLocalePropertyValue("lblLiteraturePageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public LiteraturePage verifyLiteratureBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/common/billboards/sample.jpg", getLocalePropertyValue("lblLiteraturePageBannerHeading"));
		return this;
	}
	
	
	public LiteraturePage verifyLiteraturePageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Literature Page", "INFO");
		List <WebElement> headers=locateElements("xpath", "//h2");	
		
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		
		if(headers.size()!=5)
			reportStep("One or more of the 5 Headers are not displayed for Media Centre Page", "INFO",true);
		
		reportStep("Validate Headers in Page", "INFO");
		verifyExactText(headers.get(0), getLocalePropertyValue("lblLiteraturePageHeader1"), "Header");
		verifyExactText(headers.get(1), getLocalePropertyValue("lblLiteraturePageHeader2"), "Header");
		verifyExactText(headers.get(2), getLocalePropertyValue("lblLiteraturePageHeader3"), "Header");
		verifyExactText(headers.get(3), getLocalePropertyValue("lblLiteraturePageHeader4"), "Header"); 
		verifyExactText(headers.get(4), getLocalePropertyValue("lblLiteraturePageHeader5"), "Header"); 
		
		return this;
	}
	
	
	public LiteraturePage verifyLiteratureLinks() throws InterruptedException
	{
		reportStep("Validate Literature links navigation in Page", "INFO");
	
	
		if(locateElements("xpath", "//h2/a").size()!=5)
			reportStep("The Expected Literature links count is not matching in page", "FAIL",true);
		
		String urlsArray[] = {getLocalePropertyValue("lblLtrtrLinksLandingPageHeader1"),
								getLocalePropertyValue("lblLtrtrLinksLandingPageHeader2"),
									getLocalePropertyValue("lblLtrtrLinksLandingPageHeader3"),
										getLocalePropertyValue("lblLtrtrLinksLandingPageHeader4"),
											getLocalePropertyValue("lblLtrtrLinksLandingPageHeader5")};
		int i=0;
		int size =locateElements("xpath", "//h2/a").size();
		for(int j=0;j<size ;j++) {
			String resTitle =  locateElements("xpath", "//h2/a").get(j).getText();
			reportStep("Clicking the Literature with Title :"+resTitle, "INFO");
			click(locateElements("xpath", "//h2/a").get(j),true);
			Thread.sleep(2000);
			if(locateElements("xpath","//h1").size()>0) {
			if(locateElement("xpath","//h1").getText().equals(urlsArray[i]))
				reportStep(urlsArray[i]+" header in landing page is displayed are expected", "PASS");
			else 
				reportStep(urlsArray[i]+" header in landing page is not displayed are expected", "FAIL");
			}
			else {
				if(locateElement("xpath","//h2").getText().equals(urlsArray[i]))
					reportStep(urlsArray[i]+" header in landing page is displayed are expected", "PASS");
				else 
					reportStep(urlsArray[i]+" header in landing page is not displayed are expected", "FAIL");
			}
			i++;
			driver.navigate().back();
		}	
		
	return this;	
	}
	
	
	
}

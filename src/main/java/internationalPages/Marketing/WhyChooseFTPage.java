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

public class WhyChooseFTPage extends ProjectMethods{
	
	public static String tid;
	public WhyChooseFTPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblWhyChosseFTPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public WhyChooseFTPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Why Choose FT Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Why Choose Franklin Templeton", getLocalePropertyValue("lblWhyChooseFTPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public WhyChooseFTPage verifyWhyChooseFTBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/common/billboards/BB-ourfirm.jpg", getLocalePropertyValue("lblWhyChooseFTPageBannerHeading"));
		return this;
	}
	
	
	public WhyChooseFTPage verifyWhyFTPageLayout() throws InterruptedException
	{
		List <WebElement> headings=locateElements("xpath", "//h2");	
			if(headings.size()==3) {
		
			verifyExactText(headings.get(0), getLocalePropertyValue("lblWhyFTPageHeader1"), "Why Choose FT Component",false);
			verifyExactText(headings.get(1), getLocalePropertyValue("lblWhyFTPageHeader2"), "Why Choose FT Component",false);
			verifyExactText(headings.get(2), getLocalePropertyValue("lblWhyFTPageHeader3"), "Why Choose FT Component",false);
			
			}else
				reportStep("One or More Why Choose FT component may not displayed", "FAIL");
	
		
		return this;
	}
	
	public WhyChooseFTPage verifyOurFirmSection() throws InterruptedException
	{
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("objWhyFTPageOurFirmSectionLinks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText1"), "Our Firm Link",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText2"), "Our Firm Link",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText3"), "Our Firm Link",false);
		verifyExactText(lnks.get(3), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText4"), "Our Firm Link",false);
		verifyExactText(lnks.get(4), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText5"), "Our Firm Link",false);
		verifyExactText(lnks.get(5), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText6"), "Our Firm Link",false);
		verifyExactText(lnks.get(6), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText7"), "Our Firm Link",false);
		return this;
	}
	
	public WhyChooseFTPage verifyTabs() throws InterruptedException
	{
		List <WebElement> tabs=locateElements("xpath",getLocalePropertyValue("objWhyFTPageTabls"));	
		verifyExactText(tabs.get(0), getLocalePropertyValue("lblWhyFTPageTab1"), "Tab",false);
		verifyExactText(tabs.get(1), getLocalePropertyValue("lblWhyFTPageTab2"), "Tab",false);
		verifyExactText(tabs.get(2), getLocalePropertyValue("lblWhyFTPageTab3"), "Tab",false);
		
		return this;
	}
}

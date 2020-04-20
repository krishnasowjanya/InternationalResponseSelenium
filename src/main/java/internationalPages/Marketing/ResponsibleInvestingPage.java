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

public class ResponsibleInvestingPage extends ProjectMethods{
	
	public static String tid;
	public ResponsibleInvestingPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblResponsibleInvstPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ResponsibleInvestingPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Responsible Investing Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Responsible Investing", getLocalePropertyValue("lblRespInvstPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	public ResponsibleInvestingPage verifyResponsibleInvestingBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/en-gb/responsible-investing/masthead.jpg", getLocalePropertyValue("lblRespInvstPageBannerHeading"));
		return this;
	}
	
	public ResponsibleInvestingPage verifyRespInvstPageLayout() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		verifyElementExist("xpath", getLocalePropertyValue("objRIPageHeaderESGContent"),"Related ESG Content: header");
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("objRIPageHeaderESGlinks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblRIPageESGlink1"), "links",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblRIPageESGlink2"), "links",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblRIPageESGlink3"), "links",false);
		
		List <WebElement> tabs=locateElements("xpath","//h2");	
		verifyExactText(tabs.get(0), getLocalePropertyValue("lblRIPageHeader1"), "Header",false);
		verifyExactText(tabs.get(1), getLocalePropertyValue("lblRIPageHeader2"), "Header",false);
		verifyExactText(tabs.get(2), getLocalePropertyValue("lblRIPageHeader3"), "Header",false);
		verifyExactText(tabs.get(3), getLocalePropertyValue("lblRIPageHeader4"), "Header",false);
		verifyExactText(tabs.get(4), getLocalePropertyValue("lblRIPageHeader5"), "Header",false);
		
		return this;
	}

	public ResponsibleInvestingPage verifyRespInvstPageAfflMbshpSection() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("lblRIPageAfflmbspSignatoryHeader"),"Signatory of: header");
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("lblRIPageAfflmbspSignatorylnks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblRIPageAfflMbrlink1"), "links",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblRIPageAfflMbrlink2"), "links",false);
		
		reportStep("Validating the PDF is opened for first link in affiliation & Membership section", "INFO");
		click(lnks.get(0));
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		Thread.sleep(5000);
		verifyTwoStringsPartially(driver.getCurrentUrl(), "common",true);
		   
		driver.close();
		driver.switchTo().window(tabs.get(0));	
		
		return this;
	}
	
	public ResponsibleInvestingPage verifyRespInvstPagePoliciesSection() throws InterruptedException
	{
		
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("lblRIPagePolReplnks"));	
		
		reportStep("Validating the PDF is opened for first link in Policies & Reports section", "INFO");
		click(lnks.get(0));
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		Thread.sleep(5000);
		verifyTwoStringsPartially(driver.getCurrentUrl(), "common",true);
		   
		driver.close();
		driver.switchTo().window(tabs.get(0));	
		
		return this;
	}
	
	public ResponsibleInvestingPage verifyRespInvstPageProxyVotingSection() throws InterruptedException
	{
		
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("lblRIPagePolReplnks"));	
		
		reportStep("Validating the PDF is opened for first link in Policies & Reports section", "INFO");
		click(lnks.get(0));
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		Thread.sleep(5000);
		verifyTwoStringsPartially(driver.getCurrentUrl(), "common",true);
		   
		driver.close();
		driver.switchTo().window(tabs.get(0));	
		
		return this;
	}
	
	public ResponsibleInvestingPage verifyOurFirmSection() throws InterruptedException
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
	
}

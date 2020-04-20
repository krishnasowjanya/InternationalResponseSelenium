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

public class InvestorEducationPage extends ProjectMethods{
	
	public static String tid;
	public InvestorEducationPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblInvestEduPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public InvestorEducationPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Investor Education Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Investor Education", getLocalePropertyValue("lblInvestorEduPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public InvestorEducationPage verifyInvestorEducationBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/common/billboards/Businesspartners_1170x186.jpg", getLocalePropertyValue("lblInvestEduPageBannerHeading"));
		return this;
	}
	
	
	public InvestorEducationPage verifyInvestorEduPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Investor Education Page", "INFO");
		List <WebElement> headers=locateElements("xpath", "//h2");	
		
		if(headers.size()!=8)
			reportStep("One or more of the 8 Headers are not displayed for Investor Education Page", "INFO",true);
		
		reportStep("Validate Headers in Page", "INFO");
		verifyExactText(headers.get(0), getLocalePropertyValue("lblInvestEduPageHeader1"), "Page Heading");
		verifyExactText(headers.get(1), getLocalePropertyValue("lblInvestEduPageHeader2"), "Page Heading");
		verifyExactText(headers.get(2), getLocalePropertyValue("lblInvestEduPageHeader3"), "Page Heading");
		verifyExactText(headers.get(3), getLocalePropertyValue("lblInvestEduPageHeader4"), "Page Heading");
		verifyExactText(headers.get(4), getLocalePropertyValue("lblInvestEduPageHeader5"), "Page Heading");
		verifyExactText(headers.get(5), getLocalePropertyValue("lblInvestEduPageHeader6"), "Page Heading");
		verifyExactText(headers.get(6), getLocalePropertyValue("lblInvestEduPageHeader7"), "Page Heading");
		verifyExactText(headers.get(7), getLocalePropertyValue("lblInvestEduPageHeader8"), "Page Heading");
		
		
		return this;
	}
	
	
	
	
	public InvestorEducationPage verifyLandingPageForEachHeading() throws InterruptedException
	{
		reportStep("Validate Landing page for each heading in Page", "INFO");
	

		if(locateElements("xpath", "//h2/a").size()!=8)
			reportStep("The Expected Investor Education links count is not matching in page", "FAIL",true);

			reportStep("Validating the Heading in landing page", "INFO");
			String linkText = locateElements("xpath", "//h2/a").get(0).getText();
			click(locateElements("xpath", "//h2/a").get(0),true);
			Thread.sleep(5000);
			validateTheHeaderOtherTab(locateElement("xpath","//h1"), linkText);
			
			linkText = locateElements("xpath", "//h2/a").get(1).getText();
			click(locateElements("xpath", "//h2/a").get(1),true);
			validateTheHeaderOtherTab(locateElement("xpath","//h1"),linkText);
			
			linkText = locateElements("xpath", "//h2/a").get(2).getText();
			click(locateElements("xpath", "//h2/a").get(2),true);
			validateTheHeaderOtherTab(locateElement("xpath","//h1"), linkText);
			
			linkText = locateElements("xpath", "//h2/a").get(3).getText();
			click(locateElements("xpath", "//h2/a").get(3),true);
			validateTheHeaderOtherTab(locateElement("xpath","//h1"), linkText);
			
			linkText = locateElements("xpath", "//h2/a").get(4).getText();
			click(locateElements("xpath", "//h2/a").get(4),true);
			validateTheHeaderOtherTab(locateElement("xpath","//h1"), "HERE'S A DIFFERENT VIEW OF VOLATILITY");
			
			linkText = locateElements("xpath", "//h2/a").get(5).getText();
			click(locateElements("xpath", "//h2/a").get(5),true);
			validateTheHeaderOtherTab(locateElement("xpath","//h1"),linkText);
			
			linkText = locateElements("xpath", "//h2/a").get(6).getText();
			click(locateElements("xpath", "//h2/a").get(6),true);
			validateTheHeaderOtherTab(locateElement("xpath","//h1"),linkText);
			
			linkText = locateElements("xpath", "//h2/a").get(7).getText();
			click(locateElements("xpath", "//h2/a").get(7),true);
			validateTheHeaderOtherTab(locateElement("xpath","//h1"), linkText);
			
	return this;	
	}
	
	public void validateTheHeaderOtherTab(WebElement header,String headerText) {
		
		verifyExactText(header, headerText);
		  driver.navigate().back();
	}
	
}

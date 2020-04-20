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

public class RiskManagementPage extends ProjectMethods{
	
	public static String tid;
	public RiskManagementPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblRiskManagementPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public RiskManagementPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Risk Management Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Risk Management", getLocalePropertyValue("lblRiskManagementPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	public RiskManagementPage verifyRiskManagmentPageLayout() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
	
		List <WebElement> tabs=locateElements("xpath","//h2");	
		verifyExactText(tabs.get(0), getLocalePropertyValue("lblRMPageHeader1"), "Tab",false);
		verifyExactText(tabs.get(1), getLocalePropertyValue("lblRMPageHeader2"), "Tab",false);
		
		verifyElementExist("xpath",getLocalePropertyValue("objRMPageVideo"), "Video");
		
		return this;
	}
	
	public RiskManagementPage verifyCaveats() throws InterruptedException
	{
		reportStep("Validate Caveats of Risk Managment Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objRMPageCaveats1"), "Important Information Header");
		verifyElementExist("xpath", getLocalePropertyValue("objRMPageCaveats2"), "Footnotes Header");
		
	return this;	
	}
	
	public RiskManagementPage verifyOurFirmSection() throws InterruptedException
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

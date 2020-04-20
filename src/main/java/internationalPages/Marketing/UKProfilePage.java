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

public class UKProfilePage extends ProjectMethods{
	
	public static String tid;
	public UKProfilePage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblUKProfilePageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public UKProfilePage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full UK Profile Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("UK Profile", getLocalePropertyValue("lblUKProfilePageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public UKProfilePage verifyUKProfileBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/en-gb/billboards/uk-basketball-940x200.jpg", getLocalePropertyValue("lblUKProfilePageBannerHeading"));
		return this;
	}
	
	
	public UKProfilePage verifyUKProfilePageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of UK Profile Page", "INFO");
		
		List <WebElement> tabs=locateElements("xpath","//h2");	
		verifyExactText(tabs.get(0), getLocalePropertyValue("lblUKProfilePageHeader1"), "Header",false);
		verifyExactText(tabs.get(1), getLocalePropertyValue("lblUKProfilePageHeader2"), "Header",false);
		verifyExactText(tabs.get(2), getLocalePropertyValue("lblUKProfilePageHeader3"), "Header",false);
		verifyExactText(tabs.get(3), getLocalePropertyValue("lblUKProfilePageHeader4"), "Header",false);
		verifyExactText(tabs.get(4), getLocalePropertyValue("lblUKProfilePageHeader5"), "Header",false);
		verifyExactText(tabs.get(5), getLocalePropertyValue("lblUKProfilePageHeader6"), "Header",false);
		verifyExactText(tabs.get(6), getLocalePropertyValue("lblUKProfilePageHeader7"), "Header",false);

		return this;
	}
	
	public UKProfilePage verifyUKProfilePageOurCompanySection() throws InterruptedException
	{
		reportStep("Validate Layout of UK Profile Page Our Company Section", "INFO");
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("lblUKProfilePageSectionlnks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblUKProfilePageOurCompanylink1"), "link",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblUKProfilePageOurCompanylink2"), "link",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblUKProfilePageOurCompanylink3"), "link",false);
		verifyExactText(lnks.get(3), getLocalePropertyValue("lblUKProfilePageOurCompanylink4"), "link",false);
		

		return this;
	}
	
	
}

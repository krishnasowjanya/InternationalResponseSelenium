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

public class ContactUsPage extends ProjectMethods{
	
	public static String tid;
	public ContactUsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblContactUsPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ContactUsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Contact Us Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Contact Us", getLocalePropertyValue("lblContactUsPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	public ContactUsPage verifyContactUsPageLayout() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		verifyElementExist("xpath", getLocalePropertyValue("objContactUsPageHeaderText"),"Text Below Header");
		reportStep("Validate tabs names", "INFO");	
		
		List<WebElement> tabs =  locateElements("xpath", getLocalePropertyValue("objContactUsPageTabs"));
		verifyExactText(tabs.get(0),getLocalePropertyValue("lblContactUsTab1"),"tab");
		verifyExactText(tabs.get(1),getLocalePropertyValue("lblContactUsTab2"),"tab");
		verifyExactText(tabs.get(2),getLocalePropertyValue("lblContactUsTab3"),"tab");
		verifyExactText(tabs.get(3),getLocalePropertyValue("lblContactUsTab4"),"tab");
		
		return this;
	}
	
	public ContactUsPage verifyByPhoneFaxTabData() throws InterruptedException
	{
		reportStep("Validate By Phone or Fax Tab Data", "INFO");	
		List<WebElement> prgphs =  locateElements("xpath", getLocalePropertyValue("objContactUsPageTab1DataPgph"));
		verifyExactText(prgphs.get(0),getLocalePropertyValue("lblContactUsTab1P1"),"By Phone or Fax Info");
		verifyExactText(prgphs.get(1),getLocalePropertyValue("lblContactUsTab1P2"),"By Phone or Fax Info");
		verifyExactText(prgphs.get(2),getLocalePropertyValue("lblContactUsTab1P3"),"By Phone or Fax Info");
		verifyExactText(prgphs.get(3),getLocalePropertyValue("lblContactUsTab1P4"),"By Phone or Fax Info");
		verifyExactText(prgphs.get(4),getLocalePropertyValue("lblContactUsTab1P5"),"By Phone or Fax Info");
		
		return this;
	}

	public ContactUsPage verifyByPostTabData() throws InterruptedException
	{
		reportStep("Validate By Post Tab Data", "INFO");	
		List<WebElement> tabs =  locateElements("xpath", getLocalePropertyValue("objContactUsPageTabs"));
		click(tabs.get(1),true);
		List<WebElement> post =  locateElements("xpath", getLocalePropertyValue("objContactUsPageTab2DataPgph"));
		verifyExactText(post.get(0),getLocalePropertyValue("lblContactUsTab2P1"),"By Post");
		
		return this;
	}
	
	public ContactUsPage verifyByEmailTabData() throws InterruptedException
	{
		reportStep("Validate By Email Tab Data", "INFO");	
		List<WebElement> tabs =  locateElements("xpath", getLocalePropertyValue("objContactUsPageTabs"));
		click(tabs.get(2),true);
		List<WebElement> email =  locateElements("xpath", getLocalePropertyValue("objContactUsPageTab3DataPgph"));
		verifyExactText(email.get(0),getLocalePropertyValue("lblContactUsTab3P1"),"By Email");
		verifyExactText(email.get(1),getLocalePropertyValue("lblContactUsTab3P2"),"By Email");
		
		return this;
	}
	
	public ContactUsPage verifyContactUsOnlineTabData() throws InterruptedException
	{
		reportStep("Validate Contact Us Online Tab Data", "INFO");	
		List<WebElement> tabs =  locateElements("xpath", getLocalePropertyValue("objContactUsPageTabs"));
		click(tabs.get(3),true);
		Thread.sleep(5000);
		WebElement frame =  locateElement("xpath", getLocalePropertyValue("objContactUsPageTab4Fields"));
		driver.switchTo().frame(frame);
reportStep("Validating the input, Text Area, button in Contact Us form","INFO");
		verifyExactAttribute(locateElements("xpath","//input").get(0),"name" ,getLocalePropertyValue("lblContactUsTab4Input1"));
		verifyExactAttribute(locateElements("xpath","//input").get(1),"name" ,getLocalePropertyValue("lblContactUsTab4Input2"));
		verifyExactAttribute(locateElements("xpath","//textarea").get(0),"name" ,getLocalePropertyValue("lblContactUsTab4Input3"));
		verifyExactAttribute(locateElements("xpath","//input").get(2),"name" ,getLocalePropertyValue("lblContactUsTab4Input4"));
		verifyExactAttribute(locateElements("xpath","//button").get(0),"type" ,"submit");
		driver.switchTo().defaultContent();
		return this;
	}
	
	public ContactUsPage verifyUKProfilePageOurCompanySection() throws InterruptedException
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

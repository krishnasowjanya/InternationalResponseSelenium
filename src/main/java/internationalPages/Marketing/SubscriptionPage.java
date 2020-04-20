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

public class SubscriptionPage extends ProjectMethods{
	
	public static String tid;
	public SubscriptionPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblSubscriptionPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public SubscriptionPage verifySubscriptionPageLayout() throws InterruptedException
	{
		captureFullScreen("capturing full screen of subscriptions page");
		reportStep("Validate layout of subscription page", "INFO");	
		
		
		verifyExactText(locateElement("xpath", "//h1"),getLocalePropertyValue("lblSubcptnPageHeader"),"header");
		verifyExactText(locateElement("xpath", "//h2"),getLocalePropertyValue("lblSubcptnPageSubHeader"),"Sub header");

		WebElement frame =  locateElement("xpath", "//iframe");
		driver.switchTo().frame(frame);
		System.out.println(driver.findElements(By.tagName("input")).size());
		System.out.println(driver.findElements(By.tagName("input")).get(0).getAttribute("name"));
		
		reportStep("Validate Text inputs displayed in Subscriptions page", "INFO");	
		System.out.println(driver.findElements(By.xpath("//input/preceding-sibling::span")).get(0).getText());
		
		for(int i=0;i<locateElements("xpath",getLocalePropertyValue("objSubscriptionsPageInputs")).size();i++) {
			verifyExactText(locateElements("xpath",getLocalePropertyValue("objSubscriptionsPageInputLabels")).get(i), getLocalePropertyValue("lblSubcptnPageInputlabel"+(i+1)),"Input Label");
			verifyExactAttribute(locateElements("xpath",getLocalePropertyValue("objSubscriptionsPageInputs")).get(i),"name" ,getLocalePropertyValue("lblSubcptnPageInputAttrb"+(i+1)));

		}
		
		for(int i=0;i<locateElements("xpath",getLocalePropertyValue("objSubscriptionsPageInputs1")).size();i++) {
			verifyExactText(locateElements("xpath",getLocalePropertyValue("objSubscriptionsPageInputLabels1")).get(i), getLocalePropertyValue("lblSubcptnPageInputchkboxlabel"+(i+1)),"Input Label");
			verifyExactAttribute(locateElements("xpath",getLocalePropertyValue("objSubscriptionsPageInputs1")).get(i),"name" ,getLocalePropertyValue("lblSubcptnPagechkboxAttrb"+(i+1)));

		}
		
		verifyExactAttribute(locateElements("xpath","//button").get(0),"type" ,"submit");
		return this;
	}

	public SubscriptionPage verifyUKProfilePageOurCompanySection() throws InterruptedException
	{
		reportStep("Validate Layout of Our Company Section", "INFO");
		driver.switchTo().defaultContent();
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("objMediaContactsRightLinks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblMediaContactRightlink1"), "link",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblMediaContactRightlink2"), "link",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblMediaContactRightlink3"), "link",false);
		verifyExactText(lnks.get(3), getLocalePropertyValue("lblMediaContactRightlink4"), "link",false);
		

		return this;
	}
	
	
	
}

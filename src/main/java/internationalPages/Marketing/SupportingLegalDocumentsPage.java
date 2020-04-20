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

public class SupportingLegalDocumentsPage extends ProjectMethods{
	
	public static String tid;
	public SupportingLegalDocumentsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblSupportingLegalDocPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public SupportingLegalDocumentsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Supporting Legal Documents Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Supporting Legal Documents", getLocalePropertyValue("lblSupportingLegalDocPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	
	public SupportingLegalDocumentsPage verifyPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Supporting Legal Documents Page", "INFO");

		verifyExactText(locateElement("xpath","//h1"),getLocalePropertyValue("lblSupportingLegalDocHeader"), "Page Header");
	
		reportStep("Validate 1st PDF link in the page", "INFO");
		click(locateElement("xpath", "//a[text()='PDF']"));
		
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		   driver.switchTo().window(tabs.get(1));
		Thread.sleep(3000);
		   verifyTwoStringsPartially(driver.getCurrentUrl(), "download",true);
		   
		   driver.close();
		    driver.switchTo().window(tabs.get(0));	
		

		    verifyExactText(locateElements("xpath", getLocalePropertyValue("objApplFormsPageMainTopicsLinks")).get(0),getLocalePropertyValue("objApplFormsPageMainTopicsLink1"),"Applications & Forms Section link",true );
		    verifyExactText(locateElements("xpath", getLocalePropertyValue("objApplFormsPageMainTopicsLinks")).get(1),getLocalePropertyValue("objApplFormsPageMainTopicsLink2"),"Applications & Forms Section link",true );
		    verifyExactText(locateElements("xpath", getLocalePropertyValue("objApplFormsPageMainTopicsLinks")).get(2),getLocalePropertyValue("objApplFormsPageMainTopicsLink3"),"Applications & Forms Section link",true );
		    verifyExactText(locateElements("xpath", getLocalePropertyValue("objApplFormsPageMainTopicsLinks")).get(3),getLocalePropertyValue("objApplFormsPageMainTopicsLink4"),"Applications & Forms Section link",true );
		    verifyExactText(locateElements("xpath", getLocalePropertyValue("objApplFormsPageMainTopicsLinks")).get(4),getLocalePropertyValue("objApplFormsPageMainTopicsLink5"),"Applications & Forms Section link",true );
		
		return this;
	
	}
	
	public SupportingLegalDocumentsPage verifyPageLinksCanada() throws InterruptedException
	{
	
		reportStep("Validate Links Navigation of Application & Forms Section", "INFO");
		
		for(int i=0;i<locateElements("xpath", getLocalePropertyValue("objApplFormsPageMainTopicsLinksOnly")).size();i++) {
			
			String cardLbl = locateElements("xpath",  getLocalePropertyValue("objApplFormsPageMainTopicsLinksOnly")).get(i).getText();
			click(locateElements("xpath",  getLocalePropertyValue("objApplFormsPageMainTopicsLinksOnly")).get(i),true);
			Thread.sleep(2000);
			verifyExactText(locateElement("xpath","//h1"),cardLbl.toUpperCase(),"Page Header",true );
			driver.navigate().back();
			
		}
		return this;
	
	}
}

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

public class MediaContactsPage extends ProjectMethods{
	
	public static String tid;
	public MediaContactsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblMediaContactsPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public MediaContactsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Media Contacts Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Media Contacts", getLocalePropertyValue("lblMediaContactsPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	public MediaContactsPage verifyMediaContactsPageLayout() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		reportStep("Validate Headers of Media contacts table", "INFO");	
		List<WebElement> headers =  locateElements("xpath", getLocalePropertyValue("objMediaContactsTableHeaders"));
		verifyExactText(headers.get(0),getLocalePropertyValue("lblMediacontactTableCol1"),"header");
		verifyExactText(headers.get(1),getLocalePropertyValue("lblMediacontactTableCol2"),"header");
		verifyExactText(headers.get(2),getLocalePropertyValue("lblMediacontactTableCol3"),"header");
		verifyExactText(headers.get(3),getLocalePropertyValue("lblMediacontactTableCol4"),"header");
		
		reportStep("Validate data of Media contacts table", "INFO");	
		List<WebElement> rows =  locateElements("xpath", "//table/tbody/tr");
		String namesArray= getLocalePropertyValue("lblMediaContactNames");
		String companyArray= getLocalePropertyValue("lblMediaContactCompany");
		String phoneArray= getLocalePropertyValue("lblMediaContactPhones");
		String emailArray= getLocalePropertyValue("lblMediaContactEmails");
		for(int i=0;i<rows.size();i++) {
			verifyExactText(rows.get(i).findElements(By.tagName("td")).get(0), namesArray.split(";")[i],rows.get(i).findElements(By.tagName("td")).get(0).getText());
			verifyExactText(rows.get(i).findElements(By.tagName("td")).get(1), companyArray.split(";")[i],rows.get(i).findElements(By.tagName("td")).get(0).getText());
			verifyExactText(rows.get(i).findElements(By.tagName("td")).get(2), phoneArray.split(";")[i],rows.get(i).findElements(By.tagName("td")).get(0).getText());
			verifyExactText(rows.get(i).findElements(By.tagName("td")).get(3), emailArray.split(";")[i],rows.get(i).findElements(By.tagName("td")).get(0).getText());
		}
		return this;
	}

	public MediaContactsPage verifyUKProfilePageOurCompanySection() throws InterruptedException
	{
		reportStep("Validate Layout of Our Company Section", "INFO");
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("objMediaContactsRightLinks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblMediaContactRightlink1"), "link",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblMediaContactRightlink2"), "link",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblMediaContactRightlink3"), "link",false);
		verifyExactText(lnks.get(3), getLocalePropertyValue("lblMediaContactRightlink4"), "link",false);
		

		return this;
	}
	
	
	
}

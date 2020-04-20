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

public class NonResidentTaxFormsPage extends ProjectMethods{
	
	public static String tid;
	public NonResidentTaxFormsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblNonResidentTaxFormsPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public NonResidentTaxFormsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Non-Resident Tax Forms Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Non-Resident Tax Forms", getLocalePropertyValue("lblNonResidentTaxFormsPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	
	public NonResidentTaxFormsPage verifyPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Non Resident Tax Forms Page", "INFO");

		verifyExactText(locateElement("xpath","//h1"),getLocalePropertyValue("lblNonResTaxFormsHeader"), "Page Header");
		
		if(!sExecutionLanguage.equals("French"))
		verifyElementExist("xpath","//h3[text()='"+getLocalePropertyValue("lblNonResTaxFormsStaticText")+"']", "Text - {This page is for investors who are considered non-residents for tax purposes. It outlines two types of forms:}",true);
	
		verifyExactText(locateElements("xpath","//h2").get(0),getLocalePropertyValue("lblNonResTaxFormsPageHeaderTile1").toUpperCase(), "Page Headings");
		verifyExactText(locateElements("xpath","//h2/following-sibling::p[1]").get(0),getLocalePropertyValue("lblNonResTaxFormsPageTextTile1"), "Page Headings Text");
			
		verifyExactText(locateElements("xpath","//h2").get(1),getLocalePropertyValue("lblNonResTaxFormsPageHeaderTile2").toUpperCase(), "Page Headings");
		verifyExactText(locateElements("xpath","//h2/following-sibling::p[1]").get(1),getLocalePropertyValue("lblNonResTaxFormsPageTextTile2"), "Page Headings Text");
		
		verifyExactText(locateElements("xpath","//h2").get(2),getLocalePropertyValue("lblNonResTaxFormsPageHeaderTile3").toUpperCase(), "Page Headings");
		verifyPartialText(locateElements("xpath","//h2/following-sibling::p[1]").get(2),getLocalePropertyValue("lblNonResTaxFormsPageTextTile3"));
		
		verifyExactText(locateElements("xpath","//h2").get(3),getLocalePropertyValue("lblNonResTaxFormsPageHeaderTile4").toUpperCase(), "Page Headings");
		verifyExactText(locateElements("xpath","//h2/following-sibling::p[1]").get(3),getLocalePropertyValue("lblNonResTaxFormsPageTextTile4"), "Page Headings Text");
		
		
		return this;
	
	}
	
	public NonResidentTaxFormsPage verifyPageLinksCanada() throws InterruptedException
	{
	
		reportStep("Validate Links Navigation of Application & Forms Section", "INFO");
		
		for(int i=0;i<locateElements("xpath", "//div[contains(@class,'learn-more')]//a").size();i++) {
			
			String cardLbl = locateElements("xpath", "//h2").get(i).getText();
			click(locateElements("xpath", "//div[contains(@class,'learn-more')]//a").get(i),true);
			Thread.sleep(2000);
			verifyExactText(locateElement("xpath","//h1"),cardLbl.toUpperCase().replaceAll("’", "'"),"Page Header",true );
			driver.navigate().back();
			
		}
		return this;
	
	}
}

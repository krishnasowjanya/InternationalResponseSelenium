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

public class ApplicatoinAndFormsPage extends ProjectMethods{
	
	public static String tid;
	public ApplicatoinAndFormsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblAppAndFormPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ApplicatoinAndFormsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Application Forms & Documents Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Application Forms & Documents", getLocalePropertyValue("lblApplAndFormPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	public ApplicatoinAndFormsPage verifyApplFormsDocsPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Application Forms & Doc Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		String str1 =locateElements("xpath", getLocalePropertyValue("objApplFormsDocsPageText")).get(0).getText();
		String str2 =locateElements("xpath", getLocalePropertyValue("objApplFormsDocsPageText")).get(1).getText();
		String str3 =locateElements("xpath", getLocalePropertyValue("objApplFormsDocsPageText")).get(2).getText();
		
		verifyTwoStringsExactly(str1+str2+str3, getLocalePropertyValue("lblApplFundDocPageTopText"));
	
		List <WebElement> headers=locateElements("xpath", "//h2");	
		
		reportStep("Validate Headers in Page", "INFO");
		verifyExactText(headers.get(0), getLocalePropertyValue("lblApplFundDocPageTopHeader1"), "Header");
		verifyExactText(headers.get(1), getLocalePropertyValue("lblApplFundDocPageTopHeader2"), "Header");
		verifyExactText(headers.get(2), getLocalePropertyValue("lblApplFundDocPageTopHeader3"), "Header");
		verifyExactText(headers.get(3), getLocalePropertyValue("lblApplFundDocPageTopHeader4"), "Header"); 
		verifyExactText(headers.get(4), getLocalePropertyValue("lblApplFundDocPageTopHeader5"), "Header"); 
		verifyExactText(headers.get(5), getLocalePropertyValue("lblApplFundDocPageTopHeader6"), "Header"); 
		verifyExactText(headers.get(6), getLocalePropertyValue("lblApplFundDocPageTopHeader7"), "Header"); 
		
		return this;
	
	}
	
	public ApplicatoinAndFormsPage verifyApplAndFormsPageLayoutCanada() throws InterruptedException
	{
		reportStep("Validate Layout of Application And Forms Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		
		verifyExactText( locateElement("xpath", "//h1"),getLocalePropertyValue("lblAppAndFormsPageHeader").toUpperCase(),"Page Header",true );
		verifyExactText( locateElements("xpath", "//h2").get(0),getLocalePropertyValue("lblAppAndFormsHeader1").toUpperCase(),"Investor Forms Header",true );
		verifyExactText( locateElements("xpath", "//h2").get(1),getLocalePropertyValue("lblAppAndFormsHeader2").toUpperCase(),"Applications & Forms Header",true );
	
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
	
	public ApplicatoinAndFormsPage verifyApplFormsSectionLinksCanada() throws InterruptedException
	{
		reportStep("Validate Links Navigation of Application & Forms Section", "INFO");
	
		for(int i=0;i<locateElements("xpath", getLocalePropertyValue("objApplFormsPageMainTopicsLinksOnly")).size();i++) {
			
			String cardLbl = locateElements("xpath", getLocalePropertyValue("objApplFormsPageMainTopicsLinksOnly")).get(i).getText();
			click(locateElements("xpath", getLocalePropertyValue("objApplFormsPageMainTopicsLinksOnly")).get(i),true);
			Thread.sleep(2000);
			verifyExactText(locateElement("xpath","//h1"),cardLbl.toUpperCase(),"Page Header",true );
			driver.navigate().back();
			
		}
		
		return this;
	}
}

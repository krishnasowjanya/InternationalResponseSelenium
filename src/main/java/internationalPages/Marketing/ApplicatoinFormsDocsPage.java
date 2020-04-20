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

public class ApplicatoinFormsDocsPage extends ProjectMethods{
	
	public static String tid;
	public ApplicatoinFormsDocsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblAppFormsDocsPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ApplicatoinFormsDocsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Application Forms & Documents Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Application Forms & Documents", getLocalePropertyValue("lblApplFormsDocPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	public ApplicatoinFormsDocsPage verifyBannerTitle()
	{
		verifyBannerImageAndHeading(getLocalePropertyValue("lblApplFormsPageBannerStyle"), getLocalePropertyValue("lblOurInsightsPageBannerHeading"));
		return this;
	}
	
	public ApplicatoinFormsDocsPage verifyApplFormsDocsPageLayout() throws InterruptedException
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
	
	public ApplicatoinFormsDocsPage verifyApplFormsDocsPageLinksCanada() throws InterruptedException
	{
		reportStep("Validate Layout of Application Forms & Doc Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		
		verifyExactText( locateElements("xpath", "//div[@data-fti-component='primary-landing']//h2/a").get(0),getLocalePropertyValue("lblAppFormDocPagelink1").toUpperCase(),"Page Link",true );
		verifyExactText( locateElements("xpath", "//div[@data-fti-component='primary-landing']//h2/a").get(1),getLocalePropertyValue("lblAppFormDocPagelink2").toUpperCase(),"Page Link",false );
		verifyExactText( locateElements("xpath", "//div[@data-fti-component='primary-landing']//h2/a").get(2),getLocalePropertyValue("lblAppFormDocPagelink3").toUpperCase(),"Page Link",false );
		verifyExactText( locateElements("xpath", "//div[@data-fti-component='primary-landing']//h2/a").get(3),getLocalePropertyValue("lblAppFormDocPagelink4").toUpperCase(),"Page Link",false );
		verifyExactText( locateElements("xpath", "//div[@data-fti-component='primary-landing']//h2/a").get(4),getLocalePropertyValue("lblAppFormDocPagelink5").toUpperCase(),"Page Link",false );
		
		reportStep("Validate Each Link by checking the landing page heading", "INFO");
		for(int i=0;i<locateElements("xpath", "//div[@data-fti-component='primary-landing']//h2/a").size();i++) {
			String cardLbl = locateElements("xpath", "//div[@data-fti-component='primary-landing']//h2/a").get(i).getText();
			click(locateElements("xpath", "//div[@data-fti-component='primary-landing']//h2/a").get(i));
			verifyExactText(locateElement("xpath","//h1"),cardLbl.toUpperCase(),"Page Header",true );
			driver.navigate().back();
		}
		
		return this;
	
	}
}

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

public class IntroductionToMFPage extends ProjectMethods{
	
	public static String tid;
	public IntroductionToMFPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblIntroToMFPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public IntroductionToMFPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Introduction To Mutual Funds Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Introduction To Mutual Funds", getLocalePropertyValue("lblIntroToMFPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	

	
	
	public IntroductionToMFPage verifyIntroToMFPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Introduction to Mutual Funds Page", "INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		
		verifyPageHeading(getLocalePropertyValue("lblIntorToMFPageHeader"),"Introduction To Mutual Funds");
		
		List <WebElement> headers=locateElements("xpath", "//h2");	
		
		if(headers.size()!=3)
			reportStep("One or more of the 3 Headers are not displayed for Introduction to Mutual Funds Page", "INFO",true);
		
		reportStep("Validate Headers in Page", "INFO");
		verifyExactText(headers.get(0), getLocalePropertyValue("lblIntroToMFPageSubHeader1"), "Page Heading");
		verifyExactText(headers.get(1), getLocalePropertyValue("lblIntroToMFPageSubHeader2"), "Page Heading");
		verifyExactText(headers.get(2), getLocalePropertyValue("lblIntroToMFPageSubHeader3"), "Page Heading");
		
		return this;
	}
	
	
	public IntroductionToMFPage verifyLandingPageLearnMoreLinks() throws InterruptedException
	{
		reportStep("Validate the Learn More links for {Equity Funds,Fixed Income Funds, Multi-Asset Funds, Alternative Funds} is navigating to correct pages", "INFO");
		List <WebElement> learnMoreLinks=locateElements("xpath", getLocalePropertyValue("objIntroToMFPageLearnMoreLinks"));	
		
		if(learnMoreLinks.size()!=4)
			reportStep("4 Learn More buttons{Equity Funds,Fixed Income Funds, Multi-Asset Funds, Alternative Funds} are expected in Page", "FAIL");
		
		for(int i=0;i<locateElements("xpath", getLocalePropertyValue("objIntroToMFPageLearnMoreLinks")).size();i++) {
		
		click(locateElements("xpath", getLocalePropertyValue("objIntroToMFPageLearnMoreLinks")).get(i));
		
		verifyPartialText(locateElement("xpath","//h1"),getLocalePropertyValue("lblLandingFundPageHeader"+(i+1)) , true);
		driver.navigate().back();
		}
		return this;
		}
	
}

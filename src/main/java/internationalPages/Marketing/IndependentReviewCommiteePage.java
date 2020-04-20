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

public class IndependentReviewCommiteePage extends ProjectMethods{
	
	public static String tid;
	public IndependentReviewCommiteePage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblIndenpendentReviewCommiteePageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public IndependentReviewCommiteePage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Independent Review Committee Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Independent Review Committee", getLocalePropertyValue("lblIndependentReviewCommiteePageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	public IndependentReviewCommiteePage verifyIRCPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Independent Review Commitee Page", "INFO");
		
		verifyExactText(locateElement("xpath","//h1"),getLocalePropertyValue("lblIRCHeader").toUpperCase(),"Page Header",true );
		verifyExactText(locateElement("xpath","//h2"),getLocalePropertyValue("lblIRCSubHeader").toUpperCase(),"Page Sub Header",false );
		
		verifyExactText(locateElements("xpath","//h2/following-sibling::p").get(0),getLocalePropertyValue("lblIRCStaticText1"),"Static Text 1",false );
		verifyExactText(locateElements("xpath","//h2/following-sibling::p").get(1),getLocalePropertyValue("lblIRCStaticText2"),"Static Text 2",false );
		
		verifyExactText(locateElements("xpath","//table//th").get(0),getLocalePropertyValue("lblIRCTableCol1").toUpperCase(),"Table Column 1",false );
		verifyExactText(locateElements("xpath","//table//th").get(1),getLocalePropertyValue("lblIRCTableCol2").toUpperCase(),"Table Column 2",false );
		
		if(!sExecutionLanguage.equals("French")) {
		validateFormat(locateElements("xpath","//table//tr//td[1]").get(0), "IRC Report to Securityholders \\(as of [A-Za-z]* \\d{1,2}, \\d{4}\\)", "IRC Report to Securityholders");
		validateFormat(locateElements("xpath","//table//tr//td[1]").get(1), "Franklin ETF IRC Report to Securityholders \\([A-Za-z]* \\d{1,2}, \\d{4}\\)", "Franklin ETF IRC Report to Securityholders");
		validateFormat(locateElements("xpath","//table//tr//td[1]").get(2), "IRC Report – Templeton Growth Fund, Ltd. \\([A-Za-z]* \\d{1,2}, \\d{4}\\)", "IRC Report – Templeton Growth Fund, Ltd. ");
		}
		else {
			validateFormat(locateElements("xpath","//table//tr//td[1]").get(0), "Rapport du CEI aux porteurs de titres \\(au \\d{1,2} .* \\d{4}\\)", "IRC Report to Securityholders");
			validateFormat(locateElements("xpath","//table//tr//td[1]").get(1), "Rapport du CEI aux porteurs de titres sur les FNB Franklin \\(\\d{1,2} .* \\d{4}\\)", "Franklin ETF IRC Report to Securityholders");
			validateFormat(locateElements("xpath","//table//tr//td[1]").get(2), "Rapport du CEI – Fonds de croissance Templeton, Ltée \\(\\d{1,2} .* \\d{4}\\)", "IRC Report – Templeton Growth Fund, Ltd. ");	
		}
	
		click(locateElement("xpath","//a[text()='PDF']"));
		

		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		   driver.switchTo().window(tabs.get(1));
		   Thread.sleep(3000);
		   verifyTwoStringsPartially(driver.getCurrentUrl(), "download",true);
		   
		   driver.close();
		   driver.switchTo().window(tabs.get(0));	
		    
		return this;
		}
	

	}


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

public class ReportingFundInfoPage extends ProjectMethods{
	
	public static String tid;
	public ReportingFundInfoPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblReportingFundInfoPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ReportingFundInfoPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Reporting Fund Information Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Reporting Fund Information", getLocalePropertyValue("lblRepFundInfoPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	public ReportingFundInfoPage verifyRportFundInfoPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Reporting Fund Information Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		verifyElementExist("xpath", getLocalePropertyValue("objReportingFundInfoPageSubhead"),"Related Documents");
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("objReportingFundInfoPageRelatedDocLinks"));	
		
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblRepFundInfoRelatedlink1"), "Related Documents link",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblRepFundInfoRelatedlink2"), "Related Documents link",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblRepFundInfoRelatedlink3"), "Related Documents link",false);
		
		verifyElementExist("xpath", getLocalePropertyValue("objReportingFundInfoPageRepIncmHeader"),"Reportable Income header");
		List <WebElement> tabss=locateElements("xpath",getLocalePropertyValue("objReportingFundInfoPageYearTabs"));	
		for(int i=0;i<8;i++) {
		verifyExactText(tabss.get(i),String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-i), "Year Tab",false);
		}
		
		reportStep("Click on first PDF link", "INFO");
		click(locateElement("xpath","//a[text()='PDF']"));
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		  driver.switchTo().window(tabs.get(1));
			Thread.sleep(2000);
		   verifyTwoStringsPartially(driver.getCurrentUrl(), "reportable_income",true);
		   
		   driver.close();
		    driver.switchTo().window(tabs.get(0));	
		    
		    
		return this;
	
	}
	
	
}

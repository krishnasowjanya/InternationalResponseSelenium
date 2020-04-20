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

public class StatementUserGuidesPage extends ProjectMethods{
	
	public static String tid;
	public StatementUserGuidesPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblStatementUserGuidesPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public StatementUserGuidesPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Statement User Guides Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Statement User Guides", getLocalePropertyValue("lblStmtUsrGuidesPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	public StatementUserGuidesPage verifyStatUserGuidesPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Statement User Guides Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
	
		
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objStmtUsrGuidePageTopText1")), getLocalePropertyValue("lblStmtUsrGuidePageTopText1"), "Page Top Text",false);
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objStmtUsrGuidePageTopText2")), getLocalePropertyValue("lblStmtUsrGuidePageTopText2"), "Page Top Text",false);
		
	
		List <WebElement> cols=locateElements("xpath","//table/tbody//th");	
		verifyExactText(cols.get(0), getLocalePropertyValue("lblStmtUsrGuidePageTableHead1"), "Table Header",false);
		verifyExactText(cols.get(1), getLocalePropertyValue("lblStmtUsrGuidePageTableHead2"), "Table Header",false);
		verifyExactText(cols.get(2), getLocalePropertyValue("lblStmtUsrGuidePageTableHead3"), "Table Header",false);
		
		reportStep("Click on first PDF link", "INFO");
		click(locateElement("xpath","//a[text()='PDF']"));
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		  driver.switchTo().window(tabs.get(1));
			Thread.sleep(2000);
		   verifyTwoStringsPartially(driver.getCurrentUrl(), "common",true);
		   
		   driver.close();
		    driver.switchTo().window(tabs.get(0));	
		    
		    
		return this;
	
	}
	
	public StatementUserGuidesPage verifyLiteratureSection() throws InterruptedException
	{
		reportStep("Validate Literature Section on right side of page", "INFO");
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("objStmtUsrGuidePageRightLinks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblStmtUsrGuidePageLnk1"), "link",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblStmtUsrGuidePageLnk2"), "link",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblStmtUsrGuidePageLnk3"), "link",false);
		verifyExactText(lnks.get(3), getLocalePropertyValue("lblStmtUsrGuidePageLnk4"), "link",false);
		verifyExactText(lnks.get(4), getLocalePropertyValue("lblStmtUsrGuidePageLnk5"), "link",false);

		return this;
	}
	
}

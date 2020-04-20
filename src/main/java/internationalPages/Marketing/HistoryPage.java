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

public class HistoryPage extends ProjectMethods{
	
	public static String tid;
	public HistoryPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblHistoryPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public HistoryPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full History Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("History", getLocalePropertyValue("lblHistoryPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	public HistoryPage verifyHistoryPageLayout() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
	
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objHitoryPageContent")), getLocalePropertyValue("lblHistoryPageContent"));
		List <WebElement> tabs=locateElements("xpath",getLocalePropertyValue("objWhyFTPageTabls"));	
		verifyExactText(tabs.get(0), getLocalePropertyValue("lblHistoryPageTab1"), "Tab",false);
		verifyExactText(tabs.get(1), getLocalePropertyValue("lblHistoryPageTab2"), "Tab",false);
		verifyExactText(tabs.get(2), getLocalePropertyValue("lblHistoryPageTab3"), "Tab",false);
		verifyExactText(tabs.get(3), getLocalePropertyValue("lblHistoryPageTab4"), "Tab",false);
		verifyExactText(tabs.get(4), getLocalePropertyValue("lblHistoryPageTab5"), "Tab",false);
		verifyExactText(tabs.get(5), getLocalePropertyValue("lblHistoryPageTab6"), "Tab",false);
		verifyExactText(tabs.get(6), getLocalePropertyValue("lblHistoryPageTab7"), "Tab",false);
		verifyExactText(tabs.get(7), getLocalePropertyValue("lblHistoryPageTab8"), "Tab",false);
		
		return this;
	}
	
	public HistoryPage verifyHistoryPageTabsContent() throws InterruptedException
	{
		reportStep("Validate History Page Each Tab content & Image", "INFO");
		List <WebElement> tabs=locateElements("xpath",getLocalePropertyValue("objWhyFTPageTabls"));	
		for(int i=0;i<tabs.size();i++) {
			click(tabs.get(i));
			verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageTab"+(i+1)+"Img"), "Tab "+(i+1)+ "Image Exist");
			verifyExactText(locateElement("xpath", "//div[@id='"+getLocalePropertyValue("lblHistoryPageTab"+(i+1))+"']//div/div[2]/p[1]"), getLocalePropertyValue("lblHistoryPageTab"+(i+1)+"Content"), "Tab",true);
		}
		return this;
	}
	
	
	public HistoryPage verifyOurFirmSection() throws InterruptedException
	{
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("objWhyFTPageOurFirmSectionLinks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText1"), "Our Firm Link",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText2"), "Our Firm Link",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText3"), "Our Firm Link",false);
		verifyExactText(lnks.get(3), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText4"), "Our Firm Link",false);
		verifyExactText(lnks.get(4), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText5"), "Our Firm Link",false);
		verifyExactText(lnks.get(5), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText6"), "Our Firm Link",false);
		verifyExactText(lnks.get(6), getLocalePropertyValue("lblWhyFTPageOurFirmLinkText7"), "Our Firm Link",false);
		return this;
	}
	
}

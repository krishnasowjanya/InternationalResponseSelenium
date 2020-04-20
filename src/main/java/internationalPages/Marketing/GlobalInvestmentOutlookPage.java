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

public class GlobalInvestmentOutlookPage extends ProjectMethods{
	
	public static String tid;
	public GlobalInvestmentOutlookPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblGIOPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public GlobalInvestmentOutlookPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Global Investment Outlook Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Global Investment Outlook", getLocalePropertyValue("lblGlobalInvestmentOutlookPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public GlobalInvestmentOutlookPage verifyInsightsGIOBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/common/campaigns/gio/april-18/44951_Mid-year-investment_1170x186.jpg", getLocalePropertyValue("lblGlobalInvestmentOutlookPageBannerHeading"));
		return this;
	}
	
	
	public GlobalInvestmentOutlookPage verifyInsightsGIOSPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Insights Global Investment Outlook Page", "INFO");
		List <WebElement> headers=locateElements("xpath", "//h2");	
		
		if(headers.size()!=4)
			reportStep("One or more of the 4 Headers are not displayed for Global Investment Outlook Page", "INFO",true);
		
		reportStep("Validate Headers in Page", "INFO");
		verifyExactText(headers.get(0), getLocalePropertyValue("lblGIOPageHeader1"), "OUTLOOK CONTRIBUTORS");
		verifyExactText(headers.get(1), getLocalePropertyValue("lblGIOPageHeader2").toUpperCase(),  "Resources".toUpperCase());
		verifyExactText(headers.get(2), getLocalePropertyValue("lblGIOPageHeader3"), "PREVIOUS EDITION");
		verifyExactText(headers.get(3), getLocalePropertyValue("lblGIOPageHeader4").toUpperCase(),  "Resources".toUpperCase()); //Same heading coming twice in preview env
		
		return this;
	}
	
	public GlobalInvestmentOutlookPage verifyCaveats() throws InterruptedException
	{
		reportStep("Validate Caveats of Insights Global Investment Outlook Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objMrktPageCaveatsKeyRisks"), "What Are the Risks? Header");
		verifyElementExist("xpath", getLocalePropertyValue("objMrktPageCaveatsLegalInfo"), "Important Legal Information Header");
		
	return this;	
	}
	
	
	public GlobalInvestmentOutlookPage verifyContent() throws InterruptedException
	{
		reportStep("Validate Content of Insights Global Investment Outlook Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objGIOPageContentHeader1"), "Attractive Growth Prospects Ahead \u2013 Will Trade Disputes Create a Headwind? Header");
		verifyElementExist("xpath", getLocalePropertyValue("objGIOPageContentHeader1SubText"), "Global GDP Growth Estimates (% Change Year Over Year)");
		verifyElementExist("xpath", getLocalePropertyValue("objGIOPageContentHeader2"), "US, China And Beyond: The Impact Of Trade On Global Growth");
		verifyElementExist("xpath", getLocalePropertyValue("objGIOPageContentHeader2SubText"), "Date Recorded: 12 April 2018");
		
	return this;	
	}
	
	public GlobalInvestmentOutlookPage verifyResourcesContent() throws InterruptedException
	{
		reportStep("Validate Resources Content of Insights Global Investment Outlook Page", "INFO");
	
		List<WebElement> resourceList =  locateElements("xpath", getLocalePropertyValue("objGIOPageResourcesList"));
		if(resourceList.size()!=2)
			reportStep("The Expected resource count is not matching in right pane", "FAIL",true);
		
		String urlsArray[] = {"Global-Investment-Outlook-Roar-of-Markets-Global-Growth-Q2-2018.pdf","global-investment-outlook-talking-trade-tensions-inflation-and-volatility"};
		int i=0;
		for(WebElement rEle : resourceList) {
			String resTitle =  rEle.findElement(By.tagName("p")).getText();
			reportStep("Clicking the Resource with Title :"+resTitle, "INFO");
			click(rEle.findElement(By.tagName("a")),true);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			
			   verifyTwoStringsPartially(driver.getCurrentUrl(), urlsArray[i],true);
			   i++;
			   driver.close();
			   driver.switchTo().window(tabs.get(0));	
		}	
		
	return this;	
	}
	
	public GlobalInvestmentOutlookPage verifyOutlookContributorsContent() throws InterruptedException
	{
		reportStep("Validate Outlook Contributors Content of Insights Global Investment Outlook Page", "INFO");
	
		List<WebElement> mngrsList =  locateElements("xpath", getLocalePropertyValue("objGIOPageManagersList"));
		List<WebElement> mngrsImgList =  locateElements("xpath", getLocalePropertyValue("objGIOPageManagersImages"));
		if((mngrsList.size()!=mngrsImgList.size()) || (mngrsList.size()!=5))
			reportStep("Either the Number of Managers displyed or Images are not displayed as expected", "FAIL",true);
		
		String mngrsArray[] = {"Stephen Dover, CFA;Head of Equities",
				"Michael Hasenstab, Ph.D.;Chief Investment Officer,Templeton Global Macro",
				"Christopher Molumphy, CFA;Chief Investment Officer,Franklin Templeton Fixed Income Group",
				"Ed Perks, CFA;Chief Investment Officer,Franklin Templeton Multi-Asset Solutions",
				"Manraj Sekhon;Chief Investment Officer,Franklin Templeton Emerging Markets Equity"
		};
		int i=0;
		mouseOver(mngrsList.get(0));
		for(WebElement ele:mngrsList) {
		verifyTwoStringsExactly(ele.findElement(By.tagName("strong")).getText()+";"+ele.findElement(By.tagName("p")).getText().replaceAll("\n", ""), mngrsArray[i],true);
		i++;
		}
		return this;
	}
	
}

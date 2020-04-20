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

public class OurFirmPage extends ProjectMethods{
	
	public static String tid;
	public OurFirmPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblOurFirmPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public OurFirmPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Our Firm Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Our Firm", getLocalePropertyValue("lblOurFirmPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public OurFirmPage verifyOurFirmBannerTitle()
	{
		verifyBannerImageAndHeading("/content-international/images/common/billboards/BB-ourfirm.jpg", getLocalePropertyValue("lblOurFirmPageBannerHeading"));
		return this;
	}
	
	
	public OurFirmPage verifyOurFirmPageLayout() throws InterruptedException
	{
		List <WebElement> headings=locateElements("xpath", getLocalePropertyValue("objOFSectionsNames"));	
			if(headings.size()==7) {
		
			verifyExactText(headings.get(0), getLocalePropertyValue("lblOurFirmPageHeadings1"), "Our Firm Component",false);
			verifyExactText(headings.get(1), getLocalePropertyValue("lblOurFirmPageHeadings2"), "Our Firm Component",false);
			verifyExactText(headings.get(2), getLocalePropertyValue("lblOurFirmPageHeadings3"), "Our Firm Component",false);
			verifyExactText(headings.get(3), getLocalePropertyValue("lblOurFirmPageHeadings4"), "Our Firm Component",false);
			verifyExactText(headings.get(4), getLocalePropertyValue("lblOurFirmPageHeadings5"), "Our Firm Component",false);
			verifyExactText(headings.get(5), getLocalePropertyValue("lblOurFirmPageHeadings6"), "Our Firm Component",false);
			verifyExactText(headings.get(6), getLocalePropertyValue("lblOurFirmPageHeadings7"), "Our Firm Component",false);
			}else
				reportStep("One or More Our Firm component may not displayed", "FAIL");
	
		
		return this;
	}
	
	public OurFirmPage verifyOurFirmComponentLinksNavigation() throws InterruptedException
	{
	
			if(locateElements("xpath", getLocalePropertyValue("objOFSectionsNames")).size()==7) {
				click(locateElements("xpath", getLocalePropertyValue("objOFSectionsNames")).get(0));
				verifyBannerImageAndHeading("/content-international/images/common/billboards/BB-ourfirm.jpg", getLocalePropertyValue("lblOurFirmPageLinksLandingPageHeader1"));
				click(locateElement("xpath",getLocalePropertyValue("objBreadcrumbOurFirmLink")));
				
				click(locateElements("xpath", getLocalePropertyValue("objOFSectionsNames")).get(1));
				verifyExactText(locateElement("xpath","//h1"), getLocalePropertyValue("lblOurFirmPageLinksLandingPageHeader2"),"HISTORY header",true);
				click(locateElement("xpath",getLocalePropertyValue("objBreadcrumbOurFirmLink")));
				
				click(locateElements("xpath", getLocalePropertyValue("objOFSectionsNames")).get(2));
				verifyExactText(locateElement("xpath","//h1"), getLocalePropertyValue("lblOurFirmPageLinksLandingPageHeader3"),"RISK MANAGEMENT header",true);
				click(locateElement("xpath",getLocalePropertyValue("objBreadcrumbOurFirmLink")));
				
				click(locateElements("xpath", getLocalePropertyValue("objOFSectionsNames")).get(3));
				verifyBannerImageAndHeading("/content-international/images/en-gb/responsible-investing/masthead.jpg", getLocalePropertyValue("lblOurFirmPageLinksLandingPageHeader4"));
				click(locateElement("xpath",getLocalePropertyValue("objBreadcrumbOurFirmLink")));
			
				click(locateElements("xpath", getLocalePropertyValue("objOFSectionsNames")).get(4));
				verifyExactText(locateElement("xpath","//h1"), getLocalePropertyValue("lblOurFirmPageLinksLandingPageHeader5"),"DIVERSITY AT WORK header",true);
				click(locateElement("xpath",getLocalePropertyValue("objBreadcrumbOurFirmLink")));
				
				click(locateElements("xpath", getLocalePropertyValue("objOFSectionsNames")).get(5));
				verifyExactText(locateElement("xpath","//h1"), getLocalePropertyValue("lblOurFirmPageLinksLandingPageHeader6"),"TREATING CUSTOMERS FAIRLY header",true);
				click(locateElement("xpath",getLocalePropertyValue("objBreadcrumbOurFirmLink")));
			
				click(locateElements("xpath", getLocalePropertyValue("objOFSectionsNames")).get(6));
				verifyExactText(locateElement("xpath","//h1"), getLocalePropertyValue("lblOurFirmPageLinksLandingPageHeader7"),"REGULATORY INFORMATION header",true);
				click(locateElement("xpath",getLocalePropertyValue("objBreadcrumbOurFirmLink")));
			
		
			}else
				reportStep("One or More Our Firm component may not displayed", "FAIL");
	
		
		return this;
	}
}

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

import internationalPages.Products.UKHomePage;
import net.bytebuddy.implementation.bytecode.constant.LongConstant;
import wdMethods.ProjectMethods;
import utils.ExcelDataProvider;
import utils.dateFunctions;

public class AlternativesPage extends ProjectMethods{
	
	public static String tid;
	public AlternativesPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblAlternativesPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public AlternativesPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Alternatives Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Alternatives", getLocalePropertyValue("lblAlternativesPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	public AlternativesPage verifyAlternativesBannerTitle()
	{
		verifyBannerImageAndHeading(getLocalePropertyValue("lblInsightsPageBannerStyle"), getLocalePropertyValue("lblAlternativesPageBannerHeading"));
		return this;
	}
	
	
	public AlternativesPage verifyAlternativesPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Alternatives Page", "INFO");
		List <WebElement> dropdowns=locateElements("xpath", getLocalePropertyValue("objBlogsPageDropdowns"));	
		
		if(dropdowns.size()!=2)
			reportStep("One or more of the 2 Dropdowns are not displayed for Alternatives Page", "INFO",true);
		
	
		
		click(dropdowns.get(0),true);
		Select regionDropdown  = new Select(dropdowns.get(0));
		for(WebElement ele:regionDropdown.getOptions()) {
			if(!ele.getText().matches(getLocalePropertyValue("lblRegionDropdownValuesAlternativesPage")))
				reportStep(ele.getText()+" value is not present in Region dropdown as expected", "FAIL",true);
			
		}
		
		reportStep("All the Expected Values {"+getLocalePropertyValue("lblRegionDropdownValuesAlternativesPage")+"} are  displayed for Region Filter","PASS");
		
		
		click(dropdowns.get(1),true);
		Select contentType  = new Select(dropdowns.get(1));
		for(WebElement ele:contentType.getOptions()) {
			if(!ele.getText().matches(getLocalePropertyValue("lblContentDropdownValuesAlternativesPage")))
				reportStep(ele.getText()+" value is not present in Content Type dropdown as expected", "FAIL",true);
			
		}
		
		reportStep("All the Expected Values {"+getLocalePropertyValue("lblContentDropdownValuesAlternativesPage")+"} are  displayed for Content Type Filter","PASS");
	//	verifyElementExist("xpath", getLocalePropertyValue("objBlogsPageLoadMoreBtn"), "Load More Button");
		
		return this;
	}
	
	public UKHomePage clickBenLogo() throws FileNotFoundException, IOException, InterruptedException
	{
		
		click(locateElement("xpath",localeProp.getProperty("objBenlogo")));
		
		return new UKHomePage(driver,test);
	}
}

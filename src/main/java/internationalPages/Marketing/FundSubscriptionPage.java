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

public class FundSubscriptionPage extends ProjectMethods{
	
	public static String tid;
	public FundSubscriptionPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblFundSubscriptionPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public FundSubscriptionPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Fund Subscription Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Fund Subscription", getLocalePropertyValue("lblFundSubscriptionPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	public FundSubscriptionPage verifyChooseSubscriptionTab() throws InterruptedException
	{
		reportStep("Validate Layout of Fund Subscription Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
	
		List <WebElement> tabs=locateElements("xpath", getLocalePropertyValue("objFundSubsPageTopTabs"));
		System.out.println(tabs.get(0).getAttribute("class"));
		System.out.println(tabs.get(1).getAttribute("class"));
		System.out.println(tabs.get(2).getAttribute("class"));
		if(tabs.get(0).getAttribute("class").contains("active") && (!tabs.get(1).getAttribute("class").contains("active")) && (!tabs.get(2).getAttribute("class").contains("active")))
			reportStep("Choose Subscription tab is active & other 2 tabs inactive as expected", "PASS");
		else
			reportStep("Choose Subscription tab is not active or other 2 tabs inactive as expected", "FAIL");
		
		if(locateElement("xpath", getLocalePropertyValue("objFundSubsPageHeader1")).isDisplayed())
			reportStep("Choose Subscriptions header is displayed as expected", "PASS");
		else
			reportStep("Choose Subscriptions header is not displayed as expected", "FAIL");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFundSubsPageSubHeader1"),"Monthly E-bulletin header");
		verifyElementExist("xpath", getLocalePropertyValue("objFundSubsPageSubHeader2"),"Templeton Emerging Markets Investment Trust Plc header");
		
		List <WebElement> chkbox=locateElements("xpath", getLocalePropertyValue("objFundSubsPageChooseSubsChkbox"));	
		if(chkbox.size()!=2)
			reportStep("One of the Checkbox (E-bulletin or Templeton Emerging Markets Investment Trust Plc header) is not displayed", "FAIL");
		
		click(chkbox.get(0));
		click(chkbox.get(1),true);
		
		reportStep("click on Select subscription for first fund", "INFO");
		click(locateElement("xpath", getLocalePropertyValue("objFundSubsPageChooseSubsEmergMrktSelectSubBtn")));	
		validateSelectValuefromPopup("Templeton Emerging Markets Investment Trust Plc");
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objFundSubsPageChooseSubsEmergMrktSelectSubBtn")).findElement(By.tagName("span")), "1","badge number");
		
		click(locateElement("xpath", getLocalePropertyValue("objFundSubsPageChooseSubsEmergSicavSubBtn")));	
		validateSelectValuefromPopup("Franklin Templeton Investment Funds (SICAV)");
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objFundSubsPageChooseSubsEmergSicavSubBtn")).findElement(By.tagName("span")), "1","badge number");
		
		verifyExactText(locateElements("xpath",getLocalePropertyValue("objFundSubsPageButton")).get(1), "CLEAR","Clear Button");
		verifyExactText(locateElements("xpath",getLocalePropertyValue("objFundSubsPageButton")).get(0), "CONTINUE","CONTINUE Button");
		
		reportStep("click on continue button", "INFO");
		click(locateElements("xpath",getLocalePropertyValue("objFundSubsPageButton")).get(0),true);
		return this;
	
	}
	
	public FundSubscriptionPage verifyDeliveryInfoTab() throws InterruptedException
	{
		reportStep("Validate Layout of Delivery Information Page", "INFO");
		
		List <WebElement> tabs=locateElements("xpath", getLocalePropertyValue("objFundSubsPageTopTabs"));
		System.out.println(tabs.get(0).getAttribute("class"));
		System.out.println(tabs.get(1).getAttribute("class"));
		System.out.println(tabs.get(2).getAttribute("class"));
		if(tabs.get(1).getAttribute("class").contains("active") && (!tabs.get(0).getAttribute("class").contains("active")) && (!tabs.get(2).getAttribute("class").contains("active")))
			reportStep("Delivery Information tab is active & other 2 tabs inactive as expected", "PASS");
		else
			reportStep("Delivery Information tab is not active or other 2 tabs inactive as expected", "FAIL");
		
		if(locateElement("xpath", getLocalePropertyValue("objFundSubsPageHeader2")).isDisplayed())
			reportStep("Delivery Information header is displayed as expected", "PASS");
		else
			reportStep("Delivery Information header is not displayed as expected", "FAIL");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFundSubsPageDelInfoSubHeader1"),"Personal Information");
		
		reportStep("Validate values in Title dropdown","INFO");
		for(WebElement ele: new Select(locateElement("xpath", getLocalePropertyValue("objBlogsPageDropdowns"))).getOptions()) {
			if(ele.getText().matches("Choose Option|Mr|Mrs|Miss|Ms"))
				reportStep(ele.getText()+" option is present in dropdown","PASS");	
			else
				reportStep(ele.getText()+" option is present in dropdown","FAIL");	
		}
		
		List <WebElement> inputLbls=locateElements("xpath", getLocalePropertyValue("objFundSubsPageInputLabels"));
		
		verifyExactText(inputLbls.get(0), getLocalePropertyValue("lblFundSubsPageInputLbl1"),"Input box label");
		verifyExactText(inputLbls.get(1), getLocalePropertyValue("lblFundSubsPageInputLbl2"),"Input box label");
		verifyExactText(inputLbls.get(2), getLocalePropertyValue("lblFundSubsPageInputLbl3"),"Input box label");
		verifyExactText(inputLbls.get(3), getLocalePropertyValue("lblFundSubsPageInputLbl4"),"Input box label");
		verifyExactText(inputLbls.get(4), getLocalePropertyValue("lblFundSubsPageInputLbl5"),"Input box label");
		verifyExactText(inputLbls.get(5), getLocalePropertyValue("lblFundSubsPageInputLbl6"),"Input box label");
		verifyExactText(inputLbls.get(6), getLocalePropertyValue("lblFundSubsPageInputLbl7"),"Input box label");
		verifyExactText(inputLbls.get(7), getLocalePropertyValue("lblFundSubsPageInputLbl8"),"Input box label");
		verifyExactText(inputLbls.get(8), getLocalePropertyValue("lblFundSubsPageInputLbl9"),"Input box label");
		verifyExactText(inputLbls.get(9), getLocalePropertyValue("lblFundSubsPageInputLbl10"),"Input box label");
		
		verifyExactText(locateElements("xpath", getLocalePropertyValue("objFundSubsPagetextLabel")).get(0), getLocalePropertyValue("lblFundSubsPageInputLbl11"),"Text box label");
		
		verifyExactText(locateElements("xpath",getLocalePropertyValue("objFundSubsPageButton")).get(1), "BACK","back Button");
		verifyExactText(locateElements("xpath",getLocalePropertyValue("objFundSubsPageButton")).get(0), "CONTINUE","CONTINUE Button");
		
		FillValuesInForm();
		
		return this;
	}
	
	
	public void validateSelectValuefromPopup(String header) throws InterruptedException {
	
		Thread.sleep(5000);
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objFundSubsPagePopupHeader")), header,"popup header");
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objFundSubsPagePopupHeader")), header,"popup header");
		verifyExactText(locateElements("xpath",getLocalePropertyValue("objFundSubsPagePopupButton")).get(0), "CLOSE","close button");
		verifyExactText(locateElements("xpath",getLocalePropertyValue("objFundSubsPagePopupButton")).get(1), "CONTINUE","Contiue button");
		click(locateElements("xpath",getLocalePropertyValue("objFundSubsPagePopupCheckbox")).get(0),true);
		click(locateElements("xpath",getLocalePropertyValue("objFundSubsPagePopupButton")).get(1),true);
	}
	
	
	public void FillValuesInForm() throws InterruptedException {
		
		Thread.sleep(5000);
		new Select(locateElement("xpath", getLocalePropertyValue("objBlogsPageDropdowns"))).selectByIndex(1);
		
		List <WebElement> inputs=locateElements("xpath", "//ft-form-input-validation//input");
		type(inputs.get(0),"sample");
		type(inputs.get(1),"sample");
		type(inputs.get(2),"sample");
		type(inputs.get(3),"sample");
		type(inputs.get(4),"sample");
		type(inputs.get(5),"sample");
		type(inputs.get(6),"123456");
		type(inputs.get(7),"1234567890");
		type(inputs.get(8),"abc@xyz.com");
		type(inputs.get(9),"122112");
		
		reportStep("click on continue button", "INFO");
		click(locateElements("xpath",getLocalePropertyValue("objFundSubsPageButton")).get(0),true);
		
		Thread.sleep(5000);
	}
	
	public FundSubscriptionPage verifyConfirmationTab() throws InterruptedException
	{
		reportStep("Validate Layout of Confirmation Page", "INFO");
		
		List <WebElement> tabs=locateElements("xpath", getLocalePropertyValue("objFundSubsPageTopTabs"));
		System.out.println(tabs.get(0).getAttribute("class"));
		System.out.println(tabs.get(1).getAttribute("class"));
		System.out.println(tabs.get(2).getAttribute("class"));
		if(tabs.get(2).getAttribute("class").contains("active") && (!tabs.get(0).getAttribute("class").contains("active")) && (!tabs.get(1).getAttribute("class").contains("active")))
			reportStep("Confirmation tab is active & other 2 tabs inactive as expected", "PASS");
		else
			reportStep("Confirmation tab is not active or other 2 tabs inactive as expected", "FAIL");
		
		if(locateElement("xpath", getLocalePropertyValue("objFundSubsPageHeader3")).isDisplayed())
			reportStep("Confirmation header is displayed as expected", "PASS");
		else
			reportStep("Confirmation header is not displayed as expected", "FAIL");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFundSubsPageConfirmationTabSubheader"),"Confirmation tab subheader");
		
		
	return this;
	}
}

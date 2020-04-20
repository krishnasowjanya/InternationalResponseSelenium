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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class PFICAnnualInfosPage extends ProjectMethods{
	
	public static String tid;
	public PFICAnnualInfosPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblPFICPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public PFICAnnualInfosPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full PFIC Annual Information Statements Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("PFIC Annual Information Statements", getLocalePropertyValue("lblPFICPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	public PFICAnnualInfosPage verifyPageLayoutCanada() throws InterruptedException
	{
		reportStep("Validate Layout of PFIC Annual Information Statements Page", "INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link at Top Right Corner of Page");
		
		verifyExactText(locateElement("xpath","//h1"),getLocalePropertyValue("lblPFICPageHeader").toUpperCase(),"Page Header",true );
		
		verifyExactText(locateElement("xpath","//h2"),getLocalePropertyValue("lblPFICPageSubHeader").toUpperCase(),"Page Sub Header",true );
		
		verifyElementExist("xpath",getLocalePropertyValue("objPFICPageText1"),"Text - 'Are you considered a U.S. person?'");
		
		verifyElementExist("xpath", getLocalePropertyValue("objPFICPageText2"),"Text - 'What we’re doing to help'");
		
		verifyExactText(locateElements("xpath",getLocalePropertyValue("objPFICPageStaticText")).get(0), getLocalePropertyValue("lblPFICText1"), "Are u Considered US Person?: - Text - Part 1");
		verifyPartialText(locateElements("xpath",getLocalePropertyValue("objPFICPageStaticText")).get(1), getLocalePropertyValue("lblPFICText2"));
		verifyPartialText(locateElements("xpath",getLocalePropertyValue("objPFICPageStaticText")).get(2), getLocalePropertyValue("lblPFICText3"));
		return this;
	}
	
	public PFICAnnualInfosPage verifyTabs() throws InterruptedException
	{
		reportStep("Validate 2 Tabs{Filters,Edit Columns} displayed in Fund Documents Page", "INFO");
		
		List <WebElement> tabs=locateElements("xpath", getLocalePropertyValue("objFundDocPageTabs"));	
		
		if(tabs.size()!=2)
			reportStep("One or more of the 2 tabs are not displayed for Fund Documents Page", "INFO",true);
		
		verifyExactText(tabs.get(0),getLocalePropertyValue("lblFundDocPageTab1"),"tabs");
		verifyExactText(tabs.get(1),getLocalePropertyValue("lblFundDocPageTab2"),"tabs");
		return this;
	}
	
	public PFICAnnualInfosPage verifyEditColumnsTabCanada() throws InterruptedException
	{
		
		reportStep("Validate user is able to select multiple PDFs on Enabling 'Select Multiple PDFs' Toggle", "INFO");
		List <WebElement> selectMultiPDF=locateElements("xpath", getLocalePropertyValue("objFundDocPageEditColSelectMultPDF"));
		reportStep("Click on select 'Multiple PDFs' to enable it", "INFO");
		click(selectMultiPDF.get(0), true);
		
		if(selectMultiPDF.get(0).getAttribute("class").contains("active")) {
			
			reportStep("Selecting 3 PDFs in the Fund Document Table", "INFO");
			click(locateElement("xpath","//table[contains(@label.bind,'Non-Corporate Class')]//tr[not(@class='au-target aurelia-hide')][1]//td[2]"));
			click(locateElement("xpath","//table[contains(@label.bind,'Non-Corporate Class')]//tr[not(@class='au-target aurelia-hide')][2]//td[2]"));
			click(locateElement("xpath","//table[contains(@label.bind,'Non-Corporate Class')]//tr[not(@class='au-target aurelia-hide')][3]//td[2]"),true);
			
			reportStep("Validating 'Selected PDFs' component value is reflecting number of PDFs selected", "INFO");
			verifyExactText(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFCount")), "3");
			
			reportStep("Validating Edit Button Functionality by Removing one selected fund from Fund Document Table", "INFO");
			reportStep("Click on Edit button beside 'Selected PDFs' Component", "INFO");
			click(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFEditBtn")),true);
			
			WebDriverWait wait=new WebDriverWait(driver, 200);	
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objFundDocPageEditColPopupHeader"))));
			
			verifyExactText(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColPopupHeader")), getLocalePropertyValue("lblFundDocPageEditColPopupEditHeader"),"popup header",true);
			reportStep("Remove one of the Seleted PDF", "INFO");
			click(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFEditBtnRemove")),true);
			
			click(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFEditBtnClose")),true);
			
			reportStep("Validating 'Selected PDFs' component value is Updated to 2 after removing 1 PDF from EDIT popup", "INFO");
			verifyExactText(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFCount")), "2");
			
			reportStep("Validating Email Button Popup Components", "INFO");
			reportStep("Click on Email button", "INFO");
			click(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColEmailButton")),true);
			
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objFundDocPageEditColPopupHeader"))));
			
			verifyExactText(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColPopupHeader")), getLocalePropertyValue("lblFundDocPageEditColPopupEmailHeader"),"popup header",true);
			verifyElementExist("xpath", getLocalePropertyValue("objFundDocPageEditColPopupContinue"),"continue button");
			//click(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFEditBtnClose")),true);
			
			 reportStep("Click On Continue button in popup","INFO");
			 click(locateElement("xpath",getLocalePropertyValue("objPFICPageContinueButtonPopup")));
			 
			 reportStep("Enter Values in Email details popup","INFO");
			 type(locateElements("xpath",getLocalePropertyValue("objPFICPageEmailInput1")).get(0),"test By Name");
			 type(locateElements("xpath",getLocalePropertyValue("objPFICPageEmailInput2")).get(0),"test Receiver Name");
			 type(locateElements("xpath",getLocalePropertyValue("objPFICPageEmailInput3")).get(1),"test@sample.com");
		
			 reportStep("Click On Send button","INFO");
			 click(locateElement("xpath",getLocalePropertyValue("objPFICPageEmailSend")));
			 
			 verifyElementExist("xpath",getLocalePropertyValue("objPFICPageEmailSent"),"Email Sent Successfully Message", true);
		
			 click(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFEditBtnClose")));
			 
			 Thread.sleep(2000);
			
			reportStep("Validating Download Button Popup Components", "INFO");
			reportStep("Click on Download button", "INFO");
			click(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColDownloadButton")),true);
			
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objFundDocPageEditColPopupHeader"))));
			
			verifyExactText(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColPopupHeader")), getLocalePropertyValue("lblFundDocPageEditColPopupDownloadHeader"),"popup header",true);
			verifyElementExist("xpath", getLocalePropertyValue("objFundDocPageEditColPopupDownload"),"Download button");
			click(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFEditBtnClose")),true);
		
		}
		else
			reportStep("Select Multiple PDF option was not enabled", "FAIL");
		
		reportStep("Click on select 'Multiple PDFs' to Disable it", "INFO");
		click(selectMultiPDF.get(1), true);
		
		reportStep("Click on 1st PDF link", "INFO");
		click(locateElement("xpath","//table[contains(@label.bind,'Non-Corporate Class')]//tr[not(@class='au-target aurelia-hide')][1]//td[2]"));
		
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		   driver.switchTo().window(tabs.get(1));
		   Thread.sleep(3000);
		   verifyTwoStringsPartially(driver.getCurrentUrl(), "download",true);
		   
		   driver.close();
		    driver.switchTo().window(tabs.get(0));	
		
		
		
		return this;
	}
	
	
	public PFICAnnualInfosPage verifyFundDocumentsPageLayoutCanada() throws InterruptedException
	{
			
		reportStep("Validating the filters displayed on Top of 'Filters' Tab", "INFO");
		List <WebElement> filter=locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilters"));	
		if(filter.get(0).findElements(By.tagName("input")).size()==1)
			reportStep("'Filter by fund Name or Keyword' text box is displayed as expected", "PASS",true);
		else
			reportStep("'Filter by fund Name or Keyword' text box is not displayed as expected", "FAIL",true);
		
		
		if(locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown")).size()==1)
			reportStep(1+" Dropdowns for {Asset Class} filters are displayed as expected", "PASS");
		else
			reportStep(1+" Dropdowns for {Asset Class} filters are not displayed", "FAIL");

			reportStep("Validate labels of {Filter by fund name or keyword, Asset Class} filters", "INFO");
			verifyExactText(filter.get(0).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel1"),"filter label");	
			verifyExactText(filter.get(1).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel2"),"filter label");
	
		
		List <WebElement> tableHeaders=locateElements("xpath", "//table[contains(@label.bind,'Non-Corporate Class')]//tr[2]");	
		moveContextToElement(locateElement("xpath", "//table[contains(@label.bind,'Non-Corporate Class')]//tr[2]"));
		if(tableHeaders.size()>0)
			reportStep("Fund Documents Table is displayed ", "PASS",true);
		else
			reportStep("Fund Documents Table is not displayed ", "FAIL",true);
		
		reportStep("Validate labels of Table Headers{Fund,PFIC Annual Information Statements} in Filter Tab", "INFO");
		for(int i=0;i<tableHeaders.get(0).findElements(By.tagName("th")).size();i++) {
		verifyExactText(tableHeaders.get(0).findElements(By.tagName("th")).get(i),getLocalePropertyValue("lblPFICPageTableHeader"+(i+1)),"Tabel Header");
		}
		
		return this;
	}
	
	
	
	public PFICAnnualInfosPage verifyDropdownValuesCanada() throws InterruptedException
	{
		reportStep("Validate Values in filters{Asset Class,Investment Vehicle,Share Class, Currency} Dropdown", "INFO");
		
		List<WebElement> filters =locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown"));
		
		//Not Matching the list given in test case
		reportStep("Validate Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp1Vals")+"} are present in Asset Class Filter", "INFO");
		click(filters.get(0),true);
		if(filters.get(0).findElements(By.tagName("option")).size()!=7)
			reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp1Vals")+"} are not displayed for Asset Class Filter","FAIL");		
			
		boolean vBool=true;
			for(WebElement optn:filters.get(0).findElements(By.tagName("option"))) {
			if(!optn.getText().matches(getLocalePropertyValue("lblFundDocPageDrp1Vals"))) {
				vBool=false;
				reportStep(optn.getText()+" value present for Asset Class Filter is not matching Expected values {"+getLocalePropertyValue("lblFundDocPageDrp1Vals")+"}","FAIL");	
				}	
			}
		
			if(vBool)
				reportStep("All the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp1Vals")+"} are  displayed for Asset Class Filter","PASS");		
		
			
		return this;
	}
	
	public PFICAnnualInfosPage verifySaveViewResetButton() throws InterruptedException
	{
		
		for(WebElement drp:locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown"))) {
			if(drp.findElements(By.tagName("option")).size()>1) {
			click(drp);
			click(drp.findElements(By.tagName("option")).get(1));
				}
		}
		reportStep("Validate Reset, Save View Button are displayed at top right corner of page beside Tabs", "INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objFundDocPageResetButton"),"Reset Button");
		verifyElementExist("xpath", getLocalePropertyValue("objFundDocPageSaveViewButton"),"Save View Button",true);
		return this;
	}
	
	public PFICAnnualInfosPage verifyEditColumnsFunctionality() throws InterruptedException
	{
		reportStep("Click on Edit Columns Tab", "INFO");
		List <WebElement> tabs1=locateElements("xpath", getLocalePropertyValue("objFundDocPageTabs"));	
		click(tabs1.get(1));
		
		List <WebElement> btnlbls=locateElements("xpath", getLocalePropertyValue("objFundDocPageEditColButtons"));
		
		reportStep("Validate First Edit Column button is Unchecked on Click", "INFO");
		click(btnlbls.get(0));
		//.findElement(By.tagName("input"))
		if(!btnlbls.get(0).getAttribute("class").contains("active"))
			reportStep("The first Edit column is unchecked as expected", "PASS",true);
		else
			reportStep("The first Edit column is not unchecked as expected", "FAIL",true);
		
		reportStep("Click on save view button", "INFO");
		click(locateElement("xpath", getLocalePropertyValue("objFundDocPageSaveViewButton")));
		
		Thread.sleep(2000);
		
WebElement elem =null;
		
		for(WebElement ele:locateElements("xpath",localeProp.getProperty("objFundDocPageEditColPopupHeader"))){
		if(ele.isDisplayed())
		{
			elem = ele;
			break;
			}
		}
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(elem));
		
		verifyExactText(elem, getLocalePropertyValue("lblFundDocPageEditColPopupSaveViewHeader"),"popup header",true);
		verifyElementExist("xpath", getLocalePropertyValue("objFundDocPageEditColSaveviewpopuptext"),"Popup text");
		reportStep("The following URL is displayed in Save view text box :"+locateElement("xpath","//p/input[@class='form-control']").getAttribute("value"),"INFO");
		
		String url =locateElement("xpath","//p/input[@class='form-control']").getAttribute("value");
	//	click(locateElements("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFEditBtnClose")).get(0),true);
//	
//		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,"t");
//	
//		driver.findElement(By.xpath("//a[text()='PDF']")).sendKeys(selectLinkOpeninNewTab);
		
		//workaround to open new tab
		
		((JavascriptExecutor)driver).executeScript("window.open()");
		
		
		Thread.sleep(3000);
		
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		   driver.switchTo().window(tabs.get(1));
		   reportStep("New Tab is opened", "PASS",true);
		   Thread.sleep(3000);
		   driver.get(url);
		   
		 Thread.sleep(5000);
		 List <WebElement> tableHeaders=locateElements("xpath", "//table[contains(@label.bind,'Non-Corporate Class')]//tr[2]");	
		 moveContextToElement(tableHeaders.get(0));
		 if(tableHeaders.get(0).findElements(By.tagName("th")).size()==1)
				reportStep("'PFIC Annual Information Statements' Column is removed in new Tab, as expected", "PASS",true);
			else
				reportStep("'PFIC Annual Information Statements' Column is not removed in new Tab, as expected", "FAIL",true);
		 
		 reportStep("Click on reset button", "INFO");
			click(locateElement("xpath", getLocalePropertyValue("objFundDocPageResetButton")),true);
		   driver.close();
		   driver.switchTo().window(tabs.get(0));		
		
		
		return this;
	}
	
	
}

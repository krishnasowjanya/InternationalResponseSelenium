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

public class FundDocumentsPage extends ProjectMethods{
	
	public static String tid;
	public FundDocumentsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblFundDocumentsPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public FundDocumentsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Fund Documents Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Fund Documents", getLocalePropertyValue("lblFundDocumentsPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	public FundDocumentsPage verifyTabs() throws InterruptedException
	{
		reportStep("Validate 2 Tabs{Filters,Edit Columns} displayed in Fund Documents Page", "INFO");
		
		List <WebElement> tabs=locateElements("xpath", getLocalePropertyValue("objFundDocPageTabs"));	
		
		if(tabs.size()!=2)
			reportStep("One or more of the 2 tabs are not displayed for Fund Documents Page", "INFO",true);
		
		verifyExactText(tabs.get(0),getLocalePropertyValue("lblFundDocPageTab1"),"tabs");
		verifyExactText(tabs.get(1),getLocalePropertyValue("lblFundDocPageTab2"),"tabs");
		return this;
	}
	public FundDocumentsPage verifyFundDocumentsPageLayout_Canada() throws InterruptedException
	{
		
		reportStep("Validating the filters displayed for 'Filters' Tab", "INFO");
		List <WebElement> filter=locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilters"));	
		if(filter.get(0).findElements(By.tagName("input")).size()==1)
			reportStep("'Filter by fund Name or Keyword' text box is displayed as expected", "PASS",true);
		else
			reportStep("'Filter by fund Name or Keyword' text box is not displayed as expected", "FAIL",true);
			
		if(locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown")).size()==1) {
			reportStep("{Asset Class} Dropdown filter is displayed as expected", "PASS");
			Select assetClass= new Select(locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown")).get(0));

			if(assetClass.getFirstSelectedOption().getText().equals(getLocalePropertyValue("lblFundDocPageAssetClassFilterDefaultValue")))
				reportStep("Asset Class Filter selected with default value as '" + getLocalePropertyValue("lblFundDocPageAssetClassFilterDefaultValue")+ "'", "PASS");
			else
				reportStep("Asset Class Filter may not selected with default value as '" + getLocalePropertyValue("lblFundDocPageAssetClassFilterDefaultValue")+ "'", "FAIL");
			click(locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown")).get(0),true);
			if(assetClass.getOptions().size()>1)
				reportStep("Asset Class Filter has expected options", "PASS");
			else
				reportStep("Asset Class Filter may not have other options except devault value '" + getLocalePropertyValue("lblFundDocPageAssetClassFilterDefaultValue")+ "'", "FAIL");				
			}		
		else
			reportStep("{Asset Class} Dropdown filter may not displayed as expected", "FAIL");
		
		
		reportStep("Validate labels of {Filter by fund name or keyword & Asset Class} filters", "INFO");
		verifyExactText(filter.get(0).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel1"),"filter label");
		verifyExactText(filter.get(1).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel2"),"filter label");
		
		
		List <WebElement> tableHeaders=locateElements("xpath", getLocalePropertyValue("objFundDocPageTableheaderRows"));	
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objFundDocPageTableheaderRows")));
		if(tableHeaders.size()>0)
			reportStep("Fund Documents Table is displayed ", "PASS");
		else
			reportStep("Fund Documents Table is not displayed ", "FAIL");
		
		reportStep("Validate labels of Fund Document Table Headers in Filter Tab", "INFO");
		for(int i=0;i<tableHeaders.get(0).findElements(By.tagName("th")).size();i++) {
		verifyExactText(tableHeaders.get(0).findElements(By.tagName("th")).get(i),getLocalePropertyValue("lblFundDocPageTableHeader"+(i+1)),"Tabel Header");
		}
		
		return this;
	}
	public FundDocumentsPage verifyEditColumnsTab() throws InterruptedException
	{
		reportStep("Validate Layout of 'Edit columns' tab", "INFO");
		
		List <WebElement> tabs=locateElements("xpath", getLocalePropertyValue("objFundDocPageTabs"));
	
		click(tabs.get(1),true);
		
		reportStep("Validate the Checkbox Buttons labels{Retail Factsheet,KIID, SID, Prospectus, Annual Report, Semi Annual Report, Portfolio Holdinds} displayed on top 'Edit columns' tab", "INFO");
		List <WebElement> btnlbls=locateElements("xpath", getLocalePropertyValue("objFundDocPageEditColButtons"));
		
		int vSize;
		
		switch(sCountryName)
		{
		case "UK":vSize =7 ;
					break;
		case "German":vSize =5 ;			
					break;	
		case "Canada":
			if(!sExecutionLanguage.equals("French"))
				vSize =7 ;			
			else
				vSize =6 ;	
					break;	
		default:vSize =7 ;	
					break;
		}
		
		if(btnlbls.size()!=vSize)
			reportStep("One or More of the checkbox Buttons is not displayed", "FAIL");
			
		for(int i=0;i<btnlbls.size();i++) {
			verifyExactText(btnlbls.get(i),getLocalePropertyValue("lblFundDocPageEditColTabBtn"+(i+1)),"Button label");
		}
		
		reportStep("Validate First Edit Column button is Unchecked on Click", "INFO");
		click(btnlbls.get(0));
		//.findElement(By.tagName("input"))
		if(!btnlbls.get(0).getAttribute("class").contains("active"))
			reportStep("The first Edit column is unchecked as expected", "PASS",true);
		else
			reportStep("The first Edit column is not unchecked as expected", "FAIL",true);
		
		reportStep("Validate user is able to select multiple PDFs on Enabling 'Select Multiple PDFs' Toggle", "INFO");
		List <WebElement> selectMultiPDF=locateElements("xpath", getLocalePropertyValue("objFundDocPageEditColSelectMultPDF"));
		reportStep("Click on select 'Multiple PDFs' to enable it", "INFO");
		click(selectMultiPDF.get(0), true);
		
		if(selectMultiPDF.get(0).getAttribute("class").contains("active")) {
			
			reportStep("Selecting 3 PDFs in the Fund Document Table", "INFO");
			click(locateElement("xpath","//table[1]/tbody/tr[not(@class='au-target aurelia-hide')][2]/td[4]"));
			click(locateElement("xpath","//table[1]/tbody/tr[not(@class='au-target aurelia-hide')][3]/td[4]"));
			click(locateElement("xpath","//table[1]/tbody/tr[not(@class='au-target aurelia-hide')][4]/td[4]"),true);
			
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
			click(locateElement("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFEditBtnClose")),true);
			
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
		
		
		
		return this;
	}
	
	public FundDocumentsPage verifyFundDocumentsPageLayout() throws InterruptedException
	{
		
		 switch(sCountryName) {
			case "UK": 			verifyFundDocumentsPageLayoutUK();
								break;
		
			case "German":		verifyFundDocumentsPageLayoutGerman(); 
								break;
			
			case "Canada":	    verifyFundDocumentsPageLayoutCanada(); 
								break;
			default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
								break;
			}
	
		return this;
	}
	
	public FundDocumentsPage verifyFundDocumentsPageLayoutUK() throws InterruptedException
	{
		
		reportStep("Validate Layout of Fund Documents Page", "INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link at Top Right Corner of Page");
		
		List <WebElement> shareLinks=locateElements("xpath", getLocalePropertyValue("objFundDocPageSharelinks"));	
		
	
		reportStep("Validating the 4 Links(For documents not listed below, please use the following links) in Document Section", "INFO");
		verifyExactText(shareLinks.get(0),getLocalePropertyValue("lblFundDocPageSharelnkText1"),"FIRST: Additional Doc Link in Fund Documents Section");
		verifyExactText(shareLinks.get(1),getLocalePropertyValue("lblFundDocPageSharelnkText2"),"SECOND: Additional Doc Link in Fund Documents Section");
		verifyExactText(shareLinks.get(2),getLocalePropertyValue("lblFundDocPageSharelnkText3"),"THIRD: Additional Doc Link in Fund Documents Section");
		verifyExactText(shareLinks.get(3),getLocalePropertyValue("lblFundDocPageSharelnkText4"),"FOURTH: Additional Doc Link in Fund Documents Section");
			
		reportStep("Validating the filters displayed on Top of 'Filters' Tab", "INFO");
		List <WebElement> filter=locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilters"));	
		if(filter.get(0).findElements(By.tagName("input")).size()==1)
			reportStep("'Filter by fund Name or Keyword' text box is displayed as expected", "PASS",true);
		else
			reportStep("'Filter by fund Name or Keyword' text box is not displayed as expected", "FAIL",true);
		
		
		if(locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown")).size()==4)
			reportStep(4+" Dropdowns for {Asset Class,Investment Vehicle,Share Class, Currency} filters are displayed as expected", "PASS");
		else
			reportStep(4+" Dropdowns for {Asset Class,Investment Vehicle,Share Class, Currency} filters are not displayed", "FAIL");
		
		
		reportStep("Validate labels of {Filter by fund name or keyword, Asset Class,Investment Vehicle,Share Class, Currency} filters", "INFO");
		verifyExactText(filter.get(0).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel1"),"filter label");
		verifyExactText(filter.get(1).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel2"),"filter label");
		verifyExactText(filter.get(2).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel3"),"filter label");
		verifyExactText(filter.get(3).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel4"),"filter label");
		verifyExactText(filter.get(4).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel5"),"filter label");
		
		
		List <WebElement> tableHeaders=locateElements("xpath", getLocalePropertyValue("objFundDocPageTableheaderRows"));	
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objFundDocPageTableheaderRows")));
		if(tableHeaders.size()>0)
			reportStep("Fund Documents Table is displayed ", "PASS",true);
		else
			reportStep("Fund Documents Table is not displayed ", "FAIL",true);
		
		reportStep("Validate labels of Table Headers{Fund,Retail Factsheet, KIID, SID, Prospectus, Annual Report, Semi Annual Report, Portfolio Holdings} in Filter Tab", "INFO");
		for(int i=0;i<tableHeaders.get(0).findElements(By.tagName("th")).size();i++) {
		verifyExactText(tableHeaders.get(0).findElements(By.tagName("th")).get(i),getLocalePropertyValue("lblFundDocPageTableHeader"+(i+1)),"Tabel Header");
		}
		
		return this;
	}
	
	public FundDocumentsPage verifyFundDocumentsPageLayoutGerman() throws InterruptedException
	{
		
		reportStep("Validate Layout of Fund Documents Page", "INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link at Top Right Corner of Page");
		
		//Share links are not displayed in German Site
		
		reportStep("Validating the filters displayed on Top of 'Filters' Tab", "INFO");
		List <WebElement> filter=locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilters"));	
		if(filter.get(0).findElements(By.tagName("input")).size()==1)
			reportStep("'Filter by fund Name or Keyword' text box is displayed as expected", "PASS",true);
		else
			reportStep("'Filter by fund Name or Keyword' text box is not displayed as expected", "FAIL",true);
		
		
		
		if(locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown")).size()==4)
			reportStep(4+" Dropdowns for {Asset Class,Investment Vehicle,Share Class, Currency} filters are displayed as expected", "PASS");
		else
			reportStep(4+" Dropdowns for {Asset Class,Investment Vehicle,Share Class, Currency} filters are not displayed", "FAIL");
		
		
		
		reportStep("Validate labels of {Filter by fund name or keyword, Asset Class,Investment Vehicle,Share Class, Currency} filters", "INFO");
		verifyExactText(filter.get(0).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel1"),"filter label");
		verifyExactText(filter.get(1).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel2"),"filter label");
		verifyExactText(filter.get(2).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel3"),"filter label");
		verifyExactText(filter.get(3).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel4"),"filter label");
		verifyExactText(filter.get(4).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel5"),"filter label");
		
		
		List <WebElement> tableHeaders=locateElements("xpath", getLocalePropertyValue("objFundDocPageTableheaderRows"));	
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objFundDocPageTableheaderRows")));
		if(tableHeaders.size()>0)
			reportStep("Fund Documents Table is displayed ", "PASS",true);
		else
			reportStep("Fund Documents Table is not displayed ", "FAIL",true);
		
		reportStep("Validate labels of Table Headers{Fund,Retail Factsheet, KIID, SID, Prospectus, Annual Report, Semi Annual Report, Portfolio Holdings} in Filter Tab", "INFO");
		for(int i=0;i<tableHeaders.get(0).findElements(By.tagName("th")).size();i++) {
		verifyExactText(tableHeaders.get(0).findElements(By.tagName("th")).get(i),getLocalePropertyValue("lblFundDocPageTableHeader"+(i+1)),"Tabel Header");
		}
		
		return this;
	}
	
	public FundDocumentsPage verifyFundDocumentsPageLayoutCanada() throws InterruptedException
	{
		
		reportStep("Validate Layout of Fund Documents Page", "INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link at Top Right Corner of Page");
		
		List <WebElement> shareLinks=locateElements("xpath", getLocalePropertyValue("objFundDocPageSharelinks"));	
		
		
			reportStep("Validating the 6 Links(For documents not listed below, please use the following links) in Document Section", "INFO");
			verifyExactText(shareLinks.get(0),getLocalePropertyValue("lblFundDocPageSharelnkText1"),"FIRST: Additional Doc Link in Fund Documents Section");
			verifyExactText(shareLinks.get(1),getLocalePropertyValue("lblFundDocPageSharelnkText2"),"SECOND: Additional Doc Link in Fund Documents Section");
			verifyExactText(shareLinks.get(2),getLocalePropertyValue("lblFundDocPageSharelnkText3"),"THIRD: Additional Doc Link in Fund Documents Section");
			verifyExactText(shareLinks.get(3),getLocalePropertyValue("lblFundDocPageSharelnkText4"),"FOURTH: Additional Doc Link in Fund Documents Section");
			verifyExactText(shareLinks.get(4),getLocalePropertyValue("lblFundDocPageSharelnkText5"),"FIFTH: Additional Doc Link in Fund Documents Section");
			verifyExactText(shareLinks.get(5),getLocalePropertyValue("lblFundDocPageSharelnkText6"),"SIXTH: Additional Doc Link in Fund Documents Section");
			
		
		reportStep("Validating the filters displayed on Top of 'Filters' Tab", "INFO");
		List <WebElement> filter=locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilters"));	
		if(filter.get(0).findElements(By.tagName("input")).size()==1)
			reportStep("'Filter by fund Name or Keyword' text box is displayed as expected", "PASS",true);
		else
			reportStep("'Filter by fund Name or Keyword' text box is not displayed as expected", "FAIL",true);
		
		
		if(locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown")).size()==1)
			reportStep(1+" Dropdowns for {Asset Class,Investment Vehicle,Share Class, Currency} filters are displayed as expected", "PASS");
		else
			reportStep(1+" Dropdowns for {Asset Class,Investment Vehicle,Share Class, Currency} filters are not displayed", "FAIL");
		
		

			reportStep("Validate labels of {Filter by fund name or keyword, Asset Class,Investment Vehicle,Share Class, Currency} filters", "INFO");
			verifyExactText(filter.get(0).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel1"),"filter label");	
			verifyExactText(filter.get(1).findElement(By.tagName("label")),getLocalePropertyValue("lblFundDocPageFilterLabel2"),"filter label");
	
		
		List <WebElement> tableHeaders=locateElements("xpath", getLocalePropertyValue("objFundDocPageTableheaderRows"));	
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objFundDocPageTableheaderRows")));
		if(tableHeaders.size()>0)
			reportStep("Fund Documents Table is displayed ", "PASS",true);
		else
			reportStep("Fund Documents Table is not displayed ", "FAIL",true);
		
		reportStep("Validate labels of Table Headers{Fund,Retail Factsheet, KIID, SID, Prospectus, Annual Report, Semi Annual Report, Portfolio Holdings} in Filter Tab", "INFO");
		for(int i=0;i<tableHeaders.get(0).findElements(By.tagName("th")).size();i++) {
		verifyExactText(tableHeaders.get(0).findElements(By.tagName("th")).get(i),getLocalePropertyValue("lblFundDocPageTableHeader"+(i+1)),"Tabel Header");
		}
		
		return this;
	}
	
	public FundDocumentsPage verifyDropdownValues() throws InterruptedException
	{
		 switch(sCountryName) {
			case "UK": 			verifyDropdownValuesUK();
								break;
		
			case "German":		verifyDropdownValuesGerman(); 
								break;
			
			case "Canada":	    verifyDropdownValuesCanada(); 
								break;
			default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
								break;
			}
	
		return this;
	}
	
	public FundDocumentsPage verifyDropdownValuesUK() throws InterruptedException
	{
		reportStep("Validate Values in filters{Asset Class,Investment Vehicle,Share Class, Currency} Dropdown", "INFO");
		
		List<WebElement> filters =locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown"));
		
		
		//Not Matching the list given in test case
		reportStep("Validate Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp1Vals")+"} are present in Asset Class Filter", "INFO");
		click(filters.get(0),true);
		if(filters.get(0).findElements(By.tagName("option")).size()!=6)
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
		
	
			reportStep("Validate Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp2Vals")+"} are present in Investment Vehicle Filter", "INFO");
			click(filters.get(1),true);
			if(filters.get(1).findElements(By.tagName("option")).size()!=4)
				reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp2Vals")+"} are not displayed for Investment Vehicle Filter","FAIL");		
				
			 vBool=true;
				for(WebElement optn:filters.get(1).findElements(By.tagName("option"))) {
				if(!optn.getText().matches(getLocalePropertyValue("lblFundDocPageDrp2Vals"))) {
					vBool=false;
					reportStep(optn.getText()+" value present for Investment Vehicle Filter is not matching Expected values {"+getLocalePropertyValue("lblFundDocPageDrp2Vals")+"}","FAIL");	
					}	
				}
			
				if(vBool)
					reportStep("All the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp2Vals")+"} are  displayed for Investment Vehicle Filter","PASS");			
			
				reportStep("Validate Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp3Vals")+"} are present in Share Class Filter", "INFO");
				click(filters.get(2),true);
				if(filters.get(2).findElements(By.tagName("option")).size()!=5)
					reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp3Vals")+"} are not displayed for Share Class Filter","FAIL");		
					
				 vBool=true;
					for(WebElement optn:filters.get(2).findElements(By.tagName("option"))) {
					if(!optn.getText().matches(getLocalePropertyValue("lblFundDocPageDrp3Vals"))) {
						vBool=false;
						reportStep(optn.getText()+" value present for Share Class Filter is not matching Expected values {"+getLocalePropertyValue("lblFundDocPageDrp3Vals")+"}","FAIL");	
						}	
					}
				
					if(vBool)
						reportStep("All the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp3Vals")+"} are  displayed for Share Class Filter","PASS");	
					
					
					reportStep("Validate Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp4Vals")+"} are present in Currency Filter", "INFO");
					click(filters.get(3),true);
					if(filters.get(3).findElements(By.tagName("option")).size()!=5)
						reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp4Vals")+"} are not displayed for Currency Filter","FAIL");		
						
					 vBool=true;
						for(WebElement optn:filters.get(3).findElements(By.tagName("option"))) {
						if(!optn.getText().matches(getLocalePropertyValue("lblFundDocPageDrp4Vals"))) {
							vBool=false;
							reportStep(optn.getText()+" value present for Currency Filter is not matching Expected values {"+getLocalePropertyValue("lblFundDocPageDrp4Vals")+"}","FAIL");	
							}	
						}
					
						if(vBool)
							reportStep("All the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp4Vals")+"} are  displayed for Currency Filter","PASS");		
				
			
		return this;
	}
	
	public FundDocumentsPage verifyDropdownValuesGerman() throws InterruptedException
	{
		reportStep("Validate Values in filters{Asset Class,Investment Vehicle,Share Class, Currency} Dropdown", "INFO");
		
		List<WebElement> filters =locateElements("xpath", getLocalePropertyValue("objFundDocPageSharefilterDropdown"));
		
		reportStep("Validate Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp1Vals")+"} are present in Asset Class Filter", "INFO");
		click(filters.get(0),true);
		if(filters.get(0).findElements(By.tagName("option")).size()!=7)
			reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp1Vals")+"} are not displayed for Asset Class Filter","FAIL");		
		Thread.sleep(3000);
		boolean vBool=true;
			for(WebElement optn:filters.get(0).findElements(By.tagName("option"))) {
			if(!optn.getText().matches(getLocalePropertyValue("lblFundDocPageDrp1Vals"))) {
				vBool=false;
				reportStep(optn.getText()+" value present for Asset Class Filter is not matching Expected values {"+getLocalePropertyValue("lblFundDocPageDrp1Vals")+"}","FAIL");	
				}	
			}
		
			if(vBool)
				reportStep("All the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp1Vals")+"} are  displayed for Asset Class Filter","PASS");		
		
			//Remaining filters not applicable for Canada
			Thread.sleep(3000);
	
			reportStep("Validate Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp2Vals")+"} are present in Investment Vehicle Filter", "INFO");
			click(filters.get(1),true);
			if(filters.get(1).findElements(By.tagName("option")).size()!=2)
				reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp2Vals")+"} are not displayed for Investment Vehicle Filter","FAIL");		
				
			 vBool=true;
				for(WebElement optn:filters.get(1).findElements(By.tagName("option"))) {
				if(!optn.getText().matches(getLocalePropertyValue("lblFundDocPageDrp2Vals"))) {
					vBool=false;
					reportStep(optn.getText()+" value present for Investment Vehicle Filter is not matching Expected values {"+getLocalePropertyValue("lblFundDocPageDrp2Vals")+"}","FAIL");	
					}	
				}
			
				if(vBool)
					reportStep("All the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp2Vals")+"} are  displayed for Investment Vehicle Filter","PASS");			
			
				Thread.sleep(3000);
				reportStep("Validate Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp3Vals")+"} are present in Share Class Filter", "INFO");
				click(filters.get(2),true);
				if(filters.get(2).findElements(By.tagName("option")).size()!=4)
					reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp3Vals")+"} are not displayed for Share Class Filter","FAIL");		
					
				 vBool=true;
					for(WebElement optn:filters.get(2).findElements(By.tagName("option"))) {
					if(!optn.getText().matches(getLocalePropertyValue("lblFundDocPageDrp3Vals"))) {
						vBool=false;
						reportStep(optn.getText()+" value present for Share Class Filter is not matching Expected values {"+getLocalePropertyValue("lblFundDocPageDrp3Vals")+"}","FAIL");	
						}	
					}
				
					if(vBool)
						reportStep("All the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp3Vals")+"} are  displayed for Share Class Filter","PASS");	
					
					Thread.sleep(3000);
					reportStep("Validate Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp4Vals")+"} are present in Currency Filter", "INFO");
					click(filters.get(3),true);
					if(filters.get(3).findElements(By.tagName("option")).size()!=5)
						reportStep("One or More of the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp4Vals")+"} are not displayed for Currency Filter","FAIL");		
						
					 vBool=true;
						for(WebElement optn:filters.get(3).findElements(By.tagName("option"))) {
						if(!optn.getText().matches(getLocalePropertyValue("lblFundDocPageDrp4Vals"))) {
							vBool=false;
							reportStep(optn.getText()+" value present for Currency Filter is not matching Expected values {"+getLocalePropertyValue("lblFundDocPageDrp4Vals")+"}","FAIL");	
							}	
						}
					
						if(vBool)
							reportStep("All the Expected Values {"+getLocalePropertyValue("lblFundDocPageDrp4Vals")+"} are  displayed for Currency Filter","PASS");		
				
			
		return this;
	}
	
	public FundDocumentsPage verifyDropdownValuesCanada() throws InterruptedException
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
	
	public FundDocumentsPage verifySaveViewResetButton() throws InterruptedException
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
	
	public FundDocumentsPage verifySaveViewResetFunctionality() throws InterruptedException
	{
		
		reportStep("Click on save view button", "INFO");
		click(locateElement("xpath", getLocalePropertyValue("objFundDocPageSaveViewButton")));
		
		WebDriverWait wait=new WebDriverWait(driver, 200);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElements("xpath",localeProp.getProperty("objFundDocPageEditColPopupHeader")).get(1)));
		
		verifyExactText(locateElements("xpath",getLocalePropertyValue("objFundDocPageEditColPopupHeader")).get(1), getLocalePropertyValue("lblFundDocPageEditColPopupSaveViewHeader"),"popup header",true);
		verifyElementExist("xpath", getLocalePropertyValue("objFundDocPageEditColSaveviewpopuptext"),"Popup text");
		reportStep("The following URL is displayed in Save view text box :"+locateElement("xpath","//p/input").getAttribute("value"),"INFO");
		click(locateElements("xpath",getLocalePropertyValue("objFundDocPageEditColSelectedPDFEditBtnClose")).get(1),true);
	
		reportStep("Click on reset button", "INFO");
		click(locateElement("xpath", getLocalePropertyValue("objFundDocPageResetButton")),true);
		
		return this;
	}
	
	
}

package internationalPages.Products;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentTest;

import utils.ExcelDataProvider;
import utils.TextFormatFunctions;
import utils.WebTableFunctions;
import utils.dateFunctions;
import utils.xmlDataProvider;
import wdMethods.ProjectMethods;

public class FundOverviewPage extends ProjectMethods{
	
	//public String fund="Franklin Diversified Growth Fund - W (acc)";
	public String fund,FundNumber,FundURL,InvestmentVehicle,actor,Env;
		
	public FundOverviewPage(RemoteWebDriver driver, ExtentTest test,String FundName) throws InterruptedException, FileNotFoundException, IOException {
		this.driver = driver;
		this.test = test;
		
		//Loading Locale File
		localeProp.load(new FileInputStream(new File(sLocaleFile)));
		
		fund=FundName;
		actor="investor";
		Env=sEnvironment;
		Thread.sleep(2000);	
		FundURL=driver.getCurrentUrl();
		FundNumber=FundURL.split(getLocalePropertyValue("lblURLPart")+"\\/")[1].split("\\/")[0].trim();		
		//InvestmentVehicle=getText(locateElement("class", getLocalePropertyValue("objFOInvestmentVehilce")));
		
		String title; 
		reportStep("Checking Fund Overveiw Page Title","INFO",false);
		if(sEnvironment.equals("QA1"))
			title="overview-"+ actor + " - " + fund;
		else
			title=getLocalePropertyValue("lblFOtitlePart1");
		
		//new code 3/28->CR
		reportStep("Checking Fund Overveiw Page Title & Heading","INFO",false);
		if(!(verifyTitle(title) && verifyTitle(fund))) {
			reportStep("This is NOT Fund Overview Page. Refer snap", "FAIL");
		}
		// Initialize the webelements inside the page
		
		PageFactory.initElements(driver, this);
	}
	
/*	@FindBy(how=How.CSS,using=".table.table-text.fund-information")
	private WebElement eleFundInformationTable;*/
	
	public String getShareClassInceptionDate()
	{
		String shrClassInceptionDate=locateElement("xpath", getLocalePropertyValue("objFOShareClassInceptionDateFromFundInformationSection")).getText();
		return shrClassInceptionDate;
	}
	public List<WebElement> getElementsFromFundInformation()
	{	
		WebElement eleFundInformationTable=locateElement("css", getLocalePropertyValue("objFOFundInformationWidgetTable"));
		return eleFundInformationTable.findElements(By.tagName("th"));
	}
	
	public List<WebElement> getElementValuesFromFundInformation()
	{	
		WebElement eleFundInformationTable=locateElement("css", getLocalePropertyValue("objFOFundInformationWidgetTable"));
		return eleFundInformationTable.findElements(By.tagName("td"));
	}
	public FundOverviewPage verifyFundInformationElements(String exlName)
	{		
		String dateString[];
		WebElement eleFundInformationTable=locateElement("css", getLocalePropertyValue("objFOFundInformationWidgetTable"));
		
		mouseOver(eleFundInformationTable);
		reportStep("Checking Elements under Fund Information widget","INFO");
		
		List<WebElement> actAttributes=getElementsFromFundInformation();				
		//String[][] expAttributes=ExcelDataProvider.getExcelData(exlName,"Fund_Information");
		String expTradeInfoElements=ExcelDataProvider.getCellDataByFilter(exlName, "Fund_Information", InvestmentVehicle, 1);
		if(expTradeInfoElements==null)
			reportStep("Fund Infromation Test Data may not exist in Data Sheet / Invalid. Please refer Data Sheet : '" + exlName +"'","FAIL");
		String[] expAttributes=expTradeInfoElements.split(",");
		
		reportStep("Fund Infromation Actual Attributes Count - " + actAttributes.size(),"INFO");	
		if (actAttributes.size()!=expAttributes.length) 
			reportStep("Number of Elements under Fund Information widget should be : " + expAttributes.length + ". But Actual Elements are: " + actAttributes.size(),"FAIL");
		else { 
			reportStep("Number of Elements under Fund Information widget matches with expected: " + expAttributes.length,"PASS");
			
			for (int i = 0; i < expAttributes.length; i++) {
				expAttributes[i]=expAttributes[i].replace("\n", "");
				
				if(getText(actAttributes.get(i)).contains(getLocalePropertyValue("lblFOFndInfoWdtMorningstarCategory")))
					verifyPartialText(actAttributes.get(i), expAttributes[i]);
				else if(getText(actAttributes.get(i)).contains(getLocalePropertyValue("lblFOFndInfoWdtFundSize"))) {
					verifyPartialText(actAttributes.get(i), expAttributes[i]);
					
					reportStep("Validing Date Format under 'Fund Size'","INFO");
					System.out.println("Fund Size value" + actAttributes.get(i).getText());
					dateString=actAttributes.get(i).getText().split("\n")[1].split("\\(")[0].split(getLocalePropertyValue("lblFOAsAtDateTxt"));
					System.out.println("Date:" + dateString[1].trim());
					if (dateFunctions.VerifyDateFormat(dateString[1].trim(),"dd/MM/yyyy"))
						reportStep("'Fund Size' Date Format is displayed as Expected :'dd/MM/yyyy'","PASS");
					else
						reportStep("'Fund Size' Date Format is not displayed as Expected :'dd/MM/yyyy'","FAIL");					
					 
				}
				else
					verifyExactText(actAttributes.get(i), expAttributes[i].trim());
			}			
		
		}
		
		return this;
	}
	
/*	public FundOverviewPage verifyFundInformationElements(String exlName)
	{		
		String dateString[];
		reportStep("Checking Elements under Fund Information widget","INFO");
		
		List<WebElement> actAttributes=getElementsFromFundInformation();				
		String[][] expAttributes=ExcelDataProvider.getExcelData(exlName,"Fund_Information");
		
		reportStep("Fund Infromation Actual Attributes Count - " + actAttributes.size(),"INFO");	
		if (actAttributes.size()!=expAttributes.length) 
			reportStep("Number of Elements under Fund Information widget should be : " + expAttributes.length + ". But Actual Elements are: " + actAttributes.size(),"FAIL");
		else { 
			reportStep("Number of Elements under Fund Information widget matches with expected: " + expAttributes.length,"PASS");
			
			for (int i = 0; i < expAttributes.length; i++) {
				if(getText(actAttributes.get(i)).contains("Morningstar Category"))
					verifyPartialText(actAttributes.get(i), expAttributes[i][0]);
				else if(getText(actAttributes.get(i)).contains("Fund Size")) {
					verifyPartialText(actAttributes.get(i), expAttributes[i][0]);
					
					reportStep("Validing Date Format under 'Fund Size'","INFO");
					System.out.println("Fund Size value" + actAttributes.get(i).getText());
					dateString=actAttributes.get(i).getText().split("\n")[1].split("\\(")[0].split("As at");
					System.out.println("Date:" + dateString[1].trim());
					if (dateFunctions.VerifyDateFormat(dateString[1].trim(),"dd/MM/yyyy"))
						reportStep("'Fund Size' Date Format is displayed as Expected :'dd/MM/yyyy'","PASS");
					else
						reportStep("'Fund Size' Date Format is not displayed as Expected :'dd/MM/yyyy'","FAIL");					
					
				}
				else
					verifyExactText(actAttributes.get(i), expAttributes[i][0].trim());
			}			
		
		}
		
		return this;
	}*/
	public String getAttributeValueByName(String attributeName)
	{
		//reportStep("Checking Elements under Fund Information widget","INFO");		
	
		List<WebElement> actAttributes=getElementsFromFundInformation();
		List<WebElement> actAttributeValues=getElementValuesFromFundInformation();
		if (actAttributes.size()>0 && actAttributeValues.size()>0)
		{		
			for (int i = 0; i < actAttributes.size();i++) {
				if (getText(actAttributes.get(i)).contains(attributeName))
					return getText(actAttributeValues.get(i));
			}
		}		
		return null;
	}
	
	public FundOverviewPage verifyFundInformationElementValues(String fundName,String xmlURL,String exlName) throws ParserConfigurationException, SAXException
	{	
/*		reportStep("Validating Fund Infromation Widget Element Values","INFO");
		HashMap<String, String> FundInfromationValues = extractValuesFromXML(fundName,xmlURL);		
		
		String[][] expAttributes=ExcelDataProvider.getExcelData(exlName,"Fund_Information_1");		
		String expVal, actVal;
		
		for (int i = 0; i < expAttributes.length; i++) {
			
			reportStep("Validating '" + expAttributes[i][0] + "' Element Value","INFO");
			actVal=getAttributeValueByName(expAttributes[i][0]);
			expVal=getXMLValueByElementName(expAttributes[i][0],FundInfromationValues);
			if (expVal!=null)
			verifyTwoStringsExactly(expVal,actVal);
			else
				reportStep("'" + expAttributes[i][0] + "' Element may not found in XML file","WARN");
		}		
		return this;*/
		reportStep("Validating Fund Infromation Widget Element Values","INFO");
		HashMap<String, String> FundInfromationValues = extractValuesFromXML(fundName,xmlURL);		
		
		String expTradeInfoElements=ExcelDataProvider.getCellDataByFilter(exlName, "Fund_Information", InvestmentVehicle, 1);
		String[] expAttributes=expTradeInfoElements.split(",");
		String expVal, actVal;
		
		for (int i = 0; i < expAttributes.length; i++) {
			
			expAttributes[i]=expAttributes[i].replace("\n", "");
			System.out.println(expAttributes[i]);
			reportStep("Validating '" + expAttributes[i] + "' Element Value","INFO");
			actVal=getAttributeValueByName(expAttributes[i]);
			expVal=getXMLValueByElementName(expAttributes[i],FundInfromationValues);
			if (expVal!=null)
				verifyTwoStringsExactly(expVal,actVal);
			else
				reportStep("'" + expAttributes[i] + "' Element may not found in XML file","WARN");
		}		
		return this;
		
	}
	
	public HashMap<String, String> extractValuesFromXML(String fundName,String xmlURL) throws ParserConfigurationException, SAXException
	{
		xmlDataProvider xmlDP=new xmlDataProvider();
		InputStream input;
		Element parent;
		HashMap<String, String> fundInfo = new HashMap<String, String>();		
		
		String shareClass=fundName.split("-")[1].trim();	
		System.out.println(shareClass);
		String xmlSource=xmlURL.replace("<FUND_ID>", FundNumber);
		System.out.println(xmlSource);
		try {
			input=xmlDP.getXMLSourceByFund(xmlSource);
			parent=xmlDP.getXMLParentElementByTagName(input, "ns1:SHRCLS_NAME", shareClass);
			if(parent==null)
				reportStep("XML source you are refering may not valid/correct. Please verify the URL: " + xmlSource, "FAIL");
			fundInfo=xmlDP.getChildNodesWithTextValues(parent);			
			System.out.println("XML Source refering for Fund ID: " + fundInfo.get("TA_ID"));
			
		} catch (ClientProtocolException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fundInfo;
	}
	
	public String getXMLValueByElementName(String ElementName,HashMap<String, String> xmlSorce)
	{
		HashMap<String, String> FundInfromationValues = xmlSorce;
		switch (ElementName) 
		{
		case "Fund Size":
			return FundInfromationValues.get("");			
		case "Fund Inception Date":
			return FundInfromationValues.get("FUND_INC_DT");			
		case "Share Class Inception Date":
			return FundInfromationValues.get("SHRCLS_INC_DT");			
		case "Fund Base Currency":
			return FundInfromationValues.get("FUND_BASE_CURR");			
		case "Share Class Currency":
			return FundInfromationValues.get("SHRCLS_CURR");			
		case "Benchmark":
			return FundInfromationValues.get("");			
		case "Asset Class":
			return FundInfromationValues.get("");			
		case "Morningstar Category":
			return FundInfromationValues.get("");			
		case "IA Sector":
			return FundInfromationValues.get("");			
		case "ISA Status":
			return FundInfromationValues.get("GB_ISA_STATUS");			
		default:			
			return null;
		}
			
	}
	
/*	@FindBy(how=How.XPATH,using="//h3[text()='Summary of Fund Objective']")
	private WebElement eleSummaryOfFundObjective;*/
	
	public FundOverviewPage verifySummaryHeading()
	{
		WebElement eleSummaryOfFundObjective=locateElement("xpath", getLocalePropertyValue("objFOSummaryOfFundObjectiveHeading"));
		mouseOver(eleSummaryOfFundObjective);
		verifyDisplayed(eleSummaryOfFundObjective,getLocalePropertyValue("lblFOSummaryObj").toUpperCase());		
		return this;
	}
	
/*	@FindBy(how=How.XPATH,using="//span[text()='Fund Information']")
	private WebElement eleFundInformation;*/
	
	public FundOverviewPage verifyFundInformationHeading()
	{
		WebElement eleFundInformation=locateElement("xpath", getLocalePropertyValue("objFOFundInfromationHeading"));
		mouseOver(eleFundInformation);
		verifyDisplayed(eleFundInformation,getLocalePropertyValue("lblFOFundInfo").toUpperCase());		
		return this;
	}
/*	@FindBy(how=How.XPATH,using="//span[text()='Price']")
	private WebElement elePriceHeading;*/
	
	public FundOverviewPage verifyPriceHeading()
	{
		WebElement elePriceHeading=locateElement("xpath", getLocalePropertyValue("objFOPriceHeading"));
		mouseOver(elePriceHeading);
		verifyDisplayed(elePriceHeading,getLocalePropertyValue("lblFOPrice").toUpperCase());		
		return this;
	}
/*	@FindBy(how=How.XPATH,using="//span[text()='Sales Charges']")
	private WebElement eleSalesChargesHeading;*/
	
	public FundOverviewPage verifySalesChargesHeading()
	{
		WebElement eleSalesChargesHeading=locateElement("xpath", getLocalePropertyValue("objFOSalesChargesHeading"));
		mouseOver(eleSalesChargesHeading);
		verifyDisplayed(eleSalesChargesHeading,getLocalePropertyValue("lblFOSalesCharges").toUpperCase());		
		return this;
	}
/*	@FindBy(how=How.XPATH,using="//span[text()='Identifiers']")
	private WebElement eleIdentifiersHeading;*/
	
	public FundOverviewPage verifyIdentifierHeading()
	{
		WebElement eleIdentifiersHeading=locateElement("xpath", getLocalePropertyValue("objFOIdentifiersHeading"));
		mouseOver(eleIdentifiersHeading);
		verifyDisplayed(eleIdentifiersHeading,getLocalePropertyValue("lblFOIdentifiers"));		
		return this;
	}
	
/*	@FindBy(how=How.LINK_TEXT,using="Documents")
	private WebElement eleFundDocumentTab;*/
	
	public FundDocumentPage clickFundDocumentTab() throws InterruptedException, FileNotFoundException, IOException
	{	
		WebElement eleFundDocumentTab=locateElement("link", localeProp.getProperty("objFDDocumentTab"));
		click(eleFundDocumentTab);	
		//click(eleFundDocumentTab);		
		//Delete this ..kept for swiss site
		Thread.sleep(5000);
		return new FundDocumentPage(driver,test,fund);
		
	}
	public FundPerformancePage clickPerformanceTab() throws InterruptedException, FileNotFoundException, IOException
	{	
		click(locateElement("link", localeProp.getProperty("objPEPerformanceTab")),true);
		WebDriverWait wait=new WebDriverWait(driver, 20);	
		
		if(sCountryName.equals("Singapore") || sCountryName.equals("Canada"))
			wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable"))));
		
		else if(sCountryName.equals("Offshore"))
			wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objPECumulativePerformanceMonthlyTable"))));
		
		else
			wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objPEDiscretePerformanceMonthlyTable"))));
		
		return new FundPerformancePage(driver,test,fund);
	}
	
	public PriceAndDistributionPage clickPriceAndDistributionTab() throws InterruptedException, FileNotFoundException, IOException
	{	
		click(locateElement("partial", localeProp.getProperty("objPDPriceAndDistributionTab")));
		WebDriverWait wait=new WebDriverWait(driver, 20);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objPDHistoricalPricingHeading"))));
		captureFullScreen("Capturing screenshot of full Price and Distribution Page");
		return new PriceAndDistributionPage(driver,test,fund);
	}
	
	public FundOverviewPage VerifyBreadCrumbs()
	{
		String expBS;
		reportStep("Validating Fund Overview Page BreadCrumbs","INFO");

		if(Env.equals("QA1"))
			expBS=" > overview-" + actor + " - " +fund;
		else
			expBS= fund;

//		verifyTwoStringsExactly(getBreadCrumbs().trim(), getLocalePropertyValue("lblHmHomeBreadCrumb") + " > " +getLocalePropertyValue("lblHmPriceAndPerformanceBreadCrumb")+ " > " +  expBS);
		
		//changed this due to change in bread crumb display in international site - 3/26
		verifyTwoStringsExactly(getBreadCrumbs().trim(), getLocalePropertyValue("lblHmHomeBreadCrumb") + " > " +getLocalePropertyValue("lblHmPriceAndPerformanceBreadCrumb")+ " > " +  expBS + " - " +getLocalePropertyValue("lblbreadCrumbEnding"));
		return this;
	}
	public String getBreadCrumbs()
	{
		String bCrumbs="";	
		try {
	
			WebDriverWait wait=new WebDriverWait(driver, 20);			
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objPpssBreadCrumbs"))));
			List<WebElement> eleBreadCrumbs=locateElements("xpath", getLocalePropertyValue("objPpssBreadCrumbs"));
			//System.out.println(eleBreadCrumbs.size());
			 for (int i = 0; i < eleBreadCrumbs.size(); i++) {
				 if(i!=eleBreadCrumbs.size()-1)
					 bCrumbs=bCrumbs + eleBreadCrumbs.get(i).getText() + " > ";
				 else
					 bCrumbs=bCrumbs+ eleBreadCrumbs.get(i).getText();
			}
		}catch(Exception e) {
			reportStep("BreadScrum Elements may not found", "FAIL");
		}
		 return bCrumbs;
	}
	
	public FundOverviewPage verifyFindAFundButtonExist()
	{
		verifyElementExist("xpath", getLocalePropertyValue("objFOFindAFundButton"), "Find a Fund");
		return this;
	}
	
	public FundOverviewPage clickFindAFundButtonAndVerifyPopupTitle()
	{
		click(locateElement("xpath", getLocalePropertyValue("objFOFindAFundButton")));	
		verifyElementExist("xpath", getLocalePropertyValue("objFOFindAFundPopupTitle"), "Search A Fund Popup Title");		
		return this;
	} 
	public FundOverviewPage enterTheFundToSearch(String FundName)
	{
		List<WebElement> fundPopupInputList = locateElements("xpath", getLocalePropertyValue("objFOFindAFundPopupInput"));
		System.out.println(fundPopupInputList.size());
//		for(WebElement a:fundPopupInputList) {
//			System.out.println(a.getAttribute("value.bind"));
//			if(a.isDisplayed()) {
//		type(a, FundName.split("-")[0]);
//		System.out.println(a.getAttribute("value.bind"));
//			}}
		
		type(fundPopupInputList.get(2), FundName.split("-")[0]);
		click(locateElement("partial", FundName));
		return this;
	}
	public FundOverviewPage verifySearchResult(String FundName) throws InterruptedException
	{
		Thread.sleep(5000);
		reportStep("Checking Product page is displayed according to the fund searched", "INFO");
		String exp[]=FundName.split("-");
		verifyExactText(locateElement("tag", getLocalePropertyValue("objFOPageHeader")), exp[0].trim());
		return this;
	}
	
	public FundOverviewPage verifyShareClassBasedOnFundSelected(String FundName)
	{
		reportStep("Checking Overview Page displays Share class according to the fund '" + FundName + "' selected in PPSS Page", "INFO");
		String exp[]=FundName.split(" - ");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objFOFindAFundButton")));
		//verifyExactText(locateElement(getLocalePropertyValue("objFOShareClass")), exp[1].trim());
		//commented above code after new code push in intl sites - 3/26
		if(sCountryName.equals("Canada"))
			verifyExactText(locateElement("xpath",getLocalePropertyValue("objFOShareClass")), exp[1].trim()+"-CAD");
		else
			verifyExactText(locateElement("xpath",getLocalePropertyValue("objFOShareClass")), exp[1].trim());
		return this;
	}
	public FundOverviewPage verifyLegalInormationWidget()
	{
		reportStep("Checking Overview Page Important Legal Information Widget", "INFO");
		//verifyPartialText(locateElement("xpath", getLocalePropertyValue("objFOImportantLegalInformation")), getLocalePropertyValue("lblFOImportantLegalInformationTitle"));		
		String title1="";
		if(verifyElementExist("xpath", getLocalePropertyValue("objFOImportantLegalInformation"))) {
			reportStep(" Widget Exist and Displayed", "PASS");
			mouseOver(locateElement("xpath", getLocalePropertyValue("objFOImportantLegalInformation")));
			if(verifyElementExist("xpath", getLocalePropertyValue("objFOImportantInformationHeading"))) {				
				title1=locateElement("xpath", getLocalePropertyValue("objFOImportantInformationHeading")).getText();
				if(title1.trim().equals(getLocalePropertyValue("lblFOImportantInformationTitle")))
					reportStep(title1 + " - Heading displayed as Expected", "PASS");
				else
					reportStep(title1 + " - Heading may not displayed as Expected - " + getLocalePropertyValue("lblFOImportantInformationTitle"),"FAIL");
			
			}else if(verifyElementExist("xpath", getLocalePropertyValue("objFOImportantLegalInformationHeading"))) {				
				title1=locateElement("xpath", getLocalePropertyValue("objFOImportantLegalInformationHeading")).getText();
				if(title1.trim().equals(getLocalePropertyValue("lblFOImportantLegalInformationTitle")))
					reportStep(title1 + " - Heading displayed as Expected", "PASS");
				else
					reportStep(title1 + " - Heading may not displayed as Expected - " + getLocalePropertyValue("lblFOImportantLegalInformationTitle"),"FAIL");			

			}else
				reportStep("Important Legal Information/Important Information Heading may not Displayed", "FAIL");
		}else {
			reportStep("Important Legal Information/Important Information Widget may not Exist and Displayed", "FAIL");}
		
		return this;
	}
	
	public FundPortfolioPage clickPortfolioTab() throws InterruptedException, FileNotFoundException, IOException
	{	
		clickWithNoSnap(locateElement("link",getLocalePropertyValue("objFPPortfolioTab")));
		//captureFullScreen("Capturing Full screen of Fund Portfolio page");
		
		return new FundPortfolioPage(driver,test,fund);
	}
	
	//Prateek
	
	public FundOverviewPage validateFundTabs(String fInfo) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Tabs displayed","INFO");
		List<WebElement> fundAllTabElements = locateElements("xpath", localeProp.getProperty("objFOTabsList"));
		//Converting the excel data to list format
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fundAllTabElements.get(0));
		List<String> excelTabNames = Arrays.asList(fInfo.split(","));
		Thread.sleep(3000);
		for (WebElement felement: fundAllTabElements) {
			if (excelTabNames.contains(felement.getText())) {
				reportStep(felement.getText() +" tab is displayed as expected in application","PASS");
			}
			else {
				reportStep(felement.getText() +" tab displayed in application, is not present in excel data sheet","FAIL");
			}
		     
		}
				return this;
	}
	
	public FundOverviewPage clickOnTab(String tabName) throws InterruptedException{
		Thread.sleep(1000);
	
		reportStep("Clicking on the tab "+ tabName +" to select it.","INFO");
		List<WebElement> fTab = locateElements("xpath", "//li[contains(@class,'fund-tabs__tab au-target')]//a[contains(text(),'"+tabName+"')]");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fTab.get(0));
		if (fTab.size() > 0) {
			reportStep("Tab Name is displayed in application :"+tabName,"PASS");
			click(fTab.get(0));	 
		}
		else { 
			reportStep("Tab Name is not displayed in application :"+tabName,"FAIL");		 
		} 
		//delete this ..introduced for swizz site
	Thread.sleep(5000);
		return this;
	}
	
	public FundOverviewPage validatePriceSection() throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Price Section","INFO");
		
		validateLabelwithAsOfDateDaily(locateElement("xpath",getLocalePropertyValue("objFOPriceSectionHeader")),getLocalePropertyValue("lblFOPriceSectionHeader").toUpperCase());
		
		WebElement PricingTable =locateElement("xpath", localeProp.getProperty("objFOPriceSectionTable"));
		
		verifyPartialText(WebTableFunctions.getCellElement(PricingTable, 0, 0),getLocalePropertyValue("lblFOPriceSectionNAV").toUpperCase());
		verifyPartialText(WebTableFunctions.getCellElement(PricingTable, 0, 1),getLocalePropertyValue("lblFOPriceSectionNAVChange").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(PricingTable, 1, 0),"[$€]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}",getLocalePropertyValue("lblFOPriceSectionNAV").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(PricingTable, 1, 1),"-*[$€]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}",getLocalePropertyValue("lblFOPriceSectionNAVChange").toUpperCase());
		List<WebElement> colorEle = locateElements("xpath", localeProp.getProperty("objFOPriceSectionNAVChangePercentage"));
		WebElement actualColorEle = null;
		for(WebElement ele : colorEle) {
			if(ele.isDisplayed()) {
				actualColorEle=ele;
			break;
			}
		}
		if(WebTableFunctions.getCellElement(PricingTable, 1, 1).getText().substring(0,1).equals("-")) {
			
			String EleColor = TextFormatFunctions.getElementColor(actualColorEle);
			if(EleColor.equals("#cb0000")) {
			reportStep("NAV change - Color property for Loss(Red) is displayed as Expected","PASS");
			}
			else {
			reportStep("NAV change - Color property for Loss(Red) is not displayed as Expected","FAIL");	
			}
		}
		else {
			String EleColor = TextFormatFunctions.getElementColor(actualColorEle);
			if(EleColor.equals("#4e9d2d") || EleColor.equals("#808080") ) {
			reportStep("NAV change - Color property(Green(gain or Gray(No change) is displayed as Expected","PASS");
			}
			else {
			reportStep("NAV change - Color property(Green(gain or Gray(No change) is not displayed as Expected","FAIL");	
			}	
		}
		
		return this;
	}

	public FundOverviewPage validateOEICFundInfo() throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page OEIC Fund Information attributes","INFO");
		WebElement FundInfoTable =locateElement("xpath", localeProp.getProperty("objFOFundInfoTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", FundInfoTable);
		if(sCountryName.equals("UK")) {
			//Extra validations only for UK site
			verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 8, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl9"),getLocalePropertyValue("lblFOFundInfoSectionLbl9"));
			verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 9, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl10"),getLocalePropertyValue("lblFOFundInfoSectionLbl10"));
			reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl9")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 8, 1).getText(),"INFO");
			reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl10")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 9, 1).getText(),"INFO");
		}
		return this;
	}
	
	public FundOverviewPage validateFundInfoSection(HashMap<String,String> excelData) throws InterruptedException {
		 switch(sCountryName) {
			case "UK": 			validateFundInfoSectionLux(excelData);
								break;
			case "Italy": 		validateFundInfoSectionGerman(excelData);
								break;
			case "Luxembourg":	validateFundInfoSectionLux(excelData); 
					 			break;
			case "German":		validateFundInfoSectionGerman(excelData); 
								break;
			case "Switzerland":	validateFundInfoSectionSwitzerland(excelData); 
								break;
			case "Singapore":	validateFundInfoSectionLux(excelData);
								break;
			case "Austria":		validateFundInfoSectionGerman(excelData); 
								break;
			case "Offshore":	validateFundInfoSectionLux(excelData); 
								break;
			case "Canada":	    validateFundInfoSectionCanada(excelData); 
								break;
			default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
								break;
			}
				return this;
			
	}
	
	public FundOverviewPage validateFundInfoSectionLux(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Information Section","INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objFOFundInfoSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader").toUpperCase());
		
		WebElement FundInfoTable =locateElement("xpath", localeProp.getProperty("objFOFundInfoTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", FundInfoTable);
		validateLabelwithAsOfDateMonthly(WebTableFunctions.getCellElement(FundInfoTable, 0, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl1"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 1, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl2"),getLocalePropertyValue("lblFOFundInfoSectionLbl2"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 2, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl3"),getLocalePropertyValue("lblFOFundInfoSectionLbl3"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 3, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl4"),getLocalePropertyValue("lblFOFundInfoSectionLbl4"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 4, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl5"),getLocalePropertyValue("lblFOFundInfoSectionLbl5"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 5, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl6"),getLocalePropertyValue("lblFOFundInfoSectionLbl6"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 6, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl7"),getLocalePropertyValue("lblFOFundInfoSectionLbl7"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 7, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl8"),getLocalePropertyValue("lblFOFundInfoSectionLbl8"));
		
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 0, 1),"[$€]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2} ("+getLocalePropertyValue("lblMillion")+"|"+getLocalePropertyValue("lblBillion")+")",getLocalePropertyValue("lblFOFundInfoSectionLbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 1, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFOFundInfoSectionLbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 2, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFOFundInfoSectionLbl3").toUpperCase());
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 3, 1),excelData.get("Base Currency"),getLocalePropertyValue("lblFOFundInfoSectionLbl4"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 4, 1),excelData.get("Share Class Currency"),getLocalePropertyValue("lblFOFundInfoSectionLbl5"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 5, 1),excelData.get("Benchmark"),getLocalePropertyValue("lblFOFundInfoSectionLbl6"));
		reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl7")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 6, 1).getText(),"INFO");
		reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl8")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 7, 1).getText(),"INFO");
	
		return this;
	}
	
	public FundOverviewPage validateFundInfoSectionCanada(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Information Section","INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objFOFundInfoSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader").toUpperCase());
		
		WebElement FundInfoTable =locateElement("xpath", localeProp.getProperty("objFOFundInfoTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", FundInfoTable);
		validateLabelwithAsOfDateMonthly(WebTableFunctions.getCellElement(FundInfoTable, 0, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl1"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 1, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl2"),getLocalePropertyValue("lblFOFundInfoSectionLbl2"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 2, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl3"),getLocalePropertyValue("lblFOFundInfoSectionLbl3"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 3, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl4"),getLocalePropertyValue("lblFOFundInfoSectionLbl4"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 4, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl5"),getLocalePropertyValue("lblFOFundInfoSectionLbl5"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 5, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl6"),getLocalePropertyValue("lblFOFundInfoSectionLbl6"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 6, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl7"),getLocalePropertyValue("lblFOFundInfoSectionLbl7"));
		//verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 7, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl8"),getLocalePropertyValue("lblFOFundInfoSectionLbl8"));
		
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 0, 1),"[$€]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2} ("+getLocalePropertyValue("lblMillion")+"|"+getLocalePropertyValue("lblBillion")+")",getLocalePropertyValue("lblFOFundInfoSectionLbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 1, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFOFundInfoSectionLbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 2, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFOFundInfoSectionLbl3").toUpperCase());
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 3, 1),excelData.get("Base Currency"),getLocalePropertyValue("lblFOFundInfoSectionLbl4"));
		reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl5")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 4, 1).getText(),"INFO");
		reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl6")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 5, 1).getText(),"INFO");
		reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl7")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 6, 1).getText(),"INFO");
		//reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl8")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 7, 1).getText(),"INFO");
	
		return this;
	}

	public FundOverviewPage validateFundInfoSectionGerman(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Information Section","INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objFOFundInfoSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader").toUpperCase());
		
		WebElement FundInfoTable =locateElement("xpath", localeProp.getProperty("objFOFundInfoTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", FundInfoTable);
		validateLabelwithAsOfDateMonthly(WebTableFunctions.getCellElement(FundInfoTable, 0, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl1"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 1, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl2"),getLocalePropertyValue("lblFOFundInfoSectionLbl2"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 2, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl3"),getLocalePropertyValue("lblFOFundInfoSectionLbl3"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 3, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl4"),getLocalePropertyValue("lblFOFundInfoSectionLbl4"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 4, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl5"),getLocalePropertyValue("lblFOFundInfoSectionLbl5"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 5, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl6"),getLocalePropertyValue("lblFOFundInfoSectionLbl6"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 6, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl7"),getLocalePropertyValue("lblFOFundInfoSectionLbl7"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 7, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl8"),getLocalePropertyValue("lblFOFundInfoSectionLbl8"));
				
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 0, 1),"[$€]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2} ("+getLocalePropertyValue("lblMillion")+"|"+getLocalePropertyValue("lblBillion")+")",getLocalePropertyValue("lblFOFundInfoSectionLbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 1, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFOFundInfoSectionLbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 2, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFOFundInfoSectionLbl3").toUpperCase());
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 3, 1),excelData.get("Base Currency"),getLocalePropertyValue("lblFOFundInfoSectionLbl4"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 4, 1),excelData.get("Share Class Currency"),getLocalePropertyValue("lblFOFundInfoSectionLbl5"));
		reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl6")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 5, 1).getText(),"INFO");
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 6, 1),excelData.get("Benchmark"),getLocalePropertyValue("lblFOFundInfoSectionLbl7"));
		reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl8")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 7, 1).getText(),"INFO");
		return this;
	}
	
	public FundOverviewPage validateFundInfoSectionSwitzerland(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Information Section","INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objFOFundInfoSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader").toUpperCase());
		
		WebElement FundInfoTable =locateElement("xpath", localeProp.getProperty("objFOFundInfoTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", FundInfoTable);
		validateLabelwithAsOfDateMonthly(WebTableFunctions.getCellElement(FundInfoTable, 0, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl1"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 1, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl2"),getLocalePropertyValue("lblFOFundInfoSectionLbl2"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 2, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl3"),getLocalePropertyValue("lblFOFundInfoSectionLbl3"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 3, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl4"),getLocalePropertyValue("lblFOFundInfoSectionLbl4"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 4, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl5"),getLocalePropertyValue("lblFOFundInfoSectionLbl5"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 5, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl6"),getLocalePropertyValue("lblFOFundInfoSectionLbl6"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 6, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl7"),getLocalePropertyValue("lblFOFundInfoSectionLbl7"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 7, 0),getLocalePropertyValue("lblFOFundInfoSectionLbl8"),getLocalePropertyValue("lblFOFundInfoSectionLbl8"));
		
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 0, 1),"[$€]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2} ("+getLocalePropertyValue("lblMillion")+"|"+getLocalePropertyValue("lblBillion")+")",getLocalePropertyValue("lblFOFundInfoSectionLbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 1, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFOFundInfoSectionLbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(FundInfoTable, 2, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFOFundInfoSectionLbl3").toUpperCase());
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 3, 1),excelData.get("Base Currency"),getLocalePropertyValue("lblFOFundInfoSectionLbl4"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 4, 1),excelData.get("Share Class Currency"),getLocalePropertyValue("lblFOFundInfoSectionLbl5"));
		verifyExactText(WebTableFunctions.getCellElement(FundInfoTable, 5, 1),excelData.get("Benchmark"),getLocalePropertyValue("lblFOFundInfoSectionLbl6"));
		reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl7")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 6, 1).getText(),"INFO");
		reportStep(getLocalePropertyValue("lblFOFundInfoSectionLbl8")+" Value is displayed as :"+WebTableFunctions.getCellElement(FundInfoTable, 7, 1).getText(),"INFO");
	
		return this;
	}
	public FundOverviewPage validateSalesChargeSection(HashMap<String,String> excelData) throws InterruptedException {
		
		switch(sCountryName) {
		case "UK": 			validateSalesChargeSectionUK(excelData);
							break;
		case "Italy": 		validateSalesChargeSectionItaly(excelData);
							break;
		case "Luxembourg":	validateSalesChargeSectionLux(excelData); 
				 			break;
		case "German":		validateSalesChargeSectionLux(excelData); 
							break;	
		case "Switzerland":	validateSalesChargeSectionSwitzerland(excelData); 
							break;	
		case "Singapore":	validateSalesChargeSectionItaly(excelData); 
							break;	
		case "Austria":		validateSalesChargeSectionLux(excelData); 
							break;	
		case "Offshore":	validateSalesChargeSectionLux(excelData); 
							break;
		case "Canada":		validateSalesChargeSectionUK(excelData); 
							break;
		default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
							break;
		}
		return this;
	}

	public FundOverviewPage validateSalesChargeSectionLux(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Sales Charge Section","INFO");
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFOSalesChargeSectionHeader")),getLocalePropertyValue("lblFOSalesChargeSectionHeader").toUpperCase());
		
		WebElement SalesChargeTable =locateElement("xpath", localeProp.getProperty("objFOSalesChargeTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", SalesChargeTable);
		
		verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 0, 0),getLocalePropertyValue("lblFOSalesChargeSectionlbl1"));
		verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 1, 0),getLocalePropertyValue("lblFOSalesChargeSectionlbl2"));
		verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 2, 0),getLocalePropertyValue("lblFOSalesChargeSectionlbl3"));
		verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 3, 0),getLocalePropertyValue("lblFOSalesChargeSectionlbl4"));
		
		validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionlbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 1, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionlbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 2, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionlbl3").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 3, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionlbl4").toUpperCase());
		
		return this;
	}
	
	public FundOverviewPage validateSalesChargeSectionUK(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Sales Charge Section","INFO");
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFOSalesChargeSectionHeader")),getLocalePropertyValue("lblFOSalesChargeSectionHeader").toUpperCase());
		
		WebElement SalesChargeTable =locateElement("xpath", localeProp.getProperty("objFOSalesChargeTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", SalesChargeTable);
		
			verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 0, 0),getLocalePropertyValue("lblFOSalesChargeSectionOngoingCharges"));
			validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionOngoingCharges").toUpperCase());
		
		
		return this;
	}
	
	public FundOverviewPage validateSalesChargeSectionCanada(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Sales Charge Section","INFO");
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFOSalesChargeSectionHeader")),getLocalePropertyValue("lblFOSalesChargeSectionHeader").toUpperCase());
		
		WebElement SalesChargeTable =locateElement("xpath", localeProp.getProperty("objFOSalesChargeTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", SalesChargeTable);
		
			verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 0, 0),getLocalePropertyValue("lblFOSalesChargeSectionOngoingCharges"));
			validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionOngoingCharges").toUpperCase());
		
		
		return this;
	}
	
	public FundOverviewPage validateSalesChargeSectionItaly(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Sales Charge Section","INFO");
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFOSalesChargeSectionHeader")),getLocalePropertyValue("lblFOSalesChargeSectionHeader").toUpperCase());
		
		WebElement SalesChargeTable =locateElement("xpath", localeProp.getProperty("objFOSalesChargeTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", SalesChargeTable);
		
		verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 0, 0),getLocalePropertyValue("lblFOSalesChargeSectionlbl1"));
		verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 1, 0),getLocalePropertyValue("lblFOSalesChargeSectionlbl2"));
		verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 2, 0),getLocalePropertyValue("lblFOSalesChargeSectionlbl3"));
		
		validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionlbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 1, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionlbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 2, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionlbl3").toUpperCase());
		
		return this;
	}
	
	public FundOverviewPage validateSalesChargeSectionSwitzerland(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Sales Charge Section","INFO");
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFOSalesChargeSectionHeader")),getLocalePropertyValue("lblFOSalesChargeSectionHeader").toUpperCase());
		
		WebElement SalesChargeTable =locateElement("xpath", localeProp.getProperty("objFOSalesChargeTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", SalesChargeTable);
		
		verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 0, 0),getLocalePropertyValue("lblFOSalesChargeSectionlbl1"));
		verifyPartialText(WebTableFunctions.getCellElement(SalesChargeTable, 1, 0),getLocalePropertyValue("lblFOSalesChargeSectionlbl2"));
			
		validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionlbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(SalesChargeTable, 1, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFOSalesChargeSectionlbl2").toUpperCase());
		return this;
	}
	
	public FundOverviewPage validateFundIdentifiersSection(HashMap<String,String> excelData) throws InterruptedException {
		switch(sCountryName) {
		case "UK": 			validateFundIdentifiersSectionUK(excelData);
							break;
		case "Italy": 		validateFundIdentifiersSectionItaly(excelData);
							break;
		case "Luxembourg":	validateFundIdentifiersSectionLux(excelData); 
				 			break;
		case "German":		validateFundIdentifiersSectionGerman(excelData); 
							break;	
		case "Switzerland":	validateFundIdentifiersSectionSwitzerland(excelData); 
							break;
		case "Singapore":	validateFundIdentifiersSectionUK(excelData); 
							break;
		case "Austria":		validateFundIdentifiersSectionGerman(excelData); 
							break;	
		case "Offshore":	validateFundIdentifiersSectionOffshore(excelData); 
							break;
		case "Canada":	    validateFundIdentifiersSectionCanada(excelData); 
							break;
		default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
							break;
		}

			return this;
		}
	
	public FundOverviewPage validateFundIdentifiersSectionLux(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Identifiers Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOIdentifiersSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader"));
		
		WebElement identifiersSectionTable =locateElement("xpath", localeProp.getProperty("objFOIdentifiersSectionTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", identifiersSectionTable);
		
		
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"));

				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[A-Z0-9]{12}",getLocalePropertyValue("lblFOIdentifiersSectionlbl1").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"[A-Z]{7} [A-Z]{2}",getLocalePropertyValue("lblFOIdentifiersSectionlbl2").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 1),"[A-Z0-9]{7}",getLocalePropertyValue("lblFOIdentifiersSectionlbl3").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 1),"[0-9]{4}",getLocalePropertyValue("lblFOIdentifiersSectionlbl4").toUpperCase());	
			
			
			return this;
	}
	
	public FundOverviewPage validateFundIdentifiersSectionCanada(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Identifiers Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOIdentifiersSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader"));
		
		
		WebElement identifiersSectionTable =locateElement("xpath", localeProp.getProperty("objFOIdentifiersSectionTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", identifiersSectionTable);
		
		reportStep("Validating Fund Identifier data value Formats - Series A & T funds","INFO");
		
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"));

				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[0-9]{3,4}",getLocalePropertyValue("lblFOIdentifiersSectionlbl1").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"[0-9]{3,4}",getLocalePropertyValue("lblFOIdentifiersSectionlbl2").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 1),"[0-9]{3,4}",getLocalePropertyValue("lblFOIdentifiersSectionlbl3").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 1),"CAD|USD",getLocalePropertyValue("lblFOIdentifiersSectionlbl4").toUpperCase());	
		
				reportStep("Validating Fund Identifier data value Formats - Non Series A & T funds","INFO");
				
				WebElement shareClass = locateElement("xpath",getLocalePropertyValue("objFOShareClassDrpDowndiv"));
				shareClass.click();
				List<WebElement> shareClassList = shareClass.findElements(By.tagName("li"));
				for(WebElement sc:shareClassList) {
					if(sc.getText().contains(getLocalePropertyValue("lblFOSeriesOSelectText"))) {
						sc.click();
					break;
					}
				}
				
				identifiersSectionTable =locateElement("xpath", localeProp.getProperty("objFOIdentifiersSectionTable"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlblFundCode"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
				//made fix for ADM field 8/28
				if(identifiersSectionTable.findElements(By.tagName("tr")).size()==3) {
					verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl5"),getLocalePropertyValue("lblFOIdentifiersSectionlbl5"));
					verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"));
					}
				else
					verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"));
					
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[0-9]{4}",getLocalePropertyValue("lblFOIdentifiersSectionlblFundCode").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"CAD|USD",getLocalePropertyValue("lblFOIdentifiersSectionlbl4").toUpperCase());
				
				reportStep("Validating Fund Identifier data value Formats - Series V funds","INFO");
				
		
				 shareClass = locateElement("xpath",getLocalePropertyValue("objFOShareClassDrpDowndiv"));
				shareClass.click();
				shareClassList = shareClass.findElements(By.tagName("li"));
				for(WebElement sc:shareClassList) {
					if(sc.getText().contains(getLocalePropertyValue("lblFOSeriesVSelectText"))) {
						sc.click();
					break;
					}
				}
				Thread.sleep(3000);
				identifiersSectionTable =locateElement("xpath", localeProp.getProperty("objFOIdentifiersSectionTable"));
				
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlblFundCode"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
				//made fix for ADM field 8/28
				if(identifiersSectionTable.findElements(By.tagName("tr")).size()==3) {
					verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl5"),getLocalePropertyValue("lblFOIdentifiersSectionlbl5"));
					verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"));
					}
				else
					verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"));
					
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[0-9]{4}",getLocalePropertyValue("lblFOIdentifiersSectionlblFundCode").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"CAD|USD",getLocalePropertyValue("lblFOIdentifiersSectionlbl4").toUpperCase());
				
				//changed on 8/28
//				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
//				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"));
//				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"));
//				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"));
//
//				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[0-9]{3,4}",getLocalePropertyValue("lblFOIdentifiersSectionlbl1").toUpperCase());
//				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"[0-9]{3,4}|—",getLocalePropertyValue("lblFOIdentifiersSectionlbl2").toUpperCase());
//				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 1),"[0-9]{3,4}|—",getLocalePropertyValue("lblFOIdentifiersSectionlbl3").toUpperCase());
//				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 1),"CAD|USD",getLocalePropertyValue("lblFOIdentifiersSectionlbl4").toUpperCase());	
		
			return this;
	}
	
	public FundOverviewPage validateFundIdentifiersSectionOffshore(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Identifiers Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOIdentifiersSectionHeader"),getLocalePropertyValue("lblFOIdentifiersSectionHeader"));
		
		WebElement identifiersSectionTable =locateElement("xpath", localeProp.getProperty("objFOIdentifiersSectionTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", identifiersSectionTable);
		
		
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"));

				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[0-9]{4}",getLocalePropertyValue("lblFOIdentifiersSectionlbl1").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"[A-Z0-9]{12}",getLocalePropertyValue("lblFOIdentifiersSectionlbl2").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 1),"[A-Z]{7} [A-Z]{2}",getLocalePropertyValue("lblFOIdentifiersSectionlbl3").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 1),"[A-Z0-9 ]{11}",getLocalePropertyValue("lblFOIdentifiersSectionlbl4").toUpperCase());	
				
			
			return this;
	}
	
	public FundOverviewPage validateFundIdentifiersSectionItaly(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Identifiers Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOIdentifiersSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader"));
		
		WebElement identifiersSectionTable =locateElement("xpath", localeProp.getProperty("objFOIdentifiersSectionTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", identifiersSectionTable);
		
		
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"));
				
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[A-Z0-9]{12}",getLocalePropertyValue("lblFOIdentifiersSectionlbl1").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"[A-Z]{7} [A-Z]{2}",getLocalePropertyValue("lblFOIdentifiersSectionlbl2").toUpperCase());
			
			return this;
	}
	
	public FundOverviewPage validateFundIdentifiersSectionGerman(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Identifiers Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOIdentifiersSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader"));
		
		WebElement identifiersSectionTable =locateElement("xpath", localeProp.getProperty("objFOIdentifiersSectionTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", identifiersSectionTable);
		
	
			verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
			verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"));
			verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"));
			
			validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[A-Z0-9]{6}",getLocalePropertyValue("lblFOIdentifiersSectionlbl1").toUpperCase());
			validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"[A-Z0-9]{12}",getLocalePropertyValue("lblFOIdentifiersSectionlbl2").toUpperCase());
			validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 1),"[A-Z]{7} [A-Z]{2}",getLocalePropertyValue("lblFOIdentifiersSectionlbl3").toUpperCase());
			
			
			return this;
	}
	
	public FundOverviewPage validateFundIdentifiersSectionSwitzerland(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Identifiers Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOIdentifiersSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader"));
		
		WebElement identifiersSectionTable =locateElement("xpath", localeProp.getProperty("objFOIdentifiersSectionTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", identifiersSectionTable);
		
	
			verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
			verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"));
			verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"));
			
			validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[0-9]{8}",getLocalePropertyValue("lblFOIdentifiersSectionlbl1").toUpperCase());
			validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"[A-Z0-9]{12}",getLocalePropertyValue("lblFOIdentifiersSectionlbl2").toUpperCase());
			validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 1),"[A-Z]{7} [A-Z]{2}",getLocalePropertyValue("lblFOIdentifiersSectionlbl3").toUpperCase());
			
			
			return this;
	}
	public FundOverviewPage validateFundIdentifiersSectionUK(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Identifiers Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOIdentifiersSectionHeader"),getLocalePropertyValue("lblFOFundInfoSectionHeader"));
		
		WebElement identifiersSectionTable =locateElement("xpath", localeProp.getProperty("objFOIdentifiersSectionTable"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", identifiersSectionTable);
		
		
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"),getLocalePropertyValue("lblFOIdentifiersSectionlbl1"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"),getLocalePropertyValue("lblFOIdentifiersSectionlbl2"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"),getLocalePropertyValue("lblFOIdentifiersSectionlbl3"));
				verifyExactText(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 0),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"),getLocalePropertyValue("lblFOIdentifiersSectionlbl4"));

				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 0, 1),"[0-9]{4}",getLocalePropertyValue("lblFOIdentifiersSectionlbl1").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 1, 1),"[A-Z0-9]{12}",getLocalePropertyValue("lblFOIdentifiersSectionlbl2").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 2, 1),"[A-Z]{7} [A-Z]{2}",getLocalePropertyValue("lblFOIdentifiersSectionlbl3").toUpperCase());
				validateFormat(WebTableFunctions.getCellElement(identifiersSectionTable, 3, 1),"[A-Z0-9]{7}",getLocalePropertyValue("lblFOIdentifiersSectionlbl4").toUpperCase());	
			
			return this;
	}
	public FundOverviewPage validateRatingsSections(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Ratings Section","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFORatingsSectionHeader")));
		verifyElementExist("xpath", getLocalePropertyValue("objFORatingsSectionHeader"),getLocalePropertyValue("lblFORatingsSectionHeader"));
		
		validateLabelwithAsOfDateMonthlyftLabel(locateElement("xpath", getLocalePropertyValue("objFORatingsSectionSubTitle")),getLocalePropertyValue("lblFORatingsSectionSubTitle"));
		verifyPartialText(locateElement("xpath", getLocalePropertyValue("objFORatingsSectionRatingCategoryHeader")),getLocalePropertyValue("lblFORatingsSectionRatingCategoryHeader"));
		verifyPartialText(locateElement("xpath", getLocalePropertyValue("objFORatingsSectionRatingCategoryValue")),getLocalePropertyValue("lblFORatingsSectionRatingCategoryValue"));
		
		return this;
	}
	
	public FundOverviewPage validateLipperRatingsSections() throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Lipper Ratings Section","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFORatingsSectionHeader")));
		
		WebElement lipperRatingHeader =locateElement("xpath", localeProp.getProperty("objFOLipperRatingsSectionHeader"));	
		List<WebElement> spanEle = WebTableFunctions.getAllTagsInsideAnElement(lipperRatingHeader, "span");
		List<WebElement> ftLabelEle = WebTableFunctions.getAllTagsInsideAnElement(lipperRatingHeader, "ft-label");
		String spanEleText1 = spanEle.get(0).getText();
		String spanEleText3 = ftLabelEle.get(0).getText();
		String spanEleText4 = ftLabelEle.get(1).getText();
		String spanEleText5 = ftLabelEle.get(2).getText();
		if (spanEleText1.contains(getLocalePropertyValue("lblFOLipperRatingsSectionHeader").toUpperCase()) && spanEleText3.contains(getLocalePropertyValue("lblDateFormatPart1")) && spanEleText5.contains(getLocalePropertyValue("lblDateFormatPart3")) && spanEleText4.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(getLocalePropertyValue("lblFOLipperRatingsSectionHeader")+" Label is displayed as Expected: "+lipperRatingHeader.getText(),"PASS");
		else
			reportStep(getLocalePropertyValue("lblFOLipperRatingsSectionHeader")+" Label is not displayed as Expected: "+lipperRatingHeader.getText(),"FAIL");	
		
		List<WebElement> lipperRatingValuesList = locateElements("xpath", getLocalePropertyValue("objFOLipperRatingsNumericValues"));
		List<WebElement> lipperRatingdescList = locateElements("xpath", getLocalePropertyValue("objFOLipperRatingsDescription"));
		for(int i=0;i<lipperRatingValuesList.size();i++) {
			verifyPartialText(lipperRatingdescList.get(i),getLocalePropertyValue("lblFOLipperRatingsDesc"+(i+1)).toUpperCase());
			validateFormat(lipperRatingValuesList.get(i),"[0-9]",getLocalePropertyValue("lblFOLipperRatingsDesc"+(i+1)).toUpperCase());
		}
		
		return this;
	}
	
	public FundOverviewPage validateFundManagersSection(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Fund Managers Section","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFOFundMgrSectionHeader")));
		verifyElementExist("xpath", getLocalePropertyValue("objFOFundMgrSectionHeader"),getLocalePropertyValue("lblFOFundMgrSectionHeader"));
		
		List<WebElement> fundMgrsList = locateElements("xpath",getLocalePropertyValue("objFOFundMgrsList"));
		for(int i=0;i<fundMgrsList.size();i++) {
			WebElement fundMgr = fundMgrsList.get(i);
			List<WebElement> fundMgrNameList = WebTableFunctions.getAllTagsInsideAnElement(fundMgr, "h3");
			List<WebElement> fundMgrattributesList = WebTableFunctions.getAllTagsInsideAnElement(fundMgr, "li");
			
			if(fundMgrNameList.get(0).getText().contains(excelData.get("Fund Mgr"+(i+1)).split(";")[0])) 
				reportStep("Fund Manager Name is displayed as Expected: "+fundMgrNameList.get(0).getText(),"PASS",true);
			else
				reportStep("Fund Manager Name is not displayed as Expected: "+fundMgrNameList.get(0).getText(),"FAIL",true);	
			
			if(fundMgrattributesList.get(0).getText().contains(excelData.get("Fund Mgr"+(i+1)).split(";")[1])) 
				reportStep("Fund Manager Info Line1 is displayed as Expected: "+fundMgrattributesList.get(0).getText(),"PASS",true);
			else
				reportStep("Fund Manager Info Line1 is not displayed as Expected: "+fundMgrattributesList.get(0).getText(),"FAIL",true);
			
			if(fundMgrattributesList.get(1).getText().contains(excelData.get("Fund Mgr"+(i+1)).split(";")[2])) 
				reportStep("Fund Manager Info Line2 is displayed as Expected: "+fundMgrattributesList.get(1).getText(),"PASS",true);
			else
				reportStep("Fund Manager Info Line2 is not displayed as Expected: "+fundMgrattributesList.get(1).getText(),"FAIL",true);	
			
			if(fundMgrattributesList.get(2).getText().contains(excelData.get("Fund Mgr"+(i+1)).split(";")[3])) 
				reportStep("Fund Manager Info Line3 is displayed as Expected: "+fundMgrattributesList.get(2).getText(),"PASS",true);
			else
				reportStep("Fund Manager Info Line3 is not displayed as Expected: "+fundMgrattributesList.get(2).getText(),"FAIL",true);	
			
		}
		
		return this;
	}

	public FundOverviewPage validateDividendDistributionSection(HashMap<String,String> excelData) throws InterruptedException {
		 switch(sCountryName) {
			case "UK": 			validateDividendDistributionSectionLux(excelData);
								break;
			case "Italy": 		validateDividendDistributionSectionLux(excelData);
								break;
			case "Luxembourg":	validateDividendDistributionSectionLux(excelData); 
					 			break;
			case "German":		validateDividendDistributionSectionLux(excelData); 
								break;
			case "Switzerland":	validateDividendDistributionSectionSwitzerland(excelData); 
								break;
			case "Singapore":	validateDividendDistributionSectionSingapore(excelData); 
								break;
			case "Austria":		validateDividendDistributionSectionAustria(excelData); 
								break;
			case "Offshore":	validateDividendDistributionSectionLux(excelData); 
 								break;
			case "Canada":	    validateDividendDistributionSectionCanada(excelData); 
								break;
			default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
								break;
			}
				return this;
			
		}
	
	public FundOverviewPage validateDividendDistributionSectionLux(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Dividend & Distribution Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFODividendsSectionHeader"),getLocalePropertyValue("lblFODividendsSectionHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFODividendsSectionHeader")));
		WebElement dividendSectionTable =locateElement("xpath", localeProp.getProperty("objFODividendsSectionTable"));
		
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 0, 0),getLocalePropertyValue("lblFODividendsSectionlbl1"),getLocalePropertyValue("lblFODividendsSectionlbl1"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 1, 0),getLocalePropertyValue("lblFODividendsSectionlbl2"),getLocalePropertyValue("lblFODividendsSectionlbl2"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 2, 0),getLocalePropertyValue("lblFODividendsSectionlbl3"),getLocalePropertyValue("lblFODividendsSectionlbl3"));
		
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 0, 1),"["+excelData.get("Currency")+"]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{3}",getLocalePropertyValue("lblFODividendsSectionlbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 1, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFODividendsSectionlbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 2, 1),getLocalePropertyValue("lblFrequency"),getLocalePropertyValue("lblFODividendsSectionlbl3").toUpperCase());
		
		verifyElementExist("xpath", getLocalePropertyValue("objFODistYieldSectionHeader"),getLocalePropertyValue("lblFODistYieldSectionHeader").toUpperCase());
		
		WebElement distributionSectionTable =locateElement("xpath", localeProp.getProperty("objFODistYieldSectionTable"));
		
		
		List<WebElement> spanEle = WebTableFunctions.getAllTagsInsideAnElement(WebTableFunctions.getCellElement(distributionSectionTable, 0, 0), "span");
	
		String spanEleText1 = spanEle.get(0).getText();
			int k=1;
			if(spanEle.get(k).getAttribute("class").equals("footnote"))
				k++;
	
			String spanEleText2 = spanEle.get(k).getText();
			String spanEleText3 = spanEle.get(k+1).getText();
			String spanEleText4 = spanEle.get(k+2).getText();
		//9/20 - Changed as per CR
		//String spanEleText5 = spanEle.get(4).getText();
		if (spanEleText1.contains(getLocalePropertyValue("lblFODistYieldSectionHeader")) && spanEleText2.contains(getLocalePropertyValue("lblDateFormatPart1")) && spanEleText4.contains(getLocalePropertyValue("lblDateFormatPart3")) && spanEleText3.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(getLocalePropertyValue("lblFODistYieldSectionHeader")+" Label is displayed as Expected: "+WebTableFunctions.getCellElement(distributionSectionTable, 0, 0).getText(),"PASS",true);
		else
			reportStep(getLocalePropertyValue("lblFODistYieldSectionHeader")+" Label is not displayed as Expected: "+WebTableFunctions.getCellElement(distributionSectionTable, 0, 0).getText(),"FAIL",true);	
		
		validateFormat(WebTableFunctions.getCellElement(distributionSectionTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFODistYieldSectionHeader").toUpperCase());
		
		return this;
		}
	
	public FundOverviewPage validateDividendDistributionSectionAustria(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Dividend & Distribution Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFODividendsSectionHeader"),getLocalePropertyValue("lblFODividendsSectionHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFODividendsSectionHeader")));
		WebElement dividendSectionTable =locateElement("xpath", localeProp.getProperty("objFODividendsSectionTable"));
		
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 0, 0),getLocalePropertyValue("lblFODividendsSectionlbl1"),getLocalePropertyValue("lblFODividendsSectionlbl1"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 1, 0),getLocalePropertyValue("lblFODividendsSectionlbl2"),getLocalePropertyValue("lblFODividendsSectionlbl2"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 2, 0),getLocalePropertyValue("lblFODividendsSectionlbl3"),getLocalePropertyValue("lblFODividendsSectionlbl3"));
		
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 0, 1),"["+excelData.get("Currency")+"]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{3}",getLocalePropertyValue("lblFODividendsSectionlbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 1, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFODividendsSectionlbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 2, 1),getLocalePropertyValue("lblFrequency"),getLocalePropertyValue("lblFODividendsSectionlbl3").toUpperCase());
		
		verifyElementExist("xpath", getLocalePropertyValue("objFODistYieldSectionHeader"),getLocalePropertyValue("lblFODistYieldSectionHeader").toUpperCase());
		
		WebElement distributionSectionTable =locateElement("xpath", localeProp.getProperty("objFODistYieldSectionTable"));
		
		
		List<WebElement> spanEle = WebTableFunctions.getAllTagsInsideAnElement(WebTableFunctions.getCellElement(distributionSectionTable, 0, 0), "span");
		String spanEleText1 = spanEle.get(0).getText();
		
		int k=1;
		if(spanEle.get(k).getAttribute("class").equals("footnote"))
			k++;
		String spanEleText2 = spanEle.get(k).getText();
		String spanEleText3 = spanEle.get(k+1).getText();
		String spanEleText4 = spanEle.get(k+2).getText();
		
		if (spanEleText1.contains(getLocalePropertyValue("lblFODistYieldSectionHeader")) && spanEleText2.contains(getLocalePropertyValue("lblDateFormatPart1")) && spanEleText4.contains(getLocalePropertyValue("lblDateFormatPart3")) && spanEleText3.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(getLocalePropertyValue("lblFODistYieldSectionHeader")+" Label is displayed as Expected: "+WebTableFunctions.getCellElement(distributionSectionTable, 0, 0).getText(),"PASS");
		else
			reportStep(getLocalePropertyValue("lblFODistYieldSectionHeader")+" Label is not displayed as Expected: "+WebTableFunctions.getCellElement(distributionSectionTable, 0, 0).getText(),"FAIL");	
		
		validateFormat(WebTableFunctions.getCellElement(distributionSectionTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFODistYieldSectionHeader").toUpperCase());
		
		return this;
		}
	
	public FundOverviewPage validateDividendDistributionSectionSingapore(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Dividend & Distribution Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFODividendsSectionHeader"),getLocalePropertyValue("lblFODividendsSectionHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFODividendsSectionHeader")));
		WebElement dividendSectionTable =locateElement("xpath", localeProp.getProperty("objFODividendsSectionTable"));
		
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 0, 0),getLocalePropertyValue("lblFODividendsSectionlbl1"),getLocalePropertyValue("lblFODividendsSectionlbl1"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 1, 0),getLocalePropertyValue("lblFODividendsSectionlbl2"),getLocalePropertyValue("lblFODividendsSectionlbl2"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 2, 0),getLocalePropertyValue("lblFODividendsSectionlbl3"),getLocalePropertyValue("lblFODividendsSectionlbl3"));
		
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 0, 1),"["+excelData.get("Currency")+"]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{3}",getLocalePropertyValue("lblFODividendsSectionlbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 1, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFODividendsSectionlbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 2, 1),getLocalePropertyValue("lblFrequency"),getLocalePropertyValue("lblFODividendsSectionlbl3").toUpperCase());
		
		return this;
		}
	
	
	public FundOverviewPage validateDividendDistributionSectionCanada(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Dividend & Distribution Section","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFODividendsSectionHeader"),getLocalePropertyValue("lblFODividendsSectionHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFODividendsSectionHeader")));
		WebElement dividendSectionTable =locateElement("xpath", localeProp.getProperty("objFODividendsSectionTable"));
		
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 0, 0),getLocalePropertyValue("lblFODividendsSectionlbl1"),getLocalePropertyValue("lblFODividendsSectionlbl1"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 1, 0),getLocalePropertyValue("lblFODividendsSectionlbl2"),getLocalePropertyValue("lblFODividendsSectionlbl2"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 2, 0),getLocalePropertyValue("lblFODividendsSectionlbl3"),getLocalePropertyValue("lblFODividendsSectionlbl3"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 3, 0),getLocalePropertyValue("lblFODividendsSectionlbl4"),getLocalePropertyValue("lblFODividendsSectionlbl4"));
		
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 0, 1),"["+excelData.get("Currency")+"]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{3}",getLocalePropertyValue("lblFODividendsSectionlbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 1, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFODividendsSectionlbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 2, 1),getLocalePropertyValue("lblFrequency"),getLocalePropertyValue("lblFODividendsSectionlbl3").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 3, 1),"["+excelData.get("Currency")+"]\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{3}",getLocalePropertyValue("lblFODividendsSectionlbl4").toUpperCase());
		
		return this;
		}
	public FundOverviewPage validateDividendDistributionSectionSwitzerland(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Dividend & Distribution Section","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFODividendsSectionHeader")));
		verifyElementExist("xpath", getLocalePropertyValue("objFODividendsSectionHeader"),getLocalePropertyValue("lblFODividendsSectionHeader"));
		
		WebElement dividendSectionTable =locateElement("xpath", localeProp.getProperty("objFODividendsSectionTable"));
		
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 0, 0),getLocalePropertyValue("lblFODividendsSectionlbl1"),getLocalePropertyValue("lblFODividendsSectionlbl1"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 1, 0),getLocalePropertyValue("lblFODividendsSectionlbl2"),getLocalePropertyValue("lblFODividendsSectionlbl2"));
		verifyExactText(WebTableFunctions.getCellElement(dividendSectionTable, 2, 0),getLocalePropertyValue("lblFODividendsSectionlbl3"),getLocalePropertyValue("lblFODividendsSectionlbl3"));
		
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 0, 1),"["+excelData.get("Currency")+"]\\d{1,2}"+getLocalePropertyValue("lblYearSeperator")+"\\d{3}",getLocalePropertyValue("lblFODividendsSectionlbl1").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 1, 1),"[0-3]\\d.\\d{2}.\\d{4}",getLocalePropertyValue("lblFODividendsSectionlbl2").toUpperCase());
		validateFormat(WebTableFunctions.getCellElement(dividendSectionTable, 2, 1),getLocalePropertyValue("lblFrequency"),getLocalePropertyValue("lblFODividendsSectionlbl3").toUpperCase());
		
		verifyElementExist("xpath", getLocalePropertyValue("objFODistYieldSectionHeader"),getLocalePropertyValue("lblFODistYieldSectionHeader").toUpperCase());
		
		WebElement distributionSectionTable =locateElement("xpath", localeProp.getProperty("objFODistYieldSectionTable"));
		
		
		List<WebElement> spanEle = WebTableFunctions.getAllTagsInsideAnElement(WebTableFunctions.getCellElement(distributionSectionTable, 0, 0), "span");
		String spanEleText1 = spanEle.get(0).getText();
		String spanEleText2 = spanEle.get(1).getText();
		String spanEleText3 = spanEle.get(2).getText();
		String spanEleText4 = spanEle.get(3).getText();
		//String spanEleText5 = spanEle.get(4).getText();
		if (spanEleText1.contains(getLocalePropertyValue("lblFODistYieldSectionHeader")) && spanEleText2.contains(getLocalePropertyValue("lblDateFormatPart1")) && spanEleText4.contains(getLocalePropertyValue("lblDateFormatPart3")) && spanEleText3.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(getLocalePropertyValue("lblFODistYieldSectionHeader")+" Label is displayed as Expected: "+WebTableFunctions.getCellElement(distributionSectionTable, 0, 0).getText(),"PASS");
		else
			reportStep(getLocalePropertyValue("lblFODistYieldSectionHeader")+" Label is not displayed as Expected: "+WebTableFunctions.getCellElement(distributionSectionTable, 0, 0).getText(),"FAIL");	
		
		validateFormat(WebTableFunctions.getCellElement(distributionSectionTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%",getLocalePropertyValue("lblFODistYieldSectionHeader").toUpperCase());
		
		return this;
		}

	public FundOverviewPage validatePerfSnpshtDiscretePerfSection(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Performance Snapshot-Discrete Performance Section Section","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFODiscretePerfSectionHeader")));
		
		WebElement perfHeader = locateElement("xpath",getLocalePropertyValue("objFODiscretePerfSectionHeader"));
		
		List<WebElement> discretePerfSectionHeader = WebTableFunctions.getAllTagsInsideAnElement(perfHeader, "ft-label");
	
		discretePerfSectionHeader.addAll(WebTableFunctions.getAllTagsInsideAnElement(perfHeader, "span"));
	
		String EleText1 = discretePerfSectionHeader.get(0).getText();
		String EleText2 = discretePerfSectionHeader.get(1).getText();
		String EleText3 = discretePerfSectionHeader.get(2).getText();
	
		if (EleText1.contains(getLocalePropertyValue("lblFODiscretePerfSectionHeader").toUpperCase()) && EleText2.contains(getLocalePropertyValue("lblDateFormatPart1")) && EleText3.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Label is displayed as Expected: "+locateElement("xpath", getLocalePropertyValue("objFODiscretePerfSectionHeader")).getText(),"PASS");
		else
			reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Label is not displayed as Expected: "+locateElement("xpath", getLocalePropertyValue("objFODiscretePerfSectionHeader")).getText(),"FAIL");	
		
		WebElement discretePerfSectionTable =locateElement("xpath", localeProp.getProperty("objFODiscretePerfSectionTable"));
		
		String datePart = EleText3;
		String month = datePart.split(getLocalePropertyValue("lblDateSeperator"))[1];
		String year = datePart.split(getLocalePropertyValue("lblDateSeperator"))[2].substring(datePart.split(getLocalePropertyValue("lblDateSeperator"))[2].length()-2);
		String monthName = Month.of(Integer.parseInt(month)).name().substring(0, 3);
		monthName=getMonthNameInGermanFromMonthNameInEnglish(monthName).toUpperCase();
		int k=0;
		List<WebElement> headerlist = WebTableFunctions.getAllTagsInsideAnElement(discretePerfSectionTable, "th");
		for(int i=(Integer.parseInt(year) -1);i>(Integer.parseInt(year) -1)-5;i--) {
			if(headerlist.get(k).getText().contains(monthName+"‑"+(Integer.parseInt(year)-(k+1))+" / "+monthName+"‑"+(Integer.parseInt(year)-(k))))
				reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Year Label "+(k+1) +" is displayed as Expected:"+(monthName+"‑"+(Integer.parseInt(year)-(k+1))+" / "+monthName+"‑"+(Integer.parseInt(year)-(k)))+",Actual: "+headerlist.get(k).getText(),"PASS");
			else
				reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Year Label is "+(k+1) + " not displayed as Expected:"+(monthName+"‑"+(Integer.parseInt(year)-(k+1))+" / "+monthName+"‑"+(Integer.parseInt(year)-(k)))+",Actual: "+headerlist.get(k).getText(),"FAIL");		
		k++;
		}
		k=0;
		List<WebElement> valuelist = WebTableFunctions.getAllTagsInsideAnElement(discretePerfSectionTable, "td");
		for(int i=(Integer.parseInt(year) -1);i>(Integer.parseInt(year) -1)-5;i--) {
			if(valuelist.get(k).getText().matches("(-*\\d{1,2}.\\d{2}%)|—"))
				reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Year Value "+(k+1) +" is displayed as Expected: "+valuelist.get(k).getText().replaceAll("\u2011", "-"),"PASS");
			else
				reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Year Value is "+(k+1) + " not displayed as Expected: "+valuelist.get(k).getText().replaceAll("\u2011", "-"),"FAIL");		
		k++;
		}
		if(!sCountryName.equals("Austria")) {
		click(locateElement("xpath",getLocalePropertyValue("objFODiscretePerfSectionReadMore")));
		reportStep("Read More Text: "+locateElement("xpath",getLocalePropertyValue("objFODiscretePerfSectionReadMoreText")).getText(),"INFO");
		}
		return this;
	}
	
	public FundOverviewPage validatePerfSnpshtAnnualizedPerfSection(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page Performance Snapshot-Annualizded Performance Section Section","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFODiscretePerfSectionHeader")));
		
		WebElement perfHeader = locateElement("xpath",getLocalePropertyValue("objFODiscretePerfSectionHeader"));
		
		List<WebElement> discretePerfSectionHeader = WebTableFunctions.getAllTagsInsideAnElement(perfHeader, "ft-label");
	
		discretePerfSectionHeader.addAll(WebTableFunctions.getAllTagsInsideAnElement(perfHeader, "span"));
	
		String EleText1 = discretePerfSectionHeader.get(0).getText();
		String EleText2 = discretePerfSectionHeader.get(1).getText();
		String EleText3 = discretePerfSectionHeader.get(2).getText();
	
		if (EleText1.contains(getLocalePropertyValue("lblFODiscretePerfSectionHeader").toUpperCase()) && EleText2.contains(getLocalePropertyValue("lblDateFormatPart1")) && EleText3.matches("\\d{2}.[0-3]\\d.\\d{4}"))
			reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Label is displayed as Expected: "+locateElement("xpath", getLocalePropertyValue("objFODiscretePerfSectionHeader")).getText(),"PASS");
		else
			reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Label is not displayed as Expected: "+locateElement("xpath", getLocalePropertyValue("objFODiscretePerfSectionHeader")).getText(),"FAIL");	
		
		WebElement discretePerfSectionTable =locateElement("xpath", localeProp.getProperty("objFODiscretePerfSectionTable"));
		
		List<String> modelTableHeaders = new ArrayList<String>(); 
		modelTableHeaders.add(getLocalePropertyValue("lbl1YR").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl3YRS").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl5YRS").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl10YRS").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lblFOAnnualizedPerfSinceInception").toUpperCase());

		List<WebElement> headerlist = WebTableFunctions.getAllTagsInsideAnElement(discretePerfSectionTable, "th");
		for(int i=0;i<headerlist.size();i++) {
			if(headerlist.get(i).getText().contains(modelTableHeaders.get(i)))
					
				reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Year Label "+(i+1) +" is displayed as Expected: "+headerlist.get(i).getText(),"PASS");
			else
				reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Year Label is "+(i+1) + " not displayed as Expected: "+headerlist.get(i).getText(),"FAIL");		

		}
	
		List<WebElement> valuelist = WebTableFunctions.getAllTagsInsideAnElement(discretePerfSectionTable, "td");
		for(int i=0;i<valuelist.size();i++) {
			if(valuelist.get(i).getText().matches("(-*\\d{1,2}.\\d{2}%)|—"))
				reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Year Value "+(i+1) +" is displayed as Expected: "+valuelist.get(i).getText().replaceAll("\u2011", "-"),"PASS");
			else
				reportStep(getLocalePropertyValue("lblFODiscretePerfSectionHeader")+" Year Value is "+(i+1) + " not displayed as Expected: "+valuelist.get(i).getText().replaceAll("\u2011", "-"),"FAIL");		
		}
		
		
		return this;
	}
	
	 public String getMonthNameInGermanFromMonthNameInEnglish(String monthName) {
	    	
			switch(monthName) {
			case "JAN" : return getLocalePropertyValue("lblJan");
			case "FEB" : return getLocalePropertyValue("lblFeb");
			case "MAR" : return getLocalePropertyValue("lblMar");
			case "APR" : return getLocalePropertyValue("lblApr");
			case "MAY" : return getLocalePropertyValue("lblMay"); 
			case "JUN" : return getLocalePropertyValue("lblJun");
			case "JUL" : return getLocalePropertyValue("lblJul");
			case "AUG" : return getLocalePropertyValue("lblAug");	
			case "SEP" : return getLocalePropertyValue("lblSep");	
			case "OCT" : return getLocalePropertyValue("lblOct");	
			case "NOV" : return getLocalePropertyValue("lblNov");	
			case "DEC" : return getLocalePropertyValue("lblDec");
			default    :  return null;
			}
	 }
	 
	 public FundOverviewPage validateFundContent(HashMap<String,String> excelData) throws InterruptedException {
		 switch(sCountryName) {
			case "UK": 			validateFundContentUK(excelData);
								break;
			case "Italy": 		validateFundContentItaly(excelData);
								break;
			case "Luxembourg":	validateFundContentLux(excelData); 
					 			break;
			case "German":		validateFundContentGerman(excelData); 
								break;
			case "Switzerland":	validateFundContentItaly(excelData); //Sections displayed are same as italy
								break;
			case "Singapore":	validateFundContentItaly(excelData); //Sections displayed are same as italy
								break;
			case "Austria":		validateFundContentGerman(excelData); //Sections displayed are same as italy
								break;
			case "Offshore":	validateFundContentItaly(excelData); //Sections displayed are same as italy
								break;
			case "Canada":		validateFundContentGerman(excelData); //Sections displayed are same as italy
								break;
			default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
								break;
			}
				return this;
			
	 }
		
	 
	public FundOverviewPage validateFundContentLux(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page fund content","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection")));
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection"),getLocalePropertyValue("lblFOSummaryObjectiveSection"));
		reportStep("Following Information is displayed under 'Summary of Fund Objective' label::  "+locateElement("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection")).getText(),"INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOWhyConsiderHeader"),getLocalePropertyValue("lblFOWhyConsiderHeader"));
		click(locateElement("xpath", getLocalePropertyValue("objFOWhyConsiderSlider")));
		reportStep("Following Information is displayed under 'Why consider this fund' label::  "+locateElement("xpath", getLocalePropertyValue("objFOWhyConsiderInfo")).getText(),"INFO");
		verifyElementExist("xpath", getLocalePropertyValue("objFOWhatAreKeyRisksHeader"),getLocalePropertyValue("lblFOWhatAreKeyRisksHeader"));
		reportStep("Following Information is displayed under 'What are the Key Risks?' label::  "+locateElement("xpath", getLocalePropertyValue("objFOWhatAreKeyRisksInfo")).getText(),"INFO");
		
		return this;
	}
	
	public FundOverviewPage validateFundContentItaly(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page fund content","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection")));
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection"),getLocalePropertyValue("lblFOSummaryObjectiveSection"));
		reportStep("Following Information is displayed under 'Summary of Fund Objective' label::  "+locateElement("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection")).getText(),"INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOWhatAreKeyRisksHeader"),getLocalePropertyValue("lblFOWhatAreKeyRisksHeader"));
		reportStep("Following Information is displayed under 'What are the Key Risks?' label::  "+locateElement("xpath", getLocalePropertyValue("objFOWhatAreKeyRisksInfo")).getText(),"INFO");
		
		return this;
	}
	public FundOverviewPage validateFundContentUK(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page fund content","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection")));
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection"),getLocalePropertyValue("lblFOSummaryObjectiveSection"));
		reportStep("Following Information is displayed under 'Summary of Fund Objective' label::  "+locateElement("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection")).getText(),"INFO");
		
			//This section  appear for UK site
			verifyElementExist("xpath", getLocalePropertyValue("objFOInvesterProfile"),getLocalePropertyValue("lblFOInvesterProfile").toUpperCase());
	
		verifyElementExist("xpath", getLocalePropertyValue("objFOWhatAreKeyRisksHeader"),getLocalePropertyValue("lblFOWhatAreKeyRisksHeader"));
		reportStep("Following Information is displayed under 'What are the Key Risks?' label::  "+locateElement("xpath", getLocalePropertyValue("objFOWhatAreKeyRisksInfo")).getText(),"INFO");
		
		return this;
	}
	public FundOverviewPage validateFundContentGerman(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Overview Page fund content","INFO");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection")));
		
		verifyElementExist("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection"),getLocalePropertyValue("lblFOSummaryObjectiveSection"));
		reportStep("Following Information is displayed under 'Summary of Fund Objective' label::  "+locateElement("xpath", getLocalePropertyValue("objFOSummaryObjectiveSection")).getText(),"INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objFOWhyConsiderHeader"),getLocalePropertyValue("lblFOWhyConsiderHeader"));
		click(locateElement("xpath", getLocalePropertyValue("objFOWhyConsiderSlider")));
		reportStep("Following Information is displayed under 'Why consider this fund' label::  "+locateElement("xpath", getLocalePropertyValue("objFOWhyConsiderInfo")).getText(),"INFO");
		
		return this;
	}
	
	public FundOverviewPage VerifyCumulativeFundOverviewLink(HashMap<String,String> data)
	{
		System.out.println("Country " + sCountryName);
		//HashMap<String,String> data =getTestData(sCountryName,"PERF014");
		reportStep("Verifying 'View' Link redirects to Fund Overview page with expected Share Class - " + data.get("ShareClass").replace(" (%)", ""),"INFO");
		
		//old code
//		verifyExactText(locateElement(getLocalePropertyValue("objFOShareClass")),data.get("ShareClass").replace(" (%)", ""),"Fund Overview Share Class" );
		
		//new code- changed after code push in intl site - 3/26
		if(sCountryName.equals("Canada"))
			verifyExactText(locateElement("xpath",getLocalePropertyValue("objFOShareClass")),data.get("ShareClass").replace(" (%)", "")+"-"+data.get("Currency"),"Fund Overview Share Class" );
		else
			verifyExactText(locateElement("xpath",getLocalePropertyValue("objFOShareClass")),data.get("ShareClass").replace(" (%)", ""),"Fund Overview Share Class" );
		//verifyFundOverLink(data);
		return this;
	}
	public FundOverviewPage VerifyCalendarYearFundOverviewLink(HashMap<String,String> data)
	{
		verifyFundOverLink(data);
		return this;
	}
	public FundOverviewPage VerifyDiscreteAnnualFundOverviewLink(HashMap<String,String> data)
	{
		verifyFundOverLink(data);
		return this;
	}
	public FundOverviewPage VerifyAnnualisedFundOverviewLink(HashMap<String,String> data)
	{
		verifyFundOverLink(data);
		return this;
	}
	public void verifyFundOverLink(HashMap<String,String> data)
	{
		reportStep("Verifying 'View' Link redirects to Fund Overview page with expected Share Class - " + data.get("ShareClass").replace(" (%)", ""),"INFO");
		//old code
//		verifyExactText(locateElement(getLocalePropertyValue("objFOShareClass")),data.get("ShareClass").replace(" (%)", ""),"Fund Overview Share Class" );	
	
	//new code after code push in dev env - 3/26
		if(sCountryName.equals("Canada"))
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objFOShareClass")),data.get("ShareClass").replace(" (%)", "")+"-"+data.get("Currency"),"Fund Overview Share Class" );
		else
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objFOShareClass")),data.get("ShareClass").replace(" (%)", ""),"Fund Overview Share Class" );
	}
	public HashMap<String,String> getTestData(String Country,String TestCaseID)
	{
		HashMap<String,String> testData=ExcelDataProvider.getExcelRowAsHashMapByTestID("TestData", Country,TestCaseID);
		return testData;
	}
	
	/*##############################  Smoke Test   ##################################*/
	
	public FundOverviewPage validateProductPage() throws InterruptedException
	{		
		if(locateElements("xpath","//h1").size()<1)
			reportStep("Fund page is not loaded or Error in Page", "FAIL");
		
		if(fund.contains(locateElement("xpath","//h1").getText().trim()))
			reportStep(locateElement("xpath","//h1").getText() + " fund overview Page is opened","PASS",true);
		else
			reportStep(locateElement("xpath","//h1").getText() + " fund overview Page is not opened","FAIL",true);
			
		moveContextToElement(locateElement("xpath",getLocalePropertyValue("objFundPageFootnote")));
		verifyElementExist("xpath",getLocalePropertyValue("objFundPageFootnote"),"Footnote section",true);
		verifyElementExist("xpath",getLocalePropertyValue("objProdPageImpLegalInfo"),"Important Legal Information section");	
	
		return this;
	}
	
	
	
	
}

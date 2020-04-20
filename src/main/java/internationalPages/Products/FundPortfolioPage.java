package internationalPages.Products;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import utils.WebTableFunctions;
import utils.dateFunctions;
import utils.xmlDataProvider;
import wdMethods.ProjectMethods;

public class FundPortfolioPage extends ProjectMethods{
	
	public String fund,FundNumber,FundURL,InvestmentVehicle,actor;
		
	public FundPortfolioPage(RemoteWebDriver driver, ExtentTest test,String FundName) throws InterruptedException, FileNotFoundException, IOException {
		this.driver = driver;
		this.test = test;
		
		//Loading Locale File
		localeProp.load(new FileInputStream(new File(sLocaleFile)));
		
		fund=FundName;
		actor="investor";
/*		Thread.sleep(2000);	
		FundURL=driver.getCurrentUrl();
		FundNumber=FundURL.split("overview\\/")[1].split("\\/")[0].trim();		
		InvestmentVehicle=getText(locateElement("class", getLocalePropertyValue("objFOInvestmentVehilce")));*/
		
		String title;
		reportStep("Checking Fund Portfolio Page Title","INFO",false);
		if(sEnvironment.equals("QA1"))
			title="overview-investor - " + fund;
		else
//			title=getLocalePropertyValue("lblFPTitlePart")+" - " + fund;		
			//commented above code, Changed on 3-26-2018 - due to change in page titles from latest code push
			//new code below	
			title=getLocalePropertyValue("lblFPTitlePart");		
		Thread.sleep(5000);
		if(!(verifyTitle(title) && verifyTitle(fund))) {
			reportStep("This is NOT Fund Portfolio Page. Refer snap", "FAIL");
		}
		WebDriverWait wait=new WebDriverWait(driver, 20);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFPPageComponentHeadings"))));
		// Initialize the webelements inside the page		
		//PageFactory.initElements(driver, this);
	}
	
	public FundPortfolioPage verifyPortfolioComponentsForK2AlternativeFunds(HashMap<String,String> testData) throws InterruptedException
	{	
		reportStep("Verifying Portfolio page Components for Fund 'K2 Alternative Strategies Fund'", "INFO");
		verifyPortfolioComponents("K2 Alternative Strategies Fund", testData);
		return this;				
	}
	
	public FundPortfolioPage verifyPortfolioComponentsForK2GlobalMacroOpportunitiesFund(HashMap<String,String> testData) throws InterruptedException
	{	
		reportStep("Verifying Portfolio page Components for Fund 'K2 Global Macro Opportunities Fund'", "INFO");
		verifyPortfolioComponents("K2 Global Macro Opportunities Fund", testData);
		return this;				
	}
	
	public FundPortfolioPage verifyPortfolioComponentsForK2LongShortCreditFund(HashMap<String,String> testData) throws InterruptedException
	{	
		reportStep("Verifying Portfolio page Components for Fund 'K2 Long Short Credit Fund'", "INFO");
		verifyPortfolioComponents("K2 Long Short Credit Fund", testData);
		return this;				
	}
	public FundPortfolioPage verifyPortfolioComponents(String FundOrShareClass,HashMap<String,String> testData) throws InterruptedException
	{	
		if(FundOrShareClass.contains("K2 Long Short Credit Fund")) {
			WebDriverWait wait=new WebDriverWait(driver, 20);			
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFPPortfolioStatisticsHeading"))));
			}
		Thread.sleep(5000);
		List<WebElement> actComponents=locateElements("xpath", getLocalePropertyValue("objFPPageComponentHeadings"));
		System.out.println("Size" + actComponents.size());
		String actComponentName,cName="";
		String expComponents=testData.get(FundOrShareClass);
		captureFullScreen("Capturing Full screen of Fund Portfolio page");
		
		if(expComponents==null)
			reportStep("Fund Portfolio Component Test Data may not exist in Data Sheet / Invalid. Please refer Data Sheet","FAIL",false);
		String[] expComponentNames=expComponents.split(",");		
		
		if (actComponents.size()!=expComponentNames.length) 
			reportStep("Number of Components under Fund Portfolip page should be : " + expComponentNames.length + ". But Actual Elements are: " + actComponents.size(),"FAIL");
		else { 
			//reportStep("Number of Components under Fund Portfolip page matches with expected: " + expComponentNames.length,"PASS",false);
			boolean isNotFound=false;
			
			for (int i = 0; i < expComponentNames.length; i++) {
				expComponentNames[i]=expComponentNames[i].replace("\n", "");
				System.out.println("Exp: " + expComponentNames[i]);
				actComponentName=locateElements(actComponents.get(i), "tag", "span").get(0).getText().toString();
				System.out.println("Act: " + actComponentName);				
				cName=cName + actComponentName+ ", ";
				if(!actComponentName.contains(expComponentNames[i]))
				{		isNotFound=true;
						break;
				}
			}
			
			if(isNotFound)
				reportStep("Some Components may not found / Titel Invalid. - '" + cName +"'", "FAIL",false);		
			
			else
				reportStep("Components '" + cName +"' Displayed Successfully", "PASS",false);
			}		

		return this;
	}

	public PriceAndPerformancePage goToPriceAndPerformancePage() throws FileNotFoundException, IOException	
	{
		if(!(sCountryName.equals("German")||sCountryName.equals("Austria")))
		click(locateElement("link", getLocalePropertyValue("objPpssBreadCrumbPriceAndPerformanceLink")));
		else {
			click(locateElement(localeProp.getProperty("objHmPageProductsLink")));
			WebDriverWait wait=new WebDriverWait(driver, 200);			
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objPpssBreadCrumbPriceAndPerformanceLink"))));
			click(locateElement("xpath", getLocalePropertyValue("objPpssBreadCrumbPriceAndPerformanceLink")));}
		return new PriceAndPerformancePage(driver,test);
	}
	
	public FundPortfolioPage verifyPortfolioComponentsForBalanced(HashMap<String,String> testData) throws InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 200);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFPPortfolioStatisticsHeading"))));
		reportStep("Verifying Portfolio page Components for AssetClass 'Balanced'", "INFO");
		verifyPortfolioComponents("Balanced", testData);
		return this;				
	}
	public FundPortfolioPage verifyPortfolioComponentsForEquity(HashMap<String,String> testData) throws InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 200);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFPPortfolioStatisticsHeading"))));
		reportStep("Verifying Portfolio page Components for AssetClass 'Equity'", "INFO");
		verifyPortfolioComponents("Equity", testData);
		return this;				
	}
	public FundPortfolioPage verifyPortfolioComponentsForFixedIncome(HashMap<String,String> testData) throws InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 200);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFPPortfolioStatisticsHeading"))));
		reportStep("Verifying Portfolio page Components for AssetClass 'Fixed Income'", "INFO");
		verifyPortfolioComponents("Fixed Income", testData);
		return this;				
	} 
	public FundPortfolioPage verifyStrategyBreakdownDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPStrategyBreakdownHeading"),getLocalePropertyValue("objFPStrategyBreakdownHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifyPortfolioExposureDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPPortfolioExposureHeading"),getLocalePropertyValue("objFPPortfoioExposureHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifySectorExposureDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPSectorExposureHeading"),getLocalePropertyValue("objFPSectorExposureHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifyTopRiskAllocationByAssetClassDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPTopRiskAllocationByAssetClassHeading"),getLocalePropertyValue("objFPTopRiskAllocationByAssetClassHedingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifySectorAllocationDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPSectorAllocationHeading"),getLocalePropertyValue("objFPSectorAllocationHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifyNumberOfIssuersDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPNumberOfIssuersHeading"),getLocalePropertyValue("objFPNumberOfIssuersHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifyHoldingsDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPHoldingsHeading"),getLocalePropertyValue("objFPHoldingsHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifyPortfolioAllocationDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPPortfolioAllocationHeading"),getLocalePropertyValue("objFPPortfolioAllocationHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifyManagerAllocationDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPManagerAllocationHeading"),getLocalePropertyValue("objFPManagerAllocationHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifyMarketCapitalisationDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPMarketCapitalisationHeading"),getLocalePropertyValue("objFPMarketCapitalisationHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifyGeographicalAllocationDateFormat()
	{
		verifyDateFormat(getLocalePropertyValue("lblFPGeographicAllocationHeading"),getLocalePropertyValue("objFPGeographicAllocationHeadingWithDate"));		
		return this;
	}
	public FundPortfolioPage verifyDateFormat(String widgetName,String widgetObject)
	{
		try {
			
			String dateString[];
			String dateElment;
			Thread.sleep(10000);
			if(!verifyElementExist("xpath", widgetObject)) {
				reportStep("Checking '" + widgetName +"' widget Availability","INFO");
				captureFullScreen( "'" + widgetName +"' widget" );
				reportStep("'" + widgetName +"' widget Not Available for this fund","FAIL",false);				
				return this;
			}
			mouseOver(locateElement("xpath", widgetObject));
			reportStep("Validing Date Format under '" + widgetName +"'","INFO");			
			String HeaderWithDate=getText(locateElement("xpath", widgetObject));			
			dateElment=HeaderWithDate.split("\n")[1];
/*			if(!dateElment.contains("(Updated"))
			{
				reportStep("Checking '" + widgetName +"' widget Refresh Time Availability","INFO");				
				reportStep("'" + widgetName +"' widget Refresh Time may Not available for this fund","FAIL");				
				return this;
			}*/
			dateString=dateElment.split("\\(")[0].split(getLocalePropertyValue("lblFOAsAtDateTxt"));
			//System.out.println("Date:" + dateString[1].trim());			
			
			if (dateFunctions.VerifyDateFormat(dateString[1].trim(),getLocalePropertyValue("lblDateFormat")))
				reportStep("'" + widgetName +"' Date Format is displayed as Expected :'"+getLocalePropertyValue("lblDateFormat")+"'","PASS");			
			else
				reportStep("'" + widgetName +"' Date Format is not displayed as Expected :'"+getLocalePropertyValue("lblDateFormat")+"'","FAIL");
			
			reportStep("Validing Refresh time '" + widgetName +"'","INFO");
			String refTimeCheck=getLocalePropertyValue("lblRefreshTimeMonthly").split(" ")[0];
			System.out.println(dateElment);
			System.out.println("(" + refTimeCheck);
			if(!sCountryName.equals("Italy")) {
			if(!dateElment.contains("(" + refTimeCheck))
			//if(!dateElment.contains("(Updated"))
			{
				reportStep("Checking '" + widgetName +"' widget Refresh Time Availability","INFO");				
				reportStep("'" + widgetName +"' widget Refresh Time may Not available for this fund","FAIL");				
				
			}
			if(dateElment.contains("(" + getLocalePropertyValue("lblRefreshTimeMonthly")+ ")") ||dateElment.contains("(" + getLocalePropertyValue("lblRefreshTimeQuartely")+ ")")||dateElment.contains("(" +getLocalePropertyValue("lblRefreshTimeSemiYearly")+ ")"))
				reportStep("Refresh Time displayed successfully - " + dateElment ,"PASS",false);	
			else
				reportStep("Refresh Time displayed unsuccessfully - " + dateElment ,"FAIL",false);
				}
			else {
				
				if(!dateElment.contains( refTimeCheck))
					//if(!dateElment.contains("(Updated"))
					{
						reportStep("Checking '" + widgetName +"' widget Refresh Time Availability","INFO");				
						reportStep("'" + widgetName +"' widget Refresh Time may Not available for this fund","FAIL");				
						
					}
				if(dateElment.contains( getLocalePropertyValue("lblRefreshTimeMonthly")) ||dateElment.contains(getLocalePropertyValue("lblRefreshTimeQuartely"))||dateElment.contains(getLocalePropertyValue("lblRefreshTimeSemiYearly")))
					reportStep("Refresh Time displayed successfully - " + dateElment ,"PASS",false);	
				else
					reportStep("Refresh Time displayed unsuccessfully - " + dateElment ,"FAIL",false);
			}
			
		
		}catch(Exception e)
		{
			reportStep("Un-Expected Error Occurs during execution","FAIL");
			e.printStackTrace();
		}

		return this;
	}
	public FundPortfolioPage verifyFootNoteCaveat()
	{		
		//reportStep("Validing FootNote Caveat","INFO");
		captureFullScreen("Validing FootNote Caveat - Portfolio Tab");
		if (verifyElementExist("xpath", getLocalePropertyValue("objFPPageFootnoteCaveate")))
			reportStep("FootNote Caveat exist in Portfolio tab","PASS",false);
		else
			reportStep("FootNote Caveat may not exist in Portfolio tab","FAIL",false);		
		return this;
	}
	
	public FundPortfolioPage validatePortfolioValueAtRiskSection(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Portfolio - Validating Value At Risk","INFO");
		fluentwaitForObjectToLoad(By.xpath(getLocalePropertyValue("objFPValueAtRiskSectionHeader")));
		verifyElementExist("xpath", getLocalePropertyValue("objFPValueAtRiskSectionHeader"),getLocalePropertyValue("lblFPValueAtRiskSectionHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFPValueAtRiskSectionHeader")));
	
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFPValueAtRiskSectionHeader")),getLocalePropertyValue("lblFPValueAtRiskSectionHeader").toUpperCase());
		
		WebElement valueAtRiskTable = locateElement("xpath",getLocalePropertyValue("objFPValueAtRiskSectionTable"));
		verifyTwoStringsExactly(WebTableFunctions.getCellData(valueAtRiskTable, 0, 0), getLocalePropertyValue("lblFPValueAtRiskSectionTableCol1"));
		
		if(WebTableFunctions.getCellData(valueAtRiskTable, 0, 1).matches("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%"))
			reportStep(getLocalePropertyValue("lblFPValueAtRiskSectionTableCol1")+" value is displayed as expected :" + WebTableFunctions.getCellData(valueAtRiskTable, 0, 1) ,"PASS");	
		else
			reportStep(getLocalePropertyValue("lblFPValueAtRiskSectionTableCol1")+" value is displayed as expected :" + WebTableFunctions.getCellData(valueAtRiskTable, 0, 1) ,"FAIL");
		
			return this;
	}
	
	public FundPortfolioPage validatePortfolioStatisticsEquitySection(HashMap<String,String> excelData) throws InterruptedException {
		 switch(sCountryName) {
			case "UK": 			validatePortfolioStatisticsEquitySectionLux(excelData);
								break;
			case "Italy": 		validatePortfolioStatisticsEquitySectionLux(excelData);
								break;
			case "Luxembourg":	validatePortfolioStatisticsEquitySectionLux(excelData); 
					 			break;
			case "German":		validatePortfolioStatisticsEquitySectionLux(excelData); 
								break;
			case "Switzerland":	validatePortfolioStatisticsEquitySectionSwitzerland(excelData); 
								break;
			case "Singapore":	validatePortfolioStatisticsEquitySectionLux(excelData); 
								break;
			case "Austria":		validatePortfolioStatisticsEquitySectionLux(excelData); 
								break;
			case "Offshore":	validatePortfolioStatisticsEquitySectionLux(excelData); 
 								break;
			case "Canada":		validatePortfolioStatisticsEquitySectionCanada(excelData); 
								break;
			default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
								break;
			}
				return this;
	}
	public FundPortfolioPage validatePortfolioStatisticsEquitySectionLux(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Portfolio Statistics - Statistics (Equity)","INFO");
			
		verifyElementExist("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader"), getLocalePropertyValue("lblFPPortfolioStatsHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader")));
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsSubHeader")),getLocalePropertyValue("lblFPPortfolioStatsSubHeader").toUpperCase());
		
		WebElement statTable = locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsTable"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 0, 0) , getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mf"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 1, 0), getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mt"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 2, 0), getLocalePropertyValue("lblFPPortfolioStatsEquityPtoB"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 3, 0), getLocalePropertyValue("lblFPPortfolioStatsEquityPtoCF"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}" , getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mf"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 1, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}" ,	getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mt"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 2, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}" ,	getLocalePropertyValue("lblFPPortfolioStatsEquityPtoB"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 3, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}" ,	getLocalePropertyValue("lblFPPortfolioStatsEquityPtoCF"));
		return this;
	}
	
	public FundPortfolioPage validatePortfolioStatisticsEquitySectionCanada(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Portfolio Statistics - Statistics (Equity)","INFO");
		
		
		
		verifyElementExist("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader"), getLocalePropertyValue("lblFPPortfolioStatsHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader")));
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsSubHeader")),getLocalePropertyValue("lblFPPortfolioStatsSubHeader").toUpperCase());
		
		WebElement statTable = locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsTable"));
		//verifyPartialText(WebTableFunctions.getCellElement(statTable, 0, 0) , getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mf"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 0, 0), getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mt"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 1, 0), getLocalePropertyValue("lblFPPortfolioStatsEquityPtoB"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 2, 0), getLocalePropertyValue("lblFPPortfolioStatsEquityPtoCF"));
		//validateFormat(WebTableFunctions.getCellElement(statTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}" , getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mf"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}" ,getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mt"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 1, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}" ,getLocalePropertyValue("lblFPPortfolioStatsEquityPtoB"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 2, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}" ,getLocalePropertyValue("lblFPPortfolioStatsEquityPtoCF"));
		return this;
	}
	public FundPortfolioPage validatePortfolioStatisticsEquitySectionSwitzerland(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Portfolio Statistics - Statistics (Equity)","INFO");
		
	
		
		verifyElementExist("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader"), getLocalePropertyValue("lblFPPortfolioStatsHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader")));
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsSubHeader")),getLocalePropertyValue("lblFPPortfolioStatsSubHeader").toUpperCase());
		
		WebElement statTable = locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsTable"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 0, 0) , getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mf"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 1, 0), getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mt"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 2, 0), getLocalePropertyValue("lblFPPortfolioStatsEquityPtoB"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 3, 0), getLocalePropertyValue("lblFPPortfolioStatsEquityPtoCF"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblYearSeperator")+"\\d{2}" , getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mf"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 1, 1),"\\d{1,2}"+getLocalePropertyValue("lblYearSeperator")+"\\d{2}" ,getLocalePropertyValue("lblFPPortfolioStatsEquityPtoE12mt"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 2, 1),"\\d{1,2}"+getLocalePropertyValue("lblYearSeperator")+"\\d{2}" ,getLocalePropertyValue("lblFPPortfolioStatsEquityPtoB"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 3, 1),"\\d{1,2}"+getLocalePropertyValue("lblYearSeperator")+"\\d{2}" ,getLocalePropertyValue("lblFPPortfolioStatsEquityPtoCF"));
		return this;
	}

	public FundPortfolioPage validatePortfolioStatisticsFixedIncomeSection(HashMap<String,String> excelData) throws InterruptedException {
		 switch(sCountryName) {
			case "UK": 			validatePortfolioStatisticsFixedIncomeSectionLux(excelData);
								break;
			case "Italy": 		validatePortfolioStatisticsFixedIncomeSectionLux(excelData);
								break;
			case "Luxembourg":	validatePortfolioStatisticsFixedIncomeSectionLux(excelData); 
					 			break;
			case "German":		validatePortfolioStatisticsFixedIncomeSectionLux(excelData); 
								break;
			case "Switzerland":	validatePortfolioStatisticsFixedIncomeSectionSwitzerland(excelData); 
								break;
			case "Singapore":	validatePortfolioStatisticsFixedIncomeSectionLux(excelData); 
								break;
			case "Austria":		validatePortfolioStatisticsFixedIncomeSectionLux(excelData); 
								break;
			case "Offshore":	validatePortfolioStatisticsFixedIncomeSectionLux(excelData); 
 								break;
			default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
								break;
			}
				return this;
	}
	public FundPortfolioPage validatePortfolioStatisticsFixedIncomeSectionLux(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Portfolio Statistics - Statistics (Fixed Income)","INFO");
		
		
		
		verifyElementExist("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader"), getLocalePropertyValue("lblFPPortfolioStatsHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader")));
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsSubHeader")),getLocalePropertyValue("lblFPPortfolioStatsSubHeader").toUpperCase());
		
		WebElement statTable = locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsTable"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 0, 0) , getLocalePropertyValue("lblFPPortfolioStatsFixdIncmWAM"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 1, 0), getLocalePropertyValue("lblFPPortfolioStatsFixdIncmAD"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 2, 0), getLocalePropertyValue("lblFPPortfolioStatsFixdIncmYTM"));
	//	verifyPartialText(WebTableFunctions.getCellElement(statTable, 3, 0), getLocalePropertyValue("lblFPPortfolioStatsFixdIncmACQ"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2} "+getLocalePropertyValue("lblYears") , getLocalePropertyValue("lblFPPortfolioStatsFixdIncmWAM"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 1, 1),"-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2} "+getLocalePropertyValue("lblYears") ,getLocalePropertyValue("lblFPPortfolioStatsFixdIncmAD"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 2, 1),"\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}%" ,getLocalePropertyValue("lblFPPortfolioStatsFixdIncmYTM"));
	//	validateFormat(WebTableFunctions.getCellElement(statTable, 3, 1),"[AB]{1,3}[+-]*" ,getLocalePropertyValue("lblFPPortfolioStatsFixdIncmACQ"));
		return this;
	}
	
	public FundPortfolioPage validatePortfolioStatisticsFixedIncomeSectionSwitzerland(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Portfolio Statistics - Statistics (Fixed Income)","INFO");
		
		
		
		verifyElementExist("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader"), getLocalePropertyValue("lblFPPortfolioStatsHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader")));
		validateLabelwithAsOfDateMonthly(locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsSubHeader")),getLocalePropertyValue("lblFPPortfolioStatsSubHeader").toUpperCase());
		
		WebElement statTable = locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsTable"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 0, 0) , getLocalePropertyValue("lblFPPortfolioStatsFixdIncmWAM"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 1, 0), getLocalePropertyValue("lblFPPortfolioStatsFixdIncmAD"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 2, 0), getLocalePropertyValue("lblFPPortfolioStatsFixdIncmYTM"));
		verifyPartialText(WebTableFunctions.getCellElement(statTable, 3, 0), getLocalePropertyValue("lblFPPortfolioStatsFixdIncmACQ"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 0, 1),"\\d{1,2}"+getLocalePropertyValue("lblYearSeperator")+"\\d{2} "+getLocalePropertyValue("lblYears") , getLocalePropertyValue("lblFPPortfolioStatsFixdIncmWAM"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 1, 1),"-*\\d{1,2}"+getLocalePropertyValue("lblYearSeperator")+"\\d{2} "+getLocalePropertyValue("lblYears") ,getLocalePropertyValue("lblFPPortfolioStatsFixdIncmAD"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 2, 1),"\\d{1,2}"+getLocalePropertyValue("lblYearSeperator")+"\\d{2}%" ,getLocalePropertyValue("lblFPPortfolioStatsFixdIncmYTM"));
		validateFormat(WebTableFunctions.getCellElement(statTable, 3, 1),"[AB]{1,3}[+-]*" ,getLocalePropertyValue("lblFPPortfolioStatsFixdIncmACQ"));
		return this;
	}
	
	public FundPortfolioPage validatePortfolioStatisticsNumberOfHoldingsSections(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(10000);
		reportStep("Validating Portfolio Statistics - Number of Holdings","INFO");
		
	
		
		verifyElementExist("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader"), getLocalePropertyValue("lblFPPortfolioStatsHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader")));
		validateLabelwithAsOfDateOnly(locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsHoldingsSubHeader")),getLocalePropertyValue("lblFPPortfolioStatsHoldingsSubHeader").toUpperCase());
		
		WebElement holdingsTable = locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsHoldingsTable"));
		verifyPartialText(WebTableFunctions.getCellElement(holdingsTable, 0, 0) , getLocalePropertyValue("lblFPPortfolioStatsHoldingsTableCol1"));
		validateFormat(WebTableFunctions.getCellElement(holdingsTable, 0, 1),"\\d{2,3}", getLocalePropertyValue("lblFPPortfolioStatsHoldingsTableCol1"));
		
		return this;
	}
	
	public FundPortfolioPage validatePortfolioStatisticsNumberOfIssuersSections(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(10000);
		reportStep("Validating Portfolio Statistics - Number of Issuers ","INFO");
		
		
		
		verifyElementExist("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader"), getLocalePropertyValue("lblFPPortfolioStatsHeader"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objFPPortfolioStatsHeader")));
		validateLabelwithAsOfDateOnly(locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsIssuersSubHeader")),getLocalePropertyValue("lblFPPortfolioStatsIssuersSubHeader").toUpperCase());
		
		WebElement issuersTable = locateElement("xpath",getLocalePropertyValue("objFPPortfolioStatsIssuersTable"));
		verifyPartialText(WebTableFunctions.getCellElement(issuersTable, 0, 0) , getLocalePropertyValue("lblFPPortfolioStatsIssuersTableCol1"));
		validateFormat(WebTableFunctions.getCellElement(issuersTable, 0, 1),"\\d{2,3}", getLocalePropertyValue("lblFPPortfolioStatsIssuersTableCol1"));
		
		return this;
	}
	
public FundPortfolioPage clickAtHomePageLinkInBreadCrumb() {
		
		click(locateElement("xpath",getLocalePropertyValue("objHmPageLinkBreadCrumb")));
		
		return this;
	}

public FundPortfolioPage clickAtPriceAndPerfLinkFromPreisePageAustria() {
		
		click(locateElement("xpath",getLocalePropertyValue("objPPLinkPreisePage")));
		return this;
	}



	
}
package internationalPages.Products;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.sun.jna.platform.win32.WinDef.UCHAR;


import utils.ExcelDataProvider;
import utils.commonMethods;
import utils.dateFunctions;
import wdMethods.ProjectMethods;
import org.openqa.selenium.support.Color;

public class PriceAndPerformancePage extends ProjectMethods{
	
	List<WebElement> fundsLinks=new ArrayList<WebElement>();
	public int performanceFundCout=0;
public static String tid;
	
	public PriceAndPerformancePage(RemoteWebDriver driver, ExtentTest test) throws FileNotFoundException, IOException {
		this.driver = driver;
		this.test = test;
		
		//Loading Locale File
		localeProp.load(new FileInputStream(new File(sLocaleFile)));
		
		//if(!verifyTitle("Price and Performance Snapshot")) {
		if(!verifyTitle(localeProp.getProperty("lblPpssTitle"))) {			
			reportStep("This is NOT " + localeProp.getProperty("lblPpssTitle") + " Page. Refer snap", "FAIL");
		}
		// Initialize the webelements inside the page
		PageFactory.initElements(driver, this);
	}
	 
public FundOverviewPage clickFund(String FundName) throws InterruptedException, FileNotFoundException, IOException {			
	
			FindAFund(FundName);
		
//		clickWithNoSnap(locateElement("link", FundName));
		
		//new Logic due to Change in DOM - 11/22/2018
		
		if(locateElement("xpath","//table[@class='table table-striped table-ppss']//tbody//tr//td[@class='fund-name']//a").getText().contains(FundName.split("-")[0].trim()))
			clickWithNoSnap(locateElement("xpath","//table[@class='table table-striped table-ppss']//tbody//tr//td[@class='fund-name']//a"));
		else
			reportStep("Fund Name is not matching with displayed fund link after search","FAIL");
/*		WebDriverWait wait=new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objPEDiscretePerformanceMonthlyTable"))));*/
		
		captureFullScreen("Capturing Full screen of Fund Overview page");
		return new FundOverviewPage(driver,test,FundName);
	}

public FundOverviewPage clickFundWithFilters(HashMap<String,String> exlData) throws InterruptedException, FileNotFoundException, IOException {			

		FindAFundWithFilters(exlData,"Currency");
	
//	clickWithNoSnap(locateElement("link", exlData.get("Fund Name")));
	
		//new Logic due to Change in DOM - 11/22/2018
		
	if(locateElement("xpath","//table[@class='table table-striped table-ppss']//tbody//tr//td[@class='fund-name']//a").getText().contains(exlData.get("Fund Name").split("-")[0].trim()))
		clickWithNoSnap(locateElement("xpath","//table[@class='table table-striped table-ppss']//tbody//tr//td[@class='fund-name']//a"));
	else
		reportStep("Fund Name is not matching with displayed fund link after search","FAIL");
	
/*		WebDriverWait wait=new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objPEDiscretePerformanceMonthlyTable"))));*/
	
	captureFullScreen("Capturing Full screen of Fund Overview page");
	return new FundOverviewPage(driver,test,exlData.get("Fund Name"));
}
	
	public FundOverviewPage clickFirstFundFromPerformanceList() throws InterruptedException, FileNotFoundException, IOException
	{
		String fundName="";
	
			List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
			//System.out.println(tableRows.get(0).getText());	
		if(!sCountryName.equals("UK")) {
			String data[]=tableRows.get(0).getText().split("\\n");
			fundName=data[0];
			//System.out.println(fundName);
			WebElement fLink=tableRows.get(0).findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
			click(fLink);
		}
		else {
			String data[]=tableRows.get(1).getText().split("\\n");
			fundName=data[0];
			//System.out.println(fundName);
			WebElement fLink=tableRows.get(1).findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
			click(fLink);
			
		}
			
			WebDriverWait wait=new WebDriverWait(driver, 200);
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFOFindAFundButton"))));

		return new FundOverviewPage(driver,test,fundName.trim());
	}
	
	public String getRandomFundNameFromPerformanceList() throws InterruptedException
	{
		List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		System.out.println(tableRows.get(0).getText());	
		int fundIndex=getRandomNumberBetween(0, tableRows.size()-1);
		String data[]=tableRows.get(fundIndex).getText().split("\\n");
		String fundName=data[0].trim();

		return fundName;
	}
	
	public void clickRandomFundNameFromPerformanceList(String fundName) throws InterruptedException
	{
		List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		boolean vBool=false;
	for(int i=0;i<tableRows.size();i++) {
		String data[]=tableRows.get(i).getText().split("\\n");
		if(data[0].trim().equals(fundName)) {
			vBool=true;
		click(tableRows.get(i).findElement(By.tagName("td")).findElement(By.tagName("a")));
		break;
		}
	}
	if(!vBool)	
		reportStep("Clicking on random fund failed, Please check the screenshot","FAIL");
		
	}
	
	public PriceAndPerformancePage waitUntilAllFundsLoaded()
	{	
		try {
			Thread.sleep(2500);
			WebDriverWait wait=new WebDriverWait(driver, 3000);			
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objPpssPerformanceTable"))));			
					
		}catch(Exception e){
			reportStep("Price and Performance page may not loaded successfully", "FAIL");
		}
		
		return this;
	}

	public PriceAndPerformancePage clickFundIdentifierTab()
	{		
		click(locateElement("xpath", getLocalePropertyValue("objFOFundIdentiferTab")),true);
		WebDriverWait wait=new WebDriverWait(driver, 50);
		
//		WebElement elmFIdentifierTable=locateElement("xpath", "//table[@id='table-1' and @class='table table-striped responsive']");
		//commented above code, Changed on 3-26-2018 - due to change in page titles from latest code push
		//new code below	
		WebElement elmFIdentifierTable=locateElement("xpath", "//table[@class='table table-striped responsive']");
		wait.until(ExpectedConditions.visibilityOf(elmFIdentifierTable));	
		return this;
	}
	
	public int getPerformanceTabFundCount() throws InterruptedException
	{	
		//performanceFundCout=elePerformanceFundRows.size();
		performanceFundCout=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows")).size();	
		return performanceFundCout;
	}
	
	public PriceAndPerformancePage verifyFundsBetweenPerformanceAndFundIdentifierTabs()
	{	
		
		reportStep("Checking No. of Funds is same between Performance & Fund Identifier Tab","INFO");		
		if (performanceFundCout==locateElements("xpath", getLocalePropertyValue("objPpssFundListTable")).size())
			reportStep("No. of Funds is same between Performance & Fund Identifier Tab - " + "Perfromance: " + performanceFundCout + ",Fund Identifier: " + locateElements("xpath", getLocalePropertyValue("objPpssFundListTable")).size(),"PASS" );
		else 
			reportStep("No. of Funds is not same between Performance & Fund Identifier Tab - " +"Perfromance: " + performanceFundCout + ",Fund Identifier: " + locateElements("xpath", getLocalePropertyValue("objPpssFundListTable")).size(),"FAIL");
		
		return this;
	}
	
	public PriceAndPerformancePage verifyHeadingWithFundCount(int totalFunds) throws InterruptedException	
	{			
		int totalFundLoaded,displayedCount;
		totalFundLoaded=totalFunds;
		//Ensure all the funds loaded according to the fund count displayed in the title.			
		WebElement header=locateElement("xpath",localeProp.getProperty("objPpssHeadingWithFundCount"));
		String temp=header.getText().replace(getLocalePropertyValue("lblPpssFundsDisplayedlblPart1").toUpperCase(), "").replace(getLocalePropertyValue("lblPpssFundsDisplayedlblPart2").toUpperCase(), "");
		displayedCount=Integer.parseInt(temp.trim());
		//System.out.println("Displayed Count: " + displayedCount);
		totalFundLoaded=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows")).size();
		
		int counter=0;
		while(displayedCount!=totalFundLoaded) {
			if(counter>10) {
				System.out.println("All Funds may not loaded within expected time");
				break;}
			Thread.sleep(5000);
			totalFundLoaded=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows")).size();
			//System.out.println("Counter-" + counter + " Loaded total-" + totalFundLoaded );
			counter=counter+1;			
		}
		
		String expHeading= getLocalePropertyValue("lblPpssFundsDisplayedlblPart1").toUpperCase() + totalFundLoaded + getLocalePropertyValue("lblPpssFundsDisplayedlblPart2").toUpperCase();	  //Preview
		reportStep("Checking Heading with Total Fund Count","INFO");
		verifyExactText(header, expHeading);
		return this;
	}
	
	public PriceAndPerformancePage verifyPPSSPageFiltersAndItsDefaultValues()
	{	
		reportStep("Validating Availble Filters under Fund Identifier Tab","INFO");		
		String FiltersByCountry=ExcelDataProvider.getCellDataByFilter("PPSSPage", "Filters", sCountryName, 1);	
		String[] expFilters=FiltersByCountry.split(",");
		String filterName;
		
		for (int i = 0; i < expFilters.length; i++) {	
			
			expFilters[i]=expFilters[i].replace("\n", "");			
			filterName=expFilters[i].toString();			
			
			if(filterName.equals(getLocalePropertyValue("lblAssetClass"))) {
				verifyElementExist("id", localeProp.getProperty("objPpssAssetClassFilter"),expFilters[i]);
				verifyDefaultFilterValue(locateElement(localeProp.getProperty("objPpssAssetClassFilter")),expFilters[i]);}
			
			else if(filterName.equals(getLocalePropertyValue("lblInvstCatClass"))) {
				verifyElementExist("id", localeProp.getProperty("objPpssInvestmentCategoryFilter"),expFilters[i]);
				verifyDefaultFilterValue(locateElement(localeProp.getProperty("objPpssInvestmentCategoryFilter")),expFilters[i]);}
			
			else if(filterName.equals(getLocalePropertyValue("lblRegionClass"))) {
				verifyElementExist("id", localeProp.getProperty("objPpssRegionFilter"),expFilters[i]);
				verifyDefaultFilterValue(locateElement(localeProp.getProperty("objPpssRegionFilter")),expFilters[i]);}
			
			else if(filterName.equals(getLocalePropertyValue("lblCurrencyClass"))) {
				verifyElementExist("id", localeProp.getProperty("objPpssCurrencyFilter"),expFilters[i]);
				verifyDefaultFilterValue(locateElement(localeProp.getProperty("objPpssCurrencyFilter")),expFilters[i]);}
			
			else if(filterName.equals(getLocalePropertyValue("lblShareClass"))) {
				verifyElementExist("id", localeProp.getProperty("objPpssShareClassFilter"),expFilters[i]);
				verifyDefaultFilterValue(locateElement(localeProp.getProperty("objPpssShareClassFilter")),expFilters[i]);}
			
			else if(filterName.equals(getLocalePropertyValue("lblInvstVehClass"))) {
				verifyElementExist("id", localeProp.getProperty("objPpssInvestmentVehicleFilter"),expFilters[i]);
				verifyDefaultFilterValue(locateElement(localeProp.getProperty("objPpssInvestmentVehicleFilter")),expFilters[i]);}
			
			else if(filterName.equals(getLocalePropertyValue("lblMorningStarRating"))) {
				verifyElementExist("id", localeProp.getProperty("objPpssMorningStarFilter"),expFilters[i]);
				verifyDefaultFilterValue(locateElement(localeProp.getProperty("objPpssMorningStarFilter")),expFilters[i]);}
			
			else if(filterName.equals(getLocalePropertyValue("lblPpssFundFamilyFilter"))) {
				verifyElementExist("id", localeProp.getProperty("objPpssFundFamilyFilter"),expFilters[i]);
				verifyDefaultFilterValue(locateElement(localeProp.getProperty("objPpssFundFamilyFilter")),expFilters[i]);}
			
			else if(filterName.equals(getLocalePropertyValue("lblPpssFundCategoryFilter"))) {
				verifyElementExist("id", localeProp.getProperty("objPpssFundCategoryFilter"),expFilters[i]);
				verifyDefaultFilterValue(locateElement(localeProp.getProperty("objPpssFundCategoryFilter")),expFilters[i]);}
			
			else
				reportStep("Filter '" + expFilters[i] + "' may not expected for this Country / Not handeled by the script","FAIL");
			
		}
		
		return this;
	}
	
	public void verifyDefaultFilterValue(WebElement ele,String filterName)
	{
		if(!filterName.equals(getLocalePropertyValue("lblMorningStarRating"))) {
		String selectedOption=new Select(ele).getFirstSelectedOption().getText();
		if(selectedOption.equals(getLocalePropertyValue("lblPpssPageFilterDetaultValue")))
			reportStep("Default vlaue displayed as expected for the filter " + filterName , "PASS");
		else
			reportStep("Default vlaue may not displayed as expected for the filter " + filterName , "FAIL");
	
		}
		else {
			String selectedOption=new Select(ele).getFirstSelectedOption().getText();
			if(selectedOption.equals(getLocalePropertyValue("lblPpssPageMorningStarFilterDetaultValue")))
				reportStep("Default vlaue displayed as expected for the filter " + filterName , "PASS");
			else
				reportStep("Default vlaue may not displayed as expected for the filter " + filterName+selectedOption , "FAIL");
		}
		
	
	}
	
	public PriceAndPerformancePage verifyPageHeading()
	{		
		verifyExactText(locateElement("tag", getLocalePropertyValue("objPpssPageHeading")), "PRICE AND PERFORMANCE");
		return this;
	}
	public PriceAndPerformancePage verifyPageNavigation()
	{		
		if(verifyElementExist("tag", getLocalePropertyValue("objPpssPageHeading")))
			reportStep("User Navigated Successfully to PPSS Page through Mega Menu.", "PASS",true);			
		else
			reportStep("User may not Navigated Sucessfully to PPSS Page through Mega Menu.", "FAIL",true);
		return this;
	}

	
	public PriceAndPerformancePage validateBreadCrumbs()
	{
		reportStep("Validating PPSS Page BreadCrumbs","INFO");
		verifyTwoStringsExactly(getBreadCrumbs().trim(), getLocalePropertyValue("lblHmHomeBreadCrumb") + " > " +getLocalePropertyValue("lblHmPriceAndPerformanceBreadCrumb"));
		return this;
	}
	
	public String getBreadCrumbs()
	{
		String bCrumbs="";		
		List<WebElement> eleBreadCrumbs=locateElements("xpath", getLocalePropertyValue("objPpssBreadCrumbs"));
		 for (int i = 0; i < eleBreadCrumbs.size(); i++) {
			 if(i!=eleBreadCrumbs.size()-1)
				 bCrumbs=bCrumbs + eleBreadCrumbs.get(i).getText() + " > ";
			 else
				 bCrumbs=bCrumbs+ eleBreadCrumbs.get(i).getText();
		}
		 return bCrumbs;
	}

	public PriceAndPerformancePage verifyMainTabs()
	{
		List<WebElement> eleMainTabs=locateElements("xpath", getLocalePropertyValue("objPpssPerformanceAndFundIdentifierTabs"));
		reportStep("Validating Number of PPSS Page Main Tabs","INFO");		
		int tabCount=eleMainTabs.size();
		if (tabCount==2) {
			reportStep("PPSS page dispalyed totally two Main Tabs","PASS");
		
			//Checking Tab Names
			reportStep("Validating Main Tabs Names in PPSS page","INFO");			
			verifyExactText(eleMainTabs.get(0), getLocalePropertyValue("lblPpssTab1"));
			verifyExactText(eleMainTabs.get(1), getLocalePropertyValue("lblPpssTab2"));			
		}
		else
			reportStep("Number of Main Tabs in PPSS Page is not as Expected. Actual tabs are -" + tabCount,"FAIL");
		return this;
	}
	
	public PriceAndPerformancePage verifyDefaultTabSelected()
	{	
		List<WebElement> eleMainTabs=locateElements("xpath", getLocalePropertyValue("objPpssPerformanceAndFundIdentifierTabs"));
		reportStep("Validating PPSS Page Default Tab Selected","INFO");
		if (getAttribute(eleMainTabs.get(0),"class").contains("active"))
			reportStep("'PERFORMANCE' Tab is selecte by default","PASS");
		else
			reportStep("'PERFORMANCE' Tab may not selecte by default","FAIL");
		return this;
	}
	public PriceAndPerformancePage verifyDiscreteAnnualPerformanceSelectedByDefault()
	{	
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
		reportStep("Checking Discrete Annual Performance button is selected by default", "INFO");
		String color = commonMethods.getElementColor(locateElement("xpath",getLocalePropertyValue("objPpssDiscreteAnnualBtn")));
		if(color.equals(getLocalePropertyValue("lblPpssShowPerformanceLabelWhiteColorCode")))
			reportStep("Discrete Annual Performance button is selected by default", "PASS");
		else
			reportStep("Discrete Annual Performance button is not selected by default", "FAIL");

		return this;
	}
	
	public PriceAndPerformancePage verifyAnnualizedPerformanceSelectedByDefault()
	{	
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
		reportStep("Checking Annualized Performance button is selected by default", "INFO");
		String color = commonMethods.getElementColor(locateElement("xpath",getLocalePropertyValue("objPpssAnnualizedBtn")));
		if(color.equals(getLocalePropertyValue("lblPpssShowPerformanceLabelWhiteColorCode")))
			reportStep("Annualized Performance button is selected by default", "PASS");
		else
			reportStep("Annualized Performance button is not selected by default", "FAIL");

		return this;
	}
	
	public PriceAndPerformancePage verifyCumulativePerformanceSelectedByDefault()
	{	
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
		reportStep("Checking Cumulative Performance button is selected by default", "INFO");
		String color = commonMethods.getElementColor(locateElement("xpath",getLocalePropertyValue("objPpssCumulativeBtn")));
		if(color.equals(getLocalePropertyValue("lblPpssShowPerformanceLabelWhiteColorCode")))
			reportStep("Cumulative Performance button is selected by default", "PASS");
		else
			reportStep("Cumulative Performance button is not selected by default", "FAIL");

		return this;
	}
	
	public PriceAndPerformancePage verifyFundIdentifiers()
	{
			reportStep("Validating Fund Identifiers under PPSS page->Fund Identifier tab","INFO");
			//moveToFundIdentifierHeading();
			mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")));
			//Checking the given country supports Multilingual
			boolean isThisMultilingualCountry=false;
			if(ExcelDataProvider.getCellDataByFilter("BaseData", "MultiLingualCountries",sCountryName, 0)!=null)					
				isThisMultilingualCountry=true;
			String FundIdentifiersByCountry;
			
			if(isThisMultilingualCountry)
				FundIdentifiersByCountry=ExcelDataProvider.getCellDataByFilter("PPSSPage", "FundIdentifiers", sCountryName+"_"+sExecutionLanguage, 1);	
			else
				FundIdentifiersByCountry=ExcelDataProvider.getCellDataByFilter("PPSSPage", "FundIdentifiers", sCountryName, 1);		
			
			String[] expIdentifiers=FundIdentifiersByCountry.split(",");
			List<WebElement> actIdentifiers=locateElements("xpath", localeProp.getProperty("objPpssFundIdentifierList"));
			//getPropertyValue
			String Identifier;
			
			for (int i = 0; i < expIdentifiers.length; i++) {	
				
				expIdentifiers[i]=expIdentifiers[i].replace("\n", "");				
				verifyExactText(actIdentifiers.get(i), expIdentifiers[i].toUpperCase());
			}
			
			return this;
	}
	
	public PriceAndPerformancePage validateInfoCoinforADMCanada() {
		reportStep("Validating infocoin content for adm field","INFO");
	click(locateElement("xpath", "//tr[@class='header-sortable au-target']/th/a/span/a/span"));
		System.out.println("sss::"+locateElement("xpath", "//tr[@class='header-sortable au-target']/th/a/span/a").getAttribute("data-content"));
		if(sExecutionLanguage.equals("English"))
	verifyPartialAttribute(locateElement("xpath", "//tr[@class='header-sortable au-target']/th/a/span/a"), "data-content", "refers to the Investment Advisory Services Fee purchase option for series F, FT, PF, PF (Hedged) and PFT. Please see the simplified prospectus for further details.");
		else
			verifyPartialAttribute(locateElement("xpath", "//tr[@class='header-sortable au-target']/th/a/span/a"), "data-content", "« ADM » renvoie à l’option de frais de conseils en placement pour les séries F, FT, PF, PF (couverte) et PFT. Pour en savoir plus, veuillez consulter le prospectus simplifié.");		
		return this;
	}
	
	
	
	public PriceAndPerformancePage verifyFundIdentifiersDataFormats()
	{
		switch(sCountryName) {
		case "UK": 			verifyFundIdentifiersDataFormatsLux();
							break;
		case "Italy": 		verifyFundIdentifiersDataFormatsItaly();
							break;
		case "Luxembourg":	verifyFundIdentifiersDataFormatsLux(); 
				 			break;
		case "German":		verifyFundIdentifiersDataFormatsGerman(); 
							break;	
		case "Austria":		verifyFundIdentifiersDataFormatsGerman(); 
							break;	
		case "Switzerland":	verifyFundIdentifiersDataFormatsSwitzerland(); 
							break;
		case "Singapore":	verifyFundIdentifiersDataFormatsSingapore(); 
							break;
		case "Offshore":	verifyFundIdentifiersDataFormatsOffshore(); 
							break;
		
		default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
							break;
		}
			return this;
	}

	public PriceAndPerformancePage verifyFundIdentifiersDataFormatsLux()
	{
		reportStep("Validating Fund Identifier data value Formats","INFO");
		String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data[]=fundIdentifiersData.split("\\n");
		String fundName=data[0];		
		String otherData[]=data[1].split(" ",4);		
		
		String fundID=otherData[0].trim();		
		String ISIN=otherData[1].trim();		
		String SEDOL=otherData[2].trim();		
		String Bloomberg=otherData[3].trim();		
		
		reportStep("Checking Fund ID Type (ALPHA-NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(fundID,4,"NUMERIC");		
		
		reportStep("Checking ISIN Type (ALPHA-NUMERIC) and Length (XXXXXXXXXXXX)", "INFO");
		verifyStringFormat(ISIN,12,"ALPHA-NUMERIC");
		
		reportStep("Checking SEDOL Type (ALPHA-NUMERIC) and Length (XXXXXXX)", "INFO");
		verifyStringFormat(SEDOL,7,"ALPHA-NUMERIC");
		
		reportStep("Checking Bloomberg Type (ALPHABETIC) and Length (XXXXXXX XX)", "INFO");
		String[] temp=Bloomberg.split(" ");
		verifyStringFormat(temp[0],7,"ALPHABETIC");
		verifyStringFormat(temp[1],2,"ALPHABETIC");
		
		return this;
	}
	
	
	public PriceAndPerformancePage verifyFundIdentifiersDataFormatsCanada(String TestCaseID)
	{
		
		reportStep("Validating Fund Identifier data value Formats - Series A & T funds","INFO");
		
		HashMap<String,String> datum1 = getTestData(sCountryName, TestCaseID);
		FindAFund(datum1.get("Fund Name"));
		
		click(locateElement(getLocalePropertyValue("objPpssCurrencyFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssCurrencyFilter")), datum1.get("Currency"));		
		
		
		String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data[]=fundIdentifiersData.split("\\n");
		String fundName=data[0];		
		String otherData[]=data[1].split(" ",6);		
		
		String currency=otherData[0].trim();		
		String fundCode=otherData[1].trim();		
		String frontEnd=otherData[2].trim();		
		String lowLoad=otherData[3].trim();		
		String dsc=otherData[4].trim();		
		String adm=otherData[5].trim();		
		
		reportStep("Checking currency Type (ALPHABETIC) and Length (XXX)", "INFO");
		verifyStringFormat(currency,3,"ALPHABETIC");		
		
		reportStep("Checking fundCode (—) and Length (—)", "INFO");
		verifyStringFormat(fundCode,1,"—");
		
		reportStep("Checking frontEnd  (NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(frontEnd,4,"NUMERIC");
		
		reportStep("Checking lowLoad  (NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(lowLoad,4,"NUMERIC");
		
		reportStep("Checking dsc  (NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(dsc,4,"NUMERIC");
		
		reportStep("Checking adm  (—) or (NUMERIC) and Length (—) or (XXXX)", "INFO");
		if(verifyStringFormatbool(adm,1,"—") || verifyStringFormatbool(adm,4,"NUMERIC"))
			reportStep("adm format and lengthe is as expected", "PASS");
		else
			reportStep("adm format and lengthe is not as expected", "FAIL");
		
		reportStep("Validating Fund Identifier data value Formats - Non Series A & T funds","INFO");
		
		FindAFund(datum1.get("Non A&T Series Fund"));
	
		
		click(locateElement(getLocalePropertyValue("objPpssCurrencyFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssCurrencyFilter")), datum1.get("Non A&T Series Fund Currency"));	
		
		 fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data1[]=fundIdentifiersData.split("\\n");
		 fundName=data1[0];		
		String otherData1[]=data1[1].split(" ",6);		
		
		 currency=otherData1[0].trim();		
		 fundCode=otherData1[1].trim();		
		 frontEnd=otherData1[2].trim();		
		 lowLoad=otherData1[3].trim();		
		 dsc=otherData1[4].trim();		
		 adm=otherData[5].trim();		
			
		reportStep("Checking currency Type (ALPHABETIC) and Length (XXX)", "INFO");
		verifyStringFormat(currency,3,"ALPHABETIC");		
		
		reportStep("Checking fundCode  (NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(fundCode,4,"NUMERIC");
		
		reportStep("Checking frontEnd  (—) and Length (—)", "INFO");
		verifyStringFormat(frontEnd,1,"—");
		
		reportStep("Checking lowLoad  (—) and Length (—)", "INFO");
		verifyStringFormat(lowLoad,1,"—");
		
		reportStep("Checking dsc  (—) and Length (—)", "INFO");
		verifyStringFormat(dsc,1,"—");
		
		reportStep("Checking adm  (—) or (NUMERIC) and Length (—) or (XXXX)", "INFO");
		if(verifyStringFormatbool(adm,1,"—") || verifyStringFormatbool(adm,4,"NUMERIC"))
			reportStep("adm format and lengthe is as expected", "PASS");
		else
			reportStep("adm format and lengthe is not as expected", "FAIL");
		
		reportStep("Validating Fund Identifier data value Formats - Series V funds","INFO");
		
		FindAFund(datum1.get("Series V Fund"));
	
		
		click(locateElement(getLocalePropertyValue("objPpssCurrencyFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssCurrencyFilter")), datum1.get("Series V Currency"));	
		
		 fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data2[]=fundIdentifiersData.split("\\n");
		 fundName=data2[0];		
		String otherData2[]=data2[1].split(" ",6);		
		
		 currency=otherData2[0].trim();		
		 fundCode=otherData2[1].trim();		
		 frontEnd=otherData2[2].trim();		
		 lowLoad=otherData2[3].trim();		
		 dsc=otherData2[4].trim();		
		 adm=otherData[5].trim();	
		 
		reportStep("Checking currency Type (ALPHABETIC) and Length (XXX)", "INFO");
		verifyStringFormat(currency,3,"ALPHABETIC");		
		
		reportStep("Checking fundCode  (NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(fundCode,4,"NUMERIC");
		
		reportStep("Checking frontEnd  (—) and Length (—)", "INFO");
		verifyStringFormat(frontEnd,1,"—");
	
		reportStep("Checking lowLoad  (—) and Length (—)", "INFO");
		verifyStringFormat(lowLoad,1,"—");
		
		reportStep("Checking dsc  (—) and Length (—)", "INFO");
		verifyStringFormat(dsc,1,"—");
		
		reportStep("Checking adm  (—) or (NUMERIC) and Length (—) or (XXXX)", "INFO");
		if(verifyStringFormatbool(adm,1,"—") || verifyStringFormatbool(adm,4,"NUMERIC"))
			reportStep("adm format and lengthe is as expected", "PASS");
		else
			reportStep("adm format and lengthe is not as expected", "FAIL");
		
		return this;
	}
	
	public PriceAndPerformancePage verifyFundIdentifiersDataFormatsOffshore()
	{
		reportStep("Validating Fund Identifier data value Formats","INFO");
		String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data[]=fundIdentifiersData.split("\\n");
		String fundName=data[0];		
		String otherData[]=data[1].split(" ",5);		
		
		String fundID=otherData[0].trim();		
		String ISIN=otherData[1].trim();		
		String CUSIP=otherData[4].trim();		
		String Bloomberg=otherData[2].trim()+" "+otherData[3].trim();		
		
		reportStep("Checking Fund ID Type (ALPHA-NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(fundID,4,"NUMERIC");		
		
		reportStep("Checking ISIN Type (ALPHA-NUMERIC) and Length (XXXXXXXXXXXX)", "INFO");
		verifyStringFormat(ISIN,12,"ALPHA-NUMERIC");
		
		reportStep("Checking Bloomberg Type (ALPHABETIC) and Length (XXXXXXX XX)", "INFO");
		String[] temp=Bloomberg.split(" ");
		verifyStringFormat(temp[0],7,"ALPHABETIC");
		verifyStringFormat(temp[1],2,"ALPHABETIC");
		
		reportStep("Checking CUSIP Type (ALPHA-NUMERIC) and Length (XXX XXX XXX)", "INFO");
		String[] temp1=CUSIP.split(" ");
		verifyStringFormat(temp1[0],3,"ALPHA-NUMERIC");
		verifyStringFormat(temp1[1],3,"ALPHA-NUMERIC");
		verifyStringFormat(temp1[2],3,"ALPHA-NUMERIC");
		
		
		
		return this;
	}
	
	public PriceAndPerformancePage verifyFundIdentifiersDataFormatsSingapore()
	{
		reportStep("Validating Fund Identifier data value Formats","INFO");
		String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data[]=fundIdentifiersData.split("\\n");
		String fundName=data[0];		
		String otherData[]=data[1].split(" ",5);		
		
		String fundID=otherData[0].trim();		
		String ISIN=otherData[1].trim();		
		String SEDOL=otherData[4].trim();		
		String Bloomberg=otherData[2].trim()+" "+otherData[3].trim();		
		
		reportStep("Checking Fund ID Type (ALPHA-NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(fundID,4,"NUMERIC");		
		
		reportStep("Checking ISIN Type (ALPHA-NUMERIC) and Length (XXXXXXXXXXXX)", "INFO");
		verifyStringFormat(ISIN,12,"ALPHA-NUMERIC");
		
		reportStep("Checking SEDOL Type (ALPHA-NUMERIC) and Length (XXXXXXX)", "INFO");
		verifyStringFormat(SEDOL,7,"ALPHA-NUMERIC");
		
		reportStep("Checking Bloomberg Type (ALPHABETIC) and Length (XXXXXXX XX)", "INFO");
		String[] temp=Bloomberg.split(" ");
		verifyStringFormat(temp[0],7,"ALPHABETIC");
		verifyStringFormat(temp[1],2,"ALPHABETIC");
		
		return this;
	}
	
	public PriceAndPerformancePage verifyFundIdentifiersDataFormatsSwitzerland()
	{
		reportStep("Validating Fund Identifier data value Formats","INFO");
		String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data[]=fundIdentifiersData.split("\\n");
		String fundName=data[0];		
		String otherData[]=data[1].split(" ",4);		
		
		String fundID=otherData[0].trim();	
		String valorSymbol=otherData[1].trim();	
		String ISIN=otherData[2].trim();		
			
		String Bloomberg=otherData[3].trim();		
		
		reportStep("Checking Fund ID Type (ALPHA-NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(fundID,4,"NUMERIC");		
		
		reportStep("Checking ISIN Type (ALPHA-NUMERIC) and Length (XXXXXXXXXXXX)", "INFO");
		verifyStringFormat(ISIN,12,"ALPHA-NUMERIC");
		
		reportStep("Checking valorSymbol Type (NUMERIC) and Length (XXXXXXXX)", "INFO");
		verifyStringFormat(valorSymbol,8,"NUMERIC");
		
		reportStep("Checking Bloomberg Type (ALPHABETIC) and Length (XXXXXXX XX)", "INFO");
		String[] temp=Bloomberg.split(" ");
		verifyStringFormat(temp[0],7,"ALPHABETIC");
		verifyStringFormat(temp[1],2,"ALPHABETIC");
		
		return this;
	}
	
	public PriceAndPerformancePage verifyFundIdentifiersDataFormatsItaly()
	{
		reportStep("Validating Fund Identifier data value Formats","INFO");
		String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data[]=fundIdentifiersData.split("\\n");
		String fundName=data[0];		
		String otherData[]=data[1].split(" ",4);		

		String fundID=otherData[0].trim();		
		String ISIN=otherData[1].trim();		
	
		String Bloomberg=otherData[2]+" "+otherData[3].trim();		
		
		reportStep("Checking Fund ID Type (ALPHA-NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(fundID,4,"NUMERIC");		
		
		reportStep("Checking ISIN Type (ALPHA-NUMERIC) and Length (XXXXXXXXXXXX)", "INFO");
		verifyStringFormat(ISIN,12,"ALPHA-NUMERIC");
		
		reportStep("Checking Bloomberg Type (ALPHABETIC) and Length (XXXXXXX XX)", "INFO");
		String[] temp=Bloomberg.split(" ");
		verifyStringFormat(temp[0],7,"ALPHABETIC");
		verifyStringFormat(temp[1],2,"ALPHABETIC");
	
		return this;
	}
	
	
	public PriceAndPerformancePage verifyFundIdentifiersDataFormatsGerman()
	{
		reportStep("Validating Fund Identifier data value Formats","INFO");
		String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data[]=fundIdentifiersData.split("\\n");
		String fundName=data[0];		
		String otherData[]=data[1].split(" ",4);		
		System.out.println(fundName);
	
			String fundID=otherData[0].trim();		
			System.out.println(fundID);
			String WKN=otherData[1].trim();		
			String ISIN=otherData[2].trim();		
			String Bloomberg=otherData[3].trim();		
			
			reportStep("Checking Fund ID Type (ALPHA-NUMERIC) and Length (XXXX)", "INFO");
			verifyStringFormat(fundID,4,"NUMERIC");		
			
			reportStep("Checking WKN Type (ALPHA-NUMERIC) and Length (XXXXXX)", "INFO");
			verifyStringFormat(WKN,6,"ALPHA-NUMERIC");
			
			reportStep("Checking ISIN Type (ALPHA-NUMERIC) and Length (XXXXXXXXXXXX)", "INFO");
			verifyStringFormat(ISIN,12,"ALPHA-NUMERIC");
			
			reportStep("Checking Bloomberg Type (ALPHABETIC) and Length (XXXXXXX XX)", "INFO");
			String[] temp=Bloomberg.split(" ");
			verifyStringFormat(temp[0],7,"ALPHABETIC");
			verifyStringFormat(temp[1],2,"ALPHABETIC");
		
		return this;
	}

	public void verifyMaximumStringLengthAndType(String str,int expMaxLength,String Type){
		
		if(str.length()<=expMaxLength && str.length()>0 && commonMethods.verifyStringType(str, Type))
			reportStep("'" + str + "' length(Minimum 1 to Maximum " + expMaxLength + ") and its data type displayed as Expected", "PASS",false);
		else
			reportStep("'" + str + "' length(Minimum 1 to Maximum " + expMaxLength + ") and its data type may not displayed as Expected", "FAIL",false);
	}
	
	public void verifyStringFormat(String str,int expLength){
		
		if(str.length()==expLength)
			reportStep("'" + str + "' value is displayed in expected length", "PASS",false,true);
		else
			reportStep("'" + str + "' value may not displayed in expected length", "FAIL",false,true);
	}
	public void verifyStringFormat(String str,int expLength,String Type){
		
		if(str.length()==expLength && commonMethods.verifyStringType(str, Type))
			reportStep("'" + str + "' length and its data Type is displayed as expected", "PASS",false,true);
		else
			reportStep("'" + str + "' length and its data Type may not displayed as expected", "FAIL",false,true);
	}
	
public boolean verifyStringFormatbool(String str,int expLength,String Type){
		
		if(str.length()==expLength && commonMethods.verifyStringType(str, Type))
			return true;
		else
			return false;
	}


	public List<WebElement> getFundList(List<WebElement> eleFundRows)
	{		
		WebElement fLink;		
		if(eleFundRows.size()>0)
		{
			reportStep("Number of funds listed: " + eleFundRows.size(),"INFO");
			for(WebElement tblRow: eleFundRows)
			{				
				fLink=tblRow.findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));			
				if (fLink.getText().isEmpty()) break;
				fundsLinks.add(fLink);				
			}
		}else reportStep("Number of funds listed: " + eleFundRows.size(),"FAIL");		
		
		return fundsLinks;
	}	

	public PriceAndPerformancePage verifyQuickOrFindAFundSearch()	{
		
		verifyElementExist("xpath", localeProp.getProperty("objPpssQuickSearch"),"Quick Search");
		return this;
	}

	public PriceAndPerformancePage checkMatchingFunds(String strToCheck, String filterName, String FilterValue) throws InterruptedException
	{
		boolean isFilteredSharesOnly=true;	
		String fundLinkText="";
		Thread.sleep(2000);
		//List<WebElement> funds=getFundList(driver.findElements(By.xpath("//table[@id='table-1' and @class='table table-striped responsive']//tbody/tr[not(@class ='investment-vehicle')]")));
		List<WebElement> funds=getFundList(locateElements("xpath", getLocalePropertyValue("objPpssFundListTable")));
		
		reportStep("Checking Funds displayed according to the '" + filterName + "' Filter '" + FilterValue + "' Set","INFO");
		if(funds.size()>0)
		{		
			for(WebElement fndLinks: funds)
			{	
				//Thread.sleep(10);
				fundLinkText=fndLinks.getText().trim().replaceAll("\\r", "").replaceAll("\\n", "");				
				//fundLinkText=getText(fndLinks).trim().replaceAll("\\r", "").replaceAll("\\n", "");	
				if (!fundLinkText.contains(strToCheck))
					{isFilteredSharesOnly=false;
						break;}			
			}			
			if (isFilteredSharesOnly)
				reportStep("Funds displayed currectly according to the '" + filterName + "' Filter '" + FilterValue + "' Set","PASS" );
			else 
				reportStep("Funds may not displayed currectly according to the '" + filterName + "' Filter '" + FilterValue + "' Set. First UnExpected fund : " + fundLinkText,"FAIL");
			
		}else
			reportStep("No Matching Funds Displayed","FAIL");	
		
		return this;
	}

	public HashMap<String,String> getTestData(String Country,String TestCaseID)
	{
		tid=TestCaseID;
		HashMap<String,String> testData;
		
		boolean isThisMultilingualCountry=false;
		if(ExcelDataProvider.getCellDataByFilter("BaseData", "MultiLingualCountries",sCountryName, 0)!=null)					
			isThisMultilingualCountry=true;
			
		if(isThisMultilingualCountry)
			testData=	ExcelDataProvider.getExcelRowAsHashMapByTestID("TestData", Country+"_"+sExecutionLanguage,TestCaseID);
		else
			testData=	ExcelDataProvider.getExcelRowAsHashMapByTestID("TestData", Country,TestCaseID);	
		
		return testData;
	}
	public HashMap<String,String> getTestDataAsCollection(String workbook,String sheet)
	{
		System.out.println(workbook);
		System.out.println(sheet);
		boolean isThisMultilingualCountry=false;
		if(ExcelDataProvider.getCellDataByFilter("BaseData", "MultiLingualCountries",sCountryName, 0)!=null)					
			isThisMultilingualCountry=true;
		HashMap<String,String> testData;
		
		if(isThisMultilingualCountry)
			testData=ExcelDataProvider.getExcelDataAsCollection(workbook, sheet+"_"+sExecutionLanguage);
		else
			testData=ExcelDataProvider.getExcelDataAsCollection(workbook, sheet);
		
		
		return testData;
	}
	public PriceAndPerformancePage FindAFund(String searchString)
	{
		
		reportStep("Finding a Fund", "INFO");
		try {			
			
			WebDriverWait wait1=new WebDriverWait(driver, 1000);			
			wait1.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objPpssFindAFundTextBox"))));		
			type(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundTextBox")), searchString);
			
			WebDriverWait wait=new WebDriverWait(driver, 5000);			
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objPpssFindAFundGoButton"))));
			click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundGoButton")));
			//reportStep("acutal: " + locateElement("xpath",getLocalePropertyValue("objPpssHeadingWithFundCount")).getText(), "INFO");
			//reportStep("Expected: " + searchString.toUpperCase(), "INFO");
			int k=0;
			while (!locateElement("xpath",getLocalePropertyValue("objPpssHeadingWithFundCount")).getText().contains(searchString.toUpperCase()))
			{	
				k=k+1;
				Thread.sleep(1000);
				reportStep("acutal: " + locateElement("xpath",getLocalePropertyValue("objPpssHeadingWithFundCount")).getText(), "INFO");
				reportStep("Expected: " + searchString.toUpperCase(), "INFO");
				reportStep("checking " + k + " Time", "INFO");
				click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundGoButton")));				
				if(k>4)
					break;
			};
			
			//wait until filters loaded
			//WebDriverWait wait2=new WebDriverWait(driver, 700);	
			//wait2.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(getLocalePropertyValue("objPpssHeadingWithFundCount")), searchString.toUpperCase()));
/*			mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));		
			if(!locateElement("xpath",getLocalePropertyValue("objPpssHeadingWithFundCount")).getText().contains(searchString.toUpperCase()))
			{
				reportStep("Second Time checking", "INFO");
				click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundGoButton")));
				WebDriverWait wait3=new WebDriverWait(driver, 700);	
				wait3.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(getLocalePropertyValue("objPpssHeadingWithFundCount")), searchString.toUpperCase()));
			}	*/		
			reportStep(locateElement("xpath", getLocalePropertyValue("objPpssHeadingWithFundCount")).getText(), "INFO");
		}catch(Exception e)
		{
			e.printStackTrace();
			reportStep("UnExpected Error Occurs when Finding a Fund", "FAIL");
		}				
		
		
		if(!sCountryName.equals("Canada")) {
			waitUntilAllFundsLoaded();
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));
		}
		return this;
	}
	
	public PriceAndPerformancePage FindAFundWithFilters(HashMap<String,String> exlData,String filters)
	{
		
		reportStep("Finding a Fund", "INFO");
		try {			
			
			WebDriverWait wait1=new WebDriverWait(driver, 1000);			
			wait1.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objPpssFindAFundTextBox"))));		
			type(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundTextBox")), exlData.get("Fund Name"));
			
			WebDriverWait wait=new WebDriverWait(driver, 5000);			
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objPpssFindAFundGoButton"))));
			click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundGoButton")));
			//reportStep("acutal: " + locateElement("xpath",getLocalePropertyValue("objPpssHeadingWithFundCount")).getText(), "INFO");
			//reportStep("Expected: " + searchString.toUpperCase(), "INFO");
			
			//Code for selecting filters
			System.out.println(isDataProviderPresent);
			
			if(isDataProviderPresent)
				selectCurrency("CAD");
			 else {
				 System.out.println(tid);
			HashMap<String,String> testData1 = getTestData(sCountryName, tid);
			
			String[] filtersList = filters.split(",");
			for(String fName:filtersList) {
				switch(fName) {
				case "Share Class" 			:selectShareClass(exlData.get("ShareClassFilter"));
											break;
				case "Asset Class" 			:selectAssetClass(exlData.get("AssetClassFilter"));
											break;
				case "Investment Category"  :selectInvestmentCategory(exlData.get("InvestmentCategoryFilter"));
											break;
				case "Investment Vehicle" 	:selectInvestmentVehicle(exlData.get("InvestmentVehicleFilter"));
											break;
				case "Region" 			    :selectRegion(exlData.get("RegionFilter"));
											break;
				case "Currency" 			:selectCurrency(exlData.get("Share Class Currency"));
											break;
				default            			:reportStep("Filter Value provided is not correct: "+fName, "FAIL");
				}
			}
			 }
			
			int k=0;
			while (!locateElement("xpath",getLocalePropertyValue("objPpssHeadingWithFundCount")).getText().contains(exlData.get("Fund Name").toUpperCase()))
			{	
				k=k+1;
				Thread.sleep(1000);
				reportStep("acutal: " + locateElement("xpath",getLocalePropertyValue("objPpssHeadingWithFundCount")).getText(), "INFO");
				reportStep("Expected: " + exlData.get("Fund Name").toUpperCase(), "INFO");
				reportStep("checking " + k + " Time", "INFO");
				click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundGoButton")));				
				if(k>4)
					break;
			};
			
			//wait until filters loaded
			//WebDriverWait wait2=new WebDriverWait(driver, 700);	
			//wait2.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(getLocalePropertyValue("objPpssHeadingWithFundCount")), searchString.toUpperCase()));
/*			mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));		
			if(!locateElement("xpath",getLocalePropertyValue("objPpssHeadingWithFundCount")).getText().contains(searchString.toUpperCase()))
			{
				reportStep("Second Time checking", "INFO");
				click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundGoButton")));
				WebDriverWait wait3=new WebDriverWait(driver, 700);	
				wait3.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(getLocalePropertyValue("objPpssHeadingWithFundCount")), searchString.toUpperCase()));
			}	*/		
			reportStep(locateElement("xpath", getLocalePropertyValue("objPpssHeadingWithFundCount")).getText(), "INFO");
		}catch(Exception e)
		{
			e.printStackTrace();
			reportStep("UnExpected Error Occurs when Finding a Fund", "FAIL");
		}				
		
		waitUntilAllFundsLoaded();
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));
		
		return this;
	}
	
	public PriceAndPerformancePage selectShareClass(String shareClass)
	{
		//HashMap<String,String> testData=ExcelDataProvider.getExcelRowAsHashMapByTestID("TestData", CountryName,"PPSS007");
		//System.out.println(data.get("ShareClassFilter"));
		locateElement("xpath",getLocalePropertyValue("objPpssFindAFundTextBox")).clear();
		//For Smoke Scenarios - Clicking on performance tab
		click(locateElements("xpath", "//ft-ppss-tab-header//a").get(0));
		click(locateElement(getLocalePropertyValue("objPpssShareClassFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssShareClassFilter")), shareClass);		
		WebDriverWait wait=new WebDriverWait(driver, 50);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objFOFundIdentiferTab"))));
		
		return this;
	}
	
	public PriceAndPerformancePage selectAssetClass(String assetClass)
	{
		waitUntilAllFundsLoaded();
		//System.out.println(data.get("AssetClassFilter"));
		click(locateElement(getLocalePropertyValue("objPpssAssetClassFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssAssetClassFilter")), assetClass);		

		return this;
	}
	public PriceAndPerformancePage selectInvestmentVehicle(String invstVehicle)
	{
		waitUntilAllFundsLoaded();
		//System.out.println(data.get("InvestmentVehicleFilter"));
		click(locateElement(getLocalePropertyValue("objPpssInvestmentVehicleFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssInvestmentVehicleFilter")), invstVehicle);		
	
		return this;
	}
	
	public PriceAndPerformancePage selectMorningstarRating(String invstVehicle)
	{
		waitUntilAllFundsLoaded();
		//System.out.println(data.get("InvestmentVehicleFilter"));
		click(locateElement(getLocalePropertyValue("objPpssMorningStarFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssMorningStarFilter")), invstVehicle);		
	
		return this;
	}
	public PriceAndPerformancePage selectInvestmentCategory(String invstCategory)
	{
		waitUntilAllFundsLoaded();
		//System.out.println(data.get("InvestmentCategoryFilter"));
		click(locateElement(getLocalePropertyValue("objPpssInvestmentCategoryFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssInvestmentCategoryFilter")), invstCategory);		

		return this;
	}
	
	public PriceAndPerformancePage selectFundCategory(String fundCategory)
	{
		waitUntilAllFundsLoaded();
		//System.out.println(data.get("InvestmentCategoryFilter"));
		click(locateElement(getLocalePropertyValue("objPpssFundCategoryFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssFundCategoryFilter")), fundCategory);		

		return this;
	}
	
	public PriceAndPerformancePage selectFundFamily(String fundFamily)
	{
		waitUntilAllFundsLoaded();
		//System.out.println(data.get("InvestmentCategoryFilter"));
		click(locateElement(getLocalePropertyValue("objPpssFundFamilyFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssFundFamilyFilter")), fundFamily);		

		return this;
	}
	public PriceAndPerformancePage selectRegion(String region)
	{
		waitUntilAllFundsLoaded();
		//System.out.println(data.get("RegionFilter"));
		click(locateElement(getLocalePropertyValue("objPpssRegionFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssRegionFilter")), region);		
	
		return this;
	}
	public PriceAndPerformancePage selectCurrency(String Currency)
	{
		
		waitUntilAllFundsLoaded();
		//System.out.println(data.get("CurrencyFilter"));
		click(locateElement(getLocalePropertyValue("objPpssCurrencyFilter")));		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssCurrencyFilter")), Currency);		
		return this;
	}
	public PriceAndPerformancePage verifyFilterResult(HashMap<String,String> Testdata) throws InterruptedException
	{
		reportStep("Validating Filters results", "INFO");
		//For Smoke Scenarios - Clicking on performance tab
		click(locateElements("xpath", "//ft-ppss-tab-header//a").get(0));
		click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundGoButton")));
		waitUntilAllFundsLoaded();
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));
		List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		//System.out.println(tableRows.get(0).getText());	
		String data[]=tableRows.get(0).getText().split("\\n");
		String fundName=data[0];
		WebElement fLink=tableRows.get(0).findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
		verifyExactText(fLink, Testdata.get("ExpFilterResult"));
	
		return this;
	}
	
	//@FindBy(how=How.ID,using="ASSET_CLASS")
	//public WebElement eleAssetClassFilter=locateElement(getLocalePropertyValue("objPpssAssetClassFilter"));
	
/*	public PriceAndPerformancePage setAndVerifyAssetClassFilter(String filterValue) throws InterruptedException
	{
		WebElement eleAssetClassFilter=locateElement(getLocalePropertyValue("objPpssAssetClassFilter"));
		click(eleAssetClassFilter);
		selectDropDownUsingText(eleAssetClassFilter, filterValue);
		//Thread.sleep(5000);
		WebDriverWait wait=new WebDriverWait(driver, 50);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objFOFundIdentiferTab"))));
		checkMatchingFunds(" K2 ", "ASSET CLASS", filterValue);	
		return this;
	}*/
	public PriceAndPerformancePage setAndVerifyInvestmentVehicleFilter(String filterValue) throws InterruptedException
	{
		try
		{
			int fundIndex=0;		
			String FundName;
			
			WebElement eleInvestmentVehicleFilter=locateElement(getLocalePropertyValue("objPpssInvestmentVehicleFilter"));
			click(eleInvestmentVehicleFilter);
			selectDropDownUsingText(eleInvestmentVehicleFilter, filterValue);
			
			WebDriverWait wait=new WebDriverWait(driver, 50);			
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objFOFundIdentiferTab"))));
			
			List<WebElement> investmentVehilceTitles=locateElements("xpath", getLocalePropertyValue("objPpssInvestmentVehicleListFromFundList"));
			Thread.sleep(2000);
			reportStep("Checking Fund displayed after the filter '" + filterValue + "' set","INFO");
			List<WebElement> funds=getFundList(locateElements("xpath", getLocalePropertyValue("objPpssFundListTable")));
			
			reportStep("Check application displayes only Funds belongs to Investment Vehicle selected '" + filterValue + "'","INFO");
			if(investmentVehilceTitles.size()==1)
			{	
				verifyExactText(investmentVehilceTitles.get(0), filterValue.trim().toUpperCase());
				if(funds.size()>0) {
					fundIndex=getRandomNumberBetween(0, funds.size()-1);
					FundName=funds.get(fundIndex).getText();
					System.out.println("Fund index: " + fundIndex);
					System.out.println("Fund Name: "+ funds.get(fundIndex).getText());
					click(locateElement("link", FundName));				
					
					reportStep("Check Investment Vehicle '" + filterValue + "' is displayed for the selected fund '" + FundName + "'","INFO");				
					verifyExactText(locateElement("class", getLocalePropertyValue("objFOInvestmentVehilce")), filterValue);
				}else
					reportStep("No Funds Displayed for Investment Vehicle selected '" + filterValue + "'","FAIL");		
	
			}else
				reportStep("No Matching Funds Displayed for Investment Vehicle selected '" + filterValue + "'","FAIL");
		}catch(RuntimeException e)
		{
			reportStep("Runtime Exception occured: " + e.getMessage(),"FAIL");	
		}
		return this;
	}
	
	public PriceAndPerformancePage setShareClass(String filterValue) throws InterruptedException
	{
		click(locateElement(getLocalePropertyValue("objPpssShareClassFilter")));
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssShareClassFilter")), filterValue);
		Thread.sleep(5000);
		WebDriverWait wait=new WebDriverWait(driver, 50);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objFOFundIdentiferTab"))));
		return this;
	}
	public PriceAndPerformancePage selectPerformanceAsCumulative()
	{		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objPpssCumulativeBtn")));
		click(locateElement("xpath", getLocalePropertyValue("objPpssCumulativeBtn")));
		WebDriverWait wait=new WebDriverWait(driver, 50);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(getLocalePropertyValue("objPpssTimeToggleDropDown"))));
		//wait.until(ExpectedConditions.elementToBeSelected(locateElement("xpath", getLocalePropertyValue("objPpssTimeToggleDropDown"))));
		return this;
	}
	
	public PriceAndPerformancePage selectPerformanceAsCalendar()
	{		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objPpssCalendarBtn")));
		click(locateElement("xpath", getLocalePropertyValue("objPpssCalendarBtn")));
		WebDriverWait wait=new WebDriverWait(driver, 50);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(getLocalePropertyValue("objPpssTimeToggleDropDown"))));
		//wait.until(ExpectedConditions.elementToBeSelected(locateElement("xpath", getLocalePropertyValue("objPpssTimeToggleDropDown"))));
		return this;
	}
	
	public PriceAndPerformancePage selectPerformanceAsDiscreteAnnual()
	{		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objPpssDiscreteAnnualBtn")));
		click(locateElement("xpath", getLocalePropertyValue("objPpssDiscreteAnnualBtn")));
		WebDriverWait wait=new WebDriverWait(driver, 50);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objPpssCumulativeBtn"))));		
		return this;
	}
	
	public PriceAndPerformancePage selectPerformanceAsAnnualized()
	{		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objPpssAnnualizedBtn")));
		click(locateElement("xpath", getLocalePropertyValue("objPpssAnnualizedBtn")));
		WebDriverWait wait=new WebDriverWait(driver, 50);			
		if(sCountryName.equals("Canada"))
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objPpssAnnualizedBtn"))));	
		else
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objPpssCumulativeBtn"))));		
		return this;
	}
	
	public PriceAndPerformancePage selecterTimeToggleAsMonthEnd()
	{		
		click(locateElement(getLocalePropertyValue("objPpssTimeToggleDropDown")));
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssTimeToggleDropDown")), getLocalePropertyValue("lblPpssTimeToggleMonthEnd"));
		WebDriverWait wait=new WebDriverWait(driver, 50);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(getLocalePropertyValue("objPpssTimeToggleDropDown"))));		
		return this;
	}
	public PriceAndPerformancePage selecterTimeToggleAsQuarterEnd()
	{		
		click(locateElement(getLocalePropertyValue("objPpssTimeToggleDropDown")));
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objPpssTimeToggleDropDown")), getLocalePropertyValue("lblPpssTimeToggleQuarterEnd"));
		WebDriverWait wait=new WebDriverWait(driver, 50);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(getLocalePropertyValue("objPpssTimeToggleDropDown"))));		
		return this;
	}
	
	public PriceAndPerformancePage verifyCumulativeColumnNamesByQuarterEnd()
	{	
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
/*		verifyElementExist("xpath", getLocalePropertyValue("objPpssFundNameColumn"), getLocalePropertyValue("lblPpssFundNameColumn"));
		verifyElementExist("xpath", getLocalePropertyValue("objPpssCurrencyColumn"), getLocalePropertyValue("lblPpssCurrencyColumn"));
		verifyElementExist("xpath", getLocalePropertyValue("objPpssNavColumn"), getLocalePropertyValue("lblPpssNAVColumn"));
		verifyElementExist("xpath", getLocalePropertyValue("objPpssMorningstarRating"), getLocalePropertyValue("lblPpssMorningstarRating"));*/
		verifyCommonColumnNames();
		verifyPerformanceColumns(getLocalePropertyValue("lblPpssTimeToggleQuarterEnd"));		

		return this;
	}
	public PriceAndPerformancePage verifyCumulativeColumnNamesByMonthEnd()
	{			
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
/*		verifyElementExist("xpath", getLocalePropertyValue("objPpssFundNameColumn"), getLocalePropertyValue("lblPpssFundNameColumn"));
		verifyElementExist("xpath", getLocalePropertyValue("objPpssCurrencyColumn"), getLocalePropertyValue("lblPpssCurrencyColumn"));
		verifyElementExist("xpath", getLocalePropertyValue("objPpssNavColumn"), getLocalePropertyValue("lblPpssNAVColumn"));
		verifyElementExist("xpath", getLocalePropertyValue("objPpssMorningstarRating"), getLocalePropertyValue("lblPpssMorningstarRating"));*/
		verifyCommonColumnNames();
		
		verifyPerformanceColumns(getLocalePropertyValue("lblPpssTimeToggleMonthEnd"));
		return this;
	}
	
	public void verifyPerformanceColumns(String timeToggle)
	{
		reportStep("Checking Column Names for Cumulative Performance + " + timeToggle + " Time Toggle", "INFO");
		List<WebElement> CumulativeColumns1=locateElements("xpath", getLocalePropertyValue("objPpssPerformanceTableCumulativeColumns"));
		
			if(!(sCountryName.equals("German")||sCountryName.equals("Austria"))) {
		if(CumulativeColumns1.size()!=6)
			reportStep("Expected Number of Performance columns may not displayd", "FAIL");	
		
		verifyExactText(CumulativeColumns1.get(0), getLocalePropertyValue("lblPpssCumulativeYTDColumn"));
		verifyExactText(CumulativeColumns1.get(1), getLocalePropertyValue("lblPpssCumulative1YRColumn"));
		verifyExactText(CumulativeColumns1.get(2), getLocalePropertyValue("lblPpssCumulative3YRColumn"));
		verifyExactText(CumulativeColumns1.get(3), getLocalePropertyValue("lblPpssCumulative5YRColumn"));
		verifyExactText(CumulativeColumns1.get(4), getLocalePropertyValue("lblPpssCumulative10YRColumn"));
		mouseOver(CumulativeColumns1.get(5));
		verifyPartialText(CumulativeColumns1.get(5), getLocalePropertyValue("lblPpssSinceInceptionColumn"));	
			}
			else {
				if(CumulativeColumns1.size()!=5)
					reportStep("Expected Number of Performance columns may not displayd", "FAIL");	
				
			
				verifyExactText(CumulativeColumns1.get(0), getLocalePropertyValue("lblPpssCumulative1YRColumn"));
				verifyExactText(CumulativeColumns1.get(1), getLocalePropertyValue("lblPpssCumulative3YRColumn"));
				verifyExactText(CumulativeColumns1.get(2), getLocalePropertyValue("lblPpssCumulative5YRColumn"));
				verifyExactText(CumulativeColumns1.get(3), getLocalePropertyValue("lblPpssCumulative10YRColumn"));
				mouseOver(CumulativeColumns1.get(4));
				verifyExactText(CumulativeColumns1.get(4), getLocalePropertyValue("lblPpssLaunchDate"));	
			}
			}
	
	public void verifyPerformanceColumnsAnnualized(String timeToggle)
	{
		reportStep("Checking Column Names for Annualized Performance + " + timeToggle + " Time Toggle", "INFO");
		List<WebElement> AnnualizedColumns1=locateElements("xpath", getLocalePropertyValue("objPpssPerformanceTableAnnualizedColumns"));
		
			
				if(AnnualizedColumns1.size()!=5)
					reportStep("Expected Number of Performance columns may not displayd", "FAIL");	
					
				verifyExactText(AnnualizedColumns1.get(0), getLocalePropertyValue("lblPpssCumulative1YRColumn"));
				verifyExactText(AnnualizedColumns1.get(1), getLocalePropertyValue("lblPpssCumulative3YRColumn"));
				verifyExactText(AnnualizedColumns1.get(2), getLocalePropertyValue("lblPpssCumulative5YRColumn"));
				verifyExactText(AnnualizedColumns1.get(3), getLocalePropertyValue("lblPpssCumulative10YRColumn"));
				mouseOver(AnnualizedColumns1.get(4));
				verifyExactText(AnnualizedColumns1.get(4), getLocalePropertyValue("lblFOAnnualizedPerfSinceInception").toUpperCase());	
			}
	
	public void verifyPerformanceColumnsCalendar(String timeToggle)
	{
		reportStep("Checking Column Names for Calendar Performance + " + timeToggle + " Time Toggle", "INFO");
		List<WebElement> CalendarColumns1=locateElements("xpath", getLocalePropertyValue("objPpssPerformanceTableCalendarColumns"));
		
			
				if(CalendarColumns1.size()!=7)
					reportStep("Expected Number of Performance columns may not displayd", "FAIL");	
					
				verifyExactText(CalendarColumns1.get(0), getLocalePropertyValue("lblPpssCalendarYTDColumn"));
		
				int year = Calendar.getInstance().get(Calendar.YEAR);
				int month = Calendar.getInstance().get(Calendar.MONTH);
				System.out.println(year);
				System.out.println(month);
				if(month!=12)
					year =year -1;
				
				int j=1;
				for (int i=year;i>(year-5);i--){
					verifyExactText(CalendarColumns1.get(j),Integer.toString(i));
					j++;
				}
						
				mouseOver(CalendarColumns1.get(6));
				verifyExactText(CalendarColumns1.get(6), getLocalePropertyValue("lblPpssCalendarSinceInception").toUpperCase());	
			}		
	
	public void verifyCommonColumnNames()
	{
		verifyElementExist("xpath", getLocalePropertyValue("objPpssFundNameColumn"), getLocalePropertyValue("lblPpssFundNameColumn"));
		verifyElementExist("xpath", getLocalePropertyValue("objPpssCurrencyColumn"), getLocalePropertyValue("lblPpssCurrencyColumn"));
		verifyElementExist("xpath", getLocalePropertyValue("objPpssNavColumn"), getLocalePropertyValue("lblPpssNAVColumn"));
		if(!(sCountryName.equals("German")||sCountryName.equals("Italy")||sCountryName.equals("Austria")))
		verifyElementExist("xpath", getLocalePropertyValue("objPpssMorningstarRating"), getLocalePropertyValue("lblPpssMorningstarRating"));		
			
	}
	
	public PriceAndPerformancePage verifyDiscreteAnnualColumnNamesForQuarterEnd() throws ParseException
	{	
		
		//mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));
		verifyCommonColumnNames();

		String DAColumns[]=getExpDiscreteAnnualColumnNames("QuarterEnd").split(",");		
		
		reportStep("Checking Column Names for Discrete Annual Performance + " + getLocalePropertyValue("lblPpssTimeToggleQuarterEnd") + " Time Toggle", "INFO");
		List<WebElement> DescreteAnnualColumns=locateElements("xpath", getLocalePropertyValue("objPpssPerformanceTableDiscreteAnnualColumns"));		
		if(DescreteAnnualColumns.size()!=6)
			reportStep("Expected Number of Performance columns may not displayd", "FAIL");
		
		verifyExactText(DescreteAnnualColumns.get(0), DAColumns[4]);
		verifyExactText(DescreteAnnualColumns.get(1), DAColumns[3]);
		verifyExactText(DescreteAnnualColumns.get(2), DAColumns[2]);
		verifyExactText(DescreteAnnualColumns.get(3), DAColumns[1]);
		verifyExactText(DescreteAnnualColumns.get(4), DAColumns[0]);	
		//verifyExactText(DescreteAnnualColumns.get(5), getLocalePropertyValue("lblPpssPerformanceInceptionDateColumn"));
		
		if(DescreteAnnualColumns.get(5).getText().contains(getLocalePropertyValue("lblPpssPerformanceInceptionDateColumn")))
			reportStep("'Performance Inception Date' Heading displayed", "PASS");
		else
			reportStep("Heading Expected: " + getLocalePropertyValue("lblPpssPerformanceInceptionDateColumn") + " but Actual is: " + DescreteAnnualColumns.get(5).getText(), "FAIL");

		return this;
	}	
	
	public PriceAndPerformancePage verifyAnnualizedColumnNamesForQuarterEnd() throws ParseException
	{	
		
				mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
				verifyCommonColumnNames();
				verifyPerformanceColumnsAnnualized(getLocalePropertyValue("lblPpssTimeToggleQuarterEnd"));		

				return this;
	}
	
	public PriceAndPerformancePage verifyCalendarColumnNamesForQuarterEnd() throws ParseException
	{	
		
				mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
				verifyCommonColumnNames();
				verifyPerformanceColumnsCalendar(getLocalePropertyValue("lblPpssTimeToggleQuarterEnd"));		

				return this;
	}
	
	public PriceAndPerformancePage verifyDiscreteAnnualColumnNamesForMonthEnd() throws ParseException
	{	
		
		//mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));		
		verifyCommonColumnNames();
		
		//preparing Expected data
		String DAColumns[]=getExpDiscreteAnnualColumnNames("MonthEnd").split(",");		
		
		reportStep("Checking Column Names for Discrete Annual Performance + " + getLocalePropertyValue("lblPpssTimeToggleMonthEnd") + " Time Toggle", "INFO");
		List<WebElement> DescreteAnnualColumns=locateElements("xpath", getLocalePropertyValue("objPpssPerformanceTableDiscreteAnnualColumns"));		
		if(DescreteAnnualColumns.size()!=6)
			reportStep("Expected Number of Performance columns may not displayd", "FAIL");
		
		verifyExactText(DescreteAnnualColumns.get(0), DAColumns[4]);
		verifyExactText(DescreteAnnualColumns.get(1), DAColumns[3]);
		verifyExactText(DescreteAnnualColumns.get(2), DAColumns[2]);
		verifyExactText(DescreteAnnualColumns.get(3), DAColumns[1]);
		verifyExactText(DescreteAnnualColumns.get(4), DAColumns[0]);	
		//verifyExactText(DescreteAnnualColumns.get(5), getLocalePropertyValue("lblPpssPerformanceInceptionDateColumn"));
		
		if(DescreteAnnualColumns.get(5).getText().contains(getLocalePropertyValue("lblPpssPerformanceInceptionDateColumn")))
			reportStep("'Performance Inception Date' Heading displayed", "PASS");
		else
			reportStep("Heading Expected: " + getLocalePropertyValue("lblPpssPerformanceInceptionDateColumn") + " but Actual is: " + DescreteAnnualColumns.get(5).getText(), "FAIL");

		return this;
	}
	
	public PriceAndPerformancePage verifyCalendarColumnNamesForMonthEnd() throws ParseException
	{	
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
		verifyCommonColumnNames();
		verifyPerformanceColumnsCalendar(getLocalePropertyValue("lblPpssTimeToggleMonthEnd"));	
		
		return this;
	}
	
	public PriceAndPerformancePage verifyAnnualizedColumnNamesForMonthEnd() throws ParseException
	{	
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
		verifyCommonColumnNames();
		verifyPerformanceColumnsAnnualized(getLocalePropertyValue("lblPpssTimeToggleMonthEnd"));	
		
		return this;
	}
	
	public String getExpDiscreteAnnualColumnNames(String TimeToggle) throws ParseException
	{
    	Calendar calendar = Calendar.getInstance();
    	//calendar.add(Calendar.MONTH, -differenceMonth);   	

    	String expMonth="InvalidMonth";
    	String month="InvlidMonth";
    	int expyear=-5;
		
/*    	if(TimeToggle.equals("QuarterEnd"))
    	{   
    		month=new SimpleDateFormat("MMM").format(calendar.getTime());
			if(month.equals("Jan") || month.equals("Feb") || month.equals("Mar")) {
				expMonth=getLocalePropertyValue("lblDec");
				expyear=-6;}
			else if(month.equals("Apr") || month.equals("May") || month.equals("Jun"))
				expMonth=getLocalePropertyValue("lblMar");
			else if(month.equals("Jul") || month.equals("Aug") || month.equals("Sep"))
				expMonth=getLocalePropertyValue("lblJune");
			else if(month.equals("Oct") || month.equals("Nov") || month.equals("Dec"))
				expMonth=getLocalePropertyValue("lblSep");    	
			month=expMonth.toUpperCase();
    	}
    	else if(TimeToggle.equals("MonthEnd"))
    	{*/
/*    		calendar.add(Calendar.MONTH, -1);
    		month=new SimpleDateFormat("MMM").format(calendar.getTime());
    		if(month.equals("Jan") || month.equals("Feb") || month.equals("Mar"))
    			expyear=-6;*/
    		
    		//Updating based on Monthly/Quarterly As at Date in the Investment Vehicle title
    		String strMnthYarDate=locateElement("xpath", getLocalePropertyValue("objPpssInvestmentVehicleMonthlyOrQuarterlyAsAtDate")).findElement(By.tagName("small")).getText();

    		strMnthYarDate=strMnthYarDate.split(getLocalePropertyValue("lblFOAsAtDateTxt")+" ")[1].trim();
    		System.out.println("MonYar date: " + strMnthYarDate);
      		SimpleDateFormat format = new SimpleDateFormat(getLocalePropertyValue("lblDateFormat"));
    		Date date1=format.parse(strMnthYarDate);    		
    		month=new SimpleDateFormat("MMM").format(date1).toUpperCase();	
    		String asAtYear=strMnthYarDate.split(getLocalePropertyValue("lblDateSeperator"))[2].trim();	    	
	    	System.out.println("as at year: " +asAtYear);
	    	int currentyear=calendar.get(Calendar.YEAR);
	    	System.out.println("current year2: " +currentyear);
	    	month=getMonthNameInGermanFromMonthNameInEnglish(month).toUpperCase();
    		if(currentyear-Integer.parseInt(asAtYear)>0)
    			{expyear=-6;
    			System.out.println("-7");}
    		else
    			{expyear=-5;
    			System.out.println("-6");
    			}
    	//}
		//calendar.add(Calendar.YEAR, -5);
		calendar.add(Calendar.YEAR, expyear);
		String startRange=new SimpleDateFormat("yy").format(calendar.getTime());
		int sRange=Integer.parseInt(startRange);			
		int sRng,eRng;
		String cols;
		
		String columnNames="";
		
		sRng=sRange;
		eRng=sRange+1;		
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		
		return columnNames;
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
			case "OCT" :	return getLocalePropertyValue("lblOct");	
			case "NOV" :	return getLocalePropertyValue("lblNov");	
			case "DEC" :	return getLocalePropertyValue("lblDec");
			default   : return null;
			}
	 }
	
	public PriceAndPerformancePage checkFindAFundComponentExist()
	{			
		verifyElementExist("xpath", getLocalePropertyValue("objPpssFindAFundTextBox"), "Find A Fund Component");
		return this;
	}
	
	public PriceAndPerformancePage checkFindAFundInnerText()
	{	
		reportStep("Verifying Find A Fund text box Inner Text", "INFO");
		verifyExactAttribute(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundTextBox")), "title",getLocalePropertyValue("lblPpssFindAFundInnerText"));
		return this;
	}
	public WebElement getFirstFundLinkFromPerformanceTable()
	{
		List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		WebElement fLink=tableRows.get(0).findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
		return fLink;
	}

	public PriceAndPerformancePage enterStringToSearchAndValidateResult(HashMap<String,String> testData) throws InterruptedException
	{	
		reportStep("Validating Search result through Find A Fund search", "INFO");
		//System.out.println(testData.get("FindAFundSearchString").toString());
		type(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundTextBox")), testData.get("FindAFundSearchString").toString());
		Thread.sleep(5000);
		click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundGoButton")));		
		
		waitUntilAllFundsLoaded();
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));
		WebElement fLink=getFirstFundLinkFromPerformanceTable();
		reportStep("Checking the Fund filtered based on the Search string '" + testData.get("FindAFundSearchString").toString() + "' through Find A Fund", "INFO");
		verifyExactText(fLink, testData.get("FindAFundSearchString"));
		return this;
	}
	public PriceAndPerformancePage checkResetFeatureByClickingXButton(HashMap<String,String> testData)
	{	
		reportStep("Validating Find A Fund reset feature by clicking cross (X) button in Find A Fund Edit Box", "INFO");		
		click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundCloseButton")));		
		
		waitUntilAllFundsLoaded();
		checkFindAFundInnerText();
		
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));
		List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		reportStep("Checking the Fund list after resetting Find A Fund search", "INFO");

		if(tableRows.size()>1)
			reportStep("Fund List reset back successfully", "PASS");			
		else
			reportStep("Fund List may not reset back successfully", "FAIL");	
		return this;
	}
	public FundOverviewPage enterStringToSearchAndValidateFlyoutMenuAccess(HashMap<String,String> testData) throws InterruptedException, FileNotFoundException, IOException
	{	
		reportStep("Validating Find A Fund Flyout menu Access", "INFO");	
		type(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundTextBox")), testData.get("FindAFundSearchString").toString().split("-")[0]);
		Thread.sleep(200);
		click(locateElement("xpath",getLocalePropertyValue("objPpssFindAFundSearchResultFundLink")));		
	
		return new FundOverviewPage(driver,test,testData.get("FindAFundSearchString").toString());
	}
	

	public FundOverviewPage goToK2AlternativeFundPage() throws InterruptedException, FileNotFoundException, IOException
	{	
		if(sCountryName.equals("Canada") && sExecutionLanguage.equals("French"))
		FindAFund("Fonds de stratégies alternatives Franklin K2");
			else
		FindAFund("K2 Alternative Strategies Fund");
		String fundName=getRandomFundNameFromPerformanceList();
//		clickWithNoSnap(locateElement("link", fundName));	
		clickRandomFundNameFromPerformanceList(fundName);
		//clickFund(fundName);		
		
		if(sCountryName.equals("Canada")) {
			if(sExecutionLanguage.equals("French"))
				click(locateElement("xpath", "//span[text()='Acceptez']"));
			else
				click(locateElement("xpath", "//span[text()='Accept']"));
		}
		return new FundOverviewPage(driver,test,fundName);
	}
	public FundOverviewPage goToK2GlobalMacroOpportunitiesFundPage() throws InterruptedException, FileNotFoundException, IOException
	{			
		FindAFund("K2 Global Macro Opportunities Fund");
		Thread.sleep(2000);
		String fundName=getRandomFundNameFromPerformanceList();
//		clickWithNoSnap(locateElement("link", fundName));		
		clickRandomFundNameFromPerformanceList(fundName);
		return new FundOverviewPage(driver,test,fundName);
	}
	public FundOverviewPage goToK2LongShortCreditFundPage() throws InterruptedException, FileNotFoundException, IOException
	{			
		FindAFund("K2 Long Short Credit Fund");
		String fundName=getRandomFundNameFromPerformanceList();
//		clickWithNoSnap(locateElement("link", fundName));		
		clickRandomFundNameFromPerformanceList(fundName);
		return new FundOverviewPage(driver,test,fundName);
	}
	
	public FundOverviewPage clickAnyFundFromAssetClass(String assetClass) throws InterruptedException, FileNotFoundException, IOException
	{			
		selectAssetClass(assetClass);
		WebDriverWait wait=new WebDriverWait(driver, 200);			
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", getLocalePropertyValue("objFOFundIdentiferTab"))));
		
		String fundName=getRandomFundNameFromPerformanceList();
		//String fundName="Franklin European Income Fund - I (Mdis) EUR";
		//String fundName="Franklin Biotechnology Discovery Fund - B (acc) USD";
		//String fundName="Franklin Asia Credit Fund - I (acc) USD";
//		clickWithNoSnap(locateElement("link", fundName));		
		
		clickRandomFundNameFromPerformanceList(fundName);
			
		return new FundOverviewPage(driver,test,fundName);
	}
	public FundOverviewPage clickAnyFundFromPerformanceList() throws InterruptedException, FileNotFoundException, IOException
	{			
		String fundName=getRandomFundNameFromPerformanceList();
//		clickWithNoSnap(locateElement("link", fundName));		
		clickRandomFundNameFromPerformanceList(fundName);
		return new FundOverviewPage(driver,test,fundName);
	}

	public PriceAndPerformancePage verifyDateAndNAVValuesFormates(HashMap<String,String> data)
	{	
		try {
			mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));
			
			FindAFund(data.get("Fund Name").trim());
			
			if(!sCountryName.equals("Canada")) {
			//Retrieving Dates from Investment Vehicle green section		
			WebElement InvtVehicle=locateElement("xpath", getLocalePropertyValue("objPpssInvestmentVehicleListFromPerformanceFundList"));
			String NAVDate=InvtVehicle.findElements(By.tagName("th")).get(0).getText();
			System.out.println(NAVDate);
			String DescretePerformanceDate=InvtVehicle.findElements(By.tagName("th")).get(1).getText();		
			System.out.println(DescretePerformanceDate);
			
			reportStep("Checking Date Formats of NAV and Descrete Performance under Investment Vehicle section", "INFO");
			if(dateFunctions.VerifyDateFormat(NAVDate.split(getLocalePropertyValue("lblFOAsAtDateTxtppss"))[1].trim(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Date " + NAVDate.split(getLocalePropertyValue("lblFOAsAtDateTxtppss"))[1].trim() + " format '"+getLocalePropertyValue("lblDateFormat")+"' is as expected", "PASS");
			else
				reportStep("Date " + NAVDate.split(getLocalePropertyValue("lblFOAsAtDateTxtppss"))[1].trim() + " format '"+getLocalePropertyValue("lblDateFormat")+"' may not as expected", "FAIL");
			if(dateFunctions.VerifyDateFormat(DescretePerformanceDate.split(getLocalePropertyValue("lblFOAsAtDateTxt"))[1].trim(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Date " + DescretePerformanceDate.split(getLocalePropertyValue("lblFOAsAtDateTxt"))[1].trim() + " format '"+getLocalePropertyValue("lblDateFormat")+"' is as expected", "PASS");
			else
				reportStep("Date " + DescretePerformanceDate.split(getLocalePropertyValue("lblFOAsAtDateTxt"))[1].trim() + " format '"+getLocalePropertyValue("lblDateFormat")+"' may not as expected", "FAIL");
			}
			//List<WebElement> funds=getFundList(locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows")));
			List<WebElement> fundList=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
			System.out.println(fundList.get(0).getText());
			String NAV,Nav1,Nav2;
			if(fundList.size()>0) {
				System.out.println("!: " + fundList.get(0).findElements(By.tagName("td")).get(1).getText());
				
				reportStep("Validating Currency","INFO");
				verifyExactText(fundList.get(0).findElements(By.tagName("td")).get(1), data.get("Currency"));
				WebElement eleNav=fundList.get(0).findElements(By.tagName("td")).get(2).findElement(By.tagName("span"));
				WebElement eleNavChange=fundList.get(0).findElements(By.tagName("td")).get(2).findElement(By.tagName("small"));
				
				//System.out.println(eleNav.getCssValue("color"));
				//System.out.println(eleNavChange.getCssValue("color"));
				
	            reportStep("Validating NAV Change value " + eleNavChange.getText() + " Color. NAV Chaneg value: Loss -RED, Gain - GREEN, NoChange - GRAY","INFO");
	            commonMethods cm;	
				cm = new commonMethods();	
	            
	            String expColor;
	            System.out.println(eleNavChange.getText());
	            if(eleNavChange.getText().contains("0.00"))
	            	expColor="GREY";
	            		
	            else if(eleNavChange.getText().substring(0,1).equals("-"))
	            	expColor="RED";           
	            else
	            	expColor="GREEN";          
	           
	            if(cm.verifyColorCode(eleNavChange,expColor))
	            	reportStep("NAV Change value matches the expected Color: "+ expColor, "PASS");
	            else
	            	reportStep("NAV Change value not matches the expected Color: " + expColor, "FAIL");
	            
	            reportStep("Validating NAV value Format - CurrencySymbol XX.XX","INFO");
	            if(eleNav.getText().trim().matches("[$€£]\\d{1,2}.\\d{2}"))
	            	reportStep("NAV value '" + eleNav.getText().trim() + "'matches the expected Format", "PASS");
	            else
	            	reportStep("NAV value '" + eleNav.getText().trim() + "'not matches the expected Format", "FAIL");
	            
	            reportStep("Validating NAV Change value Format - (-)CurrencySymbol XX.XX","INFO");
	            if(eleNavChange.getText().trim().matches("-?[$€£]\\d{1,2}.\\d{2}"))
	            	reportStep("NAV Change value '" + eleNavChange.getText().trim() + "' matches the expected Format", "PASS");
	            else
	            	reportStep("NAV Change value '" + eleNavChange.getText().trim() + "' not matches the expected Format", "FAIL");       
		
			}
						
			else
				reportStep("Expected Fund may not filtered", "FAIL");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}
	
// ###############################   Smoke Scenario   ###############################
	public FundOverviewPage clickFirstFund() throws InterruptedException, FileNotFoundException, IOException
	{
		String fundName="";
	
			List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
			//System.out.println(tableRows.get(0).getText());	
		
			String data[]=tableRows.get(0).getText().split("\\n");
			fundName=data[0];
			//System.out.println(fundName);
			WebElement fLink=tableRows.get(0).findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
			click(fLink,true);
	
			
			WebDriverWait wait=new WebDriverWait(driver, 200);
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFOFindAFundButton"))));

		return new FundOverviewPage(driver,test,fundName.trim());
	}
	
	

//	public FundOverviewPage validatePPSSPageSmokeflow() throws FileNotFoundException, InterruptedException, IOException {
//
//	captureFullScreen("Capturing Full Page Screenshot of PPSS Page");
//	verifyPPSSPageFiltersAndItsDefaultValues();
//	
//	click(locateElement("xpath", localeProp.getProperty("objPPSSPageInceptionDateColumns")));
//	Thread.sleep(3000);
//	List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
//	WebElement fLink=tableRows.get(0).findElements(By.tagName("td")).get(8);
//	
//	String inceptionDate="";
//	if(fLink.getAttribute("textcontent.one-time").contains("InceptionDate"))
//		inceptionDate=fLink.getText();
//	else {
//		reportStep("The column select is not inception date","FAIL");
//	}
//	moveContextToElement(fLink);
//	int inceptionyear = Integer.parseInt(inceptionDate.split(getLocalePropertyValue("lblDateSeperator"))[2]);
//	int curryear = Calendar.getInstance().get(Calendar.YEAR);
//	
////	if((curryear-inceptionyear)>10)
////		reportStep("After sorting the top fund displayed has inception year more than 10 years old","PASS",true);
////	else
////		reportStep("After sorting the top fund displayed has inception year less than 10 years old","FAIL",true);
//	
//	verifyMainTabs();
//	
//	reportStep("Validating Discrete Annual Tab - (NAV Column & Date Range Columns) should have value(Not Emdash) populated","INFO");
//	selectPerformanceAsDiscreteAnnual();
//	Thread.sleep(2000);
//	boolean vChk=true;
//	String vVal="";
//	tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
//	 fLink=tableRows.get(0).findElements(By.tagName("td")).get(8);
//	for (int i=2;i<tableRows.get(0).findElements(By.tagName("td")).size()-1;i++) {
//		if(tableRows.get(0).findElements(By.tagName("td")).get(i).getText().matches("—")) {
//			vChk=false;
//			vVal=tableRows.get(0).findElements(By.tagName("td")).get(i).getText();
//			break;
//		}
//			
//	}
//	
//	if(!vChk)
//		reportStep("Following value is populated with Emdash :"+vVal,"FAIL",true);
//	else
//		reportStep("All the values are displayed as expected for first fund in PPSS table for Discrete Annual Tab","PASS",true);
//	
//	
//	reportStep("Validating Discrete Annual Tab - MorningStar Rating is populated","INFO");
//	tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
//	 WebElement morningStarRating=tableRows.get(0).findElements(By.tagName("td")).get(9);
//	if(morningStarRating.findElement(By.tagName("span")).getText().matches("\\d Stars"))
//		reportStep("Morning Star rating is populated as expected","PASS");
//	else
//		reportStep("Morning Star rating is not populated","FAIL");
//	
//	reportStep("Validating Cumulative Tab - (NAV Column & Date Range Columns) should have value(Not Emdash) populated","INFO");
//	selectPerformanceAsCumulative();
//	Thread.sleep(2000);
//	 vChk=true;
//	 vVal="";
//	 tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
//	 fLink=tableRows.get(0).findElements(By.tagName("td")).get(8);
//	for (int i=2;i<tableRows.get(0).findElements(By.tagName("td")).size()-1;i++) {
//		if(tableRows.get(0).findElements(By.tagName("td")).get(i).getText().matches("—")) {
//			vChk=false;
//			vVal=tableRows.get(0).findElements(By.tagName("td")).get(i).getText();
//			break;
//		}
//			
//	}
//	
//	if(!vChk)
//		reportStep("Following value is populated with Emdash :"+vVal,"FAIL",true);
//	else
//		reportStep("All the values are displayed as expected for first fund in PPSS table for Cumulative Tab","PASS",true);
//	
//	reportStep("Validating Cumulative Tab - MorningStar Rating is populated","INFO");
//	tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
//	  morningStarRating=tableRows.get(0).findElements(By.tagName("td")).get(9);
//	if(morningStarRating.findElement(By.tagName("span")).getText().matches("\\d Stars"))
//		reportStep("Morning Star rating is populated as expected","PASS");
//	else
//		reportStep("Morning Star rating is not populated","FAIL");
//	
//	
//	
//	clickFundIdentifierTab();
//	
//	reportStep("Validating Fund Identifier data value Formats","INFO");
//	String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
//	String data[]=fundIdentifiersData.split("\\n");
//	//String fundName=data[0];		
//	String otherData[]=data[1].split(" ",4);		
//	
//	String fundID=otherData[0].trim();		
//	String ISIN=otherData[1].trim();		
//	String SEDOL=otherData[2].trim();		
//	String Bloomberg=otherData[3].trim();		
//	
//	reportStep("Checking Fund ID Type (ALPHA-NUMERIC) and Length (XXXX)", "INFO");
//	verifyStringFormat(fundID,4,"NUMERIC");		
//	
//	reportStep("Checking ISIN Type (ALPHA-NUMERIC) and Length (XXXXXXXXXXXX)", "INFO");
//	verifyStringFormat(ISIN,12,"ALPHA-NUMERIC");
//	
//	reportStep("Checking SEDOL Type (ALPHA-NUMERIC) and Length (XXXXXXX)", "INFO");
//	verifyStringFormat(SEDOL,7,"ALPHA-NUMERIC");
//	
//	reportStep("Checking Bloomberg Type (ALPHABETIC) and Length (XXXXXXX XX)", "INFO");
//	String[] temp=Bloomberg.split(" ");
//	verifyStringFormat(temp[0],7,"ALPHABETIC");
//	verifyStringFormat(temp[1],2,"ALPHABETIC");
//	
//	click(locateElement("xpath", getLocalePropertyValue("objPPSSPagePerformanceTab")));
//	
//	if(locateElements("xpath",getLocalePropertyValue("objPPSSPageImpLegalInfo")).size()>0) {
//		moveContextToElement(locateElement("xpath",getLocalePropertyValue("objPPSSPageImpLegalInfo")));
//		reportStep("Important Legal Information Header Exist at bottom of Page", "PASS",true);
//	}else {
//		moveContextToElement(locateElement("xpath",getLocalePropertyValue("objPPSSPageCaveat")));
//		verifyElementExist("xpath", getLocalePropertyValue("objPPSSPageCaveat"),"Caveat Section At Bottom",true);
//	}
//	
//	FindAFund(fundID);
//
//	waitUntilAllFundsLoaded();
//	
//	//verifyElementExist("xpath", getLocalePropertyValue("objPPSSPageImpLegalInfo"),"Important Legal Information");
//	String fundName="";
//	
//	
//	fundName=data[0];
//	
//	tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
//	 fLink=tableRows.get(0).findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
//	 click(fLink,true);
//	
//	WebDriverWait wait=new WebDriverWait(driver, 200);
//	wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFOFindAFundButton"))));
//
//
//return new FundOverviewPage(driver,test,fundName.trim());
//	
//
//	}
	
public FundOverviewPage validatePPSSPageSmokeflow() throws FileNotFoundException, InterruptedException, IOException {
		
		WebElement fLink=null;	
		//captureFullScreen("Capturing Full Page Screenshot of PPSS Page");		
		
		click(locateElement("xpath", localeProp.getProperty("objPPSSPageInceptionDateColumns")));
		waitForElementToBeClickable(locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows")).get(0), 5000);
		List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		if (tableRows.size()>0)
			reportStep("Funds Loaded Successfully", "PASS",true);
		else
			reportStep("Funds may not Loaded Successfully", "FAIL",true);
		
		verifyPPSSPageFiltersAndItsDefaultValues();
		
		fLink=tableRows.get(0).findElements(By.tagName("td")).get(8);
			
		moveContextToElement(fLink);		
		verifyMainTabs();
		
		reportStep("Validating Discrete Annual Tab - (NAV Column & Date Range Columns) should have value(Not Emdash) populated","INFO");
		selectPerformanceAsDiscreteAnnual();
		Thread.sleep(2000);
		boolean vChk=true;
		String vVal="";
		tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		 fLink=tableRows.get(0).findElements(By.tagName("td")).get(8);
		for (int i=2;i<tableRows.get(0).findElements(By.tagName("td")).size()-1;i++) {
			if(tableRows.get(0).findElements(By.tagName("td")).get(i).getText().matches("—")) {
				vChk=false;
				vVal=tableRows.get(0).findElements(By.tagName("td")).get(i).getText();
				break;
			}
				
		}
		
		if(!vChk)
			reportStep("Following value is populated with Emdash :"+vVal,"FAIL",true);
		else
			reportStep("All the values are displayed as expected for first fund in PPSS table for Discrete Annual Tab","PASS",true);
		
		
		reportStep("Validating Discrete Annual Tab - MorningStar Rating is populated","INFO");
		tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		 WebElement morningStarRating=tableRows.get(0).findElements(By.tagName("td")).get(9);
		if(morningStarRating.findElement(By.tagName("span")).getText().matches("\\d Stars"))
			reportStep("Morning Star rating is populated as expected","PASS");
		else
			reportStep("Morning Star rating is not populated","FAIL");
		
		reportStep("Validating Cumulative Tab - (NAV Column & Date Range Columns) should have value(Not Emdash) populated","INFO");
		selectPerformanceAsCumulative();
		Thread.sleep(2000);
		 vChk=true;
		 vVal="";
		 tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		 fLink=tableRows.get(0).findElements(By.tagName("td")).get(8);
		for (int i=2;i<tableRows.get(0).findElements(By.tagName("td")).size()-1;i++) {
			if(tableRows.get(0).findElements(By.tagName("td")).get(i).getText().matches("—")) {
				vChk=false;
				vVal=tableRows.get(0).findElements(By.tagName("td")).get(i).getText();
				break;
			}
				
		}
		
		if(!vChk)
			reportStep("Following value is populated with Emdash :"+vVal,"FAIL",true);
		else
			reportStep("All the values are displayed as expected for first fund in PPSS table for Cumulative Tab","PASS",true);
		
		reportStep("Validating Cumulative Tab - MorningStar Rating is populated","INFO");
		tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		  morningStarRating=tableRows.get(0).findElements(By.tagName("td")).get(9);
		if(morningStarRating.findElement(By.tagName("span")).getText().matches("\\d Stars"))
			reportStep("Morning Star rating is populated as expected","PASS");
		else
			reportStep("Morning Star rating is not populated","FAIL");
		
		
		
		clickFundIdentifierTab();
		
		reportStep("Validating Fund Identifier data value Formats","INFO");
		String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		String data[]=fundIdentifiersData.split("\\n");
		//String fundName=data[0];		
		String otherData[]=data[1].split(" ",4);		
		
		String fundID=otherData[0].trim();		
		String ISIN=otherData[1].trim();		
		String SEDOL=otherData[2].trim();		
		String Bloomberg=otherData[3].trim();		
		
		reportStep("Checking Fund ID Type (ALPHA-NUMERIC) and Length (XXXX)", "INFO");
		verifyStringFormat(fundID,4,"NUMERIC");		
		
		reportStep("Checking ISIN Type (ALPHA-NUMERIC) and Length (XXXXXXXXXXXX)", "INFO");
		verifyStringFormat(ISIN,12,"ALPHA-NUMERIC");
		
		reportStep("Checking SEDOL Type (ALPHA-NUMERIC) and Length (XXXXXXX)", "INFO");
		verifyStringFormat(SEDOL,7,"ALPHA-NUMERIC");
		
		reportStep("Checking Bloomberg Type (ALPHABETIC) and Length (XXXXXXX XX)", "INFO");
		String[] temp=Bloomberg.split(" ");
		verifyStringFormat(temp[0],7,"ALPHABETIC");
		verifyStringFormat(temp[1],2,"ALPHABETIC");
		
		click(locateElement("xpath", getLocalePropertyValue("objPPSSPagePerformanceTab")));
		
		if(locateElements("xpath",getLocalePropertyValue("objPPSSPageImpLegalInfo")).size()>0) {
			moveContextToElement(locateElement("xpath",getLocalePropertyValue("objPPSSPageImpLegalInfo")));
			reportStep("Important Legal Information Header Exist at bottom of Page", "PASS",true);
		}else {
			moveContextToElement(locateElement("xpath",getLocalePropertyValue("objPPSSPageCaveat")));
			verifyElementExist("xpath", getLocalePropertyValue("objPPSSPageCaveat"),"Caveat Section At Bottom",true);
		}
		
		FindAFund(fundID);
	
		waitUntilAllFundsLoaded();
		
		//verifyElementExist("xpath", getLocalePropertyValue("objPPSSPageImpLegalInfo"),"Important Legal Information");
		String fundName="";
		
		
		fundName=data[0];		
		tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		if(tableRows.size()>0)
			reportStep("Fund '" + fundID + "' Found", "PASS",true);
		else
			reportStep("Fund '" + fundID + "' may not Found", "FAIL",true);
		fLink=tableRows.get(0).findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
		click(fLink,true);
		
		WebDriverWait wait=new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFOFindAFundButton"))));	
	
		return new FundOverviewPage(driver,test,fundName.trim());	

	}
	
	public FundOverviewPage validatePPSSPageSmokeflow_Canada() throws FileNotFoundException, InterruptedException, IOException {
		
		WebElement fLink=null;	
		//captureFullScreen("Capturing Full Page Screenshot of PPSS Page");
		//verifyPPSSPageFiltersAndItsDefaultValues();
		//verifyMainTabs();		
		
		waitForElementToBeClickable(locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows")).get(0), 5000);
		List<WebElement> tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		fLink=tableRows.get(0).findElements(By.tagName("td")).get(7).findElement(By.tagName("small"));
		
		String inceptionDate="";
		if(fLink.getAttribute("textcontent.one-time").contains("InceptionDate")) {
			inceptionDate=fLink.getText();
		}else
			reportStep("Inception date column may not displayed as Expected","FAIL");
	
		moveContextToElement(fLink);
		int inceptionyear = Integer.parseInt(inceptionDate.split(getLocalePropertyValue("lblDateSeperator"))[2]);
		int curryear = Calendar.getInstance().get(Calendar.YEAR);
		int noOfYearToValidtae=curryear-inceptionyear;
/*		switch(curryear-inceptionyear)
		{
			case(0):
				noOfYearToValidtae=0;
				break;
			case (1):
			case (2):	
				noOfYearToValidtae=1;
				break;
			case (3):
			case (4):	
				noOfYearToValidtae=3;
				break;
			case (5):	
				noOfYearToValidtae=5;
				break;
			default:
				noOfYearToValidtae=5;
				break;
		}*/		
		switch(curryear-inceptionyear)
		{
			case(0):
				noOfYearToValidtae=0;
				break;
			case (1):
			case (2):	
				noOfYearToValidtae=1;
				break;
			case (3):
			case (4):	
				noOfYearToValidtae=2;
				break;
			case (5):	
				noOfYearToValidtae=3;
				break;
			default:
				noOfYearToValidtae=4;
				break;
		}
		
		reportStep("Validating Annualized Tab - (NAV Column & Date Range Columns) should have value(Not Emdash) populated","INFO");		
		selectPerformanceAsAnnualized();
		Thread.sleep(2000);
		boolean vChk=true;
		String vVal="",sVales="";
		tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));		
		int startColumn=2;
		for (int i=startColumn;i<startColumn+noOfYearToValidtae+1;i++) {
			sVales=sVales + " " + tableRows.get(0).findElements(By.tagName("td")).get(i).getText();			
			if(sVales.matches("—")) {
				vChk=false;
				vVal=tableRows.get(0).findElements(By.tagName("td")).get(i).getText();
				break;
			}
				
		}
		
		if(!vChk)
			reportStep("Following value is populated with Emdash :"+vVal,"FAIL",true);
		else
			reportStep("All the values " + sVales + " are displayed based on Inception Date '" + inceptionDate  + "' as expected for first fund in PPSS table for Annualized Tab","PASS",true);
		
		
		reportStep("Validating Annualized Tab - MorningStar Rating is populated","INFO");
		Thread.sleep(500);
		//tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		WebElement morningStarRating=tableRows.get(0).findElements(By.tagName("td")).get(8);
		if(morningStarRating.findElement(By.tagName("span")).getText().matches("\\d Stars"))
			reportStep("Morning Star rating is populated as expected","PASS");
		else
			reportStep("Morning Star rating is not populated","FAIL");
		
		reportStep("Validating Calendar Year Tab - (NAV Column & Date Range Columns) should have value(Not Emdash) populated","INFO");
		//selectPerformanceAsCumulative();
		selectPerformanceAsCalendar();
		click(locateElement("xpath", localeProp.getProperty("objPPSSPageInceptionDateColumns")));
		Thread.sleep(2000);
		 vChk=true;
		 vVal="";
		 tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		 //fLink=tableRows.get(0).findElements(By.tagName("td")).get(8);
		for (int i=2;i<tableRows.get(0).findElements(By.tagName("td")).size()-2;i++) {
			if(tableRows.get(0).findElements(By.tagName("td")).get(i).getText().matches("—")) {
				vChk=false;
				vVal=tableRows.get(0).findElements(By.tagName("td")).get(i).getText();
				break;
			}				
		}
		
		if(!vChk)
			reportStep("Following value is populated with Emdash :"+vVal,"FAIL",true);
		else
			reportStep("All the values are displayed as expected for first fund in PPSS table for Calendar Year Tab","PASS",true);
		
		reportStep("Validating Calendar Year Tab - MorningStar Rating is populated","INFO");
		tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPerfromanceFundListRows"));
		morningStarRating=tableRows.get(0).findElements(By.tagName("td")).get(10);
		if(morningStarRating.findElement(By.tagName("span")).getText().matches("\\d Stars"))
			reportStep("Morning Star rating is populated as expected","PASS");
		else
			reportStep("Morning Star rating is not populated","FAIL");
				
		
		clickFundIdentifierTab();		
		
		reportStep("Validating Fund Identifier data value Formats","INFO");
		String fundIdentifiersData=locateElement("xpath", getLocalePropertyValue("objPpssFundListTable")).getText();
		System.out.println(fundIdentifiersData);
		String data[]=fundIdentifiersData.split("\\n");
		String fundName=data[0];		
		String otherData[]=data[1].split(" ",6);		
		
		
		//Need to check with ramya becaues in UK all the values are mandate but in canada not like that.
		String Currency=otherData[0].trim();		
		String FundCode=otherData[1].trim();		
		String FrontEnd=otherData[2].trim();		
		String LowLoad=otherData[3].trim();
		String DSC=otherData[4].trim();
		String ADM=otherData[5].trim();

		reportStep("Checking Currecny (ALPHABETIC) and Length (XXX)", "INFO");
		verifyStringFormat(Currency,3,"ALPHABETIC");		

		if(FundCode.equals("—") && ADM.equals("—")) {			
		
			reportStep("Checking FrontEnd (NUMERIC) and Length (XXXX)", "INFO");			
			verifyMaximumStringLengthAndType(FrontEnd, 4,"NUMERIC");			
			reportStep("Checking LowLoad (NUMERIC) and Length (XXXX)", "INFO");		
			verifyMaximumStringLengthAndType(LowLoad, 4,"NUMERIC");	
			reportStep("Checking DSC (NUMERIC) and Length (XXXX)", "INFO");		
			verifyMaximumStringLengthAndType(DSC, 4,"NUMERIC");
			
		}else if(FrontEnd.equals("—") && LowLoad.equals("—") && DSC.equals("—")) {
			
			reportStep("Checking FundCode (NUMERIC) and Length (XXXX)", "INFO");			
			verifyMaximumStringLengthAndType(FundCode, 4,"NUMERIC");			
			reportStep("Checking ADM (NUMERIC) and Length (XXXX)", "INFO");			
			verifyMaximumStringLengthAndType(ADM, 4,"NUMERIC");

		}else
			reportStep("Fund Codes Tab values may not displayed as expected", "FAIL");
			
		//click(locateElement("xpath", getLocalePropertyValue("objPPSSPagePerformanceTab")));
		
		if(locateElements("xpath",getLocalePropertyValue("objPPSSPageImpLegalInfo")).size()>0) {
			moveContextToElement(locateElement("xpath",getLocalePropertyValue("objPPSSPageImpLegalInfo")));
			reportStep("Important Legal Information Header Exist at bottom of Page", "PASS",true);
		}else {
			moveContextToElement(locateElement("xpath",getLocalePropertyValue("objPPSSPageCaveat")));
			verifyElementExist("xpath", getLocalePropertyValue("objPPSSPageCaveat"),"Caveat Section At Bottom",true);
		}
		
		
		//##############
		FindAFund(fundName);
		waitForElementToBeClickable(locateElement("xpath", getLocalePropertyValue("objppssPageFundCodesTable")), 3000);
				
		tableRows=locateElements("xpath", getLocalePropertyValue("objPpssPageFundCodesListRows"));
		fLink=tableRows.get(0).findElements(By.tagName("td")).get(0).findElement(By.tagName("a"));
		click(fLink,true);
		
		WebDriverWait wait=new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath", localeProp.getProperty("objFOFindAFundButton"))));
		
		verifyPageHeading(fundName.split("-")[0].trim(),"PPSSS");
	
		return new FundOverviewPage(driver,test,fundName.trim());		

	}
}

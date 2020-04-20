package internationalPages.Products;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.omg.PortableServer.RequestProcessingPolicyOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.common.base.CaseFormat;
import com.relevantcodes.extentreports.ExtentTest;

import utils.ExcelDataProvider;
import utils.WebTableFunctions;
import utils.commonMethods;
import utils.dateFunctions;
import utils.xmlDataProvider;
import wdMethods.ProjectMethods;


public class PriceAndDistributionPage extends ProjectMethods{	
	
	public String pageTitle,fund,FundNumber,FundURL,InvestmentVehicle;
	public commonMethods cm=new commonMethods();
		
	public PriceAndDistributionPage(RemoteWebDriver driver, ExtentTest test,String fundName) throws InterruptedException, FileNotFoundException, IOException {
		this.driver = driver;
		this.test = test;
		
		//Loading Locale File
		localeProp.load(new FileInputStream(new File(sLocaleFile)));
		
		fund=fundName;
		Thread.sleep(2000);	
		FundURL=driver.getCurrentUrl();
		pageTitle=localeProp.getProperty("lblPDPriceAndDistributionPageTitle");
		//System.out.println("Page Title: " + pageTitle);
		//System.out.println("Page Heading: " + localeProp.getProperty("objFDPageHeading"));
		reportStep("Checking " + pageTitle + " Page Title","INFO");	
		
//		if(!verifyTitle(pageTitle + " - " + fund)) {
//			reportStep("This is NOT '" + pageTitle + "'  Page. Refer snap", "FAIL");
//		}
		//commented above code, Changed on 3-26-2018 - due to change in page titles from latest code push
		//new code below
		//pageTitle refers to tab name
		if(!(verifyTitle(fund) && verifyTitle(pageTitle))) {
			reportStep("This is NOT '" + pageTitle + "'  Page. Refer snap", "FAIL");
		}
		
		// Initialize the webelements inside the page
		PageFactory.initElements(driver, this);
	}
	
	public PriceAndDistributionPage validateHeadingAndDate()
	{	
		reportStep("Verifying Highest Lowest section Heading","INFO");
		WebElement eleHeading=locateElement("xpath", localeProp.getProperty("objPDHighestLowestNAVHeading"));
		//String heading=eleHeading.findElement(By.tagName("span"))
		verifyExactText(eleHeading.findElement(By.tagName("span")), localeProp.getProperty("lblPDHighestLowestNAVSectionHeading"));	
		
		reportStep("Verifying Highest Lowest section Heading Date Format","INFO");
		WebElement eledate=eleHeading.findElement(By.tagName("small"));
		if(dateFunctions.VerifyDateFormat(cm.getAsAtDate(eledate), getLocalePropertyValue("lblDateFormat")))
			reportStep("Date section " + cm.getAsAtDate(eledate) + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
		else
			reportStep("Date section " + cm.getAsAtDate(eledate) + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		
		return this;
	}	
	
	
	public PriceAndDistributionPage validateHeadingAndDateCanada()
	{	
		reportStep("Verifying 52 Week Range Heading","INFO");
		WebElement eleHeading=locateElement("xpath", localeProp.getProperty("objPDHighestLowestNAVHeading"));
		//String heading=eleHeading.findElement(By.tagName("span"))
		verifyExactText(eleHeading.findElement(By.tagName("span")), localeProp.getProperty("lblPDHighestLowestNAVSectionHeading"));	
		
		reportStep("Verifying Highest Lowest section Heading Date Format","INFO");
		WebElement eledate=eleHeading.findElement(By.tagName("small"));
		if(dateFunctions.VerifyDateFormat(cm.getAsAtDate(eledate), getLocalePropertyValue("lblDateFormat")))
			reportStep("Date section " + cm.getAsAtDate(eledate) + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
		else
			reportStep("Date section " + cm.getAsAtDate(eledate) + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		
		return this;
	}	
	
	public PriceAndDistributionPage validateHighestLowestNAVSectionDetails(String shrClassInceptionDate)
	{	
		
    	Calendar cal = Calendar.getInstance();
    	//cal.add(Calendar.MONTH, -3);
    	System.out.println(cal.get(Calendar.YEAR));
    	int CurrentYear=cal.get(Calendar.YEAR);
    	
		reportStep("Verifying Highest Lowest section Components","INFO");
		mouseOver(locateElement("xpath", localeProp.getProperty("objPDHighestLowestNAVTable")));
		List<WebElement> eleHLNAVTableRows=locateElement("xpath", localeProp.getProperty("objPDHighestLowestNAVTable")).findElements(By.tagName("tr"));

		verifyExactText(eleHLNAVTableRows.get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDHighestLowestNAVYear"));
		verifyExactText(eleHLNAVTableRows.get(1).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDHighestNAV"));
		verifyExactText(eleHLNAVTableRows.get(2).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDLowestNAV"));
		
		reportStep("Verifying Year component starts with current Year","INFO");
		if(Integer.parseInt(eleHLNAVTableRows.get(0).findElements(By.tagName("th")).get(1).getText())==CurrentYear)
				reportStep("Year Starts wiht expected Current Year - " + cal.get(Calendar.YEAR), "PASS");
		else
			reportStep("Year may not Starts wiht expected Current Year - " + cal.get(Calendar.YEAR) + ". Actual Year - "+ eleHLNAVTableRows.get(0).findElements(By.tagName("th")).get(1).getText(), "FAIL");
		
		reportStep("Verifying No. of Years displyaed","INFO");
		String inceptionYear=shrClassInceptionDate.split(getLocalePropertyValue("lblDateSeperator"))[2];		
		int noOfYearsToDisplay=CurrentYear-Integer.parseInt(inceptionYear)+1;		
		if(eleHLNAVTableRows.get(0).findElements(By.tagName("th")).size()-1==noOfYearsToDisplay)
			reportStep("Totally " +noOfYearsToDisplay + " Years displayed from Current year as expected based on ShaerClass Inception Date: " + shrClassInceptionDate, "PASS");
		else
			reportStep("Number of Years should display based on ShareClass Inception Date of the selected Fund. Expected number of years '" +noOfYearsToDisplay + "' may not displayed", "FAIL");
		
		List<WebElement> HNAVdata=eleHLNAVTableRows.get(1).findElements(By.tagName("td"));
		List<WebElement> LNAVdata=eleHLNAVTableRows.get(2).findElements(By.tagName("td"));
		
		boolean isDateNotValid=true;
		boolean isDateNotValid1=true;
		for (int i = 0; i < HNAVdata.size(); i++) {
			if(!dateFunctions.VerifyDateFormat(cm.getAsAtDateAlt(HNAVdata.get(i).findElement(By.tagName("small"))), getLocalePropertyValue("lblDateFormat")))
				{isDateNotValid=false;
				break;}	
			if(!dateFunctions.VerifyDateFormat(cm.getAsAtDateAlt(LNAVdata.get(i).findElement(By.tagName("small"))),  getLocalePropertyValue("lblDateFormat")))
				{isDateNotValid1=false;
				break;}	
		}
		
		
		reportStep("Verifying Highest Lowest NAV Date Format","INFO");		
		if(isDateNotValid && isDateNotValid1)
			reportStep("Highest & Lowest NAV Dates are displayed as expected format '"+ getLocalePropertyValue("lblDateFormat")+"'", "PASS");
		else
			reportStep("One or More Highest/Lowest NAV Dates may not displayed as expected format '"+ getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		
		return this;
	}
	
	public PriceAndDistributionPage validateHighestLowestNAVSectionDetailsCanada(String shrClassInceptionDate)
	{	
		
		reportStep("Verifying Highest Lowest section Components","INFO");
		mouseOver(locateElement("xpath", localeProp.getProperty("objPDHighestLowestNAVTable")));
		List<WebElement> eleHLNAVTableRows=locateElement("xpath", localeProp.getProperty("objPDHighestLowestNAVTable")).findElements(By.tagName("tr"));

		verifyExactText(eleHLNAVTableRows.get(0).findElements(By.tagName("span")).get(0), getLocalePropertyValue("lblPDHighestNAV"));
		verifyExactText(eleHLNAVTableRows.get(1).findElements(By.tagName("span")).get(0), getLocalePropertyValue("lblPDLowestNAV"));

		reportStep("Verifying Highest Lowest section Heading Date Format","INFO");
		
		WebElement eledate=eleHLNAVTableRows.get(0).findElement(By.tagName("th"));
		if(dateFunctions.VerifyDateFormat(cm.getAsAtDateAlt(eledate), getLocalePropertyValue("lblDateFormat")))
			reportStep("Date section " + cm.getAsAtDateAlt(eledate) + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
		else
			reportStep("Date section " + cm.getAsAtDateAlt(eledate) + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		
		 eledate=eleHLNAVTableRows.get(1).findElement(By.tagName("th"));
		if(dateFunctions.VerifyDateFormat(cm.getAsAtDateAlt(eledate), getLocalePropertyValue("lblDateFormat")))
			reportStep("Date section " + cm.getAsAtDateAlt(eledate) + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
		else
			reportStep("Date section " + cm.getAsAtDateAlt(eledate) + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		
		List<WebElement> HNAVdata=eleHLNAVTableRows.get(0).findElements(By.tagName("td"));
		List<WebElement> LNAVdata=eleHLNAVTableRows.get(1).findElements(By.tagName("td"));
		
		validateFormat(HNAVdata.get(0),"[$€]\\d{2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}",getLocalePropertyValue("lblPDHighestNAV").toUpperCase());
		validateFormat(LNAVdata.get(0),"[$€]\\d{2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}",getLocalePropertyValue("lblPDLowestNAV").toUpperCase());

		return this;
	}
	public PriceAndDistributionPage validateDailyFundPricesHeadingAndDate()
	{	
		reportStep("Verifying Daily Fund Prices section Heading","INFO");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPDSharePricesTable")));
		verifyElementExist("xpath", localeProp.getProperty("objPDDailyFundPricesHeading"),"Daily Fund Price Title");

		reportStep("Verifying Share Prices section Heading","INFO");		
		WebElement eleHeading=locateElement("xpath", localeProp.getProperty("objPDSharePricesHeading"));		
		verifyExactText(eleHeading.findElement(By.tagName("span")), localeProp.getProperty("lblPDSharePricesHeading"));	
		
		reportStep("Verifying Share Prices section Heading Date Format","INFO");
		WebElement eledate=eleHeading.findElement(By.tagName("small"));
		if(dateFunctions.VerifyDateFormat(cm.getAsAtDate(eledate), getLocalePropertyValue("lblDateFormat")))
			reportStep("Date section " + cm.getAsAtDate(eledate) + " displayed the date with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "PASS");
		else
			reportStep("Date section " + cm.getAsAtDate(eledate) + " may not displayed the date with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "FAIL");
		
		//verifyDateRefreshTime(eledate,"Share Prices Secion");
		
		return this;
	}
	public PriceAndDistributionPage validateSharePricesSectionDetails()
	{	
		
  /*  	Calendar cal = Calendar.getInstance();
    	//cal.add(Calendar.MONTH, -3);
    	System.out.println(cal.get(Calendar.YEAR));
    	int CurrentYear=cal.get(Calendar.YEAR);*/
    	
		reportStep("Verifying Share Prices section Components","INFO");
		List<WebElement> eleSharePricesTableRows=locateElement("xpath", localeProp.getProperty("objPDSharePricesTable")).findElements(By.tagName("tr"));

		verifyPartialText(eleSharePricesTableRows.get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDSharePricesNAV"));
		
		//verifyTwoStringsExactly(eleSharePricesTableRows.get(0).findElements(By.tagName("th")).get(0).getText().replace("?", ""), getLocalePropertyValue("lblPDSharePricesNAV"));
//		verifyExactText(eleSharePricesTableRows.get(1).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDSharePricesNAVChange"));
		verifyPartialText(eleSharePricesTableRows.get(1).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDSharePricesNAVChange"));
		WebElement eleNAV=eleSharePricesTableRows.get(0).findElements(By.tagName("td")).get(0);
		WebElement eleNAVChange=eleSharePricesTableRows.get(1).findElements(By.tagName("td")).get(0).findElement(By.tagName("b"));
		
		 //Modified format ($€£)X.XX to ($€£)XXX.XX as per ramya comment on 28-Feb-2018
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");		
		reportStep("Validating NAV value Format - CurrencySymbol($€£)XXX" + DecimalSeptr + "XX","INFO");
        if(eleNAV.getText().trim().matches("[$€£]\\d{1,3}" + DecimalSeptr + "\\d{2}"))
        	reportStep("NAV value '" + eleNAV.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("NAV value '" + eleNAV.getText().trim() + "'not matches the expected Format", "FAIL");
        
        //Modified format ($€£)X.XX to ($€£)XXX.XX as per ramya comment on 28-Feb-2018
        reportStep("Validating NAV Change value Format - (-)CurrencySymbol($€£)XXX" + DecimalSeptr + "XX","INFO");
        if(eleNAVChange.getText().trim().matches("-?[$€£]\\d{1,3}" + DecimalSeptr + "\\d{2}"))
        	reportStep("NAV Change value '" + eleNAVChange.getText().trim() + "' matches the expected Format", "PASS");
        else
        	reportStep("NAV Change value '" + eleNAVChange.getText().trim() + "' not matches the expected Format", "FAIL");        
        
        verifyNAVChangeValueColor(eleNAVChange);  

		return this;
	}
	
	
	public PriceAndDistributionPage validateDividendPerShareSectionHeadings()
	{	
		reportStep("Verifying DividendPerShare section Headings","INFO");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPDDividendsTable")));
		verifyElementExist("xpath", getLocalePropertyValue("objPDDividendHeading"), "Dividend Heading");
		verifyElementExist("xpath", getLocalePropertyValue("objPDDivedendsPerShareHeading"), "Dividend Per Share Heading");		
		
		reportStep("Verifying DividendPerShare section Components","INFO");
		List<WebElement> eleDividendTableRows=locateElement("xpath", localeProp.getProperty("objPDDividendsTable")).findElements(By.tagName("tr"));				
		
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDShareClass"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(1), getLocalePropertyValue("lblPDCurrency"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(2), getLocalePropertyValue("lblPDRecordDate"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(3), getLocalePropertyValue("lblPDExDividendDate"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(4), getLocalePropertyValue("lblPDDividendDate"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(5), getLocalePropertyValue("lblPDDividendPerShare"));
		return this;
	}
	
	public PriceAndDistributionPage validateDividendPerShareSectionHeadingsCanada()
	{	
		reportStep("Verifying Distributions section Headings","INFO");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPDDividendsTable")));
		verifyElementExist("xpath", getLocalePropertyValue("objPDDividendHeading"), "Distributions Heading");
		verifyElementExist("xpath", getLocalePropertyValue("objPDDivedendsPerShareHeading"), "Distributions Per Share Heading");		
		
		reportStep("Verifying DividendPerShare section Components","INFO");
		List<WebElement> eleDividendTableRows=locateElement("xpath", localeProp.getProperty("objPDDividendsTable")).findElements(By.tagName("tr"));				
		
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDShareClass"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(1), getLocalePropertyValue("lblPDCurrency"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(2), getLocalePropertyValue("lblPDRecordDate"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(3), getLocalePropertyValue("lblPDDistributionDate"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(4), getLocalePropertyValue("lblPDDistributionPrice"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(5), getLocalePropertyValue("lblPDInvestedIncome"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(6), getLocalePropertyValue("lblPDROC"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(7), getLocalePropertyValue("lblPDCapitalGains"));
		verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(8), getLocalePropertyValue("lblPDTDPU"));
		return this;
	}
	
	public PriceAndDistributionPage validateDividendPerShareSectionDetails(HashMap<String,String> data)
	{			
		
		List<WebElement> eleDividendTableRows=locateElement("xpath", localeProp.getProperty("objPDDividendsTable")).findElements(By.tagName("tr"));				
		
		WebElement eleShareClass=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(0);
		WebElement eleCurrency=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(1);  
		WebElement eleRecordDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(2);
		WebElement eleExDivDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(3);
		WebElement eleDivDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(4);
		WebElement eleDivPerShr=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(5);
		
		reportStep("Verifying Share Class displayed according to the fund","INFO");
		getShareClassFronFundName(fund);
		if(eleShareClass.getText().equals(getShareClassFronFundName(fund)))
			reportStep("Expected Share Class " + getShareClassFronFundName(fund) + " Displayed", "PASS");
		else
			reportStep("Expected Share Class " + getShareClassFronFundName(fund) + "may not Displayed", "FAIL");
		
		reportStep("Verifying Currency Value","INFO");
		verifyExactText(eleCurrency, data.get("Currency"), "Currency value");
		
		reportStep("Verifying Record Date, Ex-Dividend Date & Dividend Date Fromats - " + getLocalePropertyValue("lblDateFormat"),"INFO");
		if(dateFunctions.VerifyDateFormat(eleRecordDate.getText(), getLocalePropertyValue("lblDateFormat")) && dateFunctions.VerifyDateFormat(eleExDivDate.getText(), getLocalePropertyValue("lblDateFormat")) && dateFunctions.VerifyDateFormat(eleDivDate.getText(), getLocalePropertyValue("lblDateFormat")))
			reportStep("Dates '" + eleRecordDate.getText() + "','" + eleExDivDate.getText() + "','" + eleDivDate.getText() + "' displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "PASS");
		else
			reportStep("One or More Date '" + eleRecordDate.getText() + "','" + eleExDivDate.getText() + "','" + eleDivDate.getText() + "' may not displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "FAIL");
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");		
		reportStep("Validating Dividend Per Share value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleDivPerShr.getText().trim().matches("[$€£]\\d{1}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Dividend Per Share value '" + eleDivPerShr.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Dividend Per Share value '" + eleDivPerShr.getText().trim() + "'not matches the expected Format", "FAIL");
		
		return this;
	}
	
	public PriceAndDistributionPage validateDividendPerShareSectionDetailsCanada(HashMap<String,String> data)
	{			
		
		List<WebElement> eleDividendTableRows=locateElement("xpath", localeProp.getProperty("objPDDividendsTable")).findElements(By.tagName("tr"));				
		
		WebElement eleShareClass=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(0);
		WebElement eleCurrency=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(1);  
		WebElement eleRecordDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(2);
		WebElement eleDistDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(3);
		WebElement eleDistPrice=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(4);
		WebElement eleInvIncm=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(5);
		WebElement eleROC=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(6);
		WebElement eleCapGains=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(7);
		WebElement eleTDPU=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(8);
		
		reportStep("Verifying series displayed according to the fund","INFO");
		getShareClassFronFundName(fund);
		if(eleShareClass.getText().equals(getShareClassFronFundName(fund)))
			reportStep("Expected series " + getShareClassFronFundName(fund) + " Displayed", "PASS");
		else
			reportStep("Expected series " + getShareClassFronFundName(fund) + "may not Displayed", "FAIL");
		
		reportStep("Verifying Currency Value","INFO");
		verifyExactText(eleCurrency, data.get("Currency"), "Currency value");
		
		reportStep("Verifying Record Date, Distribution Date Fromats - " + getLocalePropertyValue("lblDateFormat"),"INFO");
		if(dateFunctions.VerifyDateFormat(eleRecordDate.getText(), getLocalePropertyValue("lblDateFormat")) && dateFunctions.VerifyDateFormat(eleDistDate.getText(), getLocalePropertyValue("lblDateFormat")))
			reportStep("Dates '" + eleRecordDate.getText() + "','" + eleDistDate.getText() + "' displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "PASS");
		else
			reportStep("One or More Date '" + eleRecordDate.getText() + "','" + eleDistDate.getText()  + "' may not displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "FAIL");
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");		
		reportStep("Validating Distribution price value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleDistPrice.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Distribution price value '" + eleDistPrice.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Distribution price value '" + eleDistPrice.getText().trim() + "'not matches the expected Format", "FAIL");
        
        reportStep("Validating Invested Income value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleInvIncm.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Invested Income value '" + eleInvIncm.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Invested Income value '" + eleInvIncm.getText().trim() + "'not matches the expected Format", "FAIL");
		
        reportStep("Validating Return of Capital value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleROC.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}|—"))
        	reportStep("Return of Capital value '" + eleROC.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Return of Capital value '" + eleROC.getText().trim() + "'not matches the expected Format", "FAIL");
        
        reportStep("Validating Capital Gains value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleCapGains.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Capital Gains  value '" + eleCapGains.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Capital Gains value '" + eleCapGains.getText().trim() + "'not matches the expected Format", "FAIL");
        
        reportStep("Validating Total Distributions Per Unit value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleTDPU.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Total Distributions Per Unit value '" + eleTDPU.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Total Distributions Per Unit value '" + eleTDPU.getText().trim() + "'not matches the expected Format", "FAIL");
		
		return this;
	}
	
	public PriceAndDistributionPage verifyViewHistoricalDividendLinkExpand()
	{	
				
		verifyElementExist("xpath", getLocalePropertyValue("objPDViewHistoricalDividendsDataHeading"), getLocalePropertyValue("lblPDHistoricalDividendDataLinkName"));
		reportStep("Verifying " +  getLocalePropertyValue("lblPDHistoricalDividendDataLinkName") + " Link Expand","INFO");
		click(locateElement("xpath", getLocalePropertyValue("objPDViewHistoricalDividendsDataHeading")));
		
		if(verifyElementExist("xpath", getLocalePropertyValue("objPDHistoricalDividendTable"))) {
			reportStep(getLocalePropertyValue("lblPDHistoricalDividendDataLinkName") + " Expanded successfully", "PASS",true);
		}else
			reportStep(getLocalePropertyValue("lblPDHistoricalDividendDataLinkName") + " may not Expanded successfully (This happens due to dividend distribution job not run in the environment)", "FAIL",true);
		
		return this;
	}
	public PriceAndDistributionPage verifyViewHistoricalDividendDataComponents()
	{	
		
			mouseOver(locateElement("xpath", getLocalePropertyValue("objPDHistoricalDividendTable")));			

			verifyElementExist("xpath", getLocalePropertyValue("objPDHistoricalDataDistributionRateShareHeading"), getLocalePropertyValue("lblPDHistoricalDataDistributionRateShare") + " Heading");
			verifyElementExist("xpath", getLocalePropertyValue("objPDSelectYearDropDown"), "Select Year");
			verifyPartialText(locateElement("xpath", getLocalePropertyValue("objPDDownloadHistoricalDividendText")), getLocalePropertyValue("lblPDDownloadHistoricalDividendtText"));
		
			List<WebElement> dates=locateElements("xpath", getLocalePropertyValue("objPDDownloadHistoricalDividendDates"));
			
			reportStep("Verifying " + getLocalePropertyValue("lblPDDownloadHistoricalDividendtText") + " Date Fromats - " + getLocalePropertyValue("lblDateFormat"),"INFO");
			if(dateFunctions.VerifyDateFormat(dates.get(0).getText(), getLocalePropertyValue("lblDateFormat")) && dateFunctions.VerifyDateFormat(dates.get(1).getText(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Dates '" + dates.get(0).getText() + "','" + dates.get(1).getText() + "' displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "PASS");
			else
				reportStep("One or More Date '" + dates.get(0).getText() + "','" + dates.get(1).getText() + "' may not displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "FAIL");
			
			verifyElementExist("xpath", getLocalePropertyValue("objPDDownloadHistoricalDividendXls"), "Download Historical Dividend (XLS)");
			
			reportStep("Verifying " +  getLocalePropertyValue("lblPDHistoricalDividendDataLinkName") + " Components","INFO");
			List<WebElement> eleDividendTableRows=locateElement("xpath", localeProp.getProperty("objPDHistoricalDividendTable")).findElements(By.tagName("tr"));				
			
			//verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDShareClass"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDCurrency"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(1), getLocalePropertyValue("lblPDRecordDate"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(2), getLocalePropertyValue("lblPDExDividendDate"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(3), getLocalePropertyValue("lblPDDividendDate"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(4), getLocalePropertyValue("lblPDDividendPerShare"));
			
			
			return this;
	}
	
	
	public PriceAndDistributionPage verifyViewHistoricalDividendDataComponentsCanada()
	{	
		
			mouseOver(locateElement("xpath", getLocalePropertyValue("objPDHistoricalDividendTable")));			

			verifyElementExist("xpath", getLocalePropertyValue("objPDHistoricalDataDistributionRateShareHeading"), getLocalePropertyValue("lblPDHistoricalDataDistributionRateShare") + " Heading");
			verifyElementExist("xpath", getLocalePropertyValue("objPDSelectYearDropDown"), "Select Year");
			verifyPartialText(locateElement("xpath", getLocalePropertyValue("objPDDownloadHistoricalDividendText")), getLocalePropertyValue("lblPDDownloadHistoricalDividendtText"));
		
			List<WebElement> dates=locateElements("xpath", getLocalePropertyValue("objPDDownloadHistoricalDividendDates"));
			
			reportStep("Verifying " + getLocalePropertyValue("lblPDDownloadHistoricalDividendtText") + " Date Fromats - " + getLocalePropertyValue("lblDateFormat"),"INFO");
			if(dateFunctions.VerifyDateFormat(dates.get(0).getText(), getLocalePropertyValue("lblDateFormat")) && dateFunctions.VerifyDateFormat(dates.get(1).getText(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Dates '" + dates.get(0).getText() + "','" + dates.get(1).getText() + "' displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "PASS");
			else
				reportStep("One or More Date '" + dates.get(0).getText() + "','" + dates.get(1).getText() + "' may not displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "FAIL");
			
			verifyElementExist("xpath", getLocalePropertyValue("objPDDownloadHistoricalDividendXls"), "Download Historical Dividend (XLS)");
			
			reportStep("Verifying " +  getLocalePropertyValue("lblPDHistoricalDividendDataLinkName") + " Components","INFO");
			List<WebElement> eleDividendTableRows=locateElement("xpath", localeProp.getProperty("objPDHistoricalDividendTable")).findElements(By.tagName("tr"));				
			
			//verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDShareClass"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblPDCurrency"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(1), getLocalePropertyValue("lblPDRecordDate"));
		
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(2), getLocalePropertyValue("lblPDDistributionDate"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(3), getLocalePropertyValue("lblPDDistributionPrice"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(4), getLocalePropertyValue("lblPDInvestedIncome"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(5), getLocalePropertyValue("lblPDROC"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(6), getLocalePropertyValue("lblPDCapitalGains"));
			verifyExactText(eleDividendTableRows.get(0).findElements(By.tagName("th")).get(7), getLocalePropertyValue("lblPDTDPU"));
			return this;
	}
	
	public PriceAndDistributionPage validateViewHistoricalDividendDataSectionDetails(HashMap<String,String> data)
	{			
		
		List<WebElement> eleDividendTableRows=locateElement("xpath", localeProp.getProperty("objPDHistoricalDividendTable")).findElements(By.tagName("tr"));				
		
		//WebElement eleShareClass=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(0);
		WebElement eleCurrency=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(0);  
		WebElement eleRecordDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(1);
		WebElement eleExDivDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(2);
		WebElement eleDivDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(3);
		WebElement eleDivPerShr=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(4);
		
/*		reportStep("Verifying Share Class displayed according to the fund","INFO");
		getShareClassFronFundName(fund);
		if(eleShareClass.getText().equals(getShareClassFronFundName(fund)))
			reportStep("Expected Share Class " + getShareClassFronFundName(fund) + " Displayed", "PASS");
		else
			reportStep("Expected Share Class " + getShareClassFronFundName(fund) + "may not Displayed", "FAIL");*/
		
		reportStep("Verifying Currency Value","INFO");
		verifyExactText(eleCurrency, data.get("Currency"), "Currency value");
		
		reportStep("Verifying Record Date, Ex-Dividend Date & Dividend Date Fromats - " + getLocalePropertyValue("lblDateFormat"),"INFO");
		if(dateFunctions.VerifyDateFormat(eleRecordDate.getText(), getLocalePropertyValue("lblDateFormat")) && dateFunctions.VerifyDateFormat(eleExDivDate.getText(), getLocalePropertyValue("lblDateFormat")) && dateFunctions.VerifyDateFormat(eleDivDate.getText(), getLocalePropertyValue("lblDateFormat")))
			reportStep("Dates '" + eleRecordDate.getText() + "','" + eleExDivDate.getText() + "','" + eleDivDate.getText() + "' displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "PASS");
		else
			reportStep("One or More Date '" + eleRecordDate.getText() + "','" + eleExDivDate.getText() + "','" + eleDivDate.getText() + "' may not displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "FAIL");
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");		
		reportStep("Validating Dividend Per Share value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleDivPerShr.getText().trim().matches("[$€£]\\d{1}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Dividend Per Share value '" + eleDivPerShr.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Dividend Per Share value '" + eleDivPerShr.getText().trim() + "'not matches the expected Format", "FAIL");
		
		return this;
	}
	
	public PriceAndDistributionPage validateViewHistoricalDividendDataSectionDetailsCanada(HashMap<String,String> data)
	{			
		
		List<WebElement> eleDividendTableRows=locateElement("xpath", localeProp.getProperty("objPDHistoricalDividendTable")).findElements(By.tagName("tr"));				
		
		//WebElement eleShareClass=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(0);
		WebElement eleCurrency=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(0);  
		WebElement eleRecordDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(1);
		
		WebElement eleDistDate=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(2);
		WebElement eleDistPrice=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(3);
		WebElement eleInvIncm=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(4);
		WebElement eleROC=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(5);
		WebElement eleCapGains=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(6);
		WebElement eleTDPU=eleDividendTableRows.get(1).findElements(By.tagName("td")).get(7);
	
		
		reportStep("Verifying Currency Value","INFO");
		verifyExactText(eleCurrency, data.get("Currency"), "Currency value");
		
		reportStep("Verifying Record Date, Distribution Date Fromats - " + getLocalePropertyValue("lblDateFormat"),"INFO");
		if(dateFunctions.VerifyDateFormat(eleRecordDate.getText(), getLocalePropertyValue("lblDateFormat")) && dateFunctions.VerifyDateFormat(eleDistDate.getText(), getLocalePropertyValue("lblDateFormat")))
			reportStep("Dates '" + eleRecordDate.getText() + "','" + eleDistDate.getText() + "' displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "PASS");
		else
			reportStep("One or More Date '" + eleRecordDate.getText() + "','" + eleDistDate.getText()  + "' may not displayed with expected format '" + getLocalePropertyValue("lblDateFormat")+ "'", "FAIL");
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");		
		reportStep("Validating Distribution price value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleDistPrice.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Distribution price value '" + eleDistPrice.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Distribution price value '" + eleDistPrice.getText().trim() + "'not matches the expected Format", "FAIL");
        
        reportStep("Validating Invested Income value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleInvIncm.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Invested Income value '" + eleInvIncm.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Invested Income value '" + eleInvIncm.getText().trim() + "'not matches the expected Format", "FAIL");
		
        reportStep("Validating Return of Capital value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleROC.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}|—"))
        	reportStep("Return of Capital value '" + eleROC.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Return of Capital value '" + eleROC.getText().trim() + "'not matches the expected Format", "FAIL");
        
        reportStep("Validating Capital Gains value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleCapGains.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Capital Gains  value '" + eleCapGains.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Capital Gains value '" + eleCapGains.getText().trim() + "'not matches the expected Format", "FAIL");
        
        reportStep("Validating Total Distributions Per Unit value Format - CurrencySymbol($€£)X" + DecimalSeptr + "XXX","INFO");
        if(eleTDPU.getText().trim().matches("[$€£]\\d{1,2}" + DecimalSeptr + "\\d{3}"))
        	reportStep("Total Distributions Per Unit value '" + eleTDPU.getText().trim() + "'matches the expected Format", "PASS");
        else
        	reportStep("Total Distributions Per Unit value '" + eleTDPU.getText().trim() + "'not matches the expected Format", "FAIL");
		
		return this;
	}
	
	public PriceAndDistributionPage verifyViewHistoricalDividendLinkCollapse()
	{	
		reportStep("Verifying " +  getLocalePropertyValue("lblPDHistoricalDividendDataLinkName") + " Link Collapse","INFO");		
		//verifyElementExist("xpath", getLocalePropertyValue("objPDViewHistoricalDividendsDataHeading"), getLocalePropertyValue("lblPDHistoricalDividendDataLinkName"));
		click(locateElement("xpath", getLocalePropertyValue("objPDViewHistoricalDividendsDataHeading")));
		
		if(!verifyElementExist("xpath", getLocalePropertyValue("objPDHistoricalDividendTable"))) {
			reportStep(getLocalePropertyValue("lblPDHistoricalDividendDataLinkName") + " Collapse successfully", "PASS");
		}else
			reportStep(getLocalePropertyValue("lblPDHistoricalDividendDataLinkName") + " may not Collapse successfully", "FAIL");
		
		return this;
	}
	
	public void verifyNAVChangeValueColor(WebElement eleNavChange)
	{
        reportStep("Validating NAV Change value " + eleNavChange.getText() + " Color. NAV Chaneg value: Loss -RED, Gain - GREEN, NoChange - GRAY","INFO");
        commonMethods cm;
		try {
			cm = new commonMethods();

        
        String expColor;
        //System.out.println(eleNavChange.getText());
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
        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void verifyDateRefreshTime(WebElement AsAtDateElement,String dateSectionName)
	{
		reportStep("Validing Refresh time '" + dateSectionName +"'","INFO");
		//||dateElment.contains("(" +getLocalePropertyValue("lblRefreshTimeYearly")+ ")")
		String dateElment=AsAtDateElement.getText();
		String refTimeCheck=getLocalePropertyValue("lblRefreshTimeMonthly").split(" ")[0];
		if(!dateElment.contains("(" + refTimeCheck))
		{
			reportStep("Checking '" + dateSectionName +"' widget Refresh Time Availability","INFO");				
			reportStep("'" + dateSectionName +"' widget Refresh Time may Not available for this fund","FAIL");				
		}
		
		if(dateElment.contains("(" + getLocalePropertyValue("lblRefreshTimeMonthly")+ ")") ||dateElment.contains("(" + getLocalePropertyValue("lblRefreshTimeQuartely")+ ")")||dateElment.contains("(" +getLocalePropertyValue("lblRefreshTimeSemiYearly")+ ")"))
			reportStep("Refresh Time displayed successfully - " + dateElment ,"PASS",false);	
		else
			reportStep("Refresh Time displayed successfully - " + dateElment ,"FAIL",false);
	}
	public PriceAndDistributionPage validatePriceAndDistributionHistoricaNAVSearchSection(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Highest and Lowest NAV Section","INFO");
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objPDHistoricalPriceDateSectionHeader")));
		
		verifyElementExist("xpath", getLocalePropertyValue("objPDHistoricalPriceDateSectionHeader"), getLocalePropertyValue("lblPDHistoricalPriceDateSectionHeader"));
		click(locateElement("xpath",getLocalePropertyValue("objPDHistoricalPriceDateSectionSlider")));
		verifyElementExist("xpath", getLocalePropertyValue("objPDHistoricalPriceDateSectionSubHead1"), getLocalePropertyValue("lblPDHistoricalPriceDateSectionSubHead1"));
		
		WebElement timeToggleList = locateElement("xpath",getLocalePropertyValue("objPDHistoricalPriceDateSectionTimeToggleList"));
		List<WebElement> timeToggleListOptions = WebTableFunctions.getAllTagsInsideAnElement(timeToggleList, "li");
		verifyExactText(timeToggleListOptions.get(0), getLocalePropertyValue("lbl1Mth"));
		verifyExactText(timeToggleListOptions.get(1), getLocalePropertyValue("lbl3MTHS"));
		verifyExactText(timeToggleListOptions.get(2), getLocalePropertyValue("lbl6MTHS"));
		verifyExactText(timeToggleListOptions.get(3), getLocalePropertyValue("lblYTD"));
		verifyExactText(timeToggleListOptions.get(4), getLocalePropertyValue("lbl1YR"));
		verifyExactText(timeToggleListOptions.get(5), getLocalePropertyValue("lbl3YRS"));
		verifyExactText(timeToggleListOptions.get(6), getLocalePropertyValue("lbl5YRS"));
		verifyExactText(timeToggleListOptions.get(7), getLocalePropertyValue("lblPDSinceInceptionColumn"));
		
		verifyElementExist("xpath", getLocalePropertyValue("objPDHistoricalPriceDateSectionChooseHistDatesLink"), getLocalePropertyValue("lblPDHistoricalPriceDateSectionChooseHistDatesLink"));
		
		WebElement latestNAVList = locateElement("xpath",getLocalePropertyValue("objPDHistoricalPriceDateLatestNAVSectionList"));
		List<WebElement> latestNAVListValues = WebTableFunctions.getAllTagsInsideAnElement(latestNAVList, "li");
		reportStep("Latest NAV value 1 :"+latestNAVListValues.get(0).getText(),"INFO");
		reportStep("Latest NAV value 2 :"+latestNAVListValues.get(1).getText(),"INFO");
		reportStep("Latest NAV value 3 :"+latestNAVListValues.get(2).getText(),"INFO");
		reportStep("Latest NAV value 4 :"+latestNAVListValues.get(3).getText(),"INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objPDHistoricalPriceDateDownloadXLSlink"), getLocalePropertyValue("lblPDHistoricalPriceDateDownloadXLSlink"));
		validateDownloadXLS(getLocalePropertyValue("lblPriceHistoryReportXLSName"), getLocalePropertyValue("objPDHistoricalPriceDateDownloadXLSlink"));
		
		return this;
	}
	
	
	
}
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

public class PriceTaxesPage extends ProjectMethods{
	
	public static String tid;
	public PriceTaxesPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblPriceTaxesPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public PriceTaxesPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Price & Taxes Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Price & Taxes", getLocalePropertyValue("lblPriceTaxesBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	
	public PriceTaxesPage verifyPageLayout(HashMap<String,String> testData) throws InterruptedException
	{
		
		reportStep("Validate Layout of Distribution & Taxes Page", "INFO");
		verifyElementExist("xpath", "//h1[text()='Preise mit Steuerdaten']","Page Header PREISE MIT STEUERDATEN");
		
		verifyElementExist("xpath", "//ft-label[text()='Fondsfamilie']/../../select","Fund Family Filter");
		verifyElementExist("xpath", "//ft-label[text()='Fonds']/../../select","Fund Filter");
		
		Thread.sleep(5000);
		if(new Select(locateElement("xpath","//ft-label[text()='Fondsfamilie']/../../select")).getFirstSelectedOption().getText().equals("Alle"))
				reportStep("All is selected as default option for fund family filter", "PASS");	
			else
				reportStep("All is not selected as default option for fund family filter", "PASS");	
		
		if(new Select(locateElement("xpath","//ft-label[text()='Fonds']/../../select")).getFirstSelectedOption().getText().equals("Alle"))
			reportStep("All is selected as default option for fund filter", "PASS");	
		else
			reportStep("All is not selected as default option for fund filter", "PASS");	
		
		
		reportStep("Select value in fund family filter", "INFO"); 
		selectDropDownUsingText(locateElement("xpath","//ft-label[text()='Fondsfamilie']/../../select"),testData.get("AssetClassFilter"));
		
		reportStep("Select value in fund filter", "INFO"); 
		selectDropDownUsingText(locateElement("xpath","//ft-label[text()='Fonds']/../../select"),testData.get("Fund Name"));
		
		reportStep("Validate the price & taxes table columns", "INFO"); 
		List<WebElement> tableHeaders = locateElements("xpath","(//div[@class='table-responsive']//table)[1]//th");
		verifyExactText(tableHeaders.get(0), "NAME DES FONDS", "Name des Fonds Column",false);
		verifyExactText(tableHeaders.get(1), "FONDSNUMMER","Fondsnummer Column",false);
		verifyExactText(tableHeaders.get(2), "WÄHRUNG DER ANTEILSKLASSE", "Währung der Anteilsklasse Column",false);
		verifyTwoStringsExactly(tableHeaders.get(3).getText().replaceAll("\n", ""), "AUSGABE-PREIS");
		verifyExactText(tableHeaders.get(4), "RÜCKNAHMEPREIS", "Rücknahmepreis Column",false);
		
		reportStep("Validate the table subheader shows fund family selected in filter", "INFO"); 
		WebElement tableSubHeader = locateElement("xpath","(//div[@class='table-responsive']//table)[1]//tbody/tr[1]/td[1]");
		verifyExactText(tableSubHeader, testData.get("AssetClassFilter").toUpperCase(), testData.get("AssetClassFilter").toUpperCase());
		
		validateFormat(tableHeaders.get(5), "Stand [0-3]\\d\\.[01]\\d\\.\\d{4}", "Stand Date Format");
		
		reportStep("Validate the values & Formats of fund fields in first row", "INFO"); 
		List<WebElement> tableFields=locateElements("xpath","(//div[@class='table-responsive']//table)[1]//tbody/tr[2]/td");
		
		verifyExactText(tableFields.get(0), testData.get("Fund Name"), "Fund Name");
		validateFormat(tableFields.get(1), "\\d{4}", "Fund Number");
		
		if(tableFields.get(2).getText().matches("USD|AUD|CHF|EUR|CNH"))
			verifyExactText(tableFields.get(2), testData.get("Currency"), "Currency");
		else
			reportStep("The currency is not matching one of the values expected {USD|AUD|CHF|EUR|CNH}","FAIL");
		
		String currSymbol="";
		
		switch(tableFields.get(2).getText()) {
		
		case "USD":currSymbol="$";break;
		case "EUR":currSymbol="€";break;
		case "AUD":currSymbol="$";break;
		case "CHF":currSymbol="CHF";break;
		case "CNH":currSymbol="¥";break;
		default:reportStep("Currency type is not expected","FAIL");break;
		}
		
		validateFormat(tableFields.get(3), "\\"+currSymbol+"\\d{1,2}\\,\\d{2}", "Issue Price");
		
		validateFormat(tableFields.get(4), "\\"+currSymbol+"\\d{1,2}\\,\\d{2}", "Repurchase Price");
		
		reportStep("Validate the static content below fund row", "INFO"); 
		verifyExactText(locateElement("xpath","//p[text()='Hier finden Sie aktuelle Preise mit Steuerdaten. ']"), getLocalePropertyValue("lblPriceTaxPageStaticText"),"static Text :"+getLocalePropertyValue("lblPriceTaxPageStaticText"));
		verifyExactText(locateElement("xpath","//p[contains(text(),'Für den Franklin Templeton Investment')]"), getLocalePropertyValue("lblPriceTaxPageStaticText1"),"static Text :"+getLocalePropertyValue("lblPriceTaxPageStaticText1"));
		
		reportStep("Validate the 1st PDF is opening in EStG KStG table", "INFO"); 
		click(locateElement("xpath","//th[text()='EStG']/../..//a"));
		
		Thread.sleep(3000);
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		   driver.switchTo().window(tabs.get(1));
		
		   verifyTwoStringsPartially(driver.getCurrentUrl(), "EStG",true);
		   
		   driver.close();
		    driver.switchTo().window(tabs.get(0));	
		
		    
		    verifyExactText(locateElement("xpath","//p[contains(text(),'Für den Franklin Templeton Strategic Allocations')]"), getLocalePropertyValue("lblPriceTaxPageStaticText2"),"static Text :"+getLocalePropertyValue("lblPriceTaxPageStaticText2"));
		    verifyExactText(locateElement("xpath","//p[contains(text(),'Für den Templeton Growth Fund, Inc.')]"), getLocalePropertyValue("lblPriceTaxPageStaticText3"),"static Text :"+getLocalePropertyValue("lblPriceTaxPageStaticText3"));
		    
		return this;
			 
			 
			 
	}
	
	
	
	
}

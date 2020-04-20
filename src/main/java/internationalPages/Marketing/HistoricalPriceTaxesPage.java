package internationalPages.Marketing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
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

public class HistoricalPriceTaxesPage extends ProjectMethods{
	
	public static String tid;
	public HistoricalPriceTaxesPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblHistoricalPriceTaxesPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public HistoricalPriceTaxesPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Historical Price & Taxes Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Historical Price & Taxes", getLocalePropertyValue("lblHistPriceTaxesBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	public HistoricalPriceTaxesPage verifyPageLayout() throws InterruptedException
	{
		reportStep("Validate Layout of Reporting Fund Information Page", "INFO");
	
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		verifyElementExist("xpath", getLocalePropertyValue("objReportingFundInfoPageSubhead"),"Historische Fondspreise mit Steuerdaten Header");
		
		reportStep("Validate tabs displayed in Page from year-2015 to current year", "INFO");
		List <WebElement> tabss=locateElements("xpath",getLocalePropertyValue("objReportingFundInfoPageYearTabs"));	
		int year = 2015;
		for(int i=tabss.size()-1;i>=0;i--) {
		verifyExactText(tabss.get(i),String.valueOf(year++), "Year Tab",false);
		}
		
		reportStep(tabss.get(0).getText()+" year is selected by default", "INFO");
		
		List <WebElement> tables=locateElements("xpath","//div[@class='tab-content']//table");	
		
		WebElement currYearTable = tables.get(0);
		
		verifyExactText(currYearTable.findElements(By.tagName("th")).get(0), "MONAT","table Header");
		verifyExactText(currYearTable.findElements(By.tagName("th")).get(1), "RÜCKNAHMEPREISE","table Header");
		    
//		int currMonth =Calendar.getInstance().get(Calendar.MONTH);
		int	currMonth	=LocalDateTime.now().getMonthValue();
		   reportStep("Current Month: "+currMonth, "INFO"); 
		   int j=0;
		   
		reportStep("Validate month labels from current month-1 to 1st month", "INFO");
		for(int i=currMonth-1;i>=1;i--) {
			
			verifyExactText(currYearTable.findElements(By.tagName("td")).get(j), getMonthNameInGermanFromMonthNumber(String.valueOf(i)));
			j+=2;
		}
		
		reportStep("Click on 1st PDF link", "INFO");
		click(currYearTable.findElements(By.tagName("td")).get(1).findElement(By.tagName("a")));
		
		Thread.sleep(3000);
		 ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		   driver.switchTo().window(tabs.get(1));
		
		   verifyTwoStringsPartially(driver.getCurrentUrl(), "pdf",true);
		   
		   driver.close();
		    driver.switchTo().window(tabs.get(0));	
		
		    reportStep("Click on 2nd Year Tab", "INFO");
		    tabss=locateElements("xpath",getLocalePropertyValue("objReportingFundInfoPageYearTabs"));	
		    click(tabss.get(1),true);
		 
		    tables=locateElements("xpath","//div[@class='tab-content']//table");	
			
			WebElement nextTable = tables.get(1);
			
			verifyExactText(nextTable.findElements(By.tagName("th")).get(0), "MONAT","table Header");
			verifyExactText(nextTable.findElements(By.tagName("th")).get(1), "RÜCKNAHMEPREISE","table Header");
			verifyExactText(nextTable.findElements(By.tagName("th")).get(2), "ZWISCHEN- GEWINNE","table Header");
			verifyExactText(nextTable.findElements(By.tagName("th")).get(3), "AKTIEN- GEWINNE ESTG","table Header");
			verifyExactText(nextTable.findElements(By.tagName("th")).get(4), "AKTIEN- GEWINNE KSTG","table Header");
			verifyExactText(nextTable.findElements(By.tagName("th")).get(5), "IMMOBILIEN- GEWINNE","table Header");
		    
		    
		    reportStep("Validate month labels from 12th to 1st month", "INFO");
		    j=1;
			for(int i=12;i>=1;i--) {
				
				verifyExactText(nextTable.findElements(By.tagName("tr")).get(j).findElement(By.tagName("td")), getMonthNameInGermanFromMonthNumber(String.valueOf(i)));
				j+=1;
			}
			
			reportStep("Click on 1st PDF link", "INFO");
			click(nextTable.findElements(By.tagName("td")).get(1).findElement(By.tagName("a")));
			
			Thread.sleep(3000);
			tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			
			verifyTwoStringsPartially(driver.getCurrentUrl(), "pdf",true);
			   
			driver.close();
			driver.switchTo().window(tabs.get(0));	
		    
		return this;
	
		
		
	}
	
	 public String getMonthNameInGermanFromMonthNumber(String monthNumber) {
	    	
			switch(monthNumber) {
			case "01" : return "Januar"; 
			case "1" : return "Januar"; 
			case "02" : return "Februar";
			case "2" : return "Februar";
			case "03" : return "März";
			case "04" : return "April";
			case "05" : return "Mai"; 
			case "06" : return "June";
			case "07" : return "Juli";
			case "08" : return "August";	
			case "09" : return "September";	
			case "3" : return "März";
			case "4" : return "April";
			case "5" : return "Mai"; 
			case "6" : return "June";
			case "7" : return "Juli";
			case "8" : return "August";	
			case "9" : return "September";	
			case "10" :	return "Oktober";	
			case "11" :	return "November";	
			case "12" :	return "Dezember";
			default   : reportStep("Month Value returned in wrong","FAIL"); return null;
			}
	 }
	
}

package internationalPages.Accounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

import utils.ExcelDataProvider;
import wdMethods.ProjectMethods;

public class CanadaAccountsLoginPage extends ProjectMethods{
	public String passedActor;
	
	public CanadaAccountsLoginPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		//System.out.println("From UKPAge");
		try {			
		
			this.driver = driver;
			this.test = test;
			//System.out.println(sLocaleFile);
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));
			
			//Switch to Preferred Execution Language 
			selectExecutionLanguageForCountry();
			
//			if(localeProp.getProperty("lblAccLoginPageTitle").isEmpty())
//				reportStep("'" + sEnvironment + " - " + sCountryName + "' Locale File may not Loaded. Please check the Config. Refer snap", "FAIL");
//				
//				if(!getTitle().isEmpty()) 				
//					reportStep("'" + sEnvironment + " - " + sCountryName + "' Site may down or Accounts Login Page Expected Title may not displayed. Refer snap", "FAIL");	
//			
			// Initialize the webelements inside the page
			PageFactory.initElements(driver, this);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void selectExecutionLanguageForCountry() throws InterruptedException, FileNotFoundException, IOException {
		
		switch(sCountryName) {
		
		case "Canada": 		selectExecutionLanguageCanada();
							break;
		default:			reportStep("Country does not support multiple languages, Execution will continue for default language","INFO");
							break;
		}
		
	}
	
	public void selectExecutionLanguageCanada() throws InterruptedException {
	
		switch(sExecutionLanguage) {
		
		case "English": 	reportStep("Execution will continue for default language:ENGLISH","INFO");
							break;
/*		case "French": 		click(locateElement("xpath",getLocalePropertyValue("objHmPageGermanLanguage")));
							break;
*/		default:			reportStep("Language Provided in file is not applicable for this country, Please refer the file for more info.","FAIL");
							break;		
		
		}
		
	}
	
	//Accounts Module
	
	public CanadaAccountsHomePage accountsLogin() throws InterruptedException, FileNotFoundException, IOException {
		
		String UID,pwd;
		reportStep("Login into Accounts Portal","INFO");
			
		//driver.get("https://ftcastg1.corp.frk.com/ca/retail/app/access/view/sign_in_page.jsf");
		
		
		WebDriverWait wait=new WebDriverWait(driver, 200);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objAccLoginPageLoginName"))));
		
		UID=ExcelDataProvider.getCellDataByFilter("BaseData", "Login",sCountryName + "-" + sExecutionLanguage + "-" + sActor, 1);
		pwd=ExcelDataProvider.getCellDataByFilter("BaseData", "Login",sCountryName + "-" + sExecutionLanguage + "-" + sActor, 2);
		type(locateElement("xpath",getLocalePropertyValue("objAccLoginPageLoginName")),UID);
		type(locateElement("xpath",getLocalePropertyValue("objAccLoginPagePassword")),pwd);
		
		//type(locateElement("xpath",getLocalePropertyValue("objAccLoginPageLoginName")),getLocalePropertyValue("ACCOUNTS_USERNAME"));
		//type(locateElement("xpath",getLocalePropertyValue("objAccLoginPagePassword")),getLocalePropertyValue("ACCOUNTS_PASS"));
		click(locateElement("xpath",getLocalePropertyValue("objAccLoginPageRemLogin")));
		
		click(locateElement("xpath",getLocalePropertyValue("objAccLoginPageLoginButton")));
		
		Thread.sleep(10000);		
		return new CanadaAccountsHomePage(driver,test);
		}
	
	
}

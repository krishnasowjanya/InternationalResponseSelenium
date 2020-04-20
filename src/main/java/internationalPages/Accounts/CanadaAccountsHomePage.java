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

public class CanadaAccountsHomePage extends ProjectMethods{
	public String passedActor;
	
	public CanadaAccountsHomePage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		//System.out.println("From UKPAge");
		try {			
		
			this.driver = driver;
			this.test = test;			
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));
			
			if(localeProp.getProperty("lblAccHomePageTitle").isEmpty())
				reportStep("'" + sEnvironment + " - " + sCountryName + "' Locale File may not Loaded. Please check the Config. Refer snap", "FAIL");
				
			
			if(!getTitle().trim().equals(getLocalePropertyValue("lblAccHomePageTitle").trim())) 				
					reportStep("'" + sEnvironment + " - " + sCountryName + "' Home Adviser Page Expected Title may not displayed. Refer snap", "FAIL");
			
			// Initialize the webelements inside the page
			PageFactory.initElements(driver, this);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	public CanadaAccountsHomePage selectDealerCode()
	{
		WebDriverWait wait=new WebDriverWait(driver, 200);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(getLocalePropertyValue("objIADealerOrRepCodeList"))));
		
		selectDropDownUsingText(locateElement(getLocalePropertyValue("objIADealerOrRepCodeList")),"7599/0207");
		return this;
	}
	
	public CanadaAccountsHomePage validateHomePageDisplayScreenLayout() throws InterruptedException
	{
		reportStep("Validating the Home page Screen elements and layout","INFO");	
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccHomePageSignOutBtn"),"Sing out button");
		verifyElementExist("xpath", getLocalePropertyValue("objAccHomePageCurrentDate"),"Current Date");
		
		return this;
	}
	
	public CanadaAccountsOverviewPage selectOverviewFromAssetSalesDropdown() throws InterruptedException
	{
		reportStep("Click on the Account & Sales Dropdown","INFO");
		click(locateElement("xpath",getLocalePropertyValue("objAccHomePageAssetsSalesBtn")));
		
		List<WebElement> acctSalesListValues = locateElements("xpath", getLocalePropertyValue("objAccHomePageAssetsSalesList"));
		for(WebElement lnk:acctSalesListValues) {
			if(lnk.getText().equals(getLocalePropertyValue("lblAccHomePageOverviewLink"))) {
				lnk.click();
				break;
			}
				
		}
	
		return new CanadaAccountsOverviewPage(driver,test);
	}
		
	public CanadaAccountsAssetsByFundPage selectAssetsFromAssetSalesDropdown() throws InterruptedException
	{
		reportStep("Click on the Account & Sales Dropdown","INFO");
		click(locateElement("xpath",getLocalePropertyValue("objAccHomePageAssetsSalesBtn")));
		
		List<WebElement> acctSalesListValues = locateElements("xpath", getLocalePropertyValue("objAccHomePageAssetsSalesList"));
		for(WebElement lnk:acctSalesListValues) {
			if(lnk.getText().equals(getLocalePropertyValue("lblAccHomePageAsssetsLink"))) {
				lnk.click();
				break;
			}
				
		}
	
		return new CanadaAccountsAssetsByFundPage(driver,test);
	}

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

		click(locateElement("xpath",getLocalePropertyValue("objAccLoginPageRemLogin")));		
		click(locateElement("xpath",getLocalePropertyValue("objAccLoginPageLoginButton")));
		
		Thread.sleep(10000);
		return this;
		}
	
	
	public CanadaAccountsReportPage selectBuildNewReportsFromReportsDropdown() throws InterruptedException
	{
		reportStep("Click on build new reports from Reports dropdown","INFO");
		click(locateElement("xpath",getLocalePropertyValue("objAccHomePageReportsButton")));
		click(locateElement("xpath",getLocalePropertyValue("objAccHomePageBuildNewReportsLink")));
	
		return new CanadaAccountsReportPage(driver,test);
	}
	
}

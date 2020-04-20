package internationalPages.Accounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import wdMethods.ProjectMethods;

public class CanadaAccountsListClientAccountsPage extends ProjectMethods{

	public CanadaAccountsListClientAccountsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		//System.out.println("From UKPAge");
		try {			
		
			this.driver = driver;
			this.test = test;			
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));
			
			if(localeProp.getProperty("lblAccListClientAccountsPageTitle").isEmpty())
				reportStep("'" + sEnvironment + " - " + sCountryName + "' Locale File may not Loaded. Please check the Config. Refer snap", "FAIL");
		
				if(!getTitle().trim().equals(getLocalePropertyValue("lblAccListClientAccountsPageTitle").trim()))			
					reportStep("'" + sEnvironment + " - " + sCountryName + "' Investment Advosor Page Expected Title may not displayed. Refer snap", "FAIL");
			
			// Initialize the webelements inside the page
			PageFactory.initElements(driver, this);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public CanadaAccountsListClientAccountsPage validateListofClientAccountsTableHeaders() throws InterruptedException
	{
		reportStep("Validating List of Client Accounts Table headers","INFO");
		
		WebElement clientListAccountsTable = locateElement("xpath", getLocalePropertyValue("objAccSeriesLoadPageListCientAccountsTbl"));
		
		verifyPartialText(clientListAccountsTable.findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblAccSeriesLoadPageListClientAccountsTableHeader1").toUpperCase());
		verifyPartialText(clientListAccountsTable.findElements(By.tagName("th")).get(1), getLocalePropertyValue("lblAccSeriesLoadPageListClientAccountsTableHeader2").toUpperCase());
		verifyPartialText(clientListAccountsTable.findElements(By.tagName("th")).get(2), getLocalePropertyValue("lblAccSeriesLoadPageListClientAccountsTableHeader3").toUpperCase());
		verifyPartialText(clientListAccountsTable.findElements(By.tagName("th")).get(3), getLocalePropertyValue("lblAccSeriesLoadPageListClientAccountsTableHeader4").toUpperCase());
		
	return this;
	}
	
	
	public CanadaAccountsClientSummaryPage clickOnClientonSecondRow() throws InterruptedException
	{
		reportStep("Click on Client Account in Second Row","INFO");
		click(locateElement("xpath", getLocalePropertyValue("objAccListofAccountsPageSecondClient")));
		
		return new CanadaAccountsClientSummaryPage(driver,test);
	}
	
	public CanadaAccountsTipsPage clickOnViewTipsonSecondRow() throws InterruptedException
	{
		reportStep("Click on Tips in Second Row","INFO");
		click(locateElement("xpath", getLocalePropertyValue("objAccListofAccountsPageSecondClientTipsLink")));
		
		return new CanadaAccountsTipsPage(driver,test);
	}
}

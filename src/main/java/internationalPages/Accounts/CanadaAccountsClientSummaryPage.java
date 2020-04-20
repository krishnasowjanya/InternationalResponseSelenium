package internationalPages.Accounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import wdMethods.ProjectMethods;

public class CanadaAccountsClientSummaryPage extends ProjectMethods{

	public CanadaAccountsClientSummaryPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		//System.out.println("From UKPAge");
		try {			
		
			this.driver = driver;
			this.test = test;			
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));
			
			if(localeProp.getProperty("lblAccClientSummaryPageTitle").isEmpty())
				reportStep("'" + sEnvironment + " - " + sCountryName + "' Locale File may not Loaded. Please check the Config. Refer snap", "FAIL");
				
		
			if(!getTitle().trim().equals(getLocalePropertyValue("lblAccClientSummaryPageTitle").trim()))			
					reportStep("'" + sEnvironment + " - " + sCountryName + "' Investment Advosor Page Expected Title may not displayed. Refer snap", "FAIL");
			
			// Initialize the webelements inside the page
			PageFactory.initElements(driver, this);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	public CanadaAccountsListClientAccountsPage validateClientsSummaryPageDisplayScreenLayout() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountSummaryPageHeader"),"Account Summary Page Header");
		driver.navigate().back();
		return new CanadaAccountsListClientAccountsPage(driver,test);
	}
}

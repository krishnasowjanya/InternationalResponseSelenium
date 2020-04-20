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

public class CanadaAccountsAssetsByFundPage extends ProjectMethods{

	public CanadaAccountsAssetsByFundPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		//System.out.println("From UKPAge");
		try {			
		
			this.driver = driver;
			this.test = test;			
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));
			
			if(localeProp.getProperty("lblAccAssetsByFundPageTitle").isEmpty())
				reportStep("'" + sEnvironment + " - " + sCountryName + "' Locale File may not Loaded. Please check the Config. Refer snap", "FAIL");
					
			if(!getTitle().trim().equals(getLocalePropertyValue("lblAccAssetsByFundPageTitle").trim()))				
					reportStep("'" + sEnvironment + " - " + sCountryName + "' Investment Advosor Page Expected Title may not displayed. Refer snap", "FAIL");
			
			// Initialize the webelements inside the page
			PageFactory.initElements(driver, this);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	public CanadaAccountsAssetsByFundPage validateAssetsByFundPageDisplayScreenLayout() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("objAccAssetsByFundPageHeader"),"Assets By Fund Page Header");
		verifyElementExist("xpath", getLocalePropertyValue("objAccOverviewPageDealerCodeDropdown"),"default Fund Drop Down");
		
		WebElement currentAssetsTable = locateElement("xpath", getLocalePropertyValue("objAccAssetsByFundPageCurrentAssetsTable"));
		verifyPartialText(currentAssetsTable.findElements(By.tagName("strong")).get(0), getLocalePropertyValue("lblAccAssetsByFundPageCurrAssets"));
		verifyPartialText(currentAssetsTable.findElements(By.tagName("strong")).get(1), getLocalePropertyValue("lblAccAssetsByFundPagePreviousYearClosingAssets"));
		verifyPartialText(currentAssetsTable.findElements(By.tagName("strong")).get(2), getLocalePropertyValue("lblAccAssetsByFundPageAsAt"));
		
		WebElement fundCurrencyTable = locateElement("xpath", getLocalePropertyValue("objAccAssetsByFundPageFundCurrencyTable"));
		System.out.println(fundCurrencyTable.findElements(By.tagName("label")).get(0).getText());
		verifyPartialText(fundCurrencyTable.findElements(By.tagName("label")).get(0), getLocalePropertyValue("lblAccAssetsByFundPageCurrencylbl1").toUpperCase());
		verifyPartialText(fundCurrencyTable.findElements(By.tagName("label")).get(1), getLocalePropertyValue("lblAccAssetsByFundPageCurrencylbl2").toUpperCase());
		verifyPartialText(fundCurrencyTable.findElements(By.tagName("label")).get(2), getLocalePropertyValue("lblAccAssetsByFundPageCurrencylbl3").toUpperCase());
		
		WebElement fundClientDataTable = locateElement("xpath", getLocalePropertyValue("objAccAssetsByFundPageFundClientTable"));
		verifyPartialText(fundClientDataTable.findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblAccAssetsByFundPageFundClientTablelbl1").toUpperCase());
		verifyPartialText(fundClientDataTable.findElements(By.tagName("th")).get(1), getLocalePropertyValue("lblAccAssetsByFundPageFundClientTablelbl2").toUpperCase());
		verifyPartialText(fundClientDataTable.findElements(By.tagName("th")).get(2), getLocalePropertyValue("lblAccAssetsByFundPageFundClientTablelbl3").toUpperCase());
		verifyPartialText(fundClientDataTable.findElements(By.tagName("th")).get(3), getLocalePropertyValue("lblAccAssetsByFundPageFundClientTablelbl4").toUpperCase());
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccAssetsByFundPageCurrentAssetsTableGoBtn"),"Go Button");
		return this;
	}

	
	public CanadaAccountsListClientAccountsPage clickOnClientNumberLinkFirstClient() throws InterruptedException
	{
		reportStep("Click on Clients Number Link for First Client","INFO");
		click(locateElement("xpath", getLocalePropertyValue("objAccAssetsByFundPageFirstClientsLink")));
		
		return new CanadaAccountsListClientAccountsPage(driver,test);
	}
	
	public CanadaAccountsAssetsByClassPage selectAssetsClassFromAssetsLeftPane() throws InterruptedException
	{
		
		reportStep("Click on Assets Class Link on the left Pane under Assets menu","INFO");
		click(locateElement( getLocalePropertyValue("objAccAssetsByFundPageLeftPaneAssetClassLink")));
		
		return new CanadaAccountsAssetsByClassPage(driver,test);
	}
	
	
	public CanadaAccountsSeriesLoadPage selectSeriesLoadFromAssetsLeftPane() throws InterruptedException
	{
		
		reportStep("Click on Series Load Link on the left Pane under Assets menu","INFO");
		click(locateElement( getLocalePropertyValue("objAccAssetsByFundPageLeftPaneSeriesLoadLink")));
		
		return new CanadaAccountsSeriesLoadPage(driver,test);
	}
	
	
	public CanadaAccountsAccountTypePage selectAccountTypeFromAssetsLeftPane() throws InterruptedException
	{
		
		reportStep("Click on Asset Type Link on the left Pane under Assets menu","INFO");
		click(locateElement( getLocalePropertyValue("objAccAssetsByFundPageLeftPaneAccountTypeLink")));
		
		return new CanadaAccountsAccountTypePage(driver,test);
	}
	
	public CanadaAccountsProductTypePage selectProductTypeFromAssetsLeftPane() throws InterruptedException
	{
		
		reportStep("Click on Product Type Link on the left Pane under Assets menu","INFO");
		click(locateElement( getLocalePropertyValue("objAccAssetsByFundPageLeftPaneProductTypeLink")));
		
		return new CanadaAccountsProductTypePage(driver,test);
	}

}

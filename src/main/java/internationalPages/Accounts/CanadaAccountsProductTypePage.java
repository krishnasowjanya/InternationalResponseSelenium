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

public class CanadaAccountsProductTypePage extends ProjectMethods{

	public CanadaAccountsProductTypePage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		//System.out.println("From UKPAge");
		try {			
		
			this.driver = driver;
			this.test = test;			
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));
			
			if(localeProp.getProperty("lblAccProductTypePageTitle").isEmpty())
				reportStep("'" + sEnvironment + " - " + sCountryName + "' Locale File may not Loaded. Please check the Config. Refer snap", "FAIL");
					
			if(!getTitle().trim().equals(getLocalePropertyValue("lblAccProductTypePageTitle").trim()))				
					reportStep("'" + sEnvironment + " - " + sCountryName + "' Investment Advosor Page Expected Title may not displayed. Refer snap", "FAIL");
			
			// Initialize the webelements inside the page
			PageFactory.initElements(driver, this);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	public CanadaAccountsProductTypePage validateProductTypePageDisplayScreenLayout() throws InterruptedException
	{
		reportStep("Validating Product Type Page Layout and table headers","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccProductTypePageHeader"),"Product Type Page Header");
		verifyElementExist("xpath", getLocalePropertyValue("objAccOverviewPageDealerCodeDropdown"),"default Fund Drop Down");
		
		WebElement currentAssetsTable = locateElement("xpath", getLocalePropertyValue("objAccAssetsByFundPageCurrentAssetsTable"));
		verifyPartialText(currentAssetsTable.findElements(By.tagName("strong")).get(0), getLocalePropertyValue("lblAccAssetsByFundPageCurrAssets"));
		verifyPartialText(currentAssetsTable.findElements(By.tagName("strong")).get(1), getLocalePropertyValue("lblAccAssetsByFundPagePreviousYearClosingAssets"));
		verifyPartialText(currentAssetsTable.findElements(By.tagName("strong")).get(2), getLocalePropertyValue("lblAccAssetsByFundPageAsAt"));
		
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccAssetsByFundPageCurrentAssetsTableGoBtn"),"Go Button");
		
		WebElement productTypeTable = locateElement("xpath", getLocalePropertyValue("objAccAssetsByFundPageFundClientTable"));
		
		verifyPartialText(productTypeTable.findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblAccProductTypePageProdTypeAllocTableHeader1").toUpperCase());
		verifyPartialText(productTypeTable.findElements(By.tagName("th")).get(1), getLocalePropertyValue("lblAccProductTypePageProdTypeAllocTableHeader2").toUpperCase());
		verifyPartialText(productTypeTable.findElements(By.tagName("th")).get(2), getLocalePropertyValue("lblAccProductTypePageProdTypeAllocTableHeader3").toUpperCase());
		
		return this;
	}

	
	public CanadaAccountsListClientAccountsPage clickOnClientNumberLinkFirstClient() throws InterruptedException
	{
		reportStep("Click on Clients Number Link for First Client","INFO");
		click(locateElement("xpath", getLocalePropertyValue("objAccAssetsByFundPageFirstClientsLink")));
		
		return new CanadaAccountsListClientAccountsPage(driver,test);
	}
	


}
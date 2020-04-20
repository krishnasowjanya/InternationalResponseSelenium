package internationalPages.Accounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

import wdMethods.ProjectMethods;

public class CanadaAccountsOverviewPage extends ProjectMethods{

	public CanadaAccountsOverviewPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		//System.out.println("From UKPAge");
		try {			
		
			this.driver = driver;
			this.test = test;			
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));
			
			if(localeProp.getProperty("lblAccOverviewPageTitle").isEmpty())
				reportStep("'" + sEnvironment + " - " + sCountryName + "' Locale File may not Loaded. Please check the Config. Refer snap", "FAIL");
		
			if(!getTitle().trim().equals(getLocalePropertyValue("lblAccOverviewPageTitle").trim()))				
					reportStep("'" + sEnvironment + " - " + sCountryName + "' Investment Advosor Page Expected Title may not displayed. Refer snap", "FAIL");
			
			// Initialize the webelements inside the page
			PageFactory.initElements(driver, this);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	public CanadaAccountsOverviewPage validateOverviewPageDisplayScreenLayout() throws InterruptedException
	{
		reportStep("Validating Accounts Overview Page layout","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccOverviewPageDealerCodeDropdown"),"Dealer Rep Code dropdown");
		verifyElementExist("xpath", getLocalePropertyValue("objAccOverviewPageYTDTotAssetsSalesForDealerRepCodeTable"),"Year-To-Date Total Assets & Sales for Dealer/Rep Code(s)");

		WebElement currentAssetsTable = locateElement("xpath",getLocalePropertyValue("objAccOverviewPageCurrentDataTable"));
		verifyPartialText(currentAssetsTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblAccOverviewPageCurrentDataTableCurrAssets"));
		verifyPartialText(currentAssetsTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("th")).get(2), getLocalePropertyValue("lblAccOverviewPageCurrentDataTableYTDSales"));
		
		verifyPartialText(currentAssetsTable.findElements(By.tagName("tr")).get(5).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblAccOverviewPageCurrentDataTableYTDTrailersPaid"));
		verifyPartialText(currentAssetsTable.findElements(By.tagName("tr")).get(5).findElements(By.tagName("th")).get(1), getLocalePropertyValue("lblAccOverviewPageCurrentDataTableYTDCommPaid"));
		
		WebElement mostRecentTable = locateElement("xpath",getLocalePropertyValue("objAccOverviewPageRencentPurchasesTable"));
		verifyPartialText(mostRecentTable.findElements(By.tagName("caption")).get(0), getLocalePropertyValue("lblAccOverviewPageRecentPurchaseTableCaption"));
		
		verifyPartialText(mostRecentTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("th")).get(0), getLocalePropertyValue("lblAccOverviewPageRecentPurchaseTableClient").toUpperCase());
		verifyPartialText(mostRecentTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("th")).get(1), getLocalePropertyValue("lblAccOverviewPageRecentPurchaseTableTradeDate").toUpperCase());
		verifyPartialText(mostRecentTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("th")).get(2), getLocalePropertyValue("lblAccOverviewPageRecentPurchaseTableUnitsTraded").toUpperCase());
		verifyPartialText(mostRecentTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("th")).get(3), getLocalePropertyValue("lblAccOverviewPageRecentPurchaseTableTips").toUpperCase());
		
		reportStep("Verifying 20 recent purchases and redemptions are getting populated in the overview page","INFO");
		if(locateElements("xpath", getLocalePropertyValue("objAccOverviewPageRencentPurchasesTableRowsList")).size() == 20)
			reportStep("Number of recent Purchases and redemptions rows displayed are 20","PASS");
		else
			reportStep("Number of recent Purchases and redemptions rows displayed is wrong = "+locateElements("xpath", getLocalePropertyValue("objAccOverviewPageRencentPurchasesTableRowsList")).size(),"FAIL");
		
	
		
		return this;
	}
	
	
	public CanadaAccountsAssetsByFundPage selectAssetsFromLeftPane() throws InterruptedException
	{
		
		reportStep("Click on Assets Link on the left Pane","INFO");
		click(locateElement("xpath",getLocalePropertyValue("objAccOverviewPageLeftPaneAssetsLink")));
		
		return new CanadaAccountsAssetsByFundPage(driver,test);
	}
	
}

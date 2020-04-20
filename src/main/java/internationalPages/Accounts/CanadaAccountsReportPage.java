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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

import utils.ExcelDataProvider;
import wdMethods.ProjectMethods;

public class CanadaAccountsReportPage extends ProjectMethods{
	public String passedActor;
	
	public CanadaAccountsReportPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		//System.out.println("From UKPAge");
		try {			
		
			this.driver = driver;
			this.test = test;			
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));
			
			if(localeProp.getProperty("lblAccReportHomePageTitle").isEmpty())
				reportStep("'" + sEnvironment + " - " + sCountryName + "' Locale File may not Loaded. Please check the Config. Refer snap", "FAIL");
				
			
			if(!getTitle().trim().equals(getLocalePropertyValue("lblAccReportHomePageTitle").trim())) 				
					reportStep("'" + sEnvironment + " - " + sCountryName + "' Home Adviser Page Expected Title may not displayed. Refer snap", "FAIL");
			
			// Initialize the webelements inside the page
			PageFactory.initElements(driver, this);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	public CanadaAccountsReportPage validateAccountReportPageScreenLayout() {
		reportStep("Validating the Account reports Page Screen layout and components","INFO");
		takeFullSnap();
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objAccAccountReportPageHeader")));
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportPageHeader"), "Account Report Page Header");
		verifyElementExist("xpath", getLocalePropertyValue("objAccHomePageDelRepCodeDropdown"), "Dealer Repcode dropdown");
		
//		verifyPartialText(new Select(locateElement("xpath", getLocalePropertyValue("objAccHomePageDelRepCodeDropdown"))).getFirstSelectedOption(), "Consolidate View");
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportPageMsg"), "Message Text - Account Report Page");
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportPageVideoTourLink"), "Video Tour Link");
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportAccDetailsSectionHeader"), "Account details Section header");
		
		WebElement acctTypeTable= locateElements("xpath", getLocalePropertyValue("objAccAccountReportAccDetailsTables")).get(0);
		WebElement acctStatTable= locateElements("xpath", getLocalePropertyValue("objAccAccountReportAccDetailsTables")).get(1);
		
		reportStep("Following Values for Account Type are displayed in Application for selection"+acctTypeTable.findElement(By.tagName("tr")).getText(),"INFO");
		reportStep("Following Values for Account Status are displayed in Application for selection"+acctStatTable.findElement(By.tagName("tr")).getText(),"INFO");
		
		WebElement primaryOwnerFields= locateElements("xpath", getLocalePropertyValue("objAccAccountReportUserInfoSections")).get(0);
		WebElement acctValueFields= locateElements("xpath", getLocalePropertyValue("objAccAccountReportUserInfoSections")).get(1);
		
		verifyPartialText(primaryOwnerFields.findElement(By.tagName("h3")), getLocalePropertyValue("lblAccReportPrimaryOwnerAge"));
		verifyPartialText(primaryOwnerFields.findElements(By.tagName("label")).get(0), getLocalePropertyValue("lblFrom"));
		verifyPartialText(primaryOwnerFields.findElements(By.tagName("label")).get(1), getLocalePropertyValue("lblTo"));
		
		verifyPartialText(acctValueFields.findElement(By.tagName("h3")), getLocalePropertyValue("lblAccReportAccValue"));
		verifyPartialText(acctValueFields.findElements(By.tagName("label")).get(0), getLocalePropertyValue("lblFrom"));
		verifyPartialText(acctValueFields.findElements(By.tagName("label")).get(1), getLocalePropertyValue("lblTo"));
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportSortByDropdown"), "sort By dropdown");
		
		verifyPartialText(new Select(locateElement("xpath", getLocalePropertyValue("objAccAccountReportSortByDropdown"))).getFirstSelectedOption(), getLocalePropertyValue("lblSortByDefaultValue"));
		
		reportStep("Following text is present below sort by dropdown"+locateElement("xpath", getLocalePropertyValue("objAccAccountReportBottomPageText")).getText(),"INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportGenReportBtn"), "Generate Report Button");
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportGenReportSaveBtn"), "Save Button");
		
		
		return this;
	}
	
	

	public CanadaAccountsReportPage validateFundReportPageScreenLayout() {
		reportStep("Validating the Fund reports Page Screen layout and components","INFO");
		takeFullSnap();
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objAccFundReportPageHeader")));
		verifyElementExist("xpath", getLocalePropertyValue("objAccFundReportPageHeader"), "Fund Report Page Header");
		verifyElementExist("xpath", getLocalePropertyValue("objAccHomePageDelRepCodeDropdown"), "Dealer Repcode dropdown");
		
//		verifyPartialText(new Select(locateElement("xpath", getLocalePropertyValue("objAccHomePageDelRepCodeDropdown"))).getFirstSelectedOption(), "Consolidate View");
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccFundReportPageMsg"), "Message Text - Fund Report Page");
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportPageVideoTourLink"), "Video Tour Link");
		verifyElementExist("xpath", getLocalePropertyValue("objAccFundReportAccDetailsSectionHeader"), "Fund details Section header");
		
		WebElement acctTypeTable= locateElements("xpath", getLocalePropertyValue("objAccAccountReportAccDetailsTables")).get(0);
		WebElement acctStatTable= locateElements("xpath", getLocalePropertyValue("objAccAccountReportAccDetailsTables")).get(1);
		
		reportStep("Following Values for Account Type are displayed in Application for selection"+acctTypeTable.findElement(By.tagName("tr")).getText(),"INFO");
		reportStep("Following Values for Account Status are displayed in Application for selection"+acctStatTable.findElement(By.tagName("tr")).getText(),"INFO");
		
		WebElement primaryOwnerFields= locateElements("xpath", getLocalePropertyValue("objAccAccountReportUserInfoSections")).get(0);
		WebElement acctValueFields= locateElements("xpath", getLocalePropertyValue("objAccAccountReportUserInfoSections")).get(1);
		
		verifyPartialText(primaryOwnerFields.findElement(By.tagName("h3")), getLocalePropertyValue("lblAccReportPrimaryOwnerAge"));
		verifyPartialText(primaryOwnerFields.findElements(By.tagName("label")).get(0), getLocalePropertyValue("lblFrom"));
		verifyPartialText(primaryOwnerFields.findElements(By.tagName("label")).get(1), getLocalePropertyValue("lblTo"));
		
		verifyPartialText(acctValueFields.findElement(By.tagName("h3")), getLocalePropertyValue("lblAccReportAccValue"));
		verifyPartialText(acctValueFields.findElements(By.tagName("label")).get(0), getLocalePropertyValue("lblFrom"));
		verifyPartialText(acctValueFields.findElements(By.tagName("label")).get(1), getLocalePropertyValue("lblTo"));
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportSortByDropdown"), "sort By dropdown");
		
		verifyPartialText(new Select(locateElement("xpath", getLocalePropertyValue("objAccAccountReportSortByDropdown"))).getFirstSelectedOption(), getLocalePropertyValue("lblSortByDefaultValue"));
		
		reportStep("Following text is present below sort by dropdown"+locateElement("xpath", getLocalePropertyValue("objAccAccountReportBottomPageText")).getText(),"INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportGenReportBtn"), "Generate Report Button");
		
		verifyElementExist("xpath", getLocalePropertyValue("objAccAccountReportGenReportSaveBtn"), "Save Button");
		
		
		return this;
	}
	
	
	public CanadaAccountsReportPage validateConsolidateViewReport() throws InterruptedException {
	
		reportStep("Validating the Consolidate View Report","INFO");
		
		new Select(locateElement("xpath", getLocalePropertyValue("objAccHomePageDelRepCodeDropdown"))).selectByVisibleText("Consolidate View");
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objAccAccountReportGenReportBtn")));
		
		validateDownloadXLS("Account_Report",getLocalePropertyValue("objAccAccountReportGenReportBtn"));
		return this;
	}
	
	public CanadaAccountsReportPage selectFundReportFromLeftPane() throws InterruptedException
	{
		
		reportStep("Click on Fund Report Link on the left Pane","INFO");
		click(locateElement(getLocalePropertyValue("objAccReportsPageLeftPaneFundReportLink")));
		
		return this;
	}
	
}


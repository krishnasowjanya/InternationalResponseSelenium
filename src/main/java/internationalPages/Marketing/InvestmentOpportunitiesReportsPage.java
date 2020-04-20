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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

import internationalPages.Products.UKHomePage;
import net.bytebuddy.implementation.bytecode.constant.LongConstant;
import wdMethods.ProjectMethods;
import utils.ExcelDataProvider;
import utils.dateFunctions;

public class InvestmentOpportunitiesReportsPage extends ProjectMethods{
	
	public static String tid;
	public InvestmentOpportunitiesReportsPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblInvstOppReportsPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public InvestmentOpportunitiesReportsPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Reports Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Reports", getLocalePropertyValue("lblInvstOppReportsBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	public InvestmentOpportunitiesReportsPage clickOnLoginInstReports()
	{
		reportStep("Click on Login Instituional Reports Button", "INFO");
		click(locateElement("xpath","//a[text()='Login institutionelle Reports']"));
		return this;
	}
	
	public InvestmentOpportunitiesReportsPage loginAsInstitutional() throws InterruptedException, FileNotFoundException, IOException {	
		reportStep("Login as Institutional Investor","INFO");
			
		
		Thread.sleep(5000);
		
		
		WebElement userId=null;
		for(WebElement ele : locateElements("xpath","//div[@class='sign-in']//input[@name='userName']")) {
			if(ele.isDisplayed()) {
				userId=ele;
				break;
			}
		}
		
		WebElement pin=null;
		for(WebElement ele : locateElements("xpath","//div[@class='sign-in']//input[@name='pin']")) {
			if(ele.isDisplayed()) {
				pin=ele;
				break;
			}
		}
		
		
		WebElement agreeTerms=null;
		for(WebElement ele : locateElements("xpath","//div[@class='sign-in']//input[@name='agree-terms']")) {
			if(ele.isDisplayed()) {
				agreeTerms=ele;
				break;
			}
		}
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", userId);
		
		WebDriverWait	wait=new WebDriverWait(driver, 200);	
		wait.until(ExpectedConditions.elementToBeClickable(userId));
		
		
		type(userId,getLocalePropertyValue("ADVISOR_USERNAME"));
		type(pin,getLocalePropertyValue("ADVISOR_PASS"));
		click(agreeTerms);
		
		click(locateElement("xpath",getLocalePropertyValue("objAdvLoginAcceptBtn")),true);
		
		
		
//		Thread.sleep(2000);
//		if(locateElements("xpath",getLocalePropertyValue("objAdvLoginWrongUserIdMsg")).size()>0) {
//			if(locateElements("xpath",getLocalePropertyValue("objAdvLoginWrongUserIdMsg")).get(0).isDisplayed())
//			reportStep("UserId/Password is not correct, Login Failed.","FAIL");
//		}
		
		Thread.sleep(3000);
		return this;
		}
	
	public InvestmentOpportunitiesReportsPage verifyPageLayout(HashMap<String,String> testData) throws InterruptedException
	{
		
		reportStep("Validate Layout of Reports Page - Post Login", "INFO");
		

		WebDriverWait wait=new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath","//div[contains(@class,'form-group')]//label[text()='SUCHE NACH FONDSNAME ODER SUCHBEGRIFF']")));
		
		
		verifyElementExist("xpath", "//div[contains(@class,'form-group')]//label[text()='SUCHE NACH FONDSNAME ODER SUCHBEGRIFF']","Search Box Label SUCHE NACH FONDSNAME ODER SUCHBEGRIFF");
		

		reportStep("Enter the Fund Name to Search", "INFO");
		type(locateElement("xpath","//ft-type-ahead-search//input"),testData.get("Fund Name"));
		
		Thread.sleep(3000);
		
		if(!(locateElements("xpath","//div[@class='search']/div/a").size()>0))
			reportStep("No suggestions were displayed for given fund input :"+ testData.get("Fund Name"),"FAIL");
		
		reportStep("Click on First Fund in suggestions", "INFO");
		click(locateElements("xpath","//div[@class='search']/div/a").get(0));
		
		wait=new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath","//div[@class='ft-table-responsive-flow']//th")));
		
		if(locateElements("xpath","//div[@class='ft-table-responsive-flow']//th").size()<1)
			reportStep("Column Labels are not displayed in application","FAIL");
		
		reportStep("Validate Columns labels displayed for the fund searched", "INFO");
		verifyExactText(locateElements("xpath","//div[@class='ft-table-responsive-flow']//th").get(0), "Bericht".toUpperCase(),"Bericht Column");
		verifyExactText(locateElements("xpath","//div[@class='ft-table-responsive-flow']//th").get(1), "Berichte".toUpperCase(),"Berichte Column");
	
		verifyExactText(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td/span").get(0), "Risikokennziffer (Value at Risk)","Risikokennziffer (Value at Risk)",true);
		
		if(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[2]//a").size()<1)
			reportStep("No XLS Links are displayed for Fund in 2nd Column of 1st Row","FAIL");
		
		reportStep("Click on 1st XLS Link for the fund in 1st row 2nd Column", "INFO");
		moveContextToElement(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[2]//a").get(0));
		String xlsname = locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[2]//a//ft-label").get(0).getText();
		validateDownloadXLS("VaR-worksheet-FTIF-Franklin-Income-Fund-LU0098860793-Risikokennziffer-(Value-at-Risk)",locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[2]//a//ft-label").get(0),"xlsx");
	
		    
		    reportStep("Validate Select Multiple XLS option is 'off' by default ","INFO");
		    verifyPartialAttribute(locateElements("xpath","//div[contains(@class,'ft-btn-toggle-on-off')]//input/..").get(1),"class","active");
		    
		    reportStep("Enable the Select Multiple XLS Option","INFO");
		    click(locateElements("xpath","//div[contains(@class,'ft-btn-toggle-on-off')]//input/..").get(0));
		    
		    Thread.sleep(3000);
		    reportStep("Select Multiple XLS to test EMail & Download functionality, Selecting 2 PDFs","INFO");
			click(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[2]//div[@class='media ft-document-icon']").get(0));
			click(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[2]//td[2]//div[@class='media ft-document-icon']").get(0),true);
			
			 reportStep("Validate the numbe of PDF selected is updated to '2' value ","INFO");
			 verifyExactText(locateElement("xpath","//span[text()='Ausgewählte PDFs']/.."), "Ausgewählte PDFs "+ "2" +" Bearbeiten","Ausgewählte PDFs "+ "2" +" Bearbeiten");
			 
			 reportStep("Click On EMAIL Button","INFO");
			 click(locateElement("xpath","//span[text()='Auswahl per E-Mail verschicken']"));
			 
			 reportStep("Click On Continue button in popup","INFO");
			 click(locateElement("xpath","//div[@class='modal-dialog']//a[text()='Weiter']"));
			 
			 reportStep("Enter Values in Email details popup","INFO");
			 type(locateElements("xpath","//label[text()='Von Name*']/../../..//input").get(0),"test By Name");
			 type(locateElements("xpath","//label[text()='Von Name*']/../../..//input").get(1),"test Receiver Name");
			 type(locateElements("xpath","//label[text()='Von Name*']/../../..//input").get(2),"test@sample.com");
		
			 reportStep("Click On Send button","INFO");
			 click(locateElement("xpath","//a[text()='Senden']"));
			 
			 verifyElementExist("xpath","//p[text()='E-Mail wurde erfolgreich gesendet. Bitte schauen Sie in Ihr Postfach und ggf. in Ihren Spam-Ordner.']","Email Sent Successfully Message", true);
		
			 click(locateElement("xpath","//span[text()='Schließen']"));
			
			 Thread.sleep(2000);
			 reportStep("Click On Download button to open popup","INFO");
			 click(locateElement("xpath","//span[text()='herunterladen']/.."),true);
			 
			 validateDownloadXLS("Fund-literature",locateElement("xpath","//a[text()='herunterladen']"),"zip");
			 
			 verifyElementExist("xpath","//span[text()='* Veröffentlichung erfolgt vierteljährlich.']","* Veröffentlichung erfolgt vierteljährlich. at page bottom");
			 verifyElementExist("xpath","//span[text()='** Gern stellen wir Ihnen auf Anfrage den gewünschten Report zur Verfügung. Bitte kontaktieren Sie uns dazu unter der Rufnummer 069-27223-506 oder unter der Email:']","** Gern stellen wir Ihnen auf Anfrage den gewünschten Report zur Verfügung. Bitte kontaktieren Sie uns dazu unter der Rufnummer 069-27223-506 oder unter der Email: at page bottom");
			 return this;
	}
	
	
	
	
}

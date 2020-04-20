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

public class DistributionTaxesPage extends ProjectMethods{
	
	public static String tid;
	public DistributionTaxesPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblDistributionTaxesPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public DistributionTaxesPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Distribution & Taxes Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Distribution & Taxes", getLocalePropertyValue("lblDistributionTaxesBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	
	
	
	public DistributionTaxesPage verifyPageLayout(HashMap<String,String> testData) throws InterruptedException
	{
		
		reportStep("Validate Layout of Distribution & Taxes Page", "INFO");
		verifyElementExist("xpath", "//h1[text()='Ausschüttungen & Steuern']","Page Header AUSSCHÜTTUNGEN & STEUERN");
		verifyElementExist("xpath", "//div[@class='form-group']//label[text()='SUCHE NACH FONDSNAME ODER SUCHBEGRIFF']","Search Box Label SUCHE NACH FONDSNAME ODER SUCHBEGRIFF");
		verifyElementExist("xpath", "//span[text()='Keine Ergebnisse']","Keine Ergebnisse",true);

		reportStep("Enter the Fund Name to Search", "INFO");
		type(locateElement("xpath","//ft-type-ahead-search//input"),testData.get("Fund Name"));
		
		Thread.sleep(3000);
		
		if(!(locateElements("xpath","//div[@class='search']/div/a").size()>0))
			reportStep("No suggestions were displayed for given fund input :"+ testData.get("Fund Name"),"FAIL");
		
		reportStep("Click on First Fund in suggestions", "INFO");
		click(locateElements("xpath","//div[@class='search']/div/a").get(0));
		
		WebDriverWait wait=new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath","//div[@class='form-group']/label[text()='Jahr']/following-sibling::select")));
		
		
		if(new Select(locateElement("xpath","//div[@class='form-group']/label[text()='Jahr']/following-sibling::select")).getFirstSelectedOption().getText().equals(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))))
				reportStep(Calendar.getInstance().get(Calendar.YEAR)+" is displayed as default year","PASS");
			else
				reportStep(Calendar.getInstance().get(Calendar.YEAR)+" is not displayed as default year","FAIL");	
		
		if(locateElements("xpath","//div[@class='ft-table-responsive-flow']//th").size()<1)
			reportStep("Column Labels are not displayed in application","FAIL");
		
		reportStep("Validate Columns labels displayed for the fund searched", "INFO");
		verifyExactText(locateElements("xpath","//div[@class='ft-table-responsive-flow']//th").get(0), "Fondsname".toUpperCase(),"Fondsname Column");
		verifyExactText(locateElements("xpath","//div[@class='ft-table-responsive-flow']//th").get(1), "Ausschüttungsberichte".toUpperCase(),"Ausschüttungsberichte Column");
		verifyExactText(locateElements("xpath","//div[@class='ft-table-responsive-flow']//th").get(2), "Ausschüttungsgleiche Erträge".toUpperCase(),"Ausschüttungsgleiche Erträge Column");
		
		reportStep("First Row , Fondsname :"+locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td").get(0).getText(), "INFO");
		
		if(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[2]//a").size()<1)
			reportStep("No PDF Links are displayed for Fund in 2nd Column of 1st Row","FAIL");
		
		reportStep("Click on 1st PDF Link for the fund in 1st row 2nd Column", "INFO");
		click(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[2]//a").get(0));
		
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		   driver.switchTo().window(tabs.get(1));
		
		   verifyTwoStringsPartially(driver.getCurrentUrl(), "Distribution-Tax");
		   
		   driver.close();
		    driver.switchTo().window(tabs.get(0));	
		    
		    if(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[3]//a").size()<1)
				reportStep("No PDF Links are displayed for Fund in 3rd Column of 1st Row, Instead This information is displayed :"+locateElement("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[3]").getText(),"INFO");
		    else {
		    	
		    	reportStep("Click on 1st PDF Link for the fund in 1st row 3rd Column", "INFO");
				click(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[3]//a").get(0));
				
				tabs = new ArrayList<String> (driver.getWindowHandles());
				   driver.switchTo().window(tabs.get(1));
				
				   verifyTwoStringsPartially(driver.getCurrentUrl(), "Distribution-Tax");
				   
				   driver.close();
				    driver.switchTo().window(tabs.get(0));	
		    }
		  
		    reportStep("Changing the year in Dropdown","INFO");
		    new Select(locateElement("xpath","//div[@class='form-group']/label[text()='Jahr']/following-sibling::select")).selectByIndex(1);
		    
		    reportStep("Validate Select Multiple PDF option is 'off' by default ","INFO");
		    verifyPartialAttribute(locateElements("xpath","//div[contains(@class,'ft-btn-toggle-on-off')]//input/..").get(1),"class","active");
		    
		    reportStep("Enable the Select Multiple PDFs Option","INFO");
		    click(locateElements("xpath","//div[contains(@class,'ft-btn-toggle-on-off')]//input/..").get(0));
		    
		    Thread.sleep(3000);
		    reportStep("Select Multiple PDFs to test EMail & Download functionality, Selecting 2 PDFs","INFO");
			click(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[2]//div[@class='media ft-document-icon']").get(0));
			click(locateElements("xpath","//div[@class='ft-table-responsive-flow']//tbody//tr[1]//td[2]//div[@class='media ft-document-icon']").get(1),true);
			
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
			 
			 return this;
	}
	
	
	
	
}

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

public class PressReleasesPage extends ProjectMethods{
	
	public static String tid;
	public PressReleasesPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {	
		try {			
		
			this.driver = driver;
			this.test = test;
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));			
			
			//System.out.println("Title: " +getTitle());
			//System.out.println(localeProp.getProperty("lblDefinedBenefitPlanPageTitle"));
				if(!getTitle().equals(localeProp.getProperty("lblPressReleasesPageTitle"))) 				
					reportStep("Funds Page Title may not displayed as expected. Refer snap", "FAIL");	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public PressReleasesPage verifyPageBreadCrumb()
	{
		captureFullScreen("Capturing screenshot of full Press Releases Page");
		mouseOver(locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBreadCrumb("Press Releases", getLocalePropertyValue("lblPressReleasesPageBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		return this;
	}
	

	public PressReleasesPage verifyPressReleasesPageLayout() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
		
		reportStep("Validate Headers of Press Releases  table", "INFO");	
		List<WebElement> headers =  locateElements("xpath", "//table//thead//th");
		verifyExactText(headers.get(0),getLocalePropertyValue("lblPressReleasesTableCol1"),"header");
		verifyExactText(headers.get(1),getLocalePropertyValue("lblPressReleasesTableCol2"),"header");
		
		reportStep("Validate format of Date field in first row ", "INFO");	
		
		if(dateFunctions.VerifyDateFormat(locateElement("xpath", "//table//tbody/tr/td[2]").getText(),getLocalePropertyValue("lblDateFormat")))
			reportStep("Date format is displayed as expected :"+locateElement("xpath", "//table//tbody/tr/td[2]").getText(), "PASS");	
		else
			reportStep("Date format is displayed as expected :"+locateElement("xpath", "//table//tbody/tr/td[2]").getText(), "FAIL");
		return this;
	}

	public PressReleasesPage verifyUKProfilePageOurCompanySection() throws InterruptedException
	{
		reportStep("Validate Layout of Our Company Section", "INFO");
		
		List <WebElement> lnks=locateElements("xpath",getLocalePropertyValue("objMediaContactsRightLinks"));	
		verifyExactText(lnks.get(0), getLocalePropertyValue("lblMediaContactRightlink1"), "link",false);
		verifyExactText(lnks.get(1), getLocalePropertyValue("lblMediaContactRightlink2"), "link",false);
		verifyExactText(lnks.get(2), getLocalePropertyValue("lblMediaContactRightlink3"), "link",false);
		verifyExactText(lnks.get(3), getLocalePropertyValue("lblMediaContactRightlink4"), "link",false);
		

		return this;
	}
	
	public PressReleasesPage verifyPressReleasesPageLayoutGerman() throws InterruptedException
	{
		verifyElementExist("xpath", getLocalePropertyValue("objHitoryPageSharelink"),"Share link");
	
		verifyExactText(locateElement("xpath","//h1"),"PRESSE","Page header");
		
		reportStep("Validate 3 tabs are displayed in Presse Page", "INFO");	
		
		List<WebElement> tabs = locateElements("xpath", "//ul[@class='nav nav-tabs']//li");
		
		verifyExactText(tabs.get(0), "Pressekontakt", "tab",true);
		verifyExactText(tabs.get(1), "Pressemitteilungen", "tab",false);
		verifyExactText(tabs.get(2), "Aus der Presse", "tab",false);
		
		reportStep("Validate Pressekontakt(Press Contact) tab", "INFO");	
		click(tabs.get(0));
		verifyElementExist("xpath", "//p[text()='Wenn Sie weitere Informationen wünschen oder Fragen und Anregungen haben, kontaktieren Sie bitte unsere Presse-Ansprechpartner.']", "Press Contact Tab Text");
		
		List<WebElement> tables = locateElements("xpath","//table");
		
		WebElement pressContactTable = tables.get(0);
		
		verifyExactText(pressContactTable.findElements(By.tagName("th")).get(0), "NAME", "Column",false);
		verifyExactText(pressContactTable.findElements(By.tagName("th")).get(1), "TITEL", "Column",false);
		verifyExactText(pressContactTable.findElements(By.tagName("th")).get(2), "TELEFON", "Column",false);
		verifyExactText(pressContactTable.findElements(By.tagName("th")).get(3), "E-MAIL", "Column",false);
		
		tables = locateElements("xpath","//table//tbody");
		pressContactTable = tables.get(0);
		reportStep("Validate Pressekontakt(Press Contact) tab - Table Information - 1st Row", "INFO");	
		
		verifyExactText(pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(0), "Oliver Trenk", "Row 1 - Name Column value",false);
		verifyExactText(pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(1), "Senior Corporate Communications Manager Central Europe", "Row 1 - Titel Column value",false);
		verifyExactText(pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(2), "Tel: +49 (0) 69 27223-718", "Row 1 - Telefon Column value",false);
		verifyExactText(pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(3), "presse@franklintempleton.de", "Row 1 - EMail Column value",false);
		
		reportStep("Validate Pressekontakt(Press Contact) tab - Table Information - 1st Row", "INFO");
		
		verifyExactText(pressContactTable.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(0), "Sabine Krause", "Row 1 - Name Column value",false);
		verifyExactText(pressContactTable.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(1), "Marketing Executive", "Row 1 - Titel Column value",false);
		verifyExactText(pressContactTable.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2), "Tel: +49 (0) 69 27223-239", "Row 1 - Telefon Column value",false);
		verifyExactText(pressContactTable.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(3), "presse@franklintempleton.de", "Row 1 - EMail Column value",false);
		
		reportStep("Validate Pressemitteilungen( Press Release) tab", "INFO");	
		click(tabs.get(1));
		
		List<WebElement> drps = locateElements("xpath","//select");
		verifyExactText(new Select(drps.get(0)).getFirstSelectedOption(), "Datum: Alles","Default Option(Dropdown)",true);
		
		tables = locateElements("xpath","//table");
		
		pressContactTable = tables.get(1);
		
		verifyExactText(pressContactTable.findElements(By.tagName("th")).get(0), "DATUM", "Column",false);
		verifyExactText(pressContactTable.findElements(By.tagName("th")).get(1), "TITEL", "Column",false);
		
		tables = locateElements("xpath","//table//tbody");
		pressContactTable = tables.get(1);
		
		reportStep("Validate Pressemitteilungen( Press Release) tab - Table Information - 1st Row", "INFO");
		validateFormat(pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(0), "[0-3]\\d.\\d{2}.\\d{4}", "Date Format");
		String articleTitle =  pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(1).getText();
		click( pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(1));
		verifyExactText(locateElement("xpath","//h1"), articleTitle.toUpperCase(),"articleTitle",true);
		driver.navigate().back();
		
		reportStep("Validate press tab ( Aus der Presse)", "INFO");	
		tabs = locateElements("xpath", "//ul[@class='nav nav-tabs']//li");
		click(tabs.get(2));
		
		drps = locateElements("xpath","//select");
		verifyExactText(new Select(drps.get(1)).getFirstSelectedOption(), "Datum: Alles","Default Option(Dropdown)",true);
		
		tables = locateElements("xpath","//table");
		
		pressContactTable = tables.get(2);
		
		verifyExactText(pressContactTable.findElements(By.tagName("th")).get(0), "DATUM", "Column",false);
		verifyExactText(pressContactTable.findElements(By.tagName("th")).get(1), "TITEL", "Column",false);
		
		tables = locateElements("xpath","//table//tbody");
		pressContactTable = tables.get(2);
		
		reportStep("Validate press tab ( Aus der Presse) - Table Information - 1st Row", "INFO");
		validateFormat(pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(0), "[0-3]\\d.\\d{2}.\\d{4}", "Date Format");
		articleTitle =  pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(1).getText();
		click( pressContactTable.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(1).findElement( By.tagName("a")));
		
		verifyExactText(locateElement("xpath","//h1"), articleTitle.toUpperCase(),"articleTitle",true);
		driver.navigate().back();
		
		Thread.sleep(5000);
		reportStep("Select any previous Year : '2018' in this case from dropdown","INFO");
		drps = locateElements("xpath","//select");
		new Select(drps.get(1)).selectByVisibleText("2018");
		
		tables = locateElements("xpath","//table//tbody");
		pressContactTable = tables.get(2);
		reportStep("validating all the rows have year '2018' in date column","INFO");
		for(int i=0;i<pressContactTable.findElements(By.xpath(".//tr[@role='row']")).size();i++) {
		
			verifyTwoStringsPartially(pressContactTable.findElements(By.xpath(".//tr[@role='row']")).get(i).findElements(By.tagName("td")).get(0).getText(), "2018",true);
		
		}
		return this;
	}
	
}

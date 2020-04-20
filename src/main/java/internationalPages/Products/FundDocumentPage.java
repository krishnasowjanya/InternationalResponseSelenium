package internationalPages.Products;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.common.base.CaseFormat;
import com.relevantcodes.extentreports.ExtentTest;

import utils.ExcelDataProvider;
import utils.dateFunctions;
import utils.xmlDataProvider;
import wdMethods.ProjectMethods;


public class FundDocumentPage extends ProjectMethods{	
	
	public String pageTitle,fund,FundNumber,FundURL,InvestmentVehicle;
		
	public FundDocumentPage(RemoteWebDriver driver, ExtentTest test,String fundName) throws InterruptedException, FileNotFoundException, IOException {
		this.driver = driver;
		this.test = test;
		
		//Loading Locale File
		localeProp.load(new FileInputStream(new File(sLocaleFile)));
		
		fund=fundName;
		Thread.sleep(2000);	
		FundURL=driver.getCurrentUrl();
		pageTitle=localeProp.getProperty("lblFDTitle");
		System.out.println("Page Title: " + pageTitle);
		System.out.println("Page Heading: " + localeProp.getProperty("objFDPageHeading"));
		reportStep("Checking Fund Document Page Title","INFO");	
//		if(!verifyTitle(pageTitle + " - " + fund)) {
//			reportStep("This is NOT '" + pageTitle + "'  Page. Refer snap", "FAIL");
//		}
		//Changed on 3-26-2018 - due to change in page titles 
		if(!(verifyTitle(pageTitle)&& verifyTitle(fund))) {
			reportStep("This is NOT '" + pageTitle + "'  Page. Refer snap", "FAIL");
		}
		
		// Initialize the webelements inside the page
		PageFactory.initElements(driver, this);
	}
	
	public FundDocumentPage VerifyHeader()
	{	
		reportStep("Verifying Fund Document Page Heading","INFO");
		WebElement eleDocumentsPageHeading;
		if(locateElements("xpath", localeProp.getProperty("objFDPageHeading")).size()>1)  
			 eleDocumentsPageHeading=locateElements("xpath", localeProp.getProperty("objFDPageHeading")).get(1);
		else
			 eleDocumentsPageHeading=locateElement("xpath", localeProp.getProperty("objFDPageHeading"));
		verifyExactText(eleDocumentsPageHeading, localeProp.getProperty("lblFDPageHeader"));		
		return this;
	}
	
	public FundDocumentPage VerifyFndDocumentSectionHeaders()
	{	
		reportStep("Verifying Fund Document Page Section Headings","INFO");
/*		System.out.println("Size: "+ locateElements("xpath", localeProp.getProperty("objFDFndLiteratreHeading")).size());
		WebElement eleFndLiteratreHeading=locateElement("xpath", localeProp.getProperty("objFDFndLiteratreHeading"));
		WebElement eleFndDocumentsHeading=locateElement("xpath", localeProp.getProperty("objFDFndDocumentsHeading"));
		WebElement eleAdditionalResourcesHeading=locateElement("xpath", localeProp.getProperty("objFDAdditionalResourcesHeading"));*/
		
		if(locateElements("xpath", localeProp.getProperty("objFDFndLiteratreHeading")).size()>0)
			verifyExactText(locateElement("xpath", localeProp.getProperty("objFDFndLiteratreHeading")), localeProp.getProperty("lblFDFndLiteratreHeading"));
		if(locateElements("xpath", localeProp.getProperty("objFDFndDocumentsHeading")).size()>0)
			verifyExactText(locateElement("xpath", localeProp.getProperty("objFDFndDocumentsHeading")), localeProp.getProperty("lblFDFndDocumentsHeading"));
		if(locateElements("xpath", localeProp.getProperty("objFDFndDocumentsHeading")).size()>0)
			verifyExactText(locateElement("xpath", localeProp.getProperty("objFDAdditionalResourcesHeading")), localeProp.getProperty("lblFDAdditionalResourcesHeading"));
		
		return this;
	}
	
	
	
	public FundDocumentPage verifyDownloads() throws InterruptedException	
	{			/*if(!sCountryName.equals("German")) {
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDProspectus"),localeProp.getProperty("lblFDExpStringInProspectus"));
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDAnnaulReportFull"),localeProp.getProperty("lblFDExpStringInAnnualReportFull"));
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDAnnaulReportAbridged"),localeProp.getProperty("lblFDExpStringInAnnualReportAbridged"));
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDSemiAnnualReportAbridged"),localeProp.getProperty("lblFDExpStringInSemiAnnualReportAbridged"));
		
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDSemiAnnualReportFull"),localeProp.getProperty("lblFDExpStringInSemiAnnualReportFull"));
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDConsolidatedArticlesOfIncorporation"),localeProp.getProperty("lblFDExpStringInConArticlesOfIncorp"));
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDFactsheets"),localeProp.getProperty("lblFDExpStringInFactsheets"));		
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDKIIDUSD"),localeProp.getProperty("lblFDExpStringDIIDUSD"));
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDPortfolioHoldings"),localeProp.getProperty("lblFDExpStringInPortfolioHoldings"));
		
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDAnnualReport"),localeProp.getProperty("lblFDExpStringInAnnualReport"));
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDSemiAnnualReport"),localeProp.getProperty("lblFDExpStringInSemiAnnualReport"));		
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDKIID"),localeProp.getProperty("lblFDExpStringInKIID"));		
		clickAndVerifyDocument("Link", localeProp.getProperty("lblFDApplicationForms"),localeProp.getProperty("lblFDExpStringInApplicationForms"));
		//clickAndVerifyDocument("Link", localeProp.getProperty("lblFDLiteraturePage"),localeProp.getProperty("lblFDExpStringInLiteraturePage"));		
 }
	else {*/
		clickAndVerifyDocument("PDF", localeProp.getProperty("lblFDFundDocumentFirstHeading"),localeProp.getProperty("lblFDExpStringInFirstHeading"));
	//}
		return this;
	}
	public void clickAndVerifyDocument(String Type,String label,String stringToCheck) throws InterruptedException
	{
		String url,title;
		//boolean IsDocAvailable=false;
		boolean IsExpectedDoc=false;
		
		List<WebElement> eleDownloadDocTitle=locateElements("xpath", localeProp.getProperty("objFDDownloadDocTitle"));
		List<WebElement> eleDownloadDocAction=locateElements("xpath", localeProp.getProperty("objFDDownloadDocAction"));
		
		for (int i = 0; i < eleDownloadDocTitle.size(); i++) {
			
		    if(eleDownloadDocTitle.get(i).getText().contains(label))
		    {
		    	//IsDocAvailable=true;
				click(eleDownloadDocAction.get(i));
				Thread.sleep(2000);		
			    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			    System.out.println("tabs size:"+tabs.size());
			    driver.switchTo().window(tabs.get(1));
			    waitForLoad(driver);			    		    
			    
			
			    
			   	url=driver.getCurrentUrl();
			   	title=driver.getTitle();

			   	if (Type.equals("Link") && stringToCheck!="") {   			
			   		
			   		if(title.contains(stringToCheck))
			   		IsExpectedDoc=true;}
			   		
			   	if (Type.equals("PDF") && stringToCheck!="" && url.contains(stringToCheck) && url.contains(".pdf") && locateElements("id",getLocalePropertyValue("objFDPDFDocumentHTML")).size()>0)
			   	{				   		
				   	if(locateElement(getLocalePropertyValue("objFDPDFDocumentHTML")).getAttribute("type").contains("application/pdf"))
				   		IsExpectedDoc=true;}
			   	
			   	System.out.println("IsExpe Doc:" + IsExpectedDoc);
			   	if(IsExpectedDoc)
		    		reportStep("PDF download/Reference Page is available for '" + label + "'", "PASS",true);
		    	else
		    		reportStep("PDF download/Reference Page may not available for '" + label + "'", "FAIL",true); 
		    	
			    driver.close();
			    driver.switchTo().window(tabs.get(0));	
			    Thread.sleep(5000);
			    break;
		    }
			
		}		
/*		if(!IsDocAvailable)
			reportStep("No Such Document '" + label + "' available for this Fund", "FAIL");*/
	}
	
	public FundDocumentPage clickFundDocumentTab()
	{	
		WebElement eleFundDocumentTab=locateElement("link", localeProp.getProperty("objFDDocumentTab"));
		click(eleFundDocumentTab);		
		return this;
	}
	
    public void waitForLoad(WebDriver driver) throws InterruptedException {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
        Thread.sleep(2000);
    }
}

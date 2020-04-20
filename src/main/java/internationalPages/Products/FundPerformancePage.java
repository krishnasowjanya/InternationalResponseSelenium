package internationalPages.Products;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.omg.PortableServer.RequestProcessingPolicyOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.common.base.CaseFormat;
import com.relevantcodes.extentreports.ExtentTest;

import utils.ExcelDataProvider;
import utils.WebTableFunctions;
import utils.commonMethods;
import utils.dateFunctions;
import utils.xmlDataProvider;
import wdMethods.ProjectMethods;


public class FundPerformancePage extends ProjectMethods{	
	
	public String pageTitle,fund,FundNumber,FundURL,InvestmentVehicle;
	public commonMethods cm=new commonMethods();
		
	public FundPerformancePage(RemoteWebDriver driver, ExtentTest test,String fundName) throws InterruptedException, FileNotFoundException, IOException {
		this.driver = driver;
		this.test = test;
		
		//Loading Locale File
		localeProp.load(new FileInputStream(new File(sLocaleFile)));
		
		fund=fundName;
		Thread.sleep(2000);	
		FundURL=driver.getCurrentUrl();
		pageTitle=localeProp.getProperty("lblPEPerformancePageTitle");
		//System.out.println("Page Title: " + pageTitle);
		//System.out.println("Page Heading: " + localeProp.getProperty("objFDPageHeading"));
		reportStep("Checking Performance Page Title","INFO");	
//		if(!verifyTitle(pageTitle + " - " + fund)) {
//			reportStep("This is NOT '" + pageTitle + "'  Page. Refer snap", "FAIL");
//		}
		//commented above code, Changed on 3-26-2018 - due to change in page titles from latest code push
				//new code below
		if(!(verifyTitle(pageTitle) &&  verifyTitle(fund))) {
		reportStep("This is NOT '" + pageTitle + "'  Page. Refer snap", "FAIL");
		}		
		
		// Initialize the webelements inside the page
		PageFactory.initElements(driver, this);
	}
	
	
	public FundPerformancePage clickCumulativePerformanceTab()
	{
		clickWithNoSnap(locateElement("link",getLocalePropertyValue("objPECumulativePerformanceTab")));
		WebDriverWait wait=new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objPECumulativePerformanceMonthlyTable"))));
		captureFullScreen("Capturing Full screen of Performance - Cumulative Performance page");
		return this;
	}
	public FundPerformancePage clickAnnualisedPerformanceTab()
	{
		clickWithNoSnap(locateElement("link",getLocalePropertyValue("objPEAnnualisedPerformanceTab")));
		WebDriverWait wait=new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable"))));
		captureFullScreen("Capturing Full screen of Performance - Annualised Performance page");
		return this;
	}
	public FundPerformancePage clickCalendarYearPerformanceTab()
	{
		clickWithNoSnap(locateElement("link",getLocalePropertyValue("objPEcalendarYearPerformanceTab")));
		WebDriverWait wait=new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objPECalendarYearPerformanceMonthlyTable"))));
		captureFullScreen("Capturing Full screen of Performance - Calendar Year Performance page");
		return this;
	}
	public FundPerformancePage clickDiscreteAnnualPerformanceTab()
	{
		clickWithNoSnap(locateElement("link",getLocalePropertyValue("objPEDiscreteAnnualPerformanceTab")));
		WebDriverWait wait=new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objPEDiscreteAnnualPerformanceMonthlyTable"))));
		captureFullScreen("Capturing Full screen of Performance - Discrete Annual Performance page");
		return this;
	}
	public FundPerformancePage verifyCumulativePerformanceHeadingAndDate()
	{	
		if(sCountryName.equals("Canada"))
			verifyExactText(locateElement("xpath", localeProp.getProperty("objPECumulativePerformance10KHeading")), getLocalePropertyValue("lblPECumulativePerformance10KHeading"));	
		
		WebElement eleHeading=locateElement("xpath", localeProp.getProperty("objPECumulativePerformanceHeading"));
		checkHeadingTitleAndDateFormat(eleHeading, localeProp.getProperty("lblPECumulativePerformanceHeading"));		
		return this;
	}	
	
	public FundPerformancePage verifyAnnualisedPerformanceHeadingAndDate()
	{	
		WebElement eleHeading=locateElement("xpath", localeProp.getProperty("objPEAnnualisedPerformanceHeading"));
		checkHeadingTitleAndDateFormat(eleHeading, localeProp.getProperty("lblPEAnnualisedPerformanceHeading"));		
		return this;
	}
	public FundPerformancePage verifyCalendarYearPerformanceHeadingAndDate()
	{	
		WebElement eleHeading=locateElement("xpath", localeProp.getProperty("objPECalendarYearPerformanceHeading"));
		checkHeadingTitleAndDateFormat(eleHeading, localeProp.getProperty("lblPECalendarYearPerformanceHeading"));		
		return this;
	}	
	public FundPerformancePage verifyDiscreteAnnaulPerformanceHeadingAndDate()
	{	
		WebElement eleHeading=locateElement("xpath", localeProp.getProperty("objPEDiscreteAnnualPerformanceHeading"));
		checkHeadingTitleAndDateFormat(eleHeading, localeProp.getProperty("lblPEDiscretePerformanceHeading"));		
		return this;
	}
	
	public FundPerformancePage clickCumulativePerformanceTimeToggleMonthEnd()
	{
		click(locateElement("xpath", getLocalePropertyValue("objPECumPerfMonthEndLink")));
		return this;
	}	
	public FundPerformancePage clickCumulativePerformanceTimeToggleQuarterEnd()
	{
		click(locateElement("xpath", getLocalePropertyValue("objPECumPerfQuarterEndLink")));
		return this;
	}
	
	public FundPerformancePage clickAnnualisedPerformanceTimeToggleMonthEnd()
	{
		click(locateElement("xpath", getLocalePropertyValue("objPEAnnualisedPerfMonthEndLink")));
		return this;
	}
	public FundPerformancePage clickAnnualisedPerformanceTimeToggleQuarterEnd()
	{
		click(locateElement("xpath", getLocalePropertyValue("objPEAnnualisedPerfQuarterEndLink")));
		return this;
	}
	public FundPerformancePage clickDiscreteAnnualPerformanceTimeToggleMonthEnd()
	{
		click(locateElement("xpath", getLocalePropertyValue("objPEDescreteAnnualPerfMonthEndLink")));
		return this;
	}
	public FundPerformancePage clickDiscreteAnnualPerformanceTimeToggleQuarterEnd()
	{
		click(locateElement("xpath", getLocalePropertyValue("objPEDescreteAnnualPerfQuarterEndLink")));
		return this;
	}
	public FundPerformancePage clickCalendarYearPerformanceTimeToggleMonthEnd()
	{
		click(locateElement("xpath", getLocalePropertyValue("objPEDescreteAnnualPerfMonthEndLink")));
		return this;
	}
	public FundPerformancePage clickCalendarYearPerformanceTimeToggleQuarterEnd()
	{
		click(locateElement("xpath", getLocalePropertyValue("objPEDescreteAnnualPerfQuarterEndLink")));
		return this;
	}
	public FundPerformancePage verifyCumulativePerformanceTableAgainstTimeToggle(String TimeToggle)
	{		
		verifyPerformanceTimeToggleSelected(TimeToggle, locateElement(getLocalePropertyValue("objPECumulativePerformanceMonthlyTable")), "Cumulative Performance Table");
		return this;
	}
	public FundPerformancePage verifyAnnualisedPerformanceTableAgainstTimeToggle(String TimeToggle)
	{	
		verifyPerformanceTimeToggleSelected(TimeToggle, locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceMonthlyTable")), "Annualised Performance Table");
		return this;
	}
	public FundPerformancePage verifyDiscreteAnnaulPerformanceTableAgainstTimeToggle(String TimeToggle)
	{	
		verifyPerformanceTimeToggleSelected(TimeToggle, locateElement(getLocalePropertyValue("objPEDiscreteAnnualPerformanceMonthlyTable")), "Discrete Annual Performance Table");
		return this;
	}
	public FundPerformancePage verifyCumulativePerformanceViewAllShareClassTableAgainstTimeToggle(String TimeToggle)
	{	
		verifyPerformanceTimeToggleSelected(TimeToggle, locateElement(getLocalePropertyValue("objPECumulativePerformanceViewAllShaerClassTable")), "View Performance All ShareClass");
		return this;
	}
	public FundPerformancePage verifyCalendarYearPerformanceViewAllShareClassTableAgainstTimeToggle(String TimeToggle)
	{	
		verifyPerformanceTimeToggleSelected(TimeToggle, locateElement("xpath", getLocalePropertyValue("objPECalendarYearPerformanceViewAllShaerClassTable")), "View Performance All ShareClass");
		return this;
	}
	public FundPerformancePage verifyDiscreteAnnualPerformanceViewAllShareClassTableAgainstTimeToggle(String TimeToggle)
	{	
		verifyPerformanceTimeToggleSelected(TimeToggle, locateElement("xpath", getLocalePropertyValue("objPEDiscreteAnnualPerformanceViewAllShaerClassTable")), "View Performance All ShareClass");
		return this;
	}
	public FundPerformancePage verifyAnnualisedPerformanceViewAllShareClassTableAgainstTimeToggle(String TimeToggle)
	{	
		verifyPerformanceTimeToggleSelected(TimeToggle, locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceViewAllShaerClassTable")), "View Performance All ShareClass");
		return this;
	}
	
	public void verifyPerformanceTimeToggleSelected(String TimeToggle,WebElement tableObj,String tableName)	
	{
		reportStep("Verifying " +  tableName +" data against Time Toggle - " + TimeToggle,"INFO");		
		//mouseOver(locateElement(localeProp.getProperty(tableObj)));	
		mouseOver(tableObj);			
		//List<WebElement> elePerTableRows=locateElement(localeProp.getProperty(tableObj)).findElements(By.tagName("tr"));
		List<WebElement> elePerTableRows=tableObj.findElements(By.tagName("tr"));
		String expString;
		if(TimeToggle.equals("MonthEnd"))
			expString=getLocalePropertyValue("lblPEMonthEnd");
		else
			expString=getLocalePropertyValue("lblPEQuarterEnd");
		//System.out.println(expString);
		//System.out.println(elePerTableRows.get(0).findElements(By.tagName("th")).get(0).getText());
		if(elePerTableRows.get(0).findElements(By.tagName("th")).get(0).getText().contains(expString))
			reportStep(tableName +" updated according to Time Toggle - " + TimeToggle + " Selected", "PASS");
		else
			reportStep(tableName +" may not updated according to Time Toggle - " + TimeToggle + " Selected", "FAIL");
	}
	
	public FundPerformancePage verifyCumulativePerformanceTableMonthEndHeadings()
	{	
		if(sCountryName.equals("Canada"))
			verifyCummulativePerformanceTableHeadingCanada("Cumulative Performance Table","MonthEnd","CummulativePerformance");
		else
			verifyCummulativePerformanceTableHeading("Cumulative Performance Table","MonthEnd","CummulativePerformance");
		//verifyCummulativePerformanceTableHeading("Cumulative Performance Table","MonthEnd","PerformanceViewAllShareClass");
		return this;
	}
	public FundPerformancePage verifyCumulativePerformanceTableMonthEndData(HashMap<String,String> data) throws InterruptedException
	{	
		if(sCountryName.equals("Canada"))
			verifyCummulativePerformanceTableDataCanada(data,"Cumulative Performance Table", "MonthEnd","CummulativePerformance");	
		else
			verifyCummulativePerformanceTableData(data,"Cumulative Performance Table", "MonthEnd","CummulativePerformance");	
		return this;
	}
	public FundPerformancePage verifyCumulativePerformanceViewAllShareClassMonthEndHeadings()
	{	if(sCountryName.equals("Canada"))
		verifyCummulativePerformanceTableHeadingCanada("Cumulative Performance Table - View All Shareclass","MonthEnd","PerformanceViewAllShareClass");
	else
		verifyCummulativePerformanceTableHeading("Cumulative Performance Table - View All Shareclass","MonthEnd","PerformanceViewAllShareClass");		
		return this;
	}
	public FundPerformancePage verifyCumulativePerformanceViewAllShareClassMonthEndData(HashMap<String,String> data) throws InterruptedException
	{	if(sCountryName.equals("Canada"))
		verifyCummulativePerformanceTableDataCanada(data,"Cumulative Performance Table- View All Shareclass", "MonthEnd","PerformanceViewAllShareClass");
	else
		verifyCummulativePerformanceTableData(data,"Cumulative Performance Table- View All Shareclass", "MonthEnd","PerformanceViewAllShareClass");			
		return this;
	}
	public FundPerformancePage verifyCumulativePerformanceTableQuarterEndHeadings()
	{	
		if(sCountryName.equals("Canada"))
			verifyCummulativePerformanceTableHeadingCanada("Cumulative Performance Table","QuarterEnd","CummulativePerformance");
		else
			verifyCummulativePerformanceTableHeading("Cumulative Performance Table","QuarterEnd","CummulativePerformance");	
		return this;
	}
	public FundPerformancePage verifyCumulativePerformanceTableQuarterEndData(HashMap<String,String> data) throws InterruptedException
	{	
		if(sCountryName.equals("Canada"))
			verifyCummulativePerformanceTableDataCanada(data,"Cumulative Performance Table", "QuarterEnd","CummulativePerformance"); 
		else
			verifyCummulativePerformanceTableData(data,"Cumulative Performance Table", "QuarterEnd","CummulativePerformance"); 
		return this;
	}

	public FundPerformancePage verifyCumulativePerformanceViewAllShareClassQuarterEndHeadings()
	{	if(sCountryName.equals("Canada"))
		verifyCummulativePerformanceTableHeadingCanada("Cumulative Performance Table - View All Shareclass","QuarterEnd","PerformanceViewAllShareClass");
	else
		verifyCummulativePerformanceTableHeading("Cumulative Performance Table - View All Shareclass","QuarterEnd","PerformanceViewAllShareClass");		
		return this;
	}
	public FundPerformancePage verifyCumulativePerformanceViewAllShareClassQuarterEndData(HashMap<String,String> data) throws InterruptedException
	{	
		if(sCountryName.equals("Canada"))
			verifyCummulativePerformanceTableDataCanada(data,"Cumulative Performance Table - View All Shareclass", "QuarterEnd","PerformanceViewAllShareClass");
		else
		verifyCummulativePerformanceTableData(data,"Cumulative Performance Table - View All Shareclass", "QuarterEnd","PerformanceViewAllShareClass");			
		return this;
	}
	
	
	public FundPerformancePage clickCumulativeViewPerformanceForAllShareclassLink()
	{
		
			click(locateElement("xpath", getLocalePropertyValue("objPECumlativeViewPerformanceForAllShareClassLink")));
		
		
		return this;
	}
	public void verifyCummulativePerformanceTableHeading(String componentName,String TimeToggle,String componentType)
	{
		List<WebElement> elePerTableRows;
		reportStep("Verifying " + componentName + "( " + TimeToggle + ") Headings","INFO");
		if(componentType.equals("PerformanceViewAllShareClass"))			
			elePerTableRows=locateElement(localeProp.getProperty("objPECumulativePerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
		else
			elePerTableRows=locateElement(localeProp.getProperty("objPECumulativePerformanceMonthlyTable")).findElements(By.tagName("tr"));
		
		List<WebElement> eleHeadings=elePerTableRows.get(0).findElements(By.tagName("th"));
		if(!(sCountryName.equals("German") || sCountryName.equals("Austria")) ) {
		boolean isValid=true;
		String[] str= {"lblFundNumber","lblCurrency","lblYTD","lbl1Mth","lbl3MTHS","lbl6MTHS","lbl1YR","lbl3YRS","lbl5YRS","lbl10YRS","lbl15YRS"};
		String expLbl="";
		for (int i = 0; i<str.length ; i++) {		
			
			//System.out.println(eleHeadings.get(i+1).getText());
			expLbl=expLbl +", " + getLocalePropertyValue(str[i]);
			if(!eleHeadings.get(i+1).getText().equals(getLocalePropertyValue(str[i]))) {					
				System.out.println("FAIL: " + eleHeadings.get(i+1).getText() + " , " + getLocalePropertyValue(str[i]));
				reportStep("Heading " + eleHeadings.get(i+1).getText() + " not matching with expected: " + getLocalePropertyValue(str[i]), "FALSE");
				isValid=false;	
				break;}
					
		}	
		if(isValid)
			reportStep("Headings are displayed as Expected - " + expLbl, "PASS");
		else
			reportStep("One or More Headings may not displayed as Expected - "+ expLbl, "FAIL");
		
		WebElement MonthEndEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(0);
		if(TimeToggle.equals("MonthEnd"))
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEMonthEnd"));
		else
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEQuarterEnd"));
		
		//System.out.println("Count" + elePerTableRows.get(0).findElements(By.tagName("th")).size());
		WebElement SinceInceptionEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(12);		
		//System.out.println(SinceInceptionEle.getText());	
		reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " Heading","INFO");
		if(eleHeadings.get(12).getText().contains(getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception")))
			reportStep("'Since Performance Inception' Heading displayed", "PASS");
		else
			reportStep("Heading Expected: " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " but Actual is: " + eleHeadings.get(12).getText(), "FAIL");
		
		if (componentType.equals("PerformanceViewAllShareClass"))
		{	
			
			//WebElement SinceInceptionDateEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(13);		
			System.out.println(eleHeadings.get(13).getText());	
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingPerformanceInceptionDateColumn") + " Heading","INFO");
			if(eleHeadings.get(13).getText().contains(getLocalePropertyValue("lblPECumPerHeadingPerformanceInceptionDateColumn")))
				reportStep("'Performance Inception Date' Heading displayed", "PASS");
			else
				reportStep("'Performance Inception Date'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECumPerHeadingPerformanceInceptionDateColumn"), "FAIL");
			
			//WebElement fundOverviewele=elePerTableRows.get(0).findElements(By.tagName("th")).get(14);		
			System.out.println(eleHeadings.get(14).getText());	
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingFundOverview") + " Heading","INFO");
			if(eleHeadings.get(14).getText().contains(getLocalePropertyValue("lblPECumPerHeadingFundOverview")))
				reportStep("'Fund Overview' Heading displayed", "PASS");
			else
				reportStep("'Fund Overview'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECumPerHeadingFundOverview"), "FAIL");
			
		}else
		{
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " Heading Date Format","INFO");
			WebElement eledate=null;
			if (SinceInceptionEle.findElement(By.tagName("small")).findElements(By.tagName("ft-label")).size()>0)
					eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("ft-label"));
				else
					eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("span"));
			if(dateFunctions.VerifyDateFormat(eledate.getText(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Date section " + eledate.getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
			else
				reportStep("Date section " + eledate.getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		}
		
		}	
		
		else {
			boolean isValid=true;
			String[] str= {"lblFundNumber","lblCurrency","lbl1YR","lbl3YRS","lbl5YRS","lbl10YRS","lbl15YRS"};
			String expLbl="";
			for (int i = 0; i<str.length ; i++) {		
				
				//System.out.println(eleHeadings.get(i+1).getText());
				expLbl=expLbl +", " + getLocalePropertyValue(str[i]);
				if(!eleHeadings.get(i+1).getText().equals(getLocalePropertyValue(str[i]))) {					
					System.out.println("FAIL: " + eleHeadings.get(i+1).getText() + " , " + getLocalePropertyValue(str[i]));
					reportStep("Heading " + eleHeadings.get(i+1).getText() + " not matching with expected: " + getLocalePropertyValue(str[i]), "FALSE");
					isValid=false;	
					break;}
						
			}	
			if(isValid)
				reportStep("Headings are displayed as Expected - " + expLbl, "PASS");
			else
				reportStep("One or More Headings may not displayed as Expected - "+ expLbl, "FAIL");
			
			WebElement MonthEndEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(0);
			if(TimeToggle.equals("MonthEnd"))
				checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEMonthEnd"));
			else
				checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEQuarterEnd"));
			
			//System.out.println("Count" + elePerTableRows.get(0).findElements(By.tagName("th")).size());
			WebElement SinceInceptionEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(8);		
			//System.out.println(SinceInceptionEle.getText());	
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " Heading","INFO");
			if(eleHeadings.get(8).getText().contains(getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception")))
				reportStep("'Since Performance Inception' Heading displayed", "PASS");
			else
				reportStep("Heading Expected: " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " but Actual is: " + eleHeadings.get(8).getText(), "FAIL");
			
			if (componentType.equals("PerformanceViewAllShareClass"))
			{	
				
				//WebElement SinceInceptionDateEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(13);		
				System.out.println(eleHeadings.get(9).getText());	
				reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingPerformanceInceptionDateColumn") + " Heading","INFO");
				if(eleHeadings.get(9).getText().replace("\n", " ").contains(getLocalePropertyValue("lblPECumPerHeadingPerformanceInceptionDateColumn")))
					reportStep("'Performance Inception Date' Heading displayed", "PASS");
				else
					reportStep("'Performance Inception Date'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECumPerHeadingPerformanceInceptionDateColumn"), "FAIL");
				
				//WebElement fundOverviewele=elePerTableRows.get(0).findElements(By.tagName("th")).get(14);		
				System.out.println(eleHeadings.get(10).getText());	
				reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingFundOverview") + " Heading","INFO");
				if(eleHeadings.get(10).getText().replaceAll("\n", " ").contains(getLocalePropertyValue("lblPECumPerHeadingFundOverview")))
					reportStep("'Fund Overview' Heading displayed", "PASS");
				else
					reportStep("'Fund Overview'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECumPerHeadingFundOverview"), "FAIL");
				
			}else
			{
				reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " Heading Date Format","INFO");
				WebElement eledate=null;
				if (SinceInceptionEle.findElement(By.tagName("small")).findElements(By.tagName("ft-label")).size()>0)
						eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("ft-label"));
					else
						eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("span"));
					
				if(dateFunctions.VerifyDateFormat(eledate.getText(), getLocalePropertyValue("lblDateFormat")))
					reportStep("Date section " + eledate.getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
				else
					reportStep("Date section " + eledate.getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
			 }
		}
	}
	
	public void verifyCummulativePerformanceTableHeadingCanada(String componentName,String TimeToggle,String componentType)
	{
		List<WebElement> elePerTableRows;
		reportStep("Verifying " + componentName + "( " + TimeToggle + ") Headings","INFO");
		if(componentType.equals("PerformanceViewAllShareClass"))			
			elePerTableRows=locateElement(localeProp.getProperty("objPECumulativePerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
		else
			elePerTableRows=locateElement(localeProp.getProperty("objPECumulativePerformanceMonthlyTable")).findElements(By.tagName("tr"));
		
		List<WebElement> eleHeadings=elePerTableRows.get(0).findElements(By.tagName("th"));
		
		boolean isValid=true;
		String[] str= {"lblCurrency","lblYTD","lbl1Mth","lbl3MTHS","lbl6MTHS","lbl1YR","lbl3YRS","lbl5YRS","lbl10YRS","lbl15YRS"};
		String expLbl="";
		for (int i = 0; i<str.length ; i++) {		
			
			//System.out.println(eleHeadings.get(i+1).getText());
			expLbl=expLbl +", " + getLocalePropertyValue(str[i]);
			if(!eleHeadings.get(i+1).getText().equals(getLocalePropertyValue(str[i]))) {					
				System.out.println("FAIL: " + eleHeadings.get(i+1).getText() + " , " + getLocalePropertyValue(str[i]));
				reportStep("Heading " + eleHeadings.get(i+1).getText() + " not matching with expected: " + getLocalePropertyValue(str[i]), "FALSE");
				isValid=false;	
				break;}
					
		}	
		if(isValid)
			reportStep("Headings are displayed as Expected - " + expLbl, "PASS");
		else
			reportStep("One or More Headings may not displayed as Expected - "+ expLbl, "FAIL");
		
		WebElement MonthEndEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(0);
		if(TimeToggle.equals("MonthEnd"))
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEMonthEnd"));
		else
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEQuarterEnd"));
		
		//System.out.println("Count" + elePerTableRows.get(0).findElements(By.tagName("th")).size());
		WebElement SinceInceptionEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(11);		
		//System.out.println(SinceInceptionEle.getText());	
		reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " Heading","INFO");
		if(eleHeadings.get(11).getText().contains(getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception")))
			reportStep("'Since Performance Inception' Heading displayed", "PASS");
		else
			reportStep("Heading Expected: " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " but Actual is: " + eleHeadings.get(12).getText(), "FAIL");
		
		if (componentType.equals("PerformanceViewAllShareClass"))
		{	
			
			//WebElement SinceInceptionDateEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(13);		
			System.out.println(eleHeadings.get(12).getText());	
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingPerformanceInceptionDateColumn") + " Heading","INFO");
			if(eleHeadings.get(12).getText().contains(getLocalePropertyValue("lblPECumPerHeadingPerformanceInceptionDateColumn")))
				reportStep("'Performance Inception Date' Heading displayed", "PASS");
			else
				reportStep("'Performance Inception Date'  Heading may not be displayed as Expected: " + getLocalePropertyValue("lblPECumPerHeadingPerformanceInceptionDateColumn"), "FAIL");
			
			//WebElement fundOverviewele=elePerTableRows.get(0).findElements(By.tagName("th")).get(14);		
			System.out.println(eleHeadings.get(13).getText());	
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingFundOverview") + " Heading","INFO");
			if(eleHeadings.get(13).getText().contains(getLocalePropertyValue("lblPECumPerHeadingFundOverview")))
				reportStep("'Fund Overview' Heading displayed", "PASS");
			else
				reportStep("'Fund Overview'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECumPerHeadingFundOverview"), "FAIL");
			
		}else
		{
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " Heading Date Format","INFO");
			WebElement eledate=null;
			if (SinceInceptionEle.findElement(By.tagName("small")).findElements(By.tagName("ft-label")).size()>0)
					eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("ft-label"));
				else
					eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("span"));
			if(dateFunctions.VerifyDateFormat(eledate.getText(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Date section " + eledate.getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
			else
				reportStep("Date section " + eledate.getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		}
		
		
	
	}
	

	public void verifyCummulativePerformanceTableData(HashMap<String,String> data,String ComponentName,String TimeToggle,String componentType) throws InterruptedException
	{
		//reportStep("Verifying " + ComponentName + " - " + TimeToggle,"INFO");
		List<WebElement> elePerTableRows;
		String expShrClass;
		if (componentType.equals("PerformanceViewAllShareClass")) {
			
			elePerTableRows=locateElement(localeProp.getProperty("objPECumulativePerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));			
			reportStep("Verifying Share Class value for Cummulative - View Performance All Shareclass Table","INFO");
			expShrClass=data.get("ShareClass");	}	
	
		else {
			elePerTableRows=locateElement(localeProp.getProperty("objPECumulativePerformanceMonthlyTable")).findElements(By.tagName("tr"));
			reportStep("Verifying Share Class value for Cumulative Performance Table","INFO");
			expShrClass=data.get("ShareClass") + " " + data.get("Currency") + " (%)";		
		}		

		if(elePerTableRows.get(1).findElements(By.tagName("td")).get(0).getText().contains(expShrClass))
			reportStep("Expected Share Class " + expShrClass + " Displayed", "PASS");
		else
			reportStep("Expected Share Class " + expShrClass + "may not Displayed", "FAIL");
		
		reportStep("Verifying " + ComponentName + " - Fund Number","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(1), data.get("FundNumber"), "Fund Number");
		
		reportStep("Verifying " + ComponentName + " - Currency Value","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("Currency"), "Currency");
		
		if (!componentType.equals("PerformanceViewAllShareClass"))
		{
		reportStep("Verifying " + ComponentName + " - Benchmark Index %","INFO");		
		String expBenchMark=data.get("BenchmarkIndex") + " (%)";		
		if(elePerTableRows.get(2).findElements(By.tagName("td")).get(0).getText().contains(expBenchMark))
			reportStep("Expected Benchmark Index % " + expBenchMark + " Displayed", "PASS");
		else
			reportStep("Expected Benchmark Index % " + expBenchMark + " may not Displayed", "FAIL");
		
		reportStep("Verifying " + ComponentName + " - Bench Mark Currency Value","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("BMCurrency"), "BM Currency");
		
		reportStep("Verifying " + ComponentName + " - Fund Number for Benchmark Index","INFO");
		verifyExactText(elePerTableRows.get(2).findElements(By.tagName("td")).get(1), "—" , "Fund Number");
		}
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");	
		reportStep("Validating " + ComponentName + " Values Format - <->XXX" + DecimalSeptr + "XX","INFO");		
		List<WebElement> ShrClsscols=elePerTableRows.get(1).findElements(By.tagName("td"));
		List<WebElement> BenchMarkcols=elePerTableRows.get(2).findElements(By.tagName("td"));
		boolean isShrValid=false,isBMValid=false;
		
		System.out.println(ShrClsscols.size());
		for (int i = 3; i<ShrClsscols.size()-2 ; i++) 
		{	
			//System.out.println(ShrClsscols.get(i).getText());
			if(!ShrClsscols.get(i).getText().trim().equals("—")) {				
		        if(!ShrClsscols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
		        	{isShrValid=false;
		        	break; 	}
		        else
		        	isShrValid=true;
			}
	        else
	        	isShrValid=true;
			
			if (!componentType.equals("PerformanceViewAllShareClass")) {
				//System.out.println(BenchMarkcols.get(i).getText());	
				if(!BenchMarkcols.get(i).getText().trim().equals("—")) {
					if(!BenchMarkcols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
			        	{isBMValid=false;
			        	break; 	}
			        else
			        	isBMValid=true;
				}else
					isBMValid=true;
			}else
				isBMValid=true;
		}
		//System.out.println(isShrValid);
		//System.out.println(isBMValid);
		if(isShrValid && isBMValid)
			reportStep("All the Values in " + ComponentName +" (" + TimeToggle + ") are displayed in Expected Format", "PASS");
		else
			reportStep("One or More Values in " + ComponentName +" (" + TimeToggle + ") may not displayed in Expected Format", "FAIL");
		
		if(!(sCountryName.equals("German")||sCountryName.equals("Austria"))) {
		if (componentType.equals("PerformanceViewAllShareClass"))
		{
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingInceptionDateColumn") + " Date Format","INFO");
			//WebElement eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("ft-label"));
			if(dateFunctions.VerifyDateFormat(ShrClsscols.get(13).getText(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Date section " + ShrClsscols.get(13).getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
			else
				reportStep("Date section " + ShrClsscols.get(13).getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");

			}
		}	
		else {
			if (componentType.equals("PerformanceViewAllShareClass"))
			{
				reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingInceptionDateColumn") + " Date Format","INFO");
				//WebElement eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("ft-label"));
				if(dateFunctions.VerifyDateFormat(ShrClsscols.get(9).getText(), getLocalePropertyValue("lblDateFormat")))
					reportStep("Date section " + ShrClsscols.get(9).getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
				else
					reportStep("Date section " + ShrClsscols.get(9).getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");

			}
		}
	}	
		
	public void verifyCummulativePerformanceTableDataCanada(HashMap<String,String> data,String ComponentName,String TimeToggle,String componentType) throws InterruptedException
	{
		//reportStep("Verifying " + ComponentName + " - " + TimeToggle,"INFO");
		List<WebElement> elePerTableRows;
		String expShrClass;
		if (componentType.equals("PerformanceViewAllShareClass")) {
			
			elePerTableRows=locateElement(localeProp.getProperty("objPECumulativePerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));			
			reportStep("Verifying Share Class value for Cummulative - View Performance All Shareclass Table","INFO");
			expShrClass=data.get("ShareClass");	}	
	
		else {
			elePerTableRows=locateElement(localeProp.getProperty("objPECumulativePerformanceMonthlyTable")).findElements(By.tagName("tr"));
			reportStep("Verifying Share Class value for Cumulative Performance Table","INFO");
			expShrClass=data.get("ShareClass")  + " (%)";		
		}		

		if(elePerTableRows.get(1).findElements(By.tagName("td")).get(0).getText().contains(expShrClass))
			reportStep("Expected Share Class " + expShrClass + " Displayed", "PASS");
		else
			reportStep("Expected Share Class " + expShrClass + "may not Displayed", "FAIL");
		
		
		reportStep("Verifying " + ComponentName + " - Currency Value","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(1), data.get("Currency"), "Currency");
		
		/** not applicable to canada individual inverstor**/
		
//		if (!componentType.equals("PerformanceViewAllShareClass"))
//		{
//		reportStep("Verifying " + ComponentName + " - Benchmark Index %","INFO");		
//		String expBenchMark=data.get("BenchmarkIndex") + " (%)";		
//		if(elePerTableRows.get(2).findElements(By.tagName("td")).get(0).getText().contains(expBenchMark))
//			reportStep("Expected Benchmark Index % " + expBenchMark + " Displayed", "PASS");
//		else
//			reportStep("Expected Benchmark Index % " + expBenchMark + " may not Displayed", "FAIL");
//		
//		reportStep("Verifying " + ComponentName + " - Bench Mark Currency Value","INFO");
//		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("BMCurrency"), "BM Currency");
//		
//		reportStep("Verifying " + ComponentName + " - Fund Number for Benchmark Index","INFO");
//		verifyExactText(elePerTableRows.get(2).findElements(By.tagName("td")).get(1), "—" , "Fund Number");
//		}
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");	
		reportStep("Validating " + ComponentName + " Values Format - <->XXX" + DecimalSeptr + "XX","INFO");		
		List<WebElement> ShrClsscols=elePerTableRows.get(1).findElements(By.tagName("td"));
//		List<WebElement> BenchMarkcols=elePerTableRows.get(2).findElements(By.tagName("td"));
		boolean isShrValid=false,isBMValid=false;
		
		System.out.println(ShrClsscols.size());
		for (int i = 2; i<ShrClsscols.size()-2 ; i++) 
		{	
			//System.out.println(ShrClsscols.get(i).getText());
			if(!ShrClsscols.get(i).getText().trim().equals("—")) {				
		        if(!ShrClsscols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
		        	{isShrValid=false;
		        	break; 	}
		        else
		        	isShrValid=true;
			}
	        else
	        	isShrValid=true;
		}
//			
//			if (!componentType.equals("PerformanceViewAllShareClass")) {
//				//System.out.println(BenchMarkcols.get(i).getText());	
//				if(!BenchMarkcols.get(i).getText().trim().equals("—")) {
//					if(!BenchMarkcols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
//			        	{isBMValid=false;
//			        	break; 	}
//			        else
//			        	isBMValid=true;
//				}else
//					isBMValid=true;
//			}else
//				isBMValid=true;
//		}
		//System.out.println(isShrValid);
		//System.out.println(isBMValid);
//		if(isShrValid && isBMValid)
			if(isShrValid)
			reportStep("All the Values in " + ComponentName +" (" + TimeToggle + ") are displayed in Expected Format", "PASS");
		else
			reportStep("One or More Values in " + ComponentName +" (" + TimeToggle + ") may not displayed in Expected Format", "FAIL");
		
	
		if (componentType.equals("PerformanceViewAllShareClass"))
		{
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingInceptionDateColumn") + " Date Format","INFO");
			//WebElement eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("ft-label"));
			if(dateFunctions.VerifyDateFormat(ShrClsscols.get(12).getText(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Date section " + ShrClsscols.get(12).getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
			else
				reportStep("Date section " + ShrClsscols.get(12).getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");

			}
		
	
	}	
	
	
	public FundOverviewPage clickFirstFundOverViewLinkFromCummPerViewAllShareClassTable(HashMap<String,String> data) throws InterruptedException, FileNotFoundException, IOException
	{
		String[] str=fund.split(" - ");
		String expFundName=str[0] + " - " + data.get("ShareClass").replace(" (%)", "");
		//System.out.println(expFundName);
		click(locateElement("xpath", getLocalePropertyValue("objPECumPerformanceViewAllShareClassFundOverviewLink")));
		//For Switzerland due to slowness
		Thread.sleep(5000);
		return new FundOverviewPage(driver,test,expFundName);
	}
		
	
	//Annualised Performance
	public FundPerformancePage verifyAnnualisedPerformanceTableMonthEndHeadings()
	{	
		if(sCountryName.equals("Canada"))
			verifyAnnualisedPerformanceTableHeadingCanada("Annualised Performance Table","MonthEnd","Annualised Performance");
		else
			verifyAnnualisedPerformanceTableHeading("Annualised Performance Table","MonthEnd","Annualised Performance");
		return this;
	}
	public FundPerformancePage verifyAnnualisedPerformanceTableMonthEndData(HashMap<String,String> data)
	{	
		if(sCountryName.equals("Canada"))
			verifyAnnualisedPerformanceTableDataCanada(data,"Annualised Performance Table", "MonthEnd","Annualised Performance");	
		else
			verifyAnnualisedPerformanceTableData(data,"Annualised Performance Table", "MonthEnd","Annualised Performance");	
		return this;
	}
	public FundPerformancePage verifyAnnualisedPerformanceTableQuarterEndHeadings()
	{	if(sCountryName.equals("Canada"))
		verifyAnnualisedPerformanceTableHeadingCanada("Annualised Performance Table","QuarterEnd","Annualised Performance");
	else
		verifyAnnualisedPerformanceTableHeading("Annualised Performance Table","QuarterEnd","Annualised Performance");
		return this;
	}
	public FundPerformancePage verifyAnnualisedPerformanceTableQuarterEndData(HashMap<String,String> data)
	{	
		if(sCountryName.equals("Canada"))
			verifyAnnualisedPerformanceTableDataCanada(data,"Annualised Performance Table", "QuarterEnd","Annualised Performance"); 
		else
			verifyAnnualisedPerformanceTableData(data,"Annualised Performance Table", "QuarterEnd","Annualised Performance"); 
		return this;
	}
	//View All Shareclass
	public FundPerformancePage verifyAnnualisedPerformanceViewAllShareClassTableMonthEndHeadings()
	{	if(sCountryName.equals("Canada"))
		verifyAnnualisedPerformanceTableHeadingCanada("Annualised Performance - View All ShareClass Table","MonthEnd","PerformanceViewAllShareClass");
	else
		verifyAnnualisedPerformanceTableHeading("Annualised Performance - View All ShareClass Table","MonthEnd","PerformanceViewAllShareClass");
		return this;
	}
	public FundPerformancePage verifyAnnualisedPerformanceViewAllShareClassTableMonthEndData(HashMap<String,String> data)
	{	if(sCountryName.equals("Canada"))
		verifyAnnualisedPerformanceTableDataCanada(data,"Annualised Performance - View All ShareClass Table", "MonthEnd","PerformanceViewAllShareClass");
	else
		verifyAnnualisedPerformanceTableData(data,"Annualised Performance - View All ShareClass Table", "MonthEnd","PerformanceViewAllShareClass");	
		return this;
	}
	
	public FundPerformancePage verifyAnnualisedPerformanceViewAllShareClassTableQuarterEndHeadings()
	{	
		if(sCountryName.equals("Canada"))
			verifyAnnualisedPerformanceTableHeadingCanada("Annualised Performance - View All ShareClass Table","QuarterEnd","PerformanceViewAllShareClass");
		else
		verifyAnnualisedPerformanceTableHeading("Annualised Performance - View All ShareClass Table","QuarterEnd","PerformanceViewAllShareClass");
		return this;
	}
	public FundPerformancePage verifyAnnualisedPerformanceViewAllShareClassTableQuarterEndData(HashMap<String,String> data)
	{	if(sCountryName.equals("Canada"))
		verifyAnnualisedPerformanceTableDataCanada(data,"Annualised Performance - View All ShareClass Table", "QuarterEnd","PerformanceViewAllShareClass");
	else
		verifyAnnualisedPerformanceTableData(data,"Annualised Performance - View All ShareClass Table", "QuarterEnd","PerformanceViewAllShareClass");	
		return this;
	}
	public FundOverviewPage clickFirstFundOverViewLinkFromAnnualisedViewAllShareClassTable(HashMap<String,String> data) throws InterruptedException, FileNotFoundException, IOException
	{
		String[] str=fund.split(" - ");
		String expFundName=str[0] + " - " + data.get("ShareClass").replace(" (%)", "");
		System.out.println(expFundName);
		click(locateElement("xpath", getLocalePropertyValue("objPEAnnualisedformanceViewAllShareClassFundOverviewLink")));
		Thread.sleep(5000);
		return new FundOverviewPage(driver,test,expFundName);
	}
	
	public FundPerformancePage clickAnnualisedViewPerformanceForAllShareclassLink()
	{
		
		click(locateElement("xpath", getLocalePropertyValue("objPEAnnulaisedViewPerformanceForAllShareClassLink")));
		WebDriverWait wait=new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",getLocalePropertyValue("objPEAnnulaisedViewPerformanceForAllShareClassLink"))));
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPEAnnulaisedViewPerformanceForAllShareClassLink")));
		return this;
	}
	
	public void verifyAnnualisedPerformanceTableHeading(String componentName,String TimeToggle,String componentType)
	{
		List<WebElement> elePerTableRows;		
		reportStep("Verifying " + componentName  + " headings - " + TimeToggle,"INFO");		
		//List<WebElement> elePerTableRows=locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable")).findElements(By.tagName("tr"));
		
		if (componentType.equals("PerformanceViewAllShareClass")) {			
			elePerTableRows=locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));			
			mouseOver(locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceViewAllShaerClassTable")));}	
		else {
			elePerTableRows=locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable")).findElements(By.tagName("tr"));
			mouseOver(locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceMonthlyTable")));}	
		
		List<WebElement> eleHeadings=elePerTableRows.get(0).findElements(By.tagName("th"));
		//System.out.println(eleHeadings.size());
		
		
		boolean isValid=true;
		String[] str= {"lblFundNumber","lblCurrency","lbl1YR","lbl3YRS","lbl5YRS","lbl10YRS","lbl15YRS"};
		String expLbl="";
		for (int i = 0; i<str.length ; i++) {		
			
			//System.out.println(eleHeadings.get(i+1).getText());
			expLbl=expLbl +", " + getLocalePropertyValue(str[i]);
			if(!eleHeadings.get(i+1).getText().equals(getLocalePropertyValue(str[i]))) {					
				System.out.println("FAIL: " + eleHeadings.get(i+1).getText() + " , " + getLocalePropertyValue(str[i]));
				reportStep("Heading " + eleHeadings.get(i+1).getText() + " not matching with expected: " + getLocalePropertyValue(str[i]), "FALSE");
				isValid=false;	
				break;}
					
		}	
		if(isValid)
			reportStep("Headings are displayed as Expected - " + expLbl, "PASS");
		else
			reportStep("One or More Headings may not displayed as Expected - "+ expLbl, "FAIL");
		
		WebElement MonthEndEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(0);
		if(TimeToggle.equals("MonthEnd"))
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEMonthEnd"));
		else
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEQuarterEnd"));
		
		//System.out.println("Count" + elePerTableRows.get(0).findElements(By.tagName("th")).size());
		WebElement SinceInceptionEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(8);		
		//System.out.println(SinceInceptionEle.getText());	
		reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInceptionUpdated") + " Heading","INFO");
		if(eleHeadings.get(8).getText().contains(getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInceptionUpdated")))
			reportStep("'Since Performance Inception' Heading displayed", "PASS");
		else
			reportStep("'Since Performance Inception'  Heading may displayed as UnExpected: " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInceptionUpdated"), "FAIL");
		
		if (!componentType.equals("PerformanceViewAllShareClass")) {	
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " Heading Date Format","INFO");
			WebElement eledate=null;
			if (SinceInceptionEle.findElement(By.tagName("small")).findElements(By.tagName("ft-label")).size()>0)
					eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("ft-label"));
				else
					eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("span"));
			if(dateFunctions.VerifyDateFormat(eledate.getText(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Date section " + eledate.getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
			else
				reportStep("Date section " + eledate.getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		}
		if (componentType.equals("PerformanceViewAllShareClass")) {	
			
			reportStep("Verifying " + getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate") + " Heading","INFO");
			if(eleHeadings.get(9).getText().replaceAll("\n", " ").contains(getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate")))
				reportStep("'Performance Inception Date' Heading displayed", "PASS");
			else
				reportStep("'Performance Inception Date'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate"), "FAIL");
			//verifyExactText(eleHeadings.get(9), getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate"), "PERFORMANCE INCEPTION DATE");
			
			reportStep("Verifying " + getLocalePropertyValue("lblPEAnnlPerHeadingFundOverview") + " Heading","INFO");
			verifyTwoStringsExactly(eleHeadings.get(10).getText().replaceAll("\n", " "), getLocalePropertyValue("lblPEAnnlPerHeadingFundOverview"));
			
		}
		
	}
	
	public void verifyAnnualisedPerformanceTableHeadingCanada(String componentName,String TimeToggle,String componentType)
	{
		List<WebElement> elePerTableRows;		
		reportStep("Verifying " + componentName  + " headings - " + TimeToggle,"INFO");		
		//List<WebElement> elePerTableRows=locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable")).findElements(By.tagName("tr"));
		
		if (componentType.equals("PerformanceViewAllShareClass")) {			
			elePerTableRows=locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));			
			mouseOver(locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceViewAllShaerClassTable")));}	
		else {
			elePerTableRows=locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable")).findElements(By.tagName("tr"));
			mouseOver(locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceMonthlyTable")));}	
		
		List<WebElement> eleHeadings=elePerTableRows.get(0).findElements(By.tagName("th"));
		//System.out.println(eleHeadings.size());
		
		
		boolean isValid=true;
		String[] str= {"lblCurrency","lbl1YR","lbl3YRS","lbl5YRS","lbl10YRS","lbl15YRS"};
		String expLbl="";
		for (int i = 0; i<str.length ; i++) {		
			
			//System.out.println(eleHeadings.get(i+1).getText());
			expLbl=expLbl +", " + getLocalePropertyValue(str[i]);
			if(!eleHeadings.get(i+1).getText().equals(getLocalePropertyValue(str[i]))) {					
				System.out.println("FAIL: " + eleHeadings.get(i+1).getText() + " , " + getLocalePropertyValue(str[i]));
				reportStep("Heading " + eleHeadings.get(i+1).getText() + " not matching with expected: " + getLocalePropertyValue(str[i]), "FALSE");
				isValid=false;	
				break;}
					
		}	
		if(isValid)
			reportStep("Headings are displayed as Expected - " + expLbl, "PASS");
		else
			reportStep("One or More Headings may not displayed as Expected - "+ expLbl, "FAIL");
		
		WebElement MonthEndEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(0);
		if(TimeToggle.equals("MonthEnd"))
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEMonthEnd"));
		else
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEQuarterEnd"));
		
		//System.out.println("Count" + elePerTableRows.get(0).findElements(By.tagName("th")).size());
		WebElement SinceInceptionEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(7);		
		//System.out.println(SinceInceptionEle.getText());	
		reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInceptionUpdated") + " Heading","INFO");
		if(eleHeadings.get(7).getText().contains(getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInceptionUpdated")))
			reportStep("'Since Performance Inception' Heading displayed", "PASS");
		else
			reportStep("'Since Performance Inception'  Heading may displayed as UnExpected: " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInceptionUpdated"), "FAIL");
		
		if (!componentType.equals("PerformanceViewAllShareClass")) {	
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingSincePerformanceInception") + " Heading Date Format","INFO");
			WebElement eledate=null;
			if (SinceInceptionEle.findElement(By.tagName("small")).findElements(By.tagName("ft-label")).size()>0)
					eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("ft-label"));
				else
					eledate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("span"));
			if(dateFunctions.VerifyDateFormat(eledate.getText(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Date section " + eledate.getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
			else
				reportStep("Date section " + eledate.getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		}
		if (componentType.equals("PerformanceViewAllShareClass")) {	
			
			reportStep("Verifying " + getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate") + " Heading","INFO");
			if(eleHeadings.get(8).getText().replaceAll("\n", " ").contains(getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate")))
				reportStep("'Performance Inception Date' Heading displayed", "PASS");
			else
				reportStep("'Performance Inception Date'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate"), "FAIL");
			//verifyExactText(eleHeadings.get(9), getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate"), "PERFORMANCE INCEPTION DATE");
			
			reportStep("Verifying " + getLocalePropertyValue("lblPEAnnlPerHeadingFundOverview") + " Heading","INFO");
			verifyTwoStringsExactly(eleHeadings.get(9).getText().replaceAll("\n", " "), getLocalePropertyValue("lblPEAnnlPerHeadingFundOverview"));
			
		}
		
	}
	

	public void verifyAnnualisedPerformanceTableData(HashMap<String,String> data,String ComponentName,String TimeToggle,String componentType)
	{
		reportStep("Verifying " + ComponentName + " - " + TimeToggle + " Values","INFO");
		//List<WebElement> elePerTableRows=locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable")).findElements(By.tagName("tr"));
		List<WebElement> elePerTableRows;
		if(componentType.equals("PerformanceViewAllShareClass"))
			elePerTableRows=locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
		else
			elePerTableRows=locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable")).findElements(By.tagName("tr"));
		
		reportStep("Verifying " + ComponentName + " - Share Class" ,"INFO");
		String expShrClass=data.get("ShareClass");		
		if(elePerTableRows.get(1).findElements(By.tagName("td")).get(0).getText().contains(expShrClass))
			reportStep("Expected Share Class " + expShrClass + " Displayed", "PASS");
		else
			reportStep("Expected Share Class " + expShrClass + "may not Displayed", "FAIL");
		
		reportStep("Verifying " + ComponentName + " - Currency Value","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("Currency"), "Currency");
		
		reportStep("Verifying " + ComponentName + " - Fund Number","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(1), data.get("FundNumber"), "Fund Number");
		
		if(!componentType.equals("PerformanceViewAllShareClass")) {
			reportStep("Verifying " + ComponentName + " - Benchmark Index %","INFO");		
			String expBenchMark=data.get("BenchmarkIndex") + " (%)";		
			if(elePerTableRows.get(2).findElements(By.tagName("td")).get(0).getText().contains(expBenchMark))
				reportStep("Expected Benchmark Index % " + expBenchMark + " Displayed", "PASS");
			else
				reportStep("Expected Benchmark Index % " + expBenchMark + " may not Displayed", "FAIL");
			
			reportStep("Verifying " + ComponentName + " - Bench Mark Currency Value","INFO");
			verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("BMCurrency"), "BM Currency");
			
			reportStep("Verifying " + ComponentName + " - Fund Number for Benchmark Index","INFO");
			verifyExactText(elePerTableRows.get(2).findElements(By.tagName("td")).get(1), "—" , "Fund Number");
		}
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");	
		reportStep("Validating " + ComponentName + " Values Format - <->XXX" + DecimalSeptr + "XX","INFO");		
		List<WebElement> ShrClsscols=elePerTableRows.get(1).findElements(By.tagName("td"));
		List<WebElement> BenchMarkcols=elePerTableRows.get(2).findElements(By.tagName("td"));
		boolean isShrValid=false,isBMValid=false;
		
		System.out.println(ShrClsscols.size());
		for (int i = 3; i<ShrClsscols.size()-2 ; i++) 
		{	
			//System.out.println(ShrClsscols.get(i).getText());
			if(!ShrClsscols.get(i).getText().trim().equals("—")) {				
		        if(!ShrClsscols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
		        	{isShrValid=false;
		        	break; 	}
		        else
		        	isShrValid=true;
			}
	        else
	        	isShrValid=true;
			
			if(!componentType.equals("PerformanceViewAllShareClass")) {
				//System.out.println(BenchMarkcols.get(i).getText());	
				if(!BenchMarkcols.get(i).getText().trim().equals("—")) {
					if(!BenchMarkcols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
			        	{isBMValid=false;
			        	break; 	}
			        else
			        	isBMValid=true;
				}else
					isBMValid=true;
			}else
				isBMValid=true;
		}
		//System.out.println(isShrValid);
		//System.out.println(isBMValid);
		if(isShrValid && isBMValid)
			reportStep("All the Values in " + ComponentName +" for " + TimeToggle + " are displayed in Expected Format", "PASS");
		else
			reportStep("One or More Values in " + ComponentName +" for " + TimeToggle + " may not displayed in Expected Format", "FAIL");
		
		
		if(componentType.equals("PerformanceViewAllShareClass")) {
			reportStep("Verifying " + getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate") + " Format for Share Class " + data.get("ShareClass"),"INFO");		
			if(dateFunctions.VerifyDateFormat(ShrClsscols.get(9).getText().trim(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Performance Inception Date " + ShrClsscols.get(9).getText().trim()+ " displayed with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
			else
				reportStep("Performance Inception Date " + ShrClsscols.get(9).getText().trim() + " may not displayed with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		}
		
	}
	
	public void verifyAnnualisedPerformanceTableDataCanada(HashMap<String,String> data,String ComponentName,String TimeToggle,String componentType)
	{
		reportStep("Verifying " + ComponentName + " - " + TimeToggle + " Values","INFO");
		//List<WebElement> elePerTableRows=locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable")).findElements(By.tagName("tr"));
		List<WebElement> elePerTableRows;
		if(componentType.equals("PerformanceViewAllShareClass"))
			elePerTableRows=locateElement(getLocalePropertyValue("objPEAnnualisedPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
		else
			elePerTableRows=locateElement(localeProp.getProperty("objPEAnnualisedPerformanceMonthlyTable")).findElements(By.tagName("tr"));
		
		reportStep("Verifying " + ComponentName + " - Share Class" ,"INFO");
		String expShrClass=data.get("ShareClass");		
		if(elePerTableRows.get(1).findElements(By.tagName("td")).get(0).getText().contains(expShrClass))
			reportStep("Expected Share Class " + expShrClass + " Displayed", "PASS");
		else
			reportStep("Expected Share Class " + expShrClass + "may not Displayed", "FAIL");
		
		reportStep("Verifying " + ComponentName + " - Currency Value","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(1), data.get("Currency"), "Currency");
		
//		reportStep("Verifying " + ComponentName + " - Fund Number","INFO");
//		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(1), data.get("FundNumber"), "Fund Number");
		
//		if(!componentType.equals("PerformanceViewAllShareClass")) {
//			reportStep("Verifying " + ComponentName + " - Benchmark Index %","INFO");		
//			String expBenchMark=data.get("BenchmarkIndex") + " (%)";		
//			if(elePerTableRows.get(2).findElements(By.tagName("td")).get(0).getText().contains(expBenchMark))
//				reportStep("Expected Benchmark Index % " + expBenchMark + " Displayed", "PASS");
//			else
//				reportStep("Expected Benchmark Index % " + expBenchMark + " may not Displayed", "FAIL");
//			
//			reportStep("Verifying " + ComponentName + " - Bench Mark Currency Value","INFO");
//			verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("BMCurrency"), "BM Currency");
//			
//			reportStep("Verifying " + ComponentName + " - Fund Number for Benchmark Index","INFO");
//			verifyExactText(elePerTableRows.get(2).findElements(By.tagName("td")).get(1), "—" , "Fund Number");
//		}
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");	
		reportStep("Validating " + ComponentName + " Values Format - <->XXX" + DecimalSeptr + "XX","INFO");		
		List<WebElement> ShrClsscols=elePerTableRows.get(1).findElements(By.tagName("td"));
//		List<WebElement> BenchMarkcols=elePerTableRows.get(2).findElements(By.tagName("td"));
		boolean isShrValid=false,isBMValid=false;
		
		System.out.println(ShrClsscols.size());
		for (int i = 2; i<ShrClsscols.size()-2 ; i++) 
		{	
			//System.out.println(ShrClsscols.get(i).getText());
			if(!ShrClsscols.get(i).getText().trim().equals("—")) {				
		        if(!ShrClsscols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
		        	{isShrValid=false;
		        	break; 	}
		        else
		        	isShrValid=true;
			}
	        else
	        	isShrValid=true;
		}
//			if(!componentType.equals("PerformanceViewAllShareClass")) {
//				//System.out.println(BenchMarkcols.get(i).getText());	
//				if(!BenchMarkcols.get(i).getText().trim().equals("—")) {
//					if(!BenchMarkcols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
//			        	{isBMValid=false;
//			        	break; 	}
//			        else
//			        	isBMValid=true;
//				}else
//					isBMValid=true;
//			}else
//				isBMValid=true;
//		}
		//System.out.println(isShrValid);
		//System.out.println(isBMValid);
//		if(isShrValid && isBMValid)
			if(isShrValid)
			reportStep("All the Values in " + ComponentName +" for " + TimeToggle + " are displayed in Expected Format", "PASS");
		else
			reportStep("One or More Values in " + ComponentName +" for " + TimeToggle + " may not displayed in Expected Format", "FAIL");
		
		
		if(componentType.equals("PerformanceViewAllShareClass")) {
			reportStep("Verifying " + getLocalePropertyValue("lblPEAnnPerHeadingPerformanceInceptionDate") + " Format for Share Class " + data.get("ShareClass"),"INFO");		
			if(dateFunctions.VerifyDateFormat(ShrClsscols.get(8).getText().trim(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Performance Inception Date " + ShrClsscols.get(8).getText().trim()+ " displayed with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
			else
				reportStep("Performance Inception Date " + ShrClsscols.get(8).getText().trim() + " may not displayed with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		}
		
	}

	
	//CalendarYear
	public FundPerformancePage verifyCalendarYearPerformanceTableMonthEndHeadings()
	{	if(sCountryName.equals("Canada"))
		verifyCalendarYearPerformanceTableHeadingCanada("Calendar Year Performance Table","MonthEnd","CalendarYear Performance");
	else
		verifyCalendarYearPerformanceTableHeading("Calendar Year Performance Table","MonthEnd","CalendarYear Performance");
		
	return this;
	}
	public FundPerformancePage verifyCalendarYearPerformanceTableMonthEndData(HashMap<String,String> data)
	{	if(sCountryName.equals("Canada"))
		verifyCalendarYearPerformanceTableDataCanada(data,"Calendar Year Performance Table", "MonthEnd","CalendarYear Performance");	
	else
		verifyCalendarYearPerformanceTableData(data,"Calendar Year Performance Table", "MonthEnd","CalendarYear Performance");	
		return this;
	}
/*	public FundPerformancePage verifyCalendarYearPerformanceTableQuarterEndHeadings()
	{	
		verifyCalendarYearPerformanceTableHeading("Calendar Year Performance Table","QuarterEnd","CalendarYear Performance");
		return this;
	}
	public FundPerformancePage verifyCalendarYearPerformanceTableQuarterEndData(HashMap<String,String> data)
	{	
		verifyCalendarYearPerformanceTableData(data,"Calendar Year Performance Table", "QuarterEnd","CalendarYear Performance"); 
		return this;
	}*/
	
	public FundPerformancePage verifyCalendarYearPerformanceViewAllShareClassMonthEndHeadings()
	{	if(sCountryName.equals("Canada"))
		verifyCalendarYearPerformanceTableHeadingCanada("CalendarYear Performance Table - View All Shareclass","MonthEnd","PerformanceViewAllShareClass");
	else
		verifyCalendarYearPerformanceTableHeading("CalendarYear Performance Table - View All Shareclass","MonthEnd","PerformanceViewAllShareClass");		
		return this;
	}
	public FundPerformancePage verifyCalendarYearPerformanceViewAllShareClassMonthEndData(HashMap<String,String> data) throws InterruptedException
	{	if(sCountryName.equals("Canada"))
		verifyCalendarYearPerformanceTableDataCanada(data,"CalendarYear Performance Table - View All Shareclass", "MonthEnd","PerformanceViewAllShareClass");
	else
		verifyCalendarYearPerformanceTableData(data,"CalendarYear Performance Table - View All Shareclass", "MonthEnd","PerformanceViewAllShareClass");			
		return this;
	}
	public FundPerformancePage clickCalendarYearViewPerformanceForAllShareclassLink() throws InterruptedException
	{
		
		click(locateElement("xpath", getLocalePropertyValue("objPECalendarYearViewPerformanceForAllShareClassLink")));
		WebDriverWait wait=new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",getLocalePropertyValue("objPECalendarYearViewPerformanceForAllShareClassLink"))));
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPECalendarYearViewPerformanceForAllShareClassLink")));
		return this;
	}
	public FundOverviewPage clickFirstFundOverviewLinkFromCalYearViewAllShareClassTable(HashMap<String,String> data) throws InterruptedException, FileNotFoundException, IOException
	{
/*		List<WebElement> elePerTableRows=locateElement("xpath",getLocalePropertyValue("objPECalendarYearPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
		click(elePerTableRows.get(1).findElements(By.tagName("td")).get(14).findElement(By.tagName("a")));*/
		//HashMap<String,String> data=getTestData(sCountryName,"PERF026");
		String[] str=fund.split(" - ");
		String expFundName=str[0] + " - " + data.get("ShareClass").replace(" (%)", "");
		System.out.println(expFundName);
		click(locateElement("xpath", getLocalePropertyValue("objPECalendarYearPerformanceViewAllShareClassFundOverviewLink")));
		Thread.sleep(5000);
		return new FundOverviewPage(driver,test,expFundName);
	}
	
	public void verifyCalendarYearPerformanceTableHeading(String componentName,String TimeToggle, String componentType)
	{
		List<WebElement> elePerTableRows;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		reportStep("Verifying " + componentName  + " headings - " + TimeToggle,"INFO");		
		if(componentType.equals("PerformanceViewAllShareClass")) {
			//elePerTableRows=locateElement(localeProp.getProperty("objPECalendarYearPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
			elePerTableRows=locateElement("xpath",getLocalePropertyValue("objPECalendarYearPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
			mouseOver(locateElement("xpath",getLocalePropertyValue("objPECalendarYearPerformanceViewAllShaerClassTable")));}
		else {
			elePerTableRows=locateElement(localeProp.getProperty("objPECalendarYearPerformanceMonthlyTable")).findElements(By.tagName("tr"));
			mouseOver(locateElement(localeProp.getProperty("objPECalendarYearPerformanceMonthlyTable")));}
		
		if(!(sCountryName.equals("German")||sCountryName.equals("Austria")))	{
		List<WebElement> eleHeadings=elePerTableRows.get(0).findElements(By.tagName("th"));
		
		WebElement MonthEndEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(0);
		if(TimeToggle.equals("MonthEnd"))
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEMonthEnd"));
		else
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEQuarterEnd"));
		
		verifyExactText(eleHeadings.get(1), getLocalePropertyValue("lblFundNumber"), componentName + " - Fund Number");
		verifyExactText(eleHeadings.get(2), getLocalePropertyValue("lblCurrency"), componentName + " - Currency");
		verifyExactText(eleHeadings.get(3), getLocalePropertyValue("lblYTD"), componentName + " - YTD");
		
		reportStep("Verify heading shows past 10 years", "INFO");
		boolean isValid=true;		
		String expLbl="";
		for (int i = 1; i<=10 ; i++) {		
			
			System.out.println(eleHeadings.get(i+3).getText());
			expLbl=expLbl + " " + String.valueOf(year-i);
			if(!eleHeadings.get(i+3).getText().equals(String.valueOf(year-i).trim())) {					
				System.out.println("FAIL: " + eleHeadings.get(i+3).getText() + " , " + String.valueOf(year-i).trim());
				reportStep("Heading " + eleHeadings.get(i+3).getText() + " not matching with expected: " + String.valueOf(year-i).trim(), "FALSE");
				isValid=false;	
				break;}
					
		}	
		if(isValid)
			reportStep("Headings are displayed as Expected - " + expLbl, "PASS",false);
		else
			reportStep("One or More Headings may not displayed as Expected - "+ expLbl, "FAIL");
		
		System.out.println(eleHeadings.get(14).getText());	
		reportStep("Verifying " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn") + " Heading","INFO");
		if(eleHeadings.get(14).getText().contains(getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn")))
			reportStep("'Performance Inception Date' Heading displayed", "PASS",false);
		else
			reportStep("'Performance Inception Date'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn"), "FAIL");
		
		if(componentType.equals("PerformanceViewAllShareClass")) {
			System.out.println(eleHeadings.get(15).getText());	
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingFundOverview") + " Heading","INFO");
			if(eleHeadings.get(15).getText().contains(getLocalePropertyValue("lblPECumPerHeadingFundOverview")))
				reportStep("'Fund Overview' Heading displayed", "PASS",false);
			else
				reportStep("'Fund Overview'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECumPerHeadingFundOverview"), "FAIL");
		}
		}
		else {
			List<WebElement> eleHeadings=elePerTableRows.get(0).findElements(By.tagName("th"));
					
					WebElement MonthEndEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(0);
					if(TimeToggle.equals("MonthEnd"))
						checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEMonthEnd"));
					else
						checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEQuarterEnd"));
					
					verifyExactText(eleHeadings.get(1), getLocalePropertyValue("lblFundNumber"), componentName + " - Fund Number");
					verifyExactText(eleHeadings.get(2), getLocalePropertyValue("lblCurrency"), componentName + " - Currency");
				//	verifyExactText(eleHeadings.get(3), getLocalePropertyValue("lblYTD"), componentName + " - YTD");
					
					reportStep("Verify heading shows past 10 years", "INFO");
					boolean isValid=true;		
					String expLbl="";
					for (int i = 1; i<=10 ; i++) {		
						
						System.out.println(eleHeadings.get(i+3).getText());
						expLbl=expLbl + " " + String.valueOf(year-i);
						if(!eleHeadings.get(i+2).getText().equals(String.valueOf(year-i).trim())) {					
							System.out.println("FAIL: " + eleHeadings.get(i+2).getText() + " , " + String.valueOf(year-i).trim());
							reportStep("Heading " + eleHeadings.get(i+2).getText() + " not matching with expected: " + String.valueOf(year-i).trim(), "FALSE");
							isValid=false;	
							break;}
								
					}	
					if(isValid)
						reportStep("Headings are displayed as Expected - " + expLbl, "PASS",false);
					else
						reportStep("One or More Headings may not displayed as Expected - "+ expLbl, "FAIL");
					
					System.out.println(eleHeadings.get(13).getText());	
					reportStep("Verifying " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn") + " Heading","INFO");
					if(eleHeadings.get(13).getText().replaceAll("\n", " ").contains(getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn")))
						reportStep("'Inception Date' Heading displayed", "PASS",false);
					else
						reportStep("'Inception Date'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn"), "FAIL");
					
					if(componentType.equals("PerformanceViewAllShareClass")) {
						System.out.println(eleHeadings.get(14).getText());	
						reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingFundOverview") + " Heading","INFO");
						if(eleHeadings.get(14).getText().replaceAll("\n", " ").contains(getLocalePropertyValue("lblPECumPerHeadingFundOverview")))
							reportStep("'Fund Overview' Heading displayed", "PASS",false);
						else
							reportStep("'Fund Overview'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECumPerHeadingFundOverview"), "FAIL");
					}
					
				}
				
	}
	
	public void verifyCalendarYearPerformanceTableHeadingCanada(String componentName,String TimeToggle, String componentType)
	{
		List<WebElement> elePerTableRows;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		reportStep("Verifying " + componentName  + " headings - " + TimeToggle,"INFO");		
		if(componentType.equals("PerformanceViewAllShareClass")) {
			//elePerTableRows=locateElement(localeProp.getProperty("objPECalendarYearPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
			elePerTableRows=locateElement("xpath",getLocalePropertyValue("objPECalendarYearPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
			mouseOver(locateElement("xpath",getLocalePropertyValue("objPECalendarYearPerformanceViewAllShaerClassTable")));}
		else {
			elePerTableRows=locateElement(localeProp.getProperty("objPECalendarYearPerformanceMonthlyTable")).findElements(By.tagName("tr"));
			mouseOver(locateElement(localeProp.getProperty("objPECalendarYearPerformanceMonthlyTable")));}
		
	
		List<WebElement> eleHeadings=elePerTableRows.get(0).findElements(By.tagName("th"));
		
		WebElement MonthEndEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(0);
		if(TimeToggle.equals("MonthEnd"))
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEMonthEnd"));
		else
			checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEQuarterEnd"));
		
	//	verifyExactText(eleHeadings.get(1), getLocalePropertyValue("lblFundNumber"), componentName + " - Fund Number");
		verifyExactText(eleHeadings.get(1), getLocalePropertyValue("lblCurrency"), componentName + " - Currency");
		verifyExactText(eleHeadings.get(2), getLocalePropertyValue("lblYTD"), componentName + " - YTD");
		
		reportStep("Verify heading shows past 10 years", "INFO");
		boolean isValid=true;		
		String expLbl="";
		for (int i = 1; i<=10 ; i++) {		
			
			System.out.println(eleHeadings.get(i+3).getText());
			expLbl=expLbl + " " + String.valueOf(year-i);
			if(!eleHeadings.get(i+2).getText().equals(String.valueOf(year-i).trim())) {					
				System.out.println("FAIL: " + eleHeadings.get(i+2).getText() + " , " + String.valueOf(year-i).trim());
				reportStep("Heading " + eleHeadings.get(i+2).getText() + " not matching with expected: " + String.valueOf(year-i).trim(), "FALSE");
				isValid=false;	
				break;}
					
		}	
		if(isValid)
			reportStep("Headings are displayed as Expected - " + expLbl, "PASS",false);
		else
			reportStep("One or More Headings may not displayed as Expected - "+ expLbl, "FAIL");
		
		System.out.println(eleHeadings.get(13).getText());	
		reportStep("Verifying " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn") + " Heading","INFO");
		if(eleHeadings.get(13).getText().contains(getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn")))
			reportStep("'Performance Inception Date' Heading displayed", "PASS",false);
		else
			reportStep("'Performance Inception Date'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn"), "FAIL");
		
		if(componentType.equals("PerformanceViewAllShareClass")) {
			System.out.println(eleHeadings.get(14).getText());	
			reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingFundOverview") + " Heading","INFO");
			if(eleHeadings.get(14).getText().contains(getLocalePropertyValue("lblPECumPerHeadingFundOverview")))
				reportStep("'Fund Overview' Heading displayed", "PASS",false);
			else
				reportStep("'Fund Overview'  Heading may displayed as Expected: " + getLocalePropertyValue("lblPECumPerHeadingFundOverview"), "FAIL");
		}
		
	}
	

	public void verifyCalendarYearPerformanceTableData(HashMap<String,String> data,String ComponentName,String TimeToggle,String componentType)
	{
		reportStep("Verifying " + ComponentName + " - " + TimeToggle + " Data formats","INFO");
		List<WebElement> elePerTableRows;
		if(componentType.equals("PerformanceViewAllShareClass"))
			elePerTableRows=locateElement("xpath",getLocalePropertyValue("objPECalendarYearPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
		else
			elePerTableRows=locateElement(localeProp.getProperty("objPECalendarYearPerformanceMonthlyTable")).findElements(By.tagName("tr"));
		
		reportStep("Verifying " + ComponentName + " - Share Class","INFO");
		//String expShrClass=data.get("ShareClass") + " " + data.get("Currency") + " (%)";		
		if(elePerTableRows.get(1).findElements(By.tagName("td")).get(0).getText().contains(data.get("ShareClass")))
			reportStep("Expected Share Class " + data.get("ShareClass") + " Displayed", "PASS",false);
		else
			reportStep("Expected Share Class " + data.get("ShareClass") + "may not Displayed", "FAIL");
		
		reportStep("Verifying " + ComponentName + " - Fund Number","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(1), data.get("FundNumber"), "Fund Number");
		
		reportStep("Verifying " + ComponentName + " - Currency Value","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("Currency"), "Currency");
		
		//Benchmark
		if(!componentType.equals("PerformanceViewAllShareClass")) {
			reportStep("Verifying " + ComponentName + " - Benchmark Index %","INFO");		
			String expBenchMark=data.get("BenchmarkIndex") + " (%)";		
			if(elePerTableRows.get(2).findElements(By.tagName("td")).get(0).getText().contains(expBenchMark))
				reportStep("Expected Benchmark Index % " + expBenchMark + " Displayed", "PASS",false);
			else
				reportStep("Expected Benchmark Index % " + expBenchMark + " may not Displayed", "FAIL");
			
			reportStep("Verifying " + ComponentName + " - Fund Number for Benchmark Index","INFO");
			verifyExactText(elePerTableRows.get(2).findElements(By.tagName("td")).get(1), "—" , "BM Fund Number");
			
			reportStep("Verifying " + ComponentName + " - Bench Mark Currency Value","INFO");
			verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("BMCurrency"), "BM Currency");	
		}
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");	
		reportStep("Validating " + ComponentName + " Values Format - XXX" + DecimalSeptr + "XX","INFO");		
		List<WebElement> ShrClsscols=elePerTableRows.get(1).findElements(By.tagName("td"));
		List<WebElement> BenchMarkcols=elePerTableRows.get(2).findElements(By.tagName("td"));
		boolean isShrValid=false,isBMValid=false;
		if(!(sCountryName.equals("German")||sCountryName.equals("Austria"))){
		System.out.println(ShrClsscols.size());
		for (int i = 3; i<ShrClsscols.size()-2 ; i++) 
		{	
			//System.out.println(ShrClsscols.get(i).getText());
			if(!ShrClsscols.get(i).getText().trim().equals("—")) {				
		        if(!ShrClsscols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
		        	{isShrValid=false;
		        	break; 	}
		        else
		        	isShrValid=true;
			}
	        else
	        	isShrValid=true;
			
			if(!componentType.equals("PerformanceViewAllShareClass")) {
				//System.out.println(BenchMarkcols.get(i).getText());	
				if(!BenchMarkcols.get(i).getText().trim().equals("—")) {
					if(!BenchMarkcols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
			        	{isBMValid=false;
			        	break; 	}
			        else
			        	isBMValid=true;
				}else
					isBMValid=true;
			}else
				isBMValid=true;
		}
		//System.out.println(isShrValid);
		//System.out.println(isBMValid);
		if(isShrValid && isBMValid)
			reportStep("All the Values in " + ComponentName +" for " + TimeToggle + " are displayed in Expected Format", "PASS",false);
		else
			reportStep("One or More Values in " + ComponentName +" for " + TimeToggle + " may not displayed in Expected Format", "FAIL");	
		
			
		reportStep("Verifying " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn") + " Format","INFO");
		WebElement eledate=elePerTableRows.get(1).findElements(By.tagName("td")).get(14);
		if(dateFunctions.VerifyDateFormat(eledate.getText(), getLocalePropertyValue("lblDateFormat")))
			reportStep("Inception Date " + eledate.getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS",false);
		else
			reportStep("Inception Date " + eledate.getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		
		if(!componentType.equals("PerformanceViewAllShareClass")) {
			reportStep("Verifying BenchMark " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn") ,"INFO");
			WebElement eledate1=elePerTableRows.get(2).findElements(By.tagName("td")).get(14);
			if(eledate1.getText().equals("—"))
				reportStep("BenchMark Inception Date displayed as " + eledate1.getText() + " as Expected", "PASS",false);
			else
				reportStep("BenchMark Inception Date may not displayed as '—' as expected", "FAIL");
		}
		}
		else {
			System.out.println(ShrClsscols.size());
			for (int i = 3; i<ShrClsscols.size()-2 ; i++) 
			{	
				System.out.println(ShrClsscols.get(i).getText());
				if(!ShrClsscols.get(i).getText().trim().equals("—")) {				
			        if(!ShrClsscols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
			        	{isShrValid=false;
			        	break; 	}
			        else
			        	isShrValid=true;
				}
		        else
		        	isShrValid=true;
				
				if(!componentType.equals("PerformanceViewAllShareClass")) {
					//System.out.println(BenchMarkcols.get(i).getText());	
					if(!BenchMarkcols.get(i).getText().trim().equals("—")) {
						if(!BenchMarkcols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
				        	{isBMValid=false;
				        	break; 	}
				        else
				        	isBMValid=true;
					}else
						isBMValid=true;
				}else
					isBMValid=true;
			}
			System.out.println(isShrValid);
			System.out.println(isBMValid);
			if(isShrValid && isBMValid)
				reportStep("All the Values in " + ComponentName +" for " + TimeToggle + " are displayed in Expected Format", "PASS",false);
			else
				reportStep("One or More Values in " + ComponentName +" for " + TimeToggle + " may not displayed in Expected Format", "FAIL");	
			
				
			reportStep("Verifying " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn") + " Format","INFO");
			WebElement eledate=elePerTableRows.get(1).findElements(By.tagName("td")).get(13);
			if(dateFunctions.VerifyDateFormat(eledate.getText(), getLocalePropertyValue("lblDateFormat")))
				reportStep("Inception Date " + eledate.getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS",false);
			else
				reportStep("Inception Date " + eledate.getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
			
			if(!componentType.equals("PerformanceViewAllShareClass")) {
				reportStep("Verifying BenchMark " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn") ,"INFO");
				WebElement eledate1=elePerTableRows.get(2).findElements(By.tagName("td")).get(13);
				if(eledate1.getText().equals("—"))
					reportStep("BenchMark Inception Date displayed as " + eledate1.getText() + " as Expected", "PASS",false);
				else
					reportStep("BenchMark Inception Date may not displayed as '—' as expected", "FAIL");
			}
		}
	}


	
	public void verifyCalendarYearPerformanceTableDataCanada(HashMap<String,String> data,String ComponentName,String TimeToggle,String componentType)
	{
		reportStep("Verifying " + ComponentName + " - " + TimeToggle + " Data formats","INFO");
		List<WebElement> elePerTableRows;
		if(componentType.equals("PerformanceViewAllShareClass"))
			elePerTableRows=locateElement("xpath",getLocalePropertyValue("objPECalendarYearPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
		else
			elePerTableRows=locateElement(localeProp.getProperty("objPECalendarYearPerformanceMonthlyTable")).findElements(By.tagName("tr"));
		
		reportStep("Verifying " + ComponentName + " - Share Class","INFO");
		//String expShrClass=data.get("ShareClass") + " " + data.get("Currency") + " (%)";		
		if(elePerTableRows.get(1).findElements(By.tagName("td")).get(0).getText().contains(data.get("ShareClass")))
			reportStep("Expected Share Class " + data.get("ShareClass") + " Displayed", "PASS",false);
		else
			reportStep("Expected Share Class " + data.get("ShareClass") + "may not Displayed", "FAIL");
		
//		reportStep("Verifying " + ComponentName + " - Fund Number","INFO");
//		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(1), data.get("FundNumber"), "Fund Number");
		
		reportStep("Verifying " + ComponentName + " - Currency Value","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(1), data.get("Currency"), "Currency");
		
		//Benchmark
//		if(!componentType.equals("PerformanceViewAllShareClass")) {
//			reportStep("Verifying " + ComponentName + " - Benchmark Index %","INFO");		
//			String expBenchMark=data.get("BenchmarkIndex") + " (%)";		
//			if(elePerTableRows.get(2).findElements(By.tagName("td")).get(0).getText().contains(expBenchMark))
//				reportStep("Expected Benchmark Index % " + expBenchMark + " Displayed", "PASS",false);
//			else
//				reportStep("Expected Benchmark Index % " + expBenchMark + " may not Displayed", "FAIL");
//			
//			reportStep("Verifying " + ComponentName + " - Fund Number for Benchmark Index","INFO");
//			verifyExactText(elePerTableRows.get(2).findElements(By.tagName("td")).get(1), "—" , "BM Fund Number");
//			
//			reportStep("Verifying " + ComponentName + " - Bench Mark Currency Value","INFO");
//			verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("BMCurrency"), "BM Currency");	
//		}
		
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");	
		reportStep("Validating " + ComponentName + " Values Format - XXX" + DecimalSeptr + "XX","INFO");		
		List<WebElement> ShrClsscols=elePerTableRows.get(1).findElements(By.tagName("td"));
//		List<WebElement> BenchMarkcols=elePerTableRows.get(2).findElements(By.tagName("td"));
		boolean isShrValid=false,isBMValid=false;
	
		System.out.println(ShrClsscols.size());
		for (int i = 2; i<ShrClsscols.size()-2 ; i++) 
		{	
			//System.out.println(ShrClsscols.get(i).getText());
			if(!ShrClsscols.get(i).getText().trim().equals("—")) {				
		        if(!ShrClsscols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
		        	{isShrValid=false;
		        	break; 	}
		        else
		        	isShrValid=true;
			}
	        else
	        	isShrValid=true;
		}
//			if(!componentType.equals("PerformanceViewAllShareClass")) {
//				//System.out.println(BenchMarkcols.get(i).getText());	
//				if(!BenchMarkcols.get(i).getText().trim().equals("—")) {
//					if(!BenchMarkcols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
//			        	{isBMValid=false;
//			        	break; 	}
//			        else
//			        	isBMValid=true;
//				}else
//					isBMValid=true;
//			}else
//				isBMValid=true;
//		}
		//System.out.println(isShrValid);
		//System.out.println(isBMValid);
//		if(isShrValid && isBMValid)
			if(isShrValid )
			reportStep("All the Values in " + ComponentName +" for " + TimeToggle + " are displayed in Expected Format", "PASS",false);
		else
			reportStep("One or More Values in " + ComponentName +" for " + TimeToggle + " may not displayed in Expected Format", "FAIL");	
		
			
		reportStep("Verifying " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn") + " Format","INFO");
		WebElement eledate=elePerTableRows.get(1).findElements(By.tagName("td")).get(13);
		if(dateFunctions.VerifyDateFormat(eledate.getText(), getLocalePropertyValue("lblDateFormat")))
			reportStep("Inception Date " + eledate.getText() + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS",false);
		else
			reportStep("Inception Date " + eledate.getText() + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		
//		if(!componentType.equals("PerformanceViewAllShareClass")) {
//			reportStep("Verifying BenchMark " + getLocalePropertyValue("lblPECalendarYearPerInceptionDateColumn") ,"INFO");
//			WebElement eledate1=elePerTableRows.get(2).findElements(By.tagName("td")).get(14);
//			if(eledate1.getText().equals("—"))
//				reportStep("BenchMark Inception Date displayed as " + eledate1.getText() + " as Expected", "PASS",false);
//			else
//				reportStep("BenchMark Inception Date may not displayed as '—' as expected", "FAIL");
//		
//		
//		}
	}
	
	//####################  Discrete Annaul  #########################	

	public FundPerformancePage verifyDiscreteAnnualPerformanceTableMonthEndHeadings() throws ParseException
	{	
		verifyDiscreteAnnaulPerformanceTableHeading("Discrete Annual Performance Table","MonthEnd","DiscreteAnnualPerformance");
		return this;
	}
	public FundPerformancePage verifyDiscreteAnnualPerformanceTableMonthEndData(HashMap<String,String> data)
	{	
		verifyDiscreteAnnaulPerformanceTableData(data,"Discrete Annual Performance Table", "MonthEnd","DiscreteAnnualPerformance");	
		return this;
	}
	public FundPerformancePage verifyDiscreteAnnualPerformanceTableQuarterEndHeadings() throws ParseException
	{	
		verifyDiscreteAnnaulPerformanceTableHeading("Discrete Annual Performance Table","QuarterEnd","DiscreteAnnualPerformance");
		return this;
	}
	public FundPerformancePage verifyDiscreteAnnualPerformanceTableQuarterEndData(HashMap<String,String> data)
	{	
		verifyDiscreteAnnaulPerformanceTableData(data,"Discrete Annual Performance Table", "QuarterEnd","DiscreteAnnualPerformance");	
		return this;
	}
	//View All Share Class
	public FundPerformancePage verifyDiscreteAnnualPerformanceTableViewAllShareClassMonthEndHeadings() throws ParseException
	{	
		verifyDiscreteAnnaulPerformanceTableHeading("Discrete Annual Performance Table - View All ShareClass","MonthEnd","PerformanceViewAllShareClass");
		return this;
	}
	public FundPerformancePage verifyDiscreteAnnualPerformanceTableViewAllShareClassMonthEndData(HashMap<String,String> data)
	{	
		verifyDiscreteAnnaulPerformanceTableData(data,"Discrete Annual Performance Table - View All ShareClass", "MonthEnd","PerformanceViewAllShareClass");	
		return this;
	}
	
	public FundPerformancePage verifyDiscreteAnnualPerformanceTableViewAllShareClassQuarterEndHeadings() throws ParseException
	{	
		verifyDiscreteAnnaulPerformanceTableHeading("Discrete Annual Performance Table - View All ShareClass","QuarterEnd","PerformanceViewAllShareClass");
		return this;
	}
	public FundPerformancePage verifyDiscreteAnnualPerformanceTableViewAllShareClassQuarterEndData(HashMap<String,String> data)
	{	
		verifyDiscreteAnnaulPerformanceTableData(data,"Discrete Annual Performance Table - View All ShareClass", "QuarterEnd","PerformanceViewAllShareClass");	
		return this;
	}
	public FundPerformancePage clickDiscreteAnnualViewPerformanceForAllShareclassLink()
	{
		
		click(locateElement("xpath", getLocalePropertyValue("objPEDiscreteAnualViewPerformanceForAllShareClassLink")));
		WebDriverWait wait=new WebDriverWait(driver, 200);
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",getLocalePropertyValue("objPEDiscreteAnnualPerformanceViewAllShaerClassTable"))));
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPEDiscreteAnualViewPerformanceForAllShareClassLink")));
		return this;
	}
	public FundOverviewPage clickFirstFundOverViewLinkFromDiscreteAnnaulViewAllShareClassTable(HashMap<String,String> data) throws InterruptedException, FileNotFoundException, IOException
	{
		String[] str=fund.split(" - ");
		String expFundName=str[0] + " - " + data.get("ShareClass").replace(" (%)", "");
		//System.out.println(expFundName);
		click(locateElement("xpath", getLocalePropertyValue("objPEDiscreteAnnualformanceViewAllShareClassFundOverviewLink")));
		Thread.sleep(5000);
		return new FundOverviewPage(driver,test,expFundName);
	}
	public void verifyDiscreteAnnaulPerformanceTableHeading(String componentName,String TimeToggle,String componentType) throws ParseException
	{
		List<WebElement> elePerTableRows;
		commonMethods cm;
		try {
			cm = new commonMethods();

			int ColumnSize=0;
			reportStep("Verifying " + componentName  + " headings - " + TimeToggle,"INFO");
			if(componentType.equals("PerformanceViewAllShareClass")) {
				elePerTableRows=locateElement("xpath",getLocalePropertyValue("objPEDiscreteAnnualPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));
				mouseOver(locateElement("xpath",getLocalePropertyValue("objPEDiscreteAnnualPerformanceViewAllShaerClassTable")));
				System.out.println("fromAllshare");
				ColumnSize=10;}
			else {
				elePerTableRows=locateElement(localeProp.getProperty("objPEDiscreteAnnualPerformanceMonthlyTable")).findElements(By.tagName("tr"));
				mouseOver(locateElement(localeProp.getProperty("objPEDiscreteAnnualPerformanceMonthlyTable")));
				ColumnSize=9;}
			
			List<WebElement> eleHeadings=elePerTableRows.get(0).findElements(By.tagName("th"));
			if(eleHeadings.size()!=ColumnSize)
				reportStep("Expected Number of " + componentName  + " columns may not displayd", "FAIL");	
	
			
			//Discrete Annual Columun Names identified based on As of Date
			WebElement eledate=locateElement("xpath", getLocalePropertyValue("objPEDiscreteAnnualPerformanceHeading")).findElement(By.tagName("small"));
			//System.out.println("As at Date*: " +  cm.getAsAtDate(eledate));			
			String DAColumns[]=getExpDiscreteAnnualColumnNames(TimeToggle,cm.getAsAtDate(eledate)).split(",");
			
			WebElement MonthEndEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(0);
			if(TimeToggle.equals("MonthEnd"))
				checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEMonthEnd"));
			else
				checkHeadingTitleAndDateFormat(MonthEndEle, localeProp.getProperty("lblPEQuarterEnd"));
			
			//System.out.println("Count" + elePerTableRows.get(0).findElements(By.tagName("th")).size());
			WebElement SinceInceptionEle=elePerTableRows.get(0).findElements(By.tagName("th")).get(8);		
			//System.out.println(SinceInceptionEle.getText());	
			reportStep("Verifying " + getLocalePropertyValue("lblPEDiscreteAnnualHeadingPerformanceInceptionDate") + " Heading","INFO");
			if(eleHeadings.get(8).getText().replace("\n", " ").contains(getLocalePropertyValue("lblPEDiscreteAnnualHeadingPerformanceInceptionDate")))
				reportStep("'Since Performance Inception Date' Heading displayed", "PASS");
			else
				reportStep("'Since Performance Inception Date'  Heading may not displayed as Expected: " + getLocalePropertyValue("lblPEDiscreteAnnualHeadingSincePerformanceInceptionDate"), "FAIL");
			
			reportStep("Verifying other Headings in " +componentName+ " table","INFO");
	
			verifyExactText(eleHeadings.get(1), getLocalePropertyValue("lblFundNumber"),"Fund Number");
			verifyExactText(eleHeadings.get(2), getLocalePropertyValue("lblCurrency"),"Currency");
			if(verifyHeadingBySplit(eleHeadings.get(3).getText(), DAColumns[4]) && verifyHeadingBySplit(eleHeadings.get(4).getText(), DAColumns[3]) && verifyHeadingBySplit(eleHeadings.get(5).getText(), DAColumns[2]) && verifyHeadingBySplit(eleHeadings.get(6).getText(), DAColumns[1]) && verifyHeadingBySplit(eleHeadings.get(7).getText(), DAColumns[0]))
				reportStep("Headings displayed as Expected: " + DAColumns[4] + "," + DAColumns[3] + "," +DAColumns[2] + "," +DAColumns[1] + "," +DAColumns[0], "PASS");
			else
				reportStep("Headings may not displayed as Expected: " + DAColumns[4] + "," + DAColumns[3] + "," +DAColumns[2] + "," +DAColumns[1] + "," +DAColumns[0], "FAIL");
		
			
			if(componentType.equals("PerformanceViewAllShareClass")) {
				reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingFundOverview") + " Heading","INFO");
				verifyTwoStringsExactly(elePerTableRows.get(0).findElements(By.tagName("th")).get(9).getText().replace("\n", " "), getLocalePropertyValue("lblPECumPerHeadingFundOverview"));
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean verifyHeadingBySplit(String Actual,String Expected)
	{
		boolean ss=false;
		String str[]=Actual.split(" / ");
		String str1[]=Expected.split(" / ");
		String expMnth=str1[0].split("-")[0];
		String expYear=str1[0].split("-")[1];
		String expMnth1=str1[1].split("-")[0];
		String expYear1=str1[1].split("-")[1];
		
		if(str[0].contains(expMnth) && str[0].contains(expYear) && str[1].contains(expMnth1) && str[1].contains(expYear1))
			ss=true;
		
		return ss;
	}

	public void verifyDiscreteAnnaulPerformanceTableData(HashMap<String,String> data,String ComponentName,String TimeToggle,String componentType)
	{
		List<WebElement> elePerTableRows;
		reportStep("Verifying " + ComponentName + " (" + TimeToggle + ") Values Format","INFO");
		if(componentType.equals("PerformanceViewAllShareClass"))
			elePerTableRows=locateElement("xpath",getLocalePropertyValue("objPEDiscreteAnnualPerformanceViewAllShaerClassTable")).findElements(By.tagName("tr"));		
		else 
			elePerTableRows=locateElement(getLocalePropertyValue("objPEDiscreteAnnualPerformanceMonthlyTable")).findElements(By.tagName("tr"));

		//List<WebElement> elePerTableRows=locateElement(localeProp.getProperty("objPEDiscreteAnnualPerformanceMonthlyTable")).findElements(By.tagName("tr"));
		
		reportStep("Verifying Share Class value for " + ComponentName,"INFO");
		String expShrClass=data.get("ShareClass");		
		if(elePerTableRows.get(1).findElements(By.tagName("td")).get(0).getText().contains(expShrClass))
			reportStep("Expected Share Class " + expShrClass + " Displayed", "PASS");
		else
			reportStep("Expected Share Class " + expShrClass + "may not Displayed", "FAIL");
		
		reportStep("Verifying " + ComponentName + " - Currency Value","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("Currency"), "Currency");
		
		reportStep("Verifying " + ComponentName + " - Fund Number","INFO");
		verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(1), data.get("FundNumber"), "Fund Number");
		
		if(!componentType.equals("PerformanceViewAllShareClass")) {
			reportStep("Verifying " + ComponentName + " - Benchmark Index %","INFO");		
			String expBenchMark=data.get("BenchmarkIndex") + " (%)";		
			if(elePerTableRows.get(2).findElements(By.tagName("td")).get(0).getText().contains(expBenchMark))
				reportStep("Expected Benchmark Index % " + expBenchMark + " Displayed", "PASS");
			else
				reportStep("Expected Benchmark Index % " + expBenchMark + " may not Displayed", "FAIL");
			
			reportStep("Verifying " + ComponentName + " - Bench Mark Currency Value","INFO");
			verifyExactText(elePerTableRows.get(1).findElements(By.tagName("td")).get(2), data.get("BMCurrency"), "BM Currency");
			
			reportStep("Verifying " + ComponentName + " - Fund Number for Benchmark Index","INFO");
			verifyExactText(elePerTableRows.get(2).findElements(By.tagName("td")).get(1), "—" , "Fund Number");
		}
	
		String DecimalSeptr=getLocalePropertyValue("lblNumericDecimalSeparator");	
		reportStep("Validating " + ComponentName + " Values Format - <->XXX" + DecimalSeptr + "XX","INFO");		
		List<WebElement> ShrClsscols=elePerTableRows.get(1).findElements(By.tagName("td"));
		List<WebElement> BenchMarkcols=elePerTableRows.get(2).findElements(By.tagName("td"));
		boolean isShrValid=false,isBMValid=false;
		
		//System.out.println(ShrClsscols.size());
		for (int i = 3; i<ShrClsscols.size()-2 ; i++) 
		{	
			//System.out.println(ShrClsscols.get(i).getText());
			if(!ShrClsscols.get(i).getText().trim().equals("—")) {				
		        if(!ShrClsscols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
		        	{isShrValid=false;
		        	break; 	}
		        else
		        	isShrValid=true;
			}
	        else
	        	isShrValid=true;
			if(!componentType.equals("PerformanceViewAllShareClass")) {
				//System.out.println(BenchMarkcols.get(i).getText());	
				if(!BenchMarkcols.get(i).getText().trim().equals("—")) {
					if(!BenchMarkcols.get(i).getText().trim().matches("-?\\d{1,3}" + DecimalSeptr + "\\d{2}"))
			        	{isBMValid=false;
			        	break; 	}
			        else
			        	isBMValid=true;
				}else
					isBMValid=true;
			}else
				isBMValid=true;
		}
		System.out.println(isShrValid);
		System.out.println(isBMValid);
		if(isShrValid && isBMValid)
			reportStep("All the Values in " + ComponentName +" for " + TimeToggle + " are displayed in Expected Format", "PASS");
		else
			reportStep("One or More Values in " + ComponentName +" for " + TimeToggle + " may not displayed in Expected Format", "FAIL");
		
		reportStep("Verifying " + getLocalePropertyValue("lblPECumPerHeadingInceptionDateColumn") + " Format for Share Class " + data.get("ShareClass"),"INFO");
		//WebElement eleSincIncdate=SinceInceptionEle.findElement(By.tagName("small")).findElement(By.tagName("ft-label"));
		if(dateFunctions.VerifyDateFormat(ShrClsscols.get(8).getText().trim(), getLocalePropertyValue("lblDateFormat")))
			reportStep("Inception Date " + ShrClsscols.get(8).getText().trim()+ " displayed with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
		else
			reportStep("Inception Date " + ShrClsscols.get(8).getText().trim() + " may not displayed with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");
		
		if(!componentType.equals("PerformanceViewAllShareClass")) {
			reportStep("Verifying Benchmark Index Performance Inception Date","INFO");
			verifyExactText(BenchMarkcols.get(8),"—" , "Benchmark Index Performance Inception Date");
		}
	}

	
	//Caveats
	public FundPerformancePage verifyCumulativePerformanceTabCaveats()
	{	
		verifyPerformanceCaveats("Cumulative Performance - Important Legal Information");
		return this;
	}
	public FundPerformancePage verifyAnnualisedPerformanceTabCaveats()
	{	
		verifyPerformanceCaveats("Annualised Performance - Important Legal Information");
		return this;
	}
	public FundPerformancePage verifyCalendarYearPerformanceTabCaveats()
	{	
		verifyPerformanceCaveats("Calendar Year Performance - Important Legal Information");
		return this;
	}
	public FundPerformancePage verifyDiscreteAnnualPerformanceTabCaveats()
	{	
		verifyPerformanceCaveats("Discrete Annual Performance - Important Legal Information");
		return this;
	}
	
	
	public void verifyPerformanceCaveats(String PerTabName)
	{
/*		reportStep("Verifying " + PerTabName + " Caveat","INFO");
		if(verifyElementExist("xpath", getLocalePropertyValue("objPEImportantLegalInformationSection")))				
			reportStep("'" + PerTabName +" Caveat displayed successfully" , "PASS");
		else
			reportStep("'" + PerTabName +" Caveat may not displayed successfully" , "FAIL");
		
		reportStep("Verifying " + PerTabName + " Caveat Heading","INFO");
		
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPEImportantLegalInformationHeading")));
		verifyExactText(locateElement("xpath", getLocalePropertyValue("objPEImportantLegalInformationHeading")), getLocalePropertyValue("lblPEImportantLegalInformation"), "Caveat Heading");
		*/
		reportStep("Verifying " + PerTabName + " Caveat","INFO");
		String title1="";
		if(verifyElementExist("xpath", getLocalePropertyValue("objPEImportantLegalInformationSection"))) {
			reportStep(PerTabName + " Widget Exist and Displayed", "PASS",false);
			mouseOver(locateElement("xpath", getLocalePropertyValue("objPEImportantLegalInformationSection")));
			if(verifyElementExist("xpath", getLocalePropertyValue("objPEImportantInformationHeading"))) {				
				title1=locateElement("xpath", getLocalePropertyValue("objPEImportantInformationHeading")).getText();
				if(title1.trim().equals(getLocalePropertyValue("lblPEImportantInformation")))
					reportStep(title1 + " - Heading displayed as Expected", "PASS");
				else
					reportStep(title1 + " - Heading may not displayed as Expected - " + getLocalePropertyValue("lblPEImportantInformation"),"FAIL");
			
			}else if(verifyElementExist("xpath", getLocalePropertyValue("objPEImportantLegalInformationHeading"))) {				
				title1=locateElement("xpath", getLocalePropertyValue("objPEImportantLegalInformationHeading")).getText();
				if(title1.trim().equals(getLocalePropertyValue("lblPEImportantLegalInformation")))
					reportStep(title1 + " - Heading displayed as Expected", "PASS");
				else
					reportStep(title1 + " - Heading may not displayed as Expected - " + getLocalePropertyValue("lblPEImportantLegalInformation"),"FAIL");			

			}else
				reportStep("Important Legal Information/Important Information Heading may not Displayed", "FAIL");
		}else {
			reportStep("Important Legal Information/Important Information Widget may not Exist and Displayed", "FAIL");}		
		
	}
	
	public void checkHeadingTitleAndDateFormat(WebElement ele,String heading)
	{
		reportStep("Verifying " + heading + " Heading","INFO");
		//WebElement eleHeading=locateElement("xpath", localeProp.getProperty("objPECumulativePerformanceHeading"));
		//String heading=eleHeading.findElement(By.tagName("span"))
	
		//verifyExactText(ele.findElement(By.tagName("ft-label")), heading);	9/11 - Changed as part of CR
	
		//9/19 - Putting code to select the tag as per available
		if(ele.findElements(By.tagName("span")).size()>0)
			verifyExactText(ele.findElement(By.tagName("span")), heading);	
		else
			verifyExactText(ele.findElement(By.tagName("ft-label")), heading);	
		
		
			
		reportStep("Verifying " + heading + " Heading As at Date Format","INFO");
		WebElement eledate=ele.findElement(By.tagName("small"));
		if(dateFunctions.VerifyDateFormat(cm.getAsAtDate(eledate), getLocalePropertyValue("lblDateFormat")))
			reportStep("Date section " + cm.getAsAtDate(eledate) + " displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "PASS");
		else
			reportStep("Date section " + cm.getAsAtDate(eledate) + " may not displayed the date with expected format '"+getLocalePropertyValue("lblDateFormat")+"'", "FAIL");		
	}	
	
	public FundPerformancePage validateTimePeriodToggle()
	{
		verifyElementExist("xpath", getLocalePropertyValue("objPECumPerfMonthEndLink"), "Month End Time Toggle");
		verifyElementExist("xpath", getLocalePropertyValue("objPECumPerfQuarterEndLink"), "Quarter End Time Toggle");
		return this;
	}
	
	public void verifyNAVChangeValueColor(WebElement eleNavChange)
	{
        reportStep("Validating NAV Change value " + eleNavChange.getText() + " Color. NAV Chaneg value: Loss -RED, Gain - GREEN, NoChange - GRAY","INFO");
        commonMethods cm;
		try {
			cm = new commonMethods();        
	        String expColor;
	        //System.out.println(eleNavChange.getText());
	        if(eleNavChange.getText().contains("0.00"))
	        	expColor="GREY";
	        		
	        else if(eleNavChange.getText().substring(0,1).equals("-"))
	        	expColor="RED";           
	        else
	        	expColor="GREEN";  
	       
	        if(cm.verifyColorCode(eleNavChange,expColor))
	        	reportStep("NAV Change value matches the expected Color: "+ expColor, "PASS");
	        else
	        	reportStep("NAV Change value not matches the expected Color: " + expColor, "FAIL");
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	
	public void verifyDateRefreshTime(WebElement AsAtDateElement,String dateSectionName)
	{
		reportStep("Validing Refresh time '" + dateSectionName +"'","INFO");
		//||dateElment.contains("(" +getLocalePropertyValue("lblRefreshTimeYearly")+ ")")
		String dateElment=AsAtDateElement.getText();
		String refTimeCheck=getLocalePropertyValue("lblRefreshTimeMonthly").split(" ")[0];
		if(!dateElment.contains("(" + refTimeCheck))
		{
			reportStep("Checking '" + dateSectionName +"' widget Refresh Time Availability","INFO");				
			reportStep("'" + dateSectionName +"' widget Refresh Time may Not available for this fund","FAIL");				
		}
		
		if(dateElment.contains("(" + getLocalePropertyValue("lblRefreshTimeMonthly")+ ")") ||dateElment.contains("(" + getLocalePropertyValue("lblRefreshTimeQuartely")+ ")")||dateElment.contains("(" +getLocalePropertyValue("lblRefreshTimeSemiYearly")+ ")"))
			reportStep("Refresh Time displayed successfully - " + dateElment ,"PASS",false);	
		else
			reportStep("Refresh Time displayed successfully - " + dateElment ,"FAIL",false);
	}
	
	
/*	public FundPerformancePage verifyDiscreteAnnualColumnNamesForMonthEnd()
	{	
		
		//mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssFundNameColumn")));
		mouseOver(locateElement("xpath", getLocalePropertyValue("objPpssPerformanceTable")));		
		verifyCommonColumnNames();
		
		//preparing Expected data
		String DAColumns[]=getExpDiscreteAnnualColumnNames("MonthEnd").split(",");		
		
		reportStep("Checking Column Names for Discrete Annual Performance + " + getLocalePropertyValue("lblPpssTimeToggleMonthEnd") + " Time Toggle", "INFO");
		List<WebElement> DescreteAnnualColumns=locateElements("xpath", getLocalePropertyValue("objPpssPerformanceTableDiscreteAnnualColumns"));		
		if(DescreteAnnualColumns.size()!=6)
			reportStep("Expected Number of Performance columns may not displayd", "FAIL");
		
		verifyExactText(DescreteAnnualColumns.get(0), DAColumns[4]);
		verifyExactText(DescreteAnnualColumns.get(1), DAColumns[3]);
		verifyExactText(DescreteAnnualColumns.get(2), DAColumns[2]);
		verifyExactText(DescreteAnnualColumns.get(3), DAColumns[1]);
		verifyExactText(DescreteAnnualColumns.get(4), DAColumns[0]);	
		verifyExactText(DescreteAnnualColumns.get(5), getLocalePropertyValue("lblPpssInceptionDateColumn"));

		return this;
	}*/
	

	public String getExpDiscreteAnnualColumnNames(String TimeToggle,String AsAtDate) throws ParseException
	{
    	Calendar calendar = Calendar.getInstance();
    	String expMonth="InvalidMonth";
    	String month="InvlidMonth";
    	String asAtYear;
    	int expyear=-5;
		//System.out.println("As at Date: " + AsAtDate);
		int currentyear=calendar.get(Calendar.YEAR); 	

    		
		//Updating based on As at Date in the component title
		SimpleDateFormat format = new SimpleDateFormat(getLocalePropertyValue("lblDateFormat"));
		Date date1=format.parse(AsAtDate);    		
		month=new SimpleDateFormat("MMM").format(date1).toUpperCase();
		month=getMonthNameInGermanFromMonthNameInEnglish(month).toUpperCase();
		asAtYear=AsAtDate.split(getLocalePropertyValue("lblDateSeperator"))[2].trim();
		
		//System.out.println("month : " + month);
		//System.out.println("As at Year : " + asAtYear);
		if(currentyear-Integer.parseInt(asAtYear)>0)
			{expyear=-6;
			//System.out.println("-7");
			}
		else
			{expyear=-5;
			//System.out.println("-6");
		}
			

		//calendar.add(Calendar.YEAR, -5);
		calendar.add(Calendar.YEAR, expyear);
		String startRange=new SimpleDateFormat("yy").format(calendar.getTime());
		int sRange=Integer.parseInt(startRange);			
		int sRng,eRng;
		String cols;
		
		String columnNames="";
		
		sRng=sRange;
		eRng=sRange+1;		
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		
		return columnNames;
	}
	
	public String getMonthNameInGermanFromMonthNameInEnglish(String monthName) {
    	
		switch(monthName) {
		case "JAN" : return getLocalePropertyValue("lblJan");
		case "FEB" : return getLocalePropertyValue("lblFeb");
		case "MAR" : return getLocalePropertyValue("lblMar");
		case "APR" : return getLocalePropertyValue("lblApr");
		case "MAY" : return getLocalePropertyValue("lblMay"); 
		case "JUN" : return getLocalePropertyValue("lblJun");
		case "JUL" : return getLocalePropertyValue("lblJul");
		case "AUG" : return getLocalePropertyValue("lblAug");	
		case "SEP" : return getLocalePropertyValue("lblSep");	
		case "OCT" : return getLocalePropertyValue("lblOct");	
		case "NOV" : return getLocalePropertyValue("lblNov");	
		case "DEC" : return getLocalePropertyValue("lblDec");
		default    : return null;
		}
 }
	public String getExpDiscreteAnnualColumnNames(String TimeToggle)
	{
    	Calendar calendar = Calendar.getInstance();
    	//calendar.add(Calendar.MONTH, -differenceMonth);
    	
    	//System.out.println(calendar.getTime());
    	//String year=new SimpleDateFormat("yy").format(calendar.getTime());
    	String expMonth="InvalidMonth";
    	String month="InvlidMonth";
    	int expyear=-5;
		
    	if(TimeToggle.equals("QuarterEnd"))
    	{   
    		month=new SimpleDateFormat("MMM").format(calendar.getTime());
			if(month.equals("Jan") || month.equals("Feb") || month.equals("Mar")) {
				expMonth="Dec";
				expyear=-6;}
			else if(month.equals("Apr") || month.equals("May") || month.equals("Jun"))
				expMonth="Mar";
			else if(month.equals("Jul") || month.equals("Aug") || month.equals("Sep"))
				expMonth="Jun";
			else if(month.equals("Oct") || month.equals("Nov") || month.equals("Dec"))
				expMonth="Sep";	    	    	
			month=expMonth.toUpperCase();
    	}
    	else if(TimeToggle.equals("MonthEnd"))
    	{
    		calendar.add(Calendar.MONTH, -1);
    		month=new SimpleDateFormat("MMM").format(calendar.getTime());
    		if(month.equals("Jan") || month.equals("Feb") || month.equals("Mar"))
    			expyear=-6;   		

    	}
		//calendar.add(Calendar.YEAR, -5);
		calendar.add(Calendar.YEAR, expyear);
		String startRange=new SimpleDateFormat("yy").format(calendar.getTime());
		int sRange=Integer.parseInt(startRange);			
		int sRng,eRng;
		String cols;
		
		String columnNames="";
		
		sRng=sRange;
		eRng=sRange+1;		
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		sRng=eRng;
		eRng=eRng+1;
		cols=month + "-" + sRng + " / " + month + "-" + eRng;
		columnNames=columnNames + "," + cols;
		
		
		return columnNames;
	}
	
public void validatePerfTableHeaderData(WebElement perfTable, List<String> modelTableHeaders) {
		
		List<WebElement> perfTableHeadersListhead = WebTableFunctions.getAllTagsInsideAnElement(perfTable, "thead");
		List<WebElement> perfTableHeadersList = WebTableFunctions.getAllTagsInsideAnElement(perfTableHeadersListhead.get(0), "th");
		
		if(perfTableHeadersList.size() != modelTableHeaders.size())
			reportStep("The Number of elements in model table headers list is not matching with actual table headers","FAIL");
		
		for(int i=0;i<perfTableHeadersList.size();i++) {
			
			if (perfTableHeadersList.get(i).getText().replaceAll("\n", " ").contains(modelTableHeaders.get(i)))
				reportStep("Performance Table Header Label "+ (i+1) +" is displayed as Expected: "+perfTableHeadersList.get(i).getText(),"PASS");
			else
				reportStep("Performance Table Header Label "+ (i+1) +" is not displayed as Expected: "+perfTableHeadersList.get(i).getText(),"FAIL");
		}
			
		}
		
		public void validatePerfTableData(WebElement perfTable, List<String> modelTableValueFormats) {
			
			List<WebElement> perfTableValueRows = WebTableFunctions.getAllTagsInsideAnElement(perfTable, "tr");
			int vItr = 0;
			for(int j=1; j< perfTableValueRows.size();j++) {
				
				List<WebElement> perfTableRowValues = WebTableFunctions.getAllTagsInsideAnElement(perfTableValueRows.get(j), "th");
				perfTableRowValues.addAll(WebTableFunctions.getAllTagsInsideAnElement(perfTableValueRows.get(j), "td"));
				if(perfTableRowValues.size() != modelTableValueFormats.size())
					reportStep("The Number of elements in model table value format list is not matching with actual table Values"+perfTableRowValues.size(),"FAIL");
				
				if (perfTableRowValues.get(0).getText().replaceAll("\n", " ").contains(modelTableValueFormats.get(0).split(",")[vItr])) 
					reportStep(" Indices "+ (vItr+1) +" Name is displayed as Expected:"+perfTableRowValues.get(0).getText().replaceAll("\n", " "),"PASS");
				else
					reportStep(" Indices "+ (vItr+1) +" Name is not displayed as Expected:"+perfTableRowValues.get(0).getText().replaceAll("\n", " "),"FAIL");
				
				for(int i=1;i<perfTableRowValues.size();i++) {
					if(perfTableRowValues.get(i).getText().matches(modelTableValueFormats.get(i)) || perfTableRowValues.get(i).getText().matches("—"))
						reportStep(" Indices :"+ modelTableValueFormats.get(0).split(",")[vItr] +", Value "+ (i+1) +" is displayed as Expected:"+perfTableRowValues.get(i).getText(),"PASS");
					else
						reportStep(" Indices :"+ modelTableValueFormats.get(0).split(",")[vItr] +", Value "+ (i+1) +" is not displayed as Expected:"+perfTableRowValues.get(i).getText(),"FAIL");
					}
				vItr++;
				}
			
			}
	
	
	public FundPerformancePage validatePerformanceRiskMeasuresOverviewAndStaticSection(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Performance Page Risk Measures - Overview & Static Section","INFO");
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objPERiskMeasuresSectionHeader")));
		WebElement riskMeasuresHeader = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresSectionHeader"));
		List<WebElement> ftLableEle = WebTableFunctions.getAllTagsInsideAnElement(riskMeasuresHeader, "ft-label");
		String ftLableEleText1 = ftLableEle.get(0).getText();
		String ftLableEleText2 = ftLableEle.get(1).getText();
		String ftLableEleText3 = ftLableEle.get(2).getText();
		String ftLableEleText4 = ftLableEle.get(3).getText();
		
		if ((ftLableEleText1+" — "+ftLableEleText2).replaceAll("\n", " ").contains(getLocalePropertyValue("lblPERiskMeasuresSectionHeader").toUpperCase()) && ftLableEleText3.contains(getLocalePropertyValue("lblDateFormatPart1"))  && ftLableEleText4.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(getLocalePropertyValue("lblPERiskMeasuresSectionHeader")+" Label is displayed as Expected: "+riskMeasuresHeader.getText(),"PASS");
		else
			reportStep(getLocalePropertyValue("lblPERiskMeasuresSectionHeader")+" Label is not displayed as Expected: "+riskMeasuresHeader.getText(),"FAIL");		
		
		WebElement statTable = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresStatisticsTable"));
		List<String> modelTableHeaders = new ArrayList<String>(); 
		modelTableHeaders.add(getLocalePropertyValue("lblPERiskMeasuresSectionStaticsTableCol1").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl1YR").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl3YRS").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl5YRS").toUpperCase());
		validatePerfTableHeaderData( statTable, modelTableHeaders);
		
		List<String> modelTableValueFormats = new ArrayList<String>(); 
		modelTableValueFormats.add(excelData.get("Statistics Table Ind"));
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		
		validatePerfTableData(statTable, modelTableValueFormats);
		return this;
	}
	
	public FundPerformancePage validatePerformanceRiskMeasuresOverviewAndStaticSectionCanada(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Performance Page Risk Measures - Overview & Static Section","INFO");
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objPERiskMeasuresSectionHeader")));
		WebElement riskMeasuresHeader = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresSectionHeader"));
		List<WebElement> ftLableEle = WebTableFunctions.getAllTagsInsideAnElement(riskMeasuresHeader, "ft-label");
		String ftLableEleText1 = ftLableEle.get(0).getText();
		String ftLableEleText2 = ftLableEle.get(1).getText();
		String ftLableEleText3 = ftLableEle.get(2).getText();
		String ftLableEleText4 = ftLableEle.get(3).getText();
		
		if ((ftLableEleText1+" — "+ftLableEleText2).replaceAll("\n", " ").contains(getLocalePropertyValue("lblPERiskMeasuresSectionHeader").toUpperCase()) && ftLableEleText3.contains(getLocalePropertyValue("lblDateFormatPart1"))  && ftLableEleText4.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(getLocalePropertyValue("lblPERiskMeasuresSectionHeader")+" Label is displayed as Expected: "+riskMeasuresHeader.getText(),"PASS");
		else
			reportStep(getLocalePropertyValue("lblPERiskMeasuresSectionHeader")+" Label is not displayed as Expected: "+riskMeasuresHeader.getText(),"FAIL");		
		
		WebElement statTable = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresStatisticsTable"));
		List<String> modelTableHeaders = new ArrayList<String>(); 
		modelTableHeaders.add(getLocalePropertyValue("lblPERiskMeasuresSectionStaticsTableCol1").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl3YRS").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl5YRS").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl10YRS").toUpperCase());
		validatePerfTableHeaderData( statTable, modelTableHeaders);
		
		List<String> modelTableValueFormats = new ArrayList<String>(); 
		modelTableValueFormats.add(excelData.get("Statistics Table Ind"));
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		
		validatePerfTableData(statTable, modelTableValueFormats);
		return this;
	}

	public FundPerformancePage validatePerformanceRiskMeasuresStandardDeviationSharpeRatioSections(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Performance Page Risk Measures - Standard Deviation & Sharpe Ratio Section","INFO");
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objPERiskMeasuresSectionHeader")));
		WebElement riskMeasuresHeader = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresSectionHeader"));
		List<WebElement> ftLableEle = WebTableFunctions.getAllTagsInsideAnElement(riskMeasuresHeader, "ft-label");
		String ftLableEleText1 = ftLableEle.get(0).getText();
		String ftLableEleText2 = ftLableEle.get(1).getText();
		String ftLableEleText3 = ftLableEle.get(2).getText();
		String ftLableEleText4 = ftLableEle.get(3).getText();
		
		if ((ftLableEleText1+" — "+ftLableEleText2).replaceAll("\n", " ").contains(getLocalePropertyValue("lblPERiskMeasuresSectionHeader").toUpperCase()) && ftLableEleText3.contains(getLocalePropertyValue("lblDateFormatPart1"))  && ftLableEleText4.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(getLocalePropertyValue("lblPERiskMeasuresSectionHeader")+" Label is displayed as Expected: "+riskMeasuresHeader.getText(),"PASS");
		else
			reportStep(getLocalePropertyValue("lblPERiskMeasuresSectionHeader")+" Label is not displayed as Expected: "+riskMeasuresHeader.getText(),"FAIL");		
		
		WebElement stdDevTable = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresStandardDeviationTable"));
		List<String> modelTableHeaders = new ArrayList<String>(); 
		modelTableHeaders.add(getLocalePropertyValue("lblPERiskMeasuresSectionStdDevTableCol1").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lblPERiskMeasuresSectionStdDevTableCol2").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl1YR").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl3YRS").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl5YRS").toUpperCase());
		validatePerfTableHeaderData( stdDevTable, modelTableHeaders);
		
		List<String> modelTableValueFormats = new ArrayList<String>(); 
		modelTableValueFormats.add(excelData.get("Standard Deviation Table Ind"));
		modelTableValueFormats.add("\\d{4}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		
		validatePerfTableData(stdDevTable, modelTableValueFormats);
		
		WebElement shrpRatioTable = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresSharpeRatioTable"));
		List<String> modelTableHeaders1 = new ArrayList<String>(); 
		modelTableHeaders1.add(getLocalePropertyValue("lblPERiskMeasuresSectionSharpeRatioTableCol1").toUpperCase());
		modelTableHeaders1.add(getLocalePropertyValue("lblPERiskMeasuresSectionStdDevTableCol2").toUpperCase());
		modelTableHeaders1.add(getLocalePropertyValue("lbl1YR").toUpperCase());
		modelTableHeaders1.add(getLocalePropertyValue("lbl3YRS").toUpperCase());
		modelTableHeaders1.add(getLocalePropertyValue("lbl5YRS").toUpperCase());
		validatePerfTableHeaderData( shrpRatioTable, modelTableHeaders1);
		
		List<String> modelTableValueFormats1 = new ArrayList<String>(); 
		modelTableValueFormats1.add(excelData.get("Standard Deviation Table Ind"));
		modelTableValueFormats1.add("\\d{4}");
		modelTableValueFormats1.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats1.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats1.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		
		validatePerfTableData(shrpRatioTable, modelTableValueFormats1);
		return this;
	}
	
	public FundPerformancePage validatePerformanceRiskMeasuresStandardDeviationSharpeRatioSectionsCanada(HashMap<String,String> excelData) throws InterruptedException {
		Thread.sleep(2000);
		reportStep("Validating Funds Performance Page Risk Measures - Standard Deviation & Sharpe Ratio Section","INFO");
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objPERiskMeasuresSectionHeader")));
		WebElement riskMeasuresHeader = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresSectionHeader"));
		List<WebElement> ftLableEle = WebTableFunctions.getAllTagsInsideAnElement(riskMeasuresHeader, "ft-label");
		String ftLableEleText1 = ftLableEle.get(0).getText();
		String ftLableEleText2 = ftLableEle.get(1).getText();
		String ftLableEleText3 = ftLableEle.get(2).getText();
		String ftLableEleText4 = ftLableEle.get(3).getText();
		
		if ((ftLableEleText1+" — "+ftLableEleText2).replaceAll("\n", " ").contains(getLocalePropertyValue("lblPERiskMeasuresSectionHeader").toUpperCase()) && ftLableEleText3.contains(getLocalePropertyValue("lblDateFormatPart1"))  && ftLableEleText4.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(getLocalePropertyValue("lblPERiskMeasuresSectionHeader")+" Label is displayed as Expected: "+riskMeasuresHeader.getText(),"PASS");
		else
			reportStep(getLocalePropertyValue("lblPERiskMeasuresSectionHeader")+" Label is not displayed as Expected: "+riskMeasuresHeader.getText(),"FAIL");		
		
		WebElement stdDevTable = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresStandardDeviationTable"));
		List<String> modelTableHeaders = new ArrayList<String>(); 
		modelTableHeaders.add(getLocalePropertyValue("lblPERiskMeasuresSectionStdDevTableCol1").toUpperCase());
	//	modelTableHeaders.add(getLocalePropertyValue("lblPERiskMeasuresSectionStdDevTableCol2").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl3YRS").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl5YRS").toUpperCase());
		modelTableHeaders.add(getLocalePropertyValue("lbl10YRS").toUpperCase());
		validatePerfTableHeaderData( stdDevTable, modelTableHeaders);
		
		List<String> modelTableValueFormats = new ArrayList<String>(); 
		modelTableValueFormats.add(excelData.get("Standard Deviation Table Ind"));
		//modelTableValueFormats.add("\\d{4}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		
		validatePerfTableData(stdDevTable, modelTableValueFormats);
		
		WebElement shrpRatioTable = locateElement("xpath",getLocalePropertyValue("objPERiskMeasuresSharpeRatioTable"));
		List<String> modelTableHeaders1 = new ArrayList<String>(); 
		modelTableHeaders1.add(getLocalePropertyValue("lblPERiskMeasuresSectionSharpeRatioTableCol1").toUpperCase());
		//modelTableHeaders1.add(getLocalePropertyValue("lblPERiskMeasuresSectionStdDevTableCol2").toUpperCase());
		modelTableHeaders1.add(getLocalePropertyValue("lbl3YRS").toUpperCase());
		modelTableHeaders1.add(getLocalePropertyValue("lbl5YRS").toUpperCase());
		modelTableHeaders1.add(getLocalePropertyValue("lbl10YRS").toUpperCase());
		validatePerfTableHeaderData( shrpRatioTable, modelTableHeaders1);
		
		List<String> modelTableValueFormats1 = new ArrayList<String>(); 
		modelTableValueFormats1.add(excelData.get("Standard Deviation Table Ind"));
		//modelTableValueFormats1.add("\\d{4}");
		modelTableValueFormats1.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats1.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		modelTableValueFormats1.add("-*\\d{1,2}"+getLocalePropertyValue("lblAmountSeperator")+"\\d{2}");
		
		validatePerfTableData(shrpRatioTable, modelTableValueFormats1);
		return this;
	}
	public HashMap<String,String> getTestData(String Country,String TestCaseID)
	{
		HashMap<String,String> testData=ExcelDataProvider.getExcelRowAsHashMapByTestID("TestData", Country,TestCaseID);
		return testData;
	}
	
}
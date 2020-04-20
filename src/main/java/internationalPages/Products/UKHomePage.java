package internationalPages.Products;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;


import internationalPages.Marketing.AlternativesPage;
import internationalPages.Marketing.ApplicatoinAndFormsPage;
import internationalPages.Marketing.ApplicatoinFormsDocsPage;
import internationalPages.Marketing.BlogsPage;
import internationalPages.Marketing.CompanyStatementsPage;
import internationalPages.Marketing.ContactUsPage;
import internationalPages.Marketing.DistributionTaxesPage;
import internationalPages.Marketing.DiversityAtWorkPage;
import internationalPages.Marketing.EmergingMarketsPage;
import internationalPages.Marketing.EquityPage;
import internationalPages.Marketing.FixedIncomePage;
import internationalPages.Marketing.FundDocumentsPage;
import internationalPages.Marketing.FundSubscriptionPage;
import internationalPages.Marketing.GlobalInvestmentOutlookPage;
import internationalPages.Marketing.HistoricalPriceTaxesPage;
import internationalPages.Marketing.HistoryPage;
import internationalPages.Marketing.IndependentReviewCommiteePage;
import internationalPages.Marketing.IntroductionToMFPage;
import internationalPages.Marketing.InvestmentOpportunitiesReportsPage;
import internationalPages.Marketing.InvestorEducationPage;
import internationalPages.Marketing.LiteraturePage;
import internationalPages.Marketing.MRFPsQuarterlyReportsPage;
import internationalPages.Marketing.MediaCentrePage;
import internationalPages.Marketing.MediaContactsPage;
import internationalPages.Marketing.MultiAssetPage;
import internationalPages.Marketing.NonResidentTaxFormsPage;
import internationalPages.Marketing.OurFirmPage;
import internationalPages.Marketing.OurInsightsPage;
import internationalPages.Marketing.PFICAnnualInfosPage;
import internationalPages.Marketing.PressReleasesPage;
import internationalPages.Marketing.PriceTaxesPage;
import internationalPages.Marketing.RegulatoryInformationPage;
import internationalPages.Marketing.ReportingFundInfoPage;
import internationalPages.Marketing.ResponsibleInvestingPage;
import internationalPages.Marketing.RiskManagementPage;
import internationalPages.Marketing.SharperFocusOnOutcomesPage;
import internationalPages.Marketing.StatementUserGuidesPage;
import internationalPages.Marketing.SubscriptionPage;
import internationalPages.Marketing.SupportingLegalDocumentsPage;
import internationalPages.Marketing.TreatingCustomersFairlyPage;
import internationalPages.Marketing.UKProfilePage;
import internationalPages.Marketing.WhyChooseFTPage;
import utils.ExcelDataProvider;
import wdMethods.ProjectMethods;

public class UKHomePage extends ProjectMethods{
	public String passedActor;
	
	public UKHomePage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		//System.out.println("From UKPAge");
		try {			
		
			this.driver = driver;
			this.test = test;
			System.out.println(sLocaleFile);
			
			//Loading Locale File
			localeProp.load(new FileInputStream(new File(sLocaleFile)));
			
			//Switch to Preferred Execution Language 
			selectExecutionLanguageForCountry();
			
			//System.out.println(getTitle());
			//System.out.println(localeProp.getProperty("lblHmPageTitle"));
			if(localeProp.getProperty("lblHmPageTitle").isEmpty())
				reportStep("'" + sEnvironment + " - " + sCountryName + "' Locale File may not Loaded. Please check the Config. Refer snap", "FAIL");
			
		
				if(!getTitle().equals(localeProp.getProperty("lblHmPageTitle"))) 				
					reportStep("'" + sEnvironment + " - " + sCountryName + "' Site may down or Home Page Expected Title may not displayed. Refer snap", "FAIL");	
			
			// Initialize the webelements inside the page
			PageFactory.initElements(driver, this);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void selectExecutionLanguageForCountry() throws InterruptedException, FileNotFoundException, IOException {
		
		switch(sCountryName) {
		
		case "Switzerland": selectExecutionLanguageSwitzerland();
							break;
		case "Offshore": 	selectExecutionLanguageOffshore();
							break;
		case "Canada": 		selectExecutionLanguageCanada();
							break;
		default:			//reportStep("No such Country '" + sCountryName + "' available for Language selection. Please refer the Base Data","INFO");
							break;
		}
		
	}
	
	public void selectExecutionLanguageSwitzerland() throws InterruptedException {
	
		switch(sExecutionLanguage) {
		
		case "English": 	reportStep("Executing in default language:ENGLISH","INFO");
							break;
		case "German": 		click(locateElement("xpath",getLocalePropertyValue("objHmPageGermanLanguage")));
							break;
		default:			reportStep("Language Provided in file is not applicable for this country, Please refer the file for more info.","FAIL");
							break;		
		
		}
		
	}
	
	public void selectExecutionLanguageCanada() throws InterruptedException {
		
		switch(sExecutionLanguage) {
		
		case "English": 	reportStep("Executing in default language:ENGLISH","INFO");
							break;
		case "French": 		if(locateElements("xpath",getLocalePropertyValue("objHmPageGermanLanguage")).size()>0)
							click(locateElement("xpath",getLocalePropertyValue("objHmPageGermanLanguage")));
							break;
		default:			reportStep("Language Provided in file is not applicable for this country, Please refer the file for more info.","FAIL");
							break;		
		
		}
		
	}
	
	public void selectExecutionLanguageOffshore() throws FileNotFoundException, InterruptedException, IOException {
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		
		switch(sExecutionLanguage) {
		
		case "English": 	reportStep("Execution will continue for default language:ENGLISH","INFO");
							break;
		case "Spanish": 	click(locateElement("xpath",getLocalePropertyValue("objHmPageSelectLangPopupSpanishLink")));
							wait.until(ExpectedConditions.attributeContains(locateElement("xpath",getLocalePropertyValue("objHmPageSelectLangPopupSpanishLink")), "class", "selected"));
							break;
		case "Portuguese": 	click(locateElement("xpath",getLocalePropertyValue("objHmPageSelectLangPopupPortugueseLink")));
							wait.until(ExpectedConditions.attributeContains(locateElement("xpath",getLocalePropertyValue("objHmPageSelectLangPopupPortugueseLink")), "class", "selected"));
							break;
		default:			reportStep("Language Provided in file is not applicable for this country, Please refer the file for more info.","FAIL");
							break;
		}
		
		loginAsAdvisor();
	}
	
	public UKHomePage selectActor(String actor) throws InterruptedException, FileNotFoundException, IOException {	
		
		
		
	
		
		
		passedActor=actor;
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		Thread.sleep(5000);
		switch(actor) {
		
		case "Individual Investor":	
									wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageActor"))));
									click(locateElement(getLocalePropertyValue("objHmPageActor")));
									click(locateElement("partial", getLocalePropertyValue("objHmPageIndividualInvestor")),true);
								    break;
		case "Financial Advisor":	
									wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageActor"))));
									click(locateElement(getLocalePropertyValue("objHmPageActor")));
									clickOnFALink();
								    break;		
		case "Shareholder":			
									wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageActor"))));
									click(locateElement(getLocalePropertyValue("objHmPageActor")));
									click(locateElement("partial", getLocalePropertyValue("objHmPageShareholder")),true);
									break;
									
		case "Institutional Investor":			
									wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageActor"))));
									click(locateElement(getLocalePropertyValue("objHmPageActor")));
									click(locateElement("xpath", getLocalePropertyValue("objHmPageInstitutional")),true);
									loginForInstituionalInvestor();
									break;
									
		default					:	reportStep("Actor " + actor  + " provided may not handled / Not Applicable, Please refer the file for more info.","INFO");
					 				break;
		}
		
		return this;
	}

	
	public void clickOnFALink() throws InterruptedException, FileNotFoundException, IOException {
		
		switch(sCountryName) {
		
		case "Offshore": 	clickOnFinAdvLinkWithoutLoginPopup();
							break;
		case "Canada": 		advisorLoginCanada();
							break;
		default		   :	clickOnFinAdvLinkAlt();
	    					loginAsAdvisor();
							break;
		}
		
	
		
	}
	
	public void loginForInstituionalInvestor() throws InterruptedException, FileNotFoundException, IOException {
		
		switch(sCountryName) {
		
		case "German": 		loginForInstituionalInvestorGermany();
							break;
		
		default		   :	reportStep("Institutional Investor Login is not applicble to this country","FAIL");
							break;
		}
		
	}
	
	public void loginForInstituionalInvestorGermany() throws InterruptedException {
		reportStep("Click on down Arrow in Institutional Investor login popup","INFO");
		click(locateElement("xpath","//span[@class='ft-icon ft-icon-down-carrot']"));
		Thread.sleep(3000);
		reportStep("Click on agree terms checkbox in Institutional Investor login popup","INFO");
		boolean vbool=false;
		for(WebElement ele:locateElements("xpath","//input[@name='agree-terms']")) {
			if(ele.isDisplayed()) {
				click(ele,true);
				vbool=true;
				break;
			}
		}
		
		if(!vbool)
			reportStep("Checkbox was not clicked, Script failed","FAIL");
		
		Thread.sleep(3000);
		
		reportStep("Click on agree button in Institutional Investor login popup","INFO");
		click(locateElement("xpath","//a[text()='Zustimmen']"));
	}
	
/*	@FindBy(how=How.ID,using="products")
	private WebElement eleProducts;*/	

	public UKHomePage clickProducts() throws InterruptedException {
		
	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageProductsLink"))));
		click(locateElement(localeProp.getProperty("objHmPageProductsLink")),true);
	
		return this;
	}
	
	
	public UKHomePage clickOnFinAdvLinkAlt() throws InterruptedException {
		WebElement drpdwn =  locateElement(getLocalePropertyValue("objHmPageActor"));
		for(int i=0;i<4;i++) {
		if(!drpdwn.getAttribute("aria-expanded").contains("true")) {
			//reportStep("Try number :"+(i+1),"INFO");
			click(drpdwn);
			Thread.sleep(4000);
		}else
			break;
		}
		List<WebElement> fAdvLnk = locateElements("xpath", getLocalePropertyValue("objHmPageFinancialAdvisor"));
		if(fAdvLnk.size()>0)
			reportStep("actor provided in xls file is applicable to this country.","PASS");
			else
			reportStep("actor provided in xls file is not applicable to this country, Please refer the file.","FAIL");
		
		WebElement element = locateElement("xpath", getLocalePropertyValue("objHmPageFinancialAdvisor"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		WebElement accptBtn = locateElement("xpath",getLocalePropertyValue("objAdvLoginAcceptBtn"));
		
		for(int i=0;i<3;i++) {
		if(!accptBtn.isDisplayed()) {
			//reportStep("click on actor link, Retry: "+(i+1),"INFO");
			driver.navigate().refresh();
			Thread.sleep(5000);
			click(locateElement(getLocalePropertyValue("objHmPageActor")));
			element = locateElement("xpath", getLocalePropertyValue("objHmPageFinancialAdvisor"));
			executor.executeScript("arguments[0].click();", element);
			accptBtn = locateElement("xpath",getLocalePropertyValue("objAdvLoginAcceptBtn"));
		}
		else
			break;
		}
		return this;
	}
	
	public UKHomePage clickOnFinAdvLinkWithoutLoginPopup() throws InterruptedException {
		WebElement drpdwn =  locateElement(getLocalePropertyValue("objHmPageActor"));
		for(int i=0;i<4;i++) {
		if(!drpdwn.getAttribute("aria-expanded").contains("true")) {
			//reportStep("Try number :"+(i+1),"INFO");
			click(drpdwn,true);
			Thread.sleep(4000);
		}else
			break;
		}
		List<WebElement> fAdvLnk = locateElements("xpath", getLocalePropertyValue("objHmPageFinancialAdvisor"));
		if(fAdvLnk.size()>0)
			reportStep("actor provided in xls file is applicable to this country.","PASS");
			else
			reportStep("actor provided in xls file is not applicable to this country, Please refer the file.","FAIL");
		
		WebElement element = locateElement("xpath", getLocalePropertyValue("objHmPageFinancialAdvisor"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		
		return this;
	}
	
	
/*	@FindBy(how=How.XPATH,using="(//span[text()='Price and Performance'])[1]")
	private WebElement elePriceAndPerformance;*/
	
	public PriceAndPerformancePage clickPriceAndPerformance() throws FileNotFoundException, IOException
	{	
		try {
			WebElement pplink=null;
			if(sCountryName.equals("Canada") && passedActor.equals("Financial Advisor"))
				 pplink = locateElement("xpath", getLocalePropertyValue("objHmPagePriceAndPerformanceLinkAdvisor"));		
			else
				pplink = locateElement("xpath", getLocalePropertyValue("objHmPagePriceAndPerformanceLink"));		
		
		for(int i=0;i<5;i++) {
		if(!pplink.isDisplayed()) {
		//	reportStep("Retrying the productsLink click","INFO");
			click(locateElement(localeProp.getProperty("objHmPageProductsLink")),true);
			Thread.sleep(3000);
			}else {
				break;
						}
	}
		reportStep("Clicking price & performance link","INFO");
		click(pplink,true);
		//pplink.click();
	
		}
		catch(Exception e) {
			reportStep(e.getMessage(),"FAIL");
		}
		return new PriceAndPerformancePage(driver,test);
	}
	public UKHomePage loginAsAdvisor() throws InterruptedException, FileNotFoundException, IOException {	
		
		
		switch(sCountryName) {
			case "UK": 			loginAsAdvisorItaly();
								break;
			case "Italy": 		loginAsAdvisorItaly();
								break;
			case "Luxembourg":	loginAsAdvisorLux(); 
					 			break;
			case "German":		loginAsAdvisorLux(); 
								break;
			case "Austria":		loginAsAdvisorLux(); 
								break;
			case "Switzerland":	reportStep("Financial Advisor Actor not defined for this country, Please check the actor name","FAIL");
								break;
			case "Singapore":   reportStep("Financial Advisor Actor not defined for this country, Please check the actor name","FAIL");
								break;
			case "Offshore":	loginAsAdvisorLux(); 
								break;
			default:			reportStep("Country Name not defined for any case, Please check the country name","FAIL");
								break;
			}
				return this;
		}

	public UKHomePage loginAsAdvisorLux() throws InterruptedException, FileNotFoundException, IOException {	
		reportStep("Login as Advisor","INFO");
			
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locateElement("xpath", getLocalePropertyValue("objAdvLoginUserId")));
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objAdvLoginUserId"))));
		
		
		type(locateElement("xpath",getLocalePropertyValue("objAdvLoginUserId")),getLocalePropertyValue("ADVISOR_USERNAME"));
		type(locateElement("xpath",getLocalePropertyValue("objAdvLoginPassword")),getLocalePropertyValue("ADVISOR_PASS"));
		click(locateElement("xpath",getLocalePropertyValue("objAdvLoginAgreeTerms")));
		
		click(locateElement("xpath",getLocalePropertyValue("objAdvLoginAcceptBtn")),true);
		
		
		
//		Thread.sleep(2000);
//		if(locateElements("xpath",getLocalePropertyValue("objAdvLoginWrongUserIdMsg")).size()>0) {
//			if(locateElements("xpath",getLocalePropertyValue("objAdvLoginWrongUserIdMsg")).get(0).isDisplayed())
//			reportStep("UserId/Password is not correct, Login Failed.","FAIL");
//		}
		
		Thread.sleep(3000);
		return this;
		}
	
	
	public UKHomePage loginAsAdvisorItaly() throws InterruptedException, FileNotFoundException, IOException {	
		reportStep("Login as Advisor","INFO");

		click(locateElement("xpath",getLocalePropertyValue("objAdvLoginAcceptBtn")),true);
		Thread.sleep(2000);
		return this;
		}
	
	//Accounts Module
	
	public UKHomePage accountsLogin() throws InterruptedException, FileNotFoundException, IOException {	
		reportStep("Login into Accounts Portal","INFO");
			
		driver.get("https://ftcastg1.corp.frk.com/ca/retail/app/access/view/sign_in_page.jsf");
		
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objAccLoginPageLoginName"))));
		
		
		type(locateElement("xpath",getLocalePropertyValue("objAccLoginPageLoginName")),getLocalePropertyValue("ACCOUNTS_USERNAME"));
		type(locateElement("xpath",getLocalePropertyValue("objAccLoginPagePassword")),getLocalePropertyValue("ACCOUNTS_PASS"));
		click(locateElement("xpath",getLocalePropertyValue("objAccLoginPageRemLogin")));
		
		click(locateElement("xpath",getLocalePropertyValue("objAccLoginPageLoginButton")));
		
		Thread.sleep(10000);
		return this;
		}
	
	public UKHomePage advisorLoginCanada() throws InterruptedException, FileNotFoundException, IOException {	
		reportStep("Login with advisor credentials","INFO");
			
		click(locateElement(getLocalePropertyValue("objAdvLoginPageSingInBtn")));
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objAdvLoginPageSingInForm"))));
		
		List<WebElement> userIds = locateElements("xpath",getLocalePropertyValue("objAdvLoginPageLoginName"));
		for(WebElement uid: userIds) {
			if(uid.isDisplayed()) {
				type(uid,getLocalePropertyValue("ACCOUNTS_USERNAME"));
				break;
								}
			}
		List<WebElement> passwords = locateElements("xpath",getLocalePropertyValue("objAdvLoginPagePassword"));
		for(WebElement pwd: passwords) {
			if(pwd.isDisplayed()) {
				type(pwd,getLocalePropertyValue("ACCOUNTS_PASS"));
				break;
								}
			}
		
		List<WebElement> signinBtns = locateElements("xpath",getLocalePropertyValue("objAdvLoginPageLoginButton"));
		for(WebElement sins: signinBtns) {
			if(sins.isDisplayed()) {
				click(sins);
				break;
								}
			}
		
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objAdvLoginPageAfterLoginViewAccountsButton"))));
		return this;
		}
	
		/* MARKETING TEST CASES	BELOW*/
	
	public void checkMenuItemIsOpen(WebElement menuItem) {
		
		if(menuItem.getAttribute("aria-expanded").equals("false")) 
		click(menuItem);
				
		
	}
	
	public UKHomePage clickInsights() throws InterruptedException {
		
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageInsightsLink"))));
		click(locateElement(localeProp.getProperty("objHmPageInsightsLink")),true);
	
		checkMenuItemIsOpen(locateElement(localeProp.getProperty("objHmPageInsightsLink")));
		
		return this;
	}
	
	public UKHomePage clickResources() throws InterruptedException {
		
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageResourcesLink"))));
		click(locateElement(localeProp.getProperty("objHmPageResourcesLink")),true);
	
		checkMenuItemIsOpen(locateElement(localeProp.getProperty("objHmPageResourcesLink")));
		return this;
	}
	
	public UKHomePage clickPlanning() throws InterruptedException {
		
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPagePlanningLink"))));
		click(locateElement(localeProp.getProperty("objHmPagePlanningLink")),true);
	
		checkMenuItemIsOpen(locateElement(localeProp.getProperty("objHmPagePlanningLink")));
		
		return this;
	}

	public UKHomePage validatePlanningMegamenu() throws InterruptedException {
		
		
		reportStep("Validating different links in Planning megamenu","INFO");
		
		click(locateElement("xpath",getLocalePropertyValue("objPlanningMegamenuInvstRetirment")),true);
		verifyBreadCrumb("Investing for Retirement", getLocalePropertyValue("objInvstRetirmentBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
	
		clickPlanning();
		click(locateElement("xpath",getLocalePropertyValue("objTaxFreeSavingsLink")));
		verifyBreadCrumb("Tax-Free Savings Account", getLocalePropertyValue("objTaxFreeSavingsBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyElementExist("xpath",getLocalePropertyValue("objTaxFreeSavingsPageHeader"),"Page Header",true);
		
		clickPlanning();
		click(locateElement("xpath",getLocalePropertyValue("objInvestorEduLink")));
		verifyBreadCrumb("Investor Education", getLocalePropertyValue("objInvestEduBreadCrumb"),locateElement("xpath", getLocalePropertyValue("objBreadCrumb")));
		verifyBannerImageAndHeading("/content-international/images/ftinstitutional-us/billboard/Insights_banner.jpg", getLocalePropertyValue("lblInvestorEduction"));
		
		String linkText = locateElement("xpath","//h2/a").getText();
		click(locateElement("xpath","//h2/a"));	
		verifyExactText(locateElement("xpath","//h1"), linkText, "Page Header",true);
		
		
		return this;
	}
	
	public UKHomePage clickOurCompany() throws InterruptedException {
		
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageOurCompanyLink"))));
		click(locateElement(localeProp.getProperty("objHmPageOurCompanyLink")),true);
	
		checkMenuItemIsOpen(locateElement(localeProp.getProperty("objHmPageOurCompanyLink")));
		return this;
	}

	
	public GlobalInvestmentOutlookPage clickGlobalInvestmentOutllook() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageGIOLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageGIOLink")));
		
		return new GlobalInvestmentOutlookPage(driver,test);
	}
	
	public BlogsPage clickBlogs() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageBlogsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageBlogsLink")));
		
		return new BlogsPage(driver,test);
	}

	public IndependentReviewCommiteePage clickIndependentReviewCommitee() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageIndependentReviewCommiteeLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageIndependentReviewCommiteeLink")));
		
		return new IndependentReviewCommiteePage(driver,test);
	}
	
	
	
	public OurInsightsPage clickOurInsights() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageOurInsightsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageOurInsightsLink")));
		
		return new OurInsightsPage(driver,test);
	}
	
	public AlternativesPage clickAlternatives() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageAlternativesLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageAlternativesLink")));
		
		return new AlternativesPage(driver,test);
	}
	
	public EmergingMarketsPage clickEmergingMarkets() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageEmergingMarketsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageEmergingMarketsLink")));
		
		return new EmergingMarketsPage(driver,test);
	}
	
	public FixedIncomePage clickFixedIncome() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageFixedIncomeLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageFixedIncomeLink")));
		
		return new FixedIncomePage(driver,test);
	}
	
	public MultiAssetPage clickMultiAsset() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageMultiAssetLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageMultiAssetLink")));
		
		return new MultiAssetPage(driver,test);
	}
	
	public EquityPage clickEquity() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageEquityLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageEquityLink")));
		
		return new EquityPage(driver,test);
	}
	
	public OurFirmPage clickOurFirm() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageOurFirmLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageOurFirmLink")));
		
		return new OurFirmPage(driver,test);
	}
	
	public WhyChooseFTPage clickWhyChooseFT() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageWhyChooseFTLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageWhyChooseFTLink")));
		
		return new WhyChooseFTPage(driver,test);
	}

	public HistoryPage clickHistory() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageHistoryLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageHistoryLink")));
		
		return new HistoryPage(driver,test);
	}

	
	public RiskManagementPage clickRiskManagement() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageRiskMgmtLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageRiskMgmtLink")));
		
		return new RiskManagementPage(driver,test);
	}

	public ResponsibleInvestingPage clickResponsibleInvesting() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageResponsibleInvstLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageResponsibleInvstLink")));
		
		return new ResponsibleInvestingPage(driver,test);
	}

	
	public DiversityAtWorkPage clickDvrstyWork() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageDiverstityWorkLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageDiverstityWorkLink")));
		
		return new DiversityAtWorkPage(driver,test);
	}
	
	
	public SharperFocusOnOutcomesPage clickSharperFocus() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageSharperFocusLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageSharperFocusLink")));
		
		return new SharperFocusOnOutcomesPage(driver,test);
	}

	public TreatingCustomersFairlyPage clickTreatCustomerFairly() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageTreatingCustFairlyLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageTreatingCustFairlyLink")));
		
		return new TreatingCustomersFairlyPage(driver,test);
	}
	
	public RegulatoryInformationPage clickRegulatoryInformation() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageRegulatoryInfoLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageRegulatoryInfoLink")));
		
		return new RegulatoryInformationPage(driver,test);
	}
	
	public UKProfilePage clickUKProfile() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageUKProfileLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageUKProfileLink")));
		
		return new UKProfilePage(driver,test);
	}
	public MediaCentrePage clickMediaCentre() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageMediaCentreLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageMediaCentreLink")));
		
		return new MediaCentrePage(driver,test);
	}
	
	public MediaContactsPage clickMediaContacts() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageMediaContactsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageMediaContactsLink")));
		
		return new MediaContactsPage(driver,test);
	}

	public PressReleasesPage clickPressReleases() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPagePressReleasesLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPagePressReleasesLink")));
		
		return new PressReleasesPage(driver,test);
	}

	public CompanyStatementsPage clickCompanyStatements() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageCompanyStatementsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageCompanyStatementsLink")));
		
		return new CompanyStatementsPage(driver,test);
	}
	
	public SubscriptionPage clickSubscription() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageSubscriptionLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageSubscriptionLink")));
		
		return new SubscriptionPage(driver,test);
	}
	
	public ContactUsPage clickContactUs() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageContactUsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageContactUsLink")));
		
		return new ContactUsPage(driver,test);
	}
	
	public LiteraturePage clickLiterature() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageLiteratureLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageLiteratureLink")));
		
		return new LiteraturePage(driver,test);
	}
	
	public FundDocumentsPage clickFundDocuments() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageFundDocumentsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageFundDocumentsLink")));
		
		return new FundDocumentsPage(driver,test);
	}
	
	public ApplicatoinFormsDocsPage clickApplicationFormsDocs() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageApplFormDocLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageApplFormDocLink")));
		
		return new ApplicatoinFormsDocsPage(driver,test);
	}
	
	public ApplicatoinAndFormsPage clickApplicationFormsSubLink() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageApplFormDocSubLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageApplFormDocSubLink")));
		
		return new ApplicatoinAndFormsPage(driver,test);
	}
	
	public SupportingLegalDocumentsPage clickSupportingLegalDoc() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageSupportingLegalDocLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageSupportingLegalDocLink")));
		
		return new SupportingLegalDocumentsPage(driver,test);
	}
	
	public NonResidentTaxFormsPage clickNonResidentTaxForms() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageNonResidentTaxFormsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageNonResidentTaxFormsLink")));
		
		return new NonResidentTaxFormsPage(driver,test);
	}
	
	public PFICAnnualInfosPage clickPFICAnnualInfoStatments() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPagePFICAnnualInfoStatementsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPagePFICAnnualInfoStatementsLink")));
		
		return new PFICAnnualInfosPage(driver,test);
	}
	
	public MRFPsQuarterlyReportsPage clickMRFPsQuarterlyReports() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageMRFPsQuarterlyReportsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageMRFPsQuarterlyReportsLink")));
		
		return new MRFPsQuarterlyReportsPage(driver,test);
	}

	
	public UKHomePage clickPointofSaleFundFactDocuments() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPagePointofSaleFundFactDocumentsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPagePointofSaleFundFactDocumentsLink")));
		
		return this;
	}
	
	public UKHomePage validatePointOfSaleLinkAndLayout() throws FileNotFoundException, IOException, InterruptedException {
		
		WebElement popupHeader = locateElement("xpath","//div[@class='modal-content']//h4[text()='Leaving Franklin Templeton']");
		if(popupHeader.isDisplayed())
			reportStep("Leaving FT site popup is displayed", "PASS",true);
			else
				reportStep("Leaving FT site popup is not displayed", "FAIL",true);
		
		reportStep("Click on close popup button", "INFO");
		
		click(locateElement("xpath","//div[@class='modal-content']//a[text()='Close']"));
		
		reportStep("Again Click on Point of Sale Link And Layout in megamenu", "INFO");
		clickPointofSaleFundFactDocuments();
		
		reportStep("Click on Ok popup button", "INFO");
		
		click(locateElement("xpath","//div[@class='modal-content']//a[text()='OK']"));
		
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		 	    		    
	verifyElementExist("xpath", "//div[contains(text(),'Point of Sale Fund Facts Documents')]","Page Header",true);
	
	verifyElementExist("xpath", "//th[contains(text(),'Fund Fact Sheets')]","Fund Fact Sheets Header",true);
	
	reportStep("Validate Mutual Funds Radio button", "INFO");
	verifyExactAttribute(locateElements("xpath","//input[@type='radio']").get(0),"name" ,"mutualFunds",true);
	
	reportStep("Validate ETFs Radio button", "INFO");
	verifyExactAttribute(locateElements("xpath","//input[@type='radio']").get(1),"name" ,"etfs",true);
		
		driver.close();
		driver.switchTo().window(tabs.get(0));	
		
		return this;
	}
	
	public ReportingFundInfoPage clickReportingFundInfo() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageReportingFundInfoLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageReportingFundInfoLink")));
		
		return new ReportingFundInfoPage(driver,test);
	}
	
	public StatementUserGuidesPage clickStatementUserGuides() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageReportingStatementUserGuidesLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageReportingStatementUserGuidesLink")));
		
		return new StatementUserGuidesPage(driver,test);
	}

	public FundSubscriptionPage clickFundSubscription() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageFundSubscriptoinLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageFundSubscriptoinLink")));
		
		return new FundSubscriptionPage(driver,test);
	}
	
	public InvestorEducationPage clickInvestorEducation() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageInvestEduLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageInvestEduLink")));
		
		return new InvestorEducationPage(driver,test);
	}

	public IntroductionToMFPage clickIntroToMF() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageIntoMFLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageIntoMFLink")));
		
		return new IntroductionToMFPage(driver,test);
	}
	
	public DistributionTaxesPage clickDistributionTaxes() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageDistTaxesLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageDistTaxesLink")));
		
		return new DistributionTaxesPage(driver,test);
	}
	
	public PriceTaxesPage clickPriceTaxes() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPagePriceTaxesLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPagePriceTaxesLink")));
		
		return new PriceTaxesPage(driver,test);
	}
	
	public HistoricalPriceTaxesPage clickHistoricalPriceTaxes() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageHistoricalPriceTaxesLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageHistoricalPriceTaxesLink")));
		
		return new HistoricalPriceTaxesPage(driver,test);
	}
	
	public UKHomePage clickInvestmentOpportunities() throws InterruptedException {
		
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageInvstOpportunities"))));
		click(locateElement(localeProp.getProperty("objHmPageInvstOpportunities")),true);
	
		return this;
	}
	
	public InvestmentOpportunitiesReportsPage clickInvstOppReports() throws FileNotFoundException, IOException, InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objHmPageInvstOpportunitiesReportsLink"))));
		
		click(locateElement("xpath",localeProp.getProperty("objHmPageInvstOpportunitiesReportsLink")));
		
		return new InvestmentOpportunitiesReportsPage(driver,test);
	}
	
	
	
//	Smoke Test scenarios

	public UKHomePage validateFooter() throws InterruptedException, FileNotFoundException, IOException {	
		
		moveContextToElement(locateElement("xpath",localeProp.getProperty("objHmPageFooterCountryName")));
		verifyElementExist("xpath", localeProp.getProperty("objHmPageFooterSection"), "Footer Section",true);
		System.out.println(locateElement("xpath",localeProp.getProperty("objHmPageFooterCountryFlag")).getAttribute("src"));
		System.out.println(getLocalePropertyValue("lblHmPageFooterCountryFlag"));
		if(locateElement("xpath",localeProp.getProperty("objHmPageFooterCountryFlag")).getAttribute("src").contains(getLocalePropertyValue("lblHmPageFooterCountryFlag")))
			reportStep("The Country Flag is displayed as expected at the bottom of the Home Page","PASS");
		else
			reportStep("The Country Flag is not displayed at the bottom of the Home Page","FAIL");
		
		verifyExactText(locateElement("xpath",localeProp.getProperty("objHmPageFooterCountryName")), getLocalePropertyValue("lblHmPageFooterCountryName"),"Country Name beside Flag",true);
		
		return this;
		}
	

	public UKHomePage validateSearch() throws InterruptedException, FileNotFoundException, IOException {	
	
		reportStep("Validate the Search box in HomePage","INFO");
		
	click(locateElement("xpath",localeProp.getProperty("objHmPageSearchIcon")));

	reportStep("Validate the innertext of search box - 'Search by keyword, literature, fund name or symbol'","INFO");
	verifyExactAttribute(locateElement("xpath",localeProp.getProperty("objHmPageInput")), "placeholder", "Search by keyword, literature, fund name or symbol");
	
	verifyElementExist("xpath", "//p[text()='Close']/..","close(X) button");
	
	type(locateElement("xpath",localeProp.getProperty("objHmPageInput")),"K2");
	Thread.sleep(5000);
	click(locateElement("xpath",localeProp.getProperty("objHmPageSearchButton")));
	Thread.sleep(5000);
	
	//verifyElementExist("xpath","//h1",getLocalePropertyValue("lblSearchHeader"),true);
	
	if(locateElements("xpath",localeProp.getProperty("objSearchPageTabButtons")).size()==4)
		reportStep("The 4 Tabs(All, Funds, Literature, Other Results) in search Page are displayed as expected","PASS");
	else
		reportStep("The 4 Tabs(All, Funds, Literature, Other Results) in search Page are not displayed","FAIL");
	
	if(locateElements("xpath",localeProp.getProperty("objSearchPageTabButtons")).get(0).getAttribute("class").contains("active"))
		reportStep("Tab Name - 'all' is active by default","PASS");
	else
		reportStep("Tab Name - 'all' is not active by default","PASS");
	
	reportStep("Validating the fund attributes for 1st fund card in all tab","INFO");
	verifyFundCardInAllTab();
	
	reportStep("Click on Funds tab","INFO");
	click(locateElements("xpath",localeProp.getProperty("objSearchPageTabButtons")).get(1));
	
	reportStep("Validating the fund attributes for 1st fund card in Fund tab","INFO");
	verifyFundCardInFundTab();
	
	reportStep("Click on Literature tab","INFO");
	click(locateElements("xpath",localeProp.getProperty("objSearchPageTabButtons")).get(2));
	
	reportStep("Validating 1st card in Literature tab","INFO");
	verifyLiteratureTab();

	reportStep("Click on Other Results tab","INFO");
	click(locateElements("xpath",localeProp.getProperty("objSearchPageTabButtons")).get(3));
	
	reportStep("Validating 1st card in Other Results tab","INFO");
	verifyOtherResultsTab();
	
	reportStep("Validating wrong key words searches","INFO");
	
	type(locateElement("xpath",localeProp.getProperty("objHmPageInput")),"2314XXXX");
	Thread.sleep(5000);
	click(locateElement("xpath",localeProp.getProperty("objHmPageSearchButton")));
	Thread.sleep(5000);
	
	verifyElementExist("xpath", "//h2[@class='panel-results__title no-results']", "No Results Message for Wrong Keyword Search",true);
	
	return this;
	}
	
	public void verifyOtherResultsTab() {
		
		verifyElementExist("xpath", "//h2[text()='Other Results']", "Other Results Tab Header");
		List<WebElement>  otherResults = locateElements("xpath",localeProp.getProperty("objSearchPageOtherResults"));
		
		if(otherResults.size()>0)
			reportStep(otherResults.size()+" Other Results are displayed for search criteria","PASS",true);
		else
			reportStep("No Other Results are displayed for search criteria","FAIL",true);
		
		reportStep("validate attributes of 1st PDF displayed","INFO");
		

		if(otherResults.get(0).findElements(By.tagName("img")).size()==1)
			reportStep("Ben Image is displayed for 1st Card","PASS");
		else
			reportStep("Ben Image is not displayed for 1st Card","PASS");
		
		if(otherResults.get(0).findElements(By.xpath(".//a[@innerhtml.bind='page.title']")).size()==1)
			reportStep(otherResults.get(0).findElements(By.xpath(".//a[@innerhtml.bind='page.title']")).get(0).getText()+" Title is displayed for 1st Card","PASS");
		else
			reportStep("Title is not displayed for 1st Card","FAIL");
		
		reportStep("validate the Search Text is highlighed in Bold in 1st card Content","INFO");
		if(otherResults.get(0).findElements(By.xpath(".//div[@innerhtml.bind='page.content']//strong")).get(0).getText().equals("K2"))
			reportStep("Search Text 'K2' is highlighted in bold in Card Content","PASS");
		else
			reportStep("Search Text 'K2' is not highlighted in bold in fund Card Content","PASS");
		
	}

	public void verifyLiteratureTab() {
		
		verifyElementExist("xpath", "//h2[text()='Literature']", "Literature Tab Header");
		List<WebElement>  pdfs = locateElements("xpath",localeProp.getProperty("objSearchPageLiterature"));
		
		if(pdfs.size()>0)
			reportStep(pdfs.size()+" PDF are displayed for search criteria","PASS",true);
		else
			reportStep("No PDF are displayed for search criteria","FAIL",true);
		
		reportStep("validate attributes of 1st PDF displayed","INFO");
		

		if(pdfs.get(0).findElements(By.tagName("img")).size()==1)
			reportStep("PDF Image is displayed for 1st PDF","PASS");
		else
			reportStep("PDF Image is not displayed for 1st PDF","PASS");
		
		if(pdfs.get(0).findElements(By.xpath(".//a[@innerhtml.bind='literature.title']")).size()==1)
			reportStep(pdfs.get(0).findElements(By.xpath(".//a[@innerhtml.bind='literature.title']")).get(0).getText()+" PDF Title is displayed for 1st PDF","PASS");
		else
			reportStep("PDF Title is not displayed for 1st PDF","FAIL");
		
		if(pdfs.get(0).findElements(By.xpath(".//i[@class='ft-icon ft-icon-download']")).size()==1)
			reportStep("Download button is displayed for 1st PDF","PASS");
		else
			reportStep("Download Button is not displayed for 1st PDF","FAIL");
		
		reportStep("Click on Download button of 1st PDF","INFO");
		click(pdfs.get(0).findElements(By.xpath(".//i[@class='ft-icon ft-icon-download']")).get(0));
		
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		 	    		    
		verifyTwoStringsPartially(driver.getCurrentUrl(), "pdf");
		
		driver.close();
		driver.switchTo().window(tabs.get(0));	
	}
	
	public void verifyFundCardInFundTab() {
		
		verifyElementExist("xpath", "//h2[text()='Funds']", "Fund Tab Header");
		List<WebElement>  funds = locateElements("xpath",localeProp.getProperty("objSearchPageFunds"));
		
		if(funds.size()>0)
			reportStep(funds.size()+" funds are displayed for search criteria","PASS",true);
		else
			reportStep("No funds are displayed for search criteria","FAIL",true);
		
		reportStep("validate attributes of 1st fund card","INFO");

		if(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.tickerSymbol']")).size()==2)
			reportStep(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.tickerSymbol']")).get(0).getText()+" share Class distribution with currency is displayed for 1st fund","PASS");
		else
			reportStep("share Class distribution with currency is not displayed for 1st fund","FAIL");
		
		if(funds.get(0).findElements(By.xpath(".//a[@innerhtml.bind='fund.title']")).size()==1)
			reportStep(funds.get(0).findElements(By.xpath(".//a[@innerhtml.bind='fund.title']")).get(0).getText()+" fund Name is displayed for 1st fund","PASS");
		else
			reportStep("fund Name is not displayed for 1st fund","FAIL");
		
		if(funds.get(0).findElements(By.xpath(".//small[@innerhtml.bind='fund.fundType']")).size()==1)
			reportStep(funds.get(0).findElements(By.xpath(".//small[@innerhtml.bind='fund.fundType']")).get(0).getText()+" Asset Class is displayed for 1st fund","PASS");
		else
			reportStep("Asset Class is not displayed for 1st fund","FAIL");
		
		if(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.navDate']")).size()==1)
			reportStep(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.navDate']")).get(0).getText()+" As of Date is displayed for 1st fund","PASS");
		else
			reportStep("As of Date is not displayed for 1st fund","FAIL");
		
		validateFormat(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.navDate']")).get(0), "[0-3]\\d.\\d{2}.\\d{4}", "As of Date Format- 1st Fund");
		
		validateFormat(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.nav']")).get(0), "[$€]\\d{1,2}\\.\\d{2}", "NAV Format- 1st Fund");
		
		validateFormat(funds.get(0).findElements(By.xpath(".//small[@innerhtml.bind='fund.navChange']")).get(0), "-*[$€]\\d{1,2}\\.\\d{2}", "NAV Change Format- 1st Fund");
	
		reportStep("validate the Search Text is highlighed in Bold in fund name for 1st card","INFO");
		if(funds.get(0).findElements(By.xpath(".//a[@innerhtml.bind='fund.title']//strong")).get(0).getText().equals("K2"))
			reportStep("Search Text 'K2' is highlighted in bold in fund name","PASS");
		else
			reportStep("Search Text 'K2' is not highlighted in bold in fund name","PASS");
		
		
		reportStep("validate the product page links for 1st fund","INFO");
		
		verifyExactText(funds.get(0).findElements(By.xpath(".//ul//li")).get(0), "Overview","Overview Link");
		verifyExactText(funds.get(0).findElements(By.xpath(".//ul//li")).get(1), "Portfolio","Portfolio Link");
		verifyExactText(funds.get(0).findElements(By.xpath(".//ul//li")).get(2), "Performance","Performance Link");
		verifyExactText(funds.get(0).findElements(By.xpath(".//ul//li")).get(3), "Price / Distribution","Price / Distribution Link");
		verifyExactText(funds.get(0).findElements(By.xpath(".//ul//li")).get(4), "Documents","Documents Link");
	}

	
	public void verifyFundCardInAllTab() {
		List<WebElement>  funds = locateElements("xpath",localeProp.getProperty("objSearchPageFunds"));
		
		
		
		if(funds.size()>0)
			reportStep(funds.size()+" funds are displayed for search criteria","PASS",true);
		else
			reportStep("No funds are displayed for search criteria","FAIL",true);
		
		reportStep("validate attributes of 1st fund card","INFO");

		if(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.tickerSymbol']")).size()==2)
			reportStep(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.tickerSymbol']")).get(0).getText()+" share Class distribution with currency is displayed for 1st fund","PASS");
		else
			reportStep("share Class distribution with currency is not displayed for 1st fund","FAIL");
		
		if(funds.get(0).findElements(By.xpath(".//a[@innerhtml.bind='fund.title']")).size()==1)
			reportStep(funds.get(0).findElements(By.xpath(".//a[@innerhtml.bind='fund.title']")).get(0).getText()+" fund Name is displayed for 1st fund","PASS");
		else
			reportStep("fund Name is not displayed for 1st fund","FAIL");
		
		if(funds.get(0).findElements(By.xpath(".//small[@innerhtml.bind='fund.fundType']")).size()==1)
			reportStep(funds.get(0).findElements(By.xpath(".//small[@innerhtml.bind='fund.fundType']")).get(0).getText()+" Asset Class is displayed for 1st fund","PASS");
		else
			reportStep("Asset Class is not displayed for 1st fund","FAIL");
		
		if(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.navDate']")).size()==1)
			reportStep(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.navDate']")).get(0).getText()+" As of Date is displayed for 1st fund","PASS");
		else
			reportStep("As of Date is not displayed for 1st fund","FAIL");
		
		validateFormat(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.navDate']")).get(0), "[0-3]\\d.\\d{2}.\\d{4}", "As of Date Format- 1st Fund");
		
		validateFormat(funds.get(0).findElements(By.xpath(".//span[@innerhtml.bind='fund.nav']")).get(0), "[$€]\\d{1,2}\\.\\d{2}", "NAV Format- 1st Fund");
		
		validateFormat(funds.get(0).findElements(By.xpath(".//small[@innerhtml.bind='fund.navChange']")).get(0), "-*[$€]\\d{1,2}\\.\\d{2}", "NAV Change Format- 1st Fund");
	
		reportStep("validate the Search Text is highlighed in Bold in fund name for 1st card","INFO");
		if(funds.get(0).findElements(By.xpath(".//a[@innerhtml.bind='fund.title']//strong")).get(0).getText().equals("K2"))
			reportStep("Search Text 'K2' is highlighted in bold in fund name","PASS");
		else
			reportStep("Search Text 'K2' is not highlighted in bold in fund name","PASS");
	}
	
	
// Marketing Scenarios
	
public UKHomePage validateRoleSelectorForPassedActor(String actor) throws InterruptedException, FileNotFoundException, IOException {	
		
	
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		Thread.sleep(5000);
		String actorLabel ;
		switch(actor) {
		
		case "Individual Investor":	actorLabel=getLocalePropertyValue("lblIIActor");
									wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageActor"))));
									click(locateElement(getLocalePropertyValue("objHmPageActor")));
									click(locateElement("partial", getLocalePropertyValue("objHmPageIndividualInvestor")),true);
								    break;
		case "Financial Advisor":	actorLabel=getLocalePropertyValue("lblFAActor");
									wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageActor"))));
									click(locateElement(getLocalePropertyValue("objHmPageActor")));
									clickOnFALink();
								    break;		
		case "Shareholder":			actorLabel=getLocalePropertyValue("lblSHActor");
									wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageActor"))));
									click(locateElement(getLocalePropertyValue("objHmPageActor")));
									click(locateElement("partial", getLocalePropertyValue("objHmPageShareholder")),true);
									break;
		
		case "Institutions":		actorLabel=getLocalePropertyValue("lblINActor");
									wait.until(ExpectedConditions.elementToBeClickable(locateElement(localeProp.getProperty("objHmPageActor"))));
									click(locateElement(getLocalePropertyValue("objHmPageActor")));
									click(locateElement("xpath", getLocalePropertyValue("objHmPageInstitutions")),true);
									loginAsAdvisor();
									break;
		
		default					:	actorLabel="";
									reportStep("Actor " + actor  + " provided may not handled / Not Applicable, Please refer the file for more info.","INFO");
					 				break;
		}
		
		verifyElementExist("xpath",getLocalePropertyValue("objHmPageFooterRoleSelector"),"Bottom Role Selector");
		moveContextToElement(locateElement("xpath",getLocalePropertyValue("objHmPageFooterRoleSelector")));
		verifyExactText(locateElement("xpath",getLocalePropertyValue("objHmPageFooterRoleSelector")), actorLabel,"Actor Text in Bottom Role Selector",true);
		
		return this;
	}
	
	
	public UKHomePage validateMegamenuLinks() {
	
		reportStep("Validating All Mega Menu Links are displayed in Homepage","INFO");
		
		verifyElementExist("id", getLocalePropertyValue("objHmPageInsightsLink"),"Insights Link");
		verifyElementExist("id", getLocalePropertyValue("objHmPageResourcesLink"),"Resources Link");
		verifyElementExist("id", getLocalePropertyValue("objHmPageOurCompanyLink"),"Our Company Link");
		verifyElementExist("id", getLocalePropertyValue("objHmPageProductsLink"),"Products Link");
		return this;
	}
	
	public UKHomePage validateBenLogo() {
		
		reportStep("Validating BenLogo displayed in Homepage","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objHmPageBenLogo"),"Ben Logo Top Left Corner");
		
		return this;
	}
	
	public UKHomePage validateBanner() {
		
		reportStep("Validating Banner displayed in Homepage","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objHmPageBannerLink"),"Banner in HomePage");
		click(locateElement("xpath",getLocalePropertyValue("objHmPageBannerLink")),true);
		return this;
	}
	
public UKHomePage validateQuickLinks() {
		
		reportStep("Validating Quick Links displayed in Homepage","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objHmPageQuickLinks"),"Quick Links");
		List<WebElement> quicklinks =  locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks"));
		
		verifyExactText(quicklinks.get(0), getLocalePropertyValue("lblquickLinksEquity"),"Asset Class Option",true);
		verifyExactText(quicklinks.get(1), getLocalePropertyValue("lblquickLinksFI"),"Asset Class Option",true);
		verifyExactText(quicklinks.get(2), getLocalePropertyValue("lblquickLinksAlternatives"),"Asset Class Option",true);
		verifyExactText(quicklinks.get(3), getLocalePropertyValue("lblquickLinksMA"),"Asset Class Option",true);
		verifyExactText(quicklinks.get(4), getLocalePropertyValue("lblquickLinksAllFunds"),"Asset Class Option",true);
		
		reportStep("Validating PPSS Page For Each Link","INFO");
		for(int i=0;i<locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks")).size();i++) {
			String linkText = locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks")).get(i).getText();
			if(linkText.equals("Multi Asset"))
				linkText="Multi-Asset";
			else if(linkText.equals("All Funds"))
				linkText="All";
			click(locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks")).get(i));
			
		verifyExactText(new Select(locateElement(getLocalePropertyValue("objPpssAssetClassFilter"))).getFirstSelectedOption(),linkText,"Asset Class Option",true);
		driver.navigate().back();
		}
		
		return this;
	}

public UKHomePage validateBillboardBlogsInvestmentThemesGermany() throws InterruptedException {
	
	reportStep("Validating Billboard , Latest blogs & Investment themes section displayed in Homepage","INFO");
	
	verifyElementExist("xpath", "//div[@class='jumbotron billboard-large']//h1","Text in Billboard Image");
	String billText = locateElement("xpath","//div[@class='jumbotron billboard-large']//h1").getText();
	reportStep("Following text exist in billboard imagae in Home page "+locateElement("xpath","//div[@class='jumbotron billboard-large']//h1").getText(),"INFO");
	
	if(verifyElementExist("xpath", getLocalePropertyValue("objHmPageBannerLink"))) {
		reportStep("Read More link for billboard image exists","INFO");
	click(locateElement("xpath",getLocalePropertyValue("objHmPageBannerLink")),true);
	verifyExactText(locateElement("xpath","//h1"),billText.toUpperCase(),"Header Text",true);
	driver.navigate().back();
	}
	else
		reportStep("Read More link for billboard image does not exist","INFO");
	//news cards
	reportStep("Validating News Cards displayed in Homepage","INFO");
	
	verifyElementExist("xpath", getLocalePropertyValue("objHmPageFeaturedCards"),"News Cards Header");
	moveContextToElement(locateElement("xpath", getLocalePropertyValue("objHmPageFeaturedCards")));
	List<WebElement> insightCards =  locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedCards"));
	
	reportStep(insightCards.size()+" News Cards are displayed in Home Page","INFO");
	
	reportStep("Validating 'Blog' text & Date format in 1st Featured news card","INFO");
	verifyElementExist("xpath", "(//h2[text()='Neuigkeiten']/../../../../..//following-sibling::div[1]//div[@data-fti-component='card'])[1]//h4[text()='Blog']","Blog Text");
	validateFormat(locateElement("xpath","(//h2[text()='Neuigkeiten']/../../../../..//following-sibling::div[1]//div[@data-fti-component='card'])[1]//p"), "[0-3]\\d.\\d{2}.\\d{4}", "Date Format");
	
	String cardTitle = locateElement("xpath","(//h2[text()='Neuigkeiten']/../../../../..//following-sibling::div[1]//div[@data-fti-component='card'])[1]//h3").getText();
	
	reportStep("Click on 1st Featured news card","INFO");
	click(insightCards.get(0));
	
	verifyPartialText(locateElement("xpath","//h1"),cardTitle.toUpperCase(),true);
	
	driver.navigate().back();
	
	//investmentTheme cards
	
	reportStep("Validating Theme Cards displayed in Homepage","INFO");
	
	verifyElementExist("xpath", getLocalePropertyValue("objHmPageThemeCards"),"Theme Cards Header");
	moveContextToElement(locateElement("xpath", getLocalePropertyValue("objHmPageThemeCards")));
	List<WebElement> themeCards =  locateElements("xpath",getLocalePropertyValue("objHmPageThemeCards"));
	
	reportStep(themeCards.size()+" Theme Cards are displayed in Home Page","INFO");
	
	reportStep("Validating 'Märkte' or 'Produkte' text in 1st Featured theme card","INFO");
	validateFormat(locateElement("xpath", "(//h2[text()='Anlagethemen']/../../..//following-sibling::div[1]//div[@data-fti-component='card'])[1]//h4"),"MÄRKTE|PRODUKTE","'Märkte' or 'Produkte' Text");

	 cardTitle = locateElement("xpath","(//h2[text()='Anlagethemen']/../../..//following-sibling::div[1]//div[@data-fti-component='card'])[1]//h3").getText();
	
	reportStep("Click on 1st Featured news card","INFO");
	click(themeCards.get(0));

	ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	if(tabs.size()>1) {
	driver.switchTo().window(tabs.get(1));
	 	    		    
	verifyPartialText(locateElement("xpath","//h1"),cardTitle.toUpperCase(),true);
	
	driver.close();
	driver.switchTo().window(tabs.get(0));	
	}
	else {
	
	verifyPartialText(locateElement("xpath","//h1"),cardTitle.toUpperCase(),true);
	
	driver.navigate().back();
	}
	return this;
}

public UKHomePage validateFeaturedFundsGermany() throws InterruptedException {
	reportStep("Validating Featured Funds header displayed in Homepage","INFO");
	
	verifyElementExist("xpath", "//h2[text()='Ausgewählte Fonds']","Featured Funds header");
	moveContextToElement(locateElement("xpath", "//h2[text()='Ausgewählte Fonds']"));
	List<WebElement> insightCards =  locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds"));
	Thread.sleep(5000);
	if(insightCards.size()==4)
		reportStep("4 Featured Funds are displayed in Home Page","PASS");
	else
		reportStep(insightCards.size()+" Featured Funds are displayed in Home Page","FAIL");
	
	moveContextToElement(insightCards.get(0));
	
	
		reportStep("Validating values & Formats of 1st card","INFO");
		WebElement shareClass=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(0).findElement(By.xpath("//div[@class='fund-card__fund-symbol']"));
		reportStep("Share Class :"+shareClass.getText(),"INFO",true);
		String sClass = shareClass.getText();
		
		WebElement fundName=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(0).findElement(By.xpath("//div[@class='fund-card__fund-name']"));
		reportStep("Fund Name :"+fundName.getText(),"INFO");
		String fN= fundName.getText();
		
		WebElement fundStats=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(0).findElement(By.xpath("//ul[@class='fund-card-stats']"));
		List<WebElement> fundStatsElements =fundStats.findElements(By.tagName("li"));
		
		List<WebElement> Nav=fundStatsElements.get(0).findElements(By.xpath("./span"));
		List<WebElement> NavChange=fundStatsElements.get(1).findElements(By.xpath("./span"));
		List<WebElement> FundSize=fundStatsElements.get(2).findElements(By.xpath("./span"));
		List<WebElement> AssetClass=fundStatsElements.get(3).findElements(By.xpath("./span"));
		List<WebElement> InceptionDate=fundStatsElements.get(4).findElements(By.xpath("./span"));
		List<WebElement> Distribution=fundStatsElements.get(5).findElements(By.xpath("./span"));
		
	validateFormat(Nav.get(0),"Rücknahmepreis Stand [0-3]\\d.\\d{2}.\\d{4}","Rücknahmepreis Stand dd/MM/yyyy");
	validateFormat(Nav.get(1),"[€$£(CHF¥)]\\d{1,2},\\d{2}","NAV");
	
	validateFormat(NavChange.get(0),"Veränderung zum Vortag","Veränderung zum Vortag");
	validateFormat(NavChange.get(1),"-*[€$£(CHF¥)]\\d{1,2},\\d{2}","NAV Change Value");
	
	validateFormat(FundSize.get(0),"Fondsvolumen Stand [0-3]\\d.\\d{2}.\\d{4}","Fund Size As of dd/MM/yyyy");
	validateFormat(FundSize.get(1),"[€$£(CHF¥)]\\d{1,3},\\d{2} Milliarden","Fund Size Value");
	
	validateFormat(AssetClass.get(0),"Fondsart","Fondsart");
	validateFormat(AssetClass.get(1),"Aktienfonds|Renten // Anleihen|Mischfonds|Multi-Asset","Fondsart format");
	
	validateFormat(InceptionDate.get(0),"Auflegungsdatum der Anteilsklasse","Auflegungsdatum der Anteilsklasse Label");
	validateFormat(InceptionDate.get(1),"[0-3]\\d.\\d{2}.\\d{4}","Share Class Inception Date format");
	
	reportStep("Distribution :"+Distribution.get(0).getText(),"INFO");
	validateFormat(Distribution.get(1),"Nein|monatlich|Jährlich","Distribution Label");
	
	WebElement fundlinks=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(0).findElement(By.xpath("//ul[@class='fund-card__actions']"));
	verifyExactText(fundlinks.findElements(By.tagName("a")).get(0),"Fondsübersicht","Fund Details Link",true);
	verifyExactText(fundlinks.findElements(By.tagName("a")).get(1),"Dokumente","Documents Link",true);
	
	reportStep("Validating fund overview link for 1st card","INFO");
	click(fundlinks.findElements(By.tagName("a")).get(0),true);
	verifyExactText(locateElement("xpath","//h1"),fN,"Fund Name Header" );
	verifyExactText(locateElement("xpath",getLocalePropertyValue("objFOShareClass")),sClass,"Fund Overview Share Class" ,true );
	
	driver.navigate().back();
	
	reportStep("Validating fund Document link for 1st card","INFO");
	fundlinks=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(0).findElement(By.xpath("//ul[@class='fund-card__actions']"));
	click(fundlinks.findElements(By.tagName("a")).get(1),true);
	
	verifyExactText(locateElement("xpath","//li[@class='fund-tabs__tab au-target active']/a"),"Dokumente","Dokumente Tab",true );
	
	return this;
}

public UKHomePage validateLearnMoreInvstStrategiesSection() throws InterruptedException {
	reportStep("Validating Learn More about investment strategies section displayed in Homepage","INFO");
	
	verifyElementExist("xpath", "//h2[text()='ERFAHREN SIE MEHR ÜBER UNSERE INVESTMENTSTRATEGIEN']","ERFAHREN SIE MEHR ÜBER UNSERE INVESTMENTSTRATEGIEN header");
	moveContextToElement(locateElement("xpath", "//h2[text()='ERFAHREN SIE MEHR ÜBER UNSERE INVESTMENTSTRATEGIEN']"));
	
	List<WebElement> invstStrat = locateElements("xpath", "//h2[text()='ERFAHREN SIE MEHR ÜBER UNSERE INVESTMENTSTRATEGIEN']/..//h3");
	
	if(invstStrat.size()==4)
		reportStep("4 Investment Strategies are displayed in Home Page","PASS");
	else
		reportStep(invstStrat.size()+" Investment Strategies are displayed in Home Page","FAIL");
	
	
	verifyExactText(invstStrat.get(0), "Aktienfonds","Aktienfonds Header",true);
	verifyExactText(invstStrat.get(1), "Rentenfonds","Rentenfonds Header");
	verifyExactText(invstStrat.get(2), "Multi-Asset-Fonds","Multi-Asset-Fonds Header");
	verifyExactText(invstStrat.get(3), "Alternative Anlagestrategien","Alternative Anlagestrategien Header");
	
	for(int i=0;i<invstStrat.size();i++) {
		verifyElementExist("xpath", "(//h2[text()='ERFAHREN SIE MEHR ÜBER UNSERE INVESTMENTSTRATEGIEN']/..//h3/../..)["+(i+1)+"]//p[contains(@class,'main-header')]");
	}
	
	for(int i=0;i<invstStrat.size();i++) {
		verifyElementExist("xpath", "(//h2[text()='ERFAHREN SIE MEHR ÜBER UNSERE INVESTMENTSTRATEGIEN']/..//h3/../..)["+(i+1)+"]//p/a");
	}
	
	reportStep("Click on 'Informieren Sie sich hier' Link for all Cards & Verify Landing Pagee","INFO");
	
	for(int i=0;i<4;i++) {
		String cardLbl = locateElement("xpath","(//h2[text()='ERFAHREN SIE MEHR ÜBER UNSERE INVESTMENTSTRATEGIEN']/..//h3/../..)["+(i+1)+"]//h3").getText();
		click(locateElement("xpath","(//h2[text()='ERFAHREN SIE MEHR ÜBER UNSERE INVESTMENTSTRATEGIEN']/..//h3/../..)["+(i+1)+"]//p/a"));
		if(cardLbl.equals("Multi-Asset-Fonds"))
			verifyExactText(locateElement("xpath","//h1"),"MISCHFONDS UND MULTI-ASSET FONDS","Banner Label",true );
		else
		verifyExactText(locateElement("xpath","//h1"),cardLbl.toUpperCase(),"Banner Label",true );
		driver.navigate().back();
	}
	
	return this;
}
public UKHomePage validateQuickLinksGerman() {
	
	reportStep("Validating Quick Links displayed in Homepage","INFO");
	List<WebElement> quicklinks=null;
	if(passedActor.equals("Institutional Investor")) {
		verifyElementExist("xpath", "//h3[text()='Schneller Finden']/following-sibling::nav//li","Quick Links");
		quicklinks	 =  locateElements("xpath","//h3[text()='Schneller Finden']/following-sibling::nav//li");
	}
	else {
	verifyElementExist("xpath", getLocalePropertyValue("objHmPageQuickLinks"),"Quick Links");
	 quicklinks =  locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks"));
	}
	
	if(passedActor.equals("Individual Investor")) {
	verifyExactText(quicklinks.get(0),"Preise & Wertenwicklung" ,"Quick Link - Preise & Wertenwicklung",true);
	verifyExactText(quicklinks.get(1), "Investieren","Quick Link - Investieren",true);
	verifyExactText(quicklinks.get(2), "Formulare","Quick Link - Formulare",true);
	verifyExactText(quicklinks.get(3), "Depot Login","Quick Link - Depot Login",true);
	verifyExactText(quicklinks.get(4), "Ihre Steuermitteilung","Quick Link - Ihre Steuermitteilung",true);
	
	WebDriverWait wait=new WebDriverWait(driver, 10);	
	
	reportStep("Validating Landing Page For Each Quick Link","INFO");
	for(int i=0;i<locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks")).size();i++) {
		String linkText = locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks")).get(i).getText();
		click(locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks")).get(i));
	
		
		if(linkText.equals("Depot Login")) {
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath","//div[text()='Welcome to Franklin Templeton Online']")));
			verifyElementExist("xpath", "//div[text()='Welcome to Franklin Templeton Online']");
		}else {
			wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath","//h1")));
			verifyPartialText(locateElement("xpath","//h1"),linkText.toUpperCase(),true);
		}
	driver.navigate().back();
		}
	}
	else if(passedActor.equals("Financial Advisor")) {
		
		verifyExactText(quicklinks.get(0),"Preise & Wertenwicklung" ,"Quick Link - Preise & Wertenwicklung",true);
		verifyExactText(quicklinks.get(1), "Formulare","Quick Link - Formulare",true);
		verifyExactText(quicklinks.get(2), "Kostenrechner nach MiFID II","Quick Link - Kostenrechner nach MiFID II",true);
		verifyExactText(quicklinks.get(3), "FONDS@NALYSE.TOOL","Quick Link - FONDS@NALYSE.TOOL",true);
		verifyExactText(quicklinks.get(4), "Berater.News","Quick Link - Berater.News",true);
		verifyExactText(quicklinks.get(5), "Webshop","Quick Link - Webshop",true);
		
		WebDriverWait wait=new WebDriverWait(driver, 10);	
		
		reportStep("Validating Landing Page For Each Quick Link","INFO");
		for(int i=0;i<locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks")).size();i++) {
			String linkText = locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks")).get(i).getText();
			click(locateElements("xpath",getLocalePropertyValue("objHmPageQuickLinks")).get(i));
			
			if(linkText.equals("Preise & Wertenwicklung")) {
				verifyPartialText(locateElement("xpath","//h1"),"PRODUKTÜBERSICHT",true);
			}
			else if(linkText.equals("Kostenrechner nach MiFID II")){
				verifyPartialText(locateElement("xpath","//h1"),"KOSTENINFORMATION GEMÄSS § 63 ABSATZ 7 WERTPAPIERHANDELSGESETZ",true);
			}
			else if(linkText.equals("Webshop")){
				verifyPartialText(locateElement("xpath","//h1"),"ANMELDEN ODER BENUTZERKONTO ERSTELLEN",true);
			}
			else { 
				wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath","//h1")));
				verifyPartialText(locateElement("xpath","//h1"),linkText.toUpperCase(),true);
			}
		driver.navigate().back();
			}
	}
		else if(passedActor.equals("Institutional Investor")) {
			
			verifyExactText(quicklinks.get(0),"Investmentstrategien" ,"Quick Link - Investmentstrategien",true);
			verifyExactText(quicklinks.get(1), "Investmentthemen","Quick Link - Investmentthemen",true);
	
			verifyExactText(quicklinks.get(2), "Produkte","Quick Link - Produkte",true);
			verifyExactText(quicklinks.get(3), "Reports","Quick Link - Reports",true);
		
			
			WebDriverWait wait=new WebDriverWait(driver, 10);	
			
			reportStep("Validating Landing Page For Each Quick Link","INFO");
			for(int i=0;i<locateElements("xpath","//h3[text()='Schneller Finden']/following-sibling::nav//li").size();i++) {
				String linkText = locateElements("xpath","//h3[text()='Schneller Finden']/following-sibling::nav//li").get(i).getText();
				click(locateElements("xpath","//h3[text()='Schneller Finden']/following-sibling::nav//li").get(i));
				
					wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath","//h1")));
					verifyPartialText(locateElement("xpath","//h1"),linkText.toUpperCase(),true);
				
			driver.navigate().back();
				}
	}
	return this;
	}

	public UKHomePage validateInsights() {
	
	reportStep("Validating Insights Cards displayed in Homepage","INFO");
	
	verifyElementExist("xpath", getLocalePropertyValue("objHmPageInsightCards"),"Insights Header");
	List<WebElement> insightCards =  locateElements("xpath",getLocalePropertyValue("objHmPageInsightCards"));
	
	if(insightCards.size()==4)
		reportStep("4 Insight Cards are displayed in Home Page","PASS");
	else
		reportStep(insightCards.size()+" Insight Cards are displayed in Home Page","FAIL");
	
	reportStep("Validating Heading & Date format in landing page of each insight card","INFO");
	for (int i=0;i<locateElements("xpath",getLocalePropertyValue("objHmPageInsightCards")).size();i++) {
		String cardHeader = locateElements("xpath",getLocalePropertyValue("objHmPageInsightCards")).get(i).findElement(By.tagName("a")).getText();
		click(locateElements("xpath",getLocalePropertyValue("objHmPageInsightCards")).get(i).findElement(By.tagName("a")));
		
		verifyPartialText(locateElement("xpath","//h1"),cardHeader.toUpperCase(),true);
		if(locateElement("xpath","//h1").findElements(By.tagName("small")).size()>0)
		validateFormat(locateElement("xpath","//h1").findElement(By.tagName("small")), "[A-Z]{1}[a-z]{2} \\d{1,2}, \\d{4}", "Date Format");
		else
			reportStep("Date Part is not displayed for this card","INFO");
		driver.navigate().back();
	}
	
	return this;
	}

	public UKHomePage validateFeaturedCards() {
		
		reportStep("Validating Featured Cards displayed in Homepage","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objHmPageFeaturedCards"),"Featured Header");
		List<WebElement> insightCards =  locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedCards"));
		
		if(insightCards.size()==4)
			reportStep("4 Featured Cards are displayed in Home Page","PASS");
		else
			reportStep(insightCards.size()+" Featured Cards are displayed in Home Page","FAIL");
		
		reportStep("Validating Heading & Date format in landing page of each Featured card","INFO");
		for (int i=0;i<locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedCards")).size();i++) {
			String cardHeader = locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedCards")).get(i).findElement(By.tagName("a")).getText();
			click(locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedCards")).get(i).findElement(By.tagName("a")));
			
			verifyPartialText(locateElement("xpath","//h1"),cardHeader.toUpperCase(),true);
			if(locateElement("xpath","//h1").findElements(By.tagName("small")).size()>0)
			validateFormat(locateElement("xpath","//h1").findElement(By.tagName("small")), "[A-Z]{1}[a-z]{2} \\d{2}, \\d{4}", "Date Format");
			else
				reportStep("Date Part is not displayed for this card","INFO");
			driver.navigate().back();
		}
		
		return this;
		}
	
	
public UKHomePage validateFeaturedFunds() throws InterruptedException {
		
		reportStep("Validating Featured Funds displayed in Homepage","INFO");
		
		verifyElementExist("xpath", getLocalePropertyValue("objHmPageFeaturedFunds"),"Featured Funds Header");
		List<WebElement> insightCards =  locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds"));
		Thread.sleep(5000);
		if(insightCards.size()==4)
			reportStep("4 Featured Funds are displayed in Home Page","PASS");
		else
			reportStep(insightCards.size()+" Featured Funds are displayed in Home Page","FAIL");
		
		moveContextToElement(insightCards.get(0));
		
		for (int i=0;i<locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).size();i++) {
			reportStep("Validating values & Formats of "+(i+1)+" card","INFO");
			WebElement shareClass=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(i).findElement(By.xpath("//div[@class='fund-card__fund-symbol']"));
			reportStep("Share Class :"+shareClass.getText(),"INFO",true);
			
			WebElement fundName=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(i).findElement(By.xpath("//div[@class='fund-card__fund-name']"));
			reportStep("Fund Name :"+fundName.getText(),"INFO");
			
			WebElement fundStats=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(i).findElement(By.xpath("//ul[@class='fund-card-stats']"));
			List<WebElement> fundStatsElements =fundStats.findElements(By.tagName("li"));
			
			List<WebElement> Nav=fundStatsElements.get(0).findElements(By.xpath("./span"));
			List<WebElement> NavChange=fundStatsElements.get(1).findElements(By.xpath("./span"));
			List<WebElement> FundSize=fundStatsElements.get(2).findElements(By.xpath("./span"));
			List<WebElement> AssetClass=fundStatsElements.get(3).findElements(By.xpath("./span"));
			List<WebElement> InceptionDate=fundStatsElements.get(4).findElements(By.xpath("./span"));
			List<WebElement> Distribution=fundStatsElements.get(5).findElements(By.xpath("./span"));
			
		validateFormat(Nav.get(0),"NAV As of [0-3]\\d.\\d{2}.\\d{4}","NAV As of dd/MM/yyyy");
		validateFormat(Nav.get(1),"£\\d{1,2}\\.\\d{2}","NAV");
		
		validateFormat(NavChange.get(0),"NAV Change","NAV Change");
		validateFormat(NavChange.get(1),"-*£\\d{1,2}\\.\\d{2}","NAV Change Value");
		
		validateFormat(FundSize.get(0),"Fund Size As of [0-3]\\d.\\d{2}.\\d{4}","Fund Size As of dd/MM/yyyy");
		validateFormat(FundSize.get(1),"£\\d{1,3}\\.\\d{2} Million","Fund Size Value");
		
		validateFormat(AssetClass.get(0),"Asset Class","Asset Class");
		reportStep("Asset Class :"+AssetClass.get(1).getText(),"INFO");
		
		validateFormat(InceptionDate.get(0),"Share Class Inception Date","Share Class Inception Date Label");
		validateFormat(InceptionDate.get(1),"[0-3]\\d.\\d{2}.\\d{4}","Share Class Inception Date format");
		
		reportStep("Distribution :"+Distribution.get(0).getText(),"INFO");
		validateFormat(Distribution.get(1),"Accumulating","Accumulating Label");
		
		WebElement fundlinks=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(i).findElement(By.xpath("//ul[@class='fund-card__actions']"));
		verifyExactText(fundlinks.findElements(By.tagName("a")).get(0),"Fund Details","Fund Details Link",true);
		verifyExactText(fundlinks.findElements(By.tagName("a")).get(1),"Documents","Documents Link",true);
			}
		
		return this;
		}
	

public FundOverviewPage validateFeaturedFundsDetails() throws FileNotFoundException, InterruptedException, IOException {
	
	reportStep("Validating Featured Funds Details link for first Fund","INFO");
	
	verifyElementExist("xpath", getLocalePropertyValue("objHmPageFeaturedFunds"),"Featured Funds Header");
	List<WebElement> insightCards =  locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds"));
	
	Thread.sleep(5000);
	if(insightCards.size()==4) {
		moveContextToElement(insightCards.get(0));
		reportStep("4 Featured Funds are displayed in Home Page","PASS",true);
	}else {
		moveContextToElement(insightCards.get(0));
	reportStep(insightCards.size()+" Featured Funds are displayed in Home Page","FAIL",true);
	}
	moveContextToElement(insightCards.get(0));
	
	reportStep("Validating Fund Details Link For First Fund in featured funds section","INFO");
	WebElement fundlinks=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(0).findElement(By.xpath("//ul[@class='fund-card__actions']"));
	verifyExactText(fundlinks.findElements(By.tagName("a")).get(0),"Fund Details","Fund Details Link",true);
	verifyExactText(fundlinks.findElements(By.tagName("a")).get(1),"Documents","Documents Link",true);
	
	String fundName=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(0).findElement(By.xpath("//div[@class='fund-card__fund-name']")).getText();
	
	click(fundlinks.findElements(By.tagName("a")).get(0),true);
		
	
	return new FundOverviewPage(driver,test,fundName);
	}


public FundDocumentPage validateFeaturedFundsDocuments() throws FileNotFoundException, InterruptedException, IOException {
	
	reportStep("Validating Featured Funds Documents link for first Fund","INFO");
	
	verifyElementExist("xpath", getLocalePropertyValue("objHmPageFeaturedFunds"),"Featured Funds Header");
	List<WebElement> insightCards =  locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds"));
	
	Thread.sleep(5000);
	if(insightCards.size()==4) {
		moveContextToElement(insightCards.get(0));
		reportStep("4 Featured Funds are displayed in Home Page","PASS",true);
	}else {
		moveContextToElement(insightCards.get(0));
	reportStep(insightCards.size()+" Featured Funds are displayed in Home Page","FAIL",true);
	}
	moveContextToElement(insightCards.get(0));
	
	reportStep("Validating Fund Document Link For First Fund in featured funds section","INFO");
	WebElement fundlinks=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(0).findElement(By.xpath("//ul[@class='fund-card__actions']"));
	verifyExactText(fundlinks.findElements(By.tagName("a")).get(0),"Fund Details","Fund Details Link",true);
	verifyExactText(fundlinks.findElements(By.tagName("a")).get(1),"Documents","Documents Link",true);
	
	String fundName=locateElements("xpath",getLocalePropertyValue("objHmPageFeaturedFunds")).get(0).findElement(By.xpath("//div[@class='fund-card__fund-name']")).getText();
	
	click(fundlinks.findElements(By.tagName("a")).get(1),true);
		
	
	return new FundDocumentPage(driver,test,fundName);
	}


	public UKHomePage validateFooterMediaLinks() throws InterruptedException{
	reportStep("Validating the Footer Media Links for {Our Company, Products, Insights, Resources}","INFO");	
	
	
	List<WebElement> ourCompanyLinks = locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterOurCompanyLinks"));
	List<WebElement> productsLinks = locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterProductsLinks"));
	List<WebElement> insightsLinks = locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterInsightsLinks"));
	List<WebElement> resourcesLinks = locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterResourcesLinks"));
	
	if(ourCompanyLinks.size()==4) {
		moveContextToElement(ourCompanyLinks.get(0));
		reportStep("4 links are displayed for Our Company in Footer of Homepage","PASS",true);
	}else {
		moveContextToElement(ourCompanyLinks.get(0));
		reportStep(ourCompanyLinks.size()+" links are displayed for Our Company in Footer of Homepage, Expected: 4","FAIL",true);
	}
	
	if(productsLinks.size()==5)
		reportStep("5 links are displayed for Products in Footer of Homepage","PASS");
	else
		reportStep(productsLinks.size()+" links are displayed for Products in Footer of Homepage, Expected: 5","FAIL");
	
	
	if(insightsLinks.size()==3)
		reportStep("3 links are displayed for Insights in Footer of Homepage","PASS");
	else
		reportStep(insightsLinks.size()+" links are displayed for Insights in Footer of Homepage, Expected: 3","FAIL");
	
	
	if(resourcesLinks.size()==4)
		reportStep("4 links are displayed for Resources in Footer of Homepage","PASS");
	else
		reportStep(resourcesLinks.size()+" links are displayed for Resources in Footer of Homepage, Expected: 4","FAIL");
	
	verifyExactText(ourCompanyLinks.get(0),"Our Firm" ,"Our Firm Link");
	verifyExactText(ourCompanyLinks.get(1),"UK Profile" ,"UK Profile Link");
	verifyExactText(ourCompanyLinks.get(2),"Media Centre" ,"Media Centre Link");
	verifyExactText(ourCompanyLinks.get(3),"Contact Us" ,"Contact Us Link");
	
	verifyExactText(productsLinks.get(0),"Mutual Fund Price and Performance" ,"Mutual Fund Price and Performance Link");
	verifyExactText(productsLinks.get(1),"ETF Price and Performance" ,"ETF Price and Performance Link");
	verifyExactText(productsLinks.get(2),"Investment Trust (TEMIT)" ,"Investment Trust (TEMIT) Link");
	verifyExactText(productsLinks.get(3),"Fund Charges and Costs" ,"Fund Charges and Costs Link");
	verifyExactText(productsLinks.get(4),"Our Products" ,"Our Products Link");
	
	verifyExactText(insightsLinks.get(0),"Global Investment Outlook" ,"Global Investment Outlook Link");
	verifyExactText(insightsLinks.get(1),"Blogs" ,"Blogs Link");
	verifyExactText(insightsLinks.get(2),"Our Insights" ,"Our Insights Link");

	
	verifyExactText(resourcesLinks.get(0),"Literature" ,"Literature Link");
	verifyExactText(resourcesLinks.get(1),"Investor Education" ,"Investor Education Link");
	verifyExactText(resourcesLinks.get(2),"Investment Advice" ,"Investment Advice Link");
	verifyExactText(resourcesLinks.get(3),"Your Account" ,"Your Account Link");
	
	reportStep("Validating Each Link is landing to proper page by clicking on it","INFO");	
	
	reportStep("Click on Our Firm Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterOurCompanyLinks")).get(0));
	verifyExactText(locateElement("xpath","//h1"),"OUR FIRM","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Profile Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterOurCompanyLinks")).get(1));
	verifyExactText(locateElement("xpath","//h1"),"FRANKLIN TEMPLETON – UK PROFILE","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Media Center Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterOurCompanyLinks")).get(2));
	verifyExactText(locateElement("xpath","//h1"),"MEDIA CENTRE","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Contact Us Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterOurCompanyLinks")).get(3));
	verifyExactText(locateElement("xpath","//h1"),"CONTACT US","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Mutual Fund Price & Performance Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterProductsLinks")).get(0));
	verifyExactText(locateElement("xpath","//h1"),"MUTUAL FUND PRICE AND PERFORMANCE","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on ETF Price & Performance Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterProductsLinks")).get(1));
	verifyExactText(locateElement("xpath","//h1"),"ETF PRICE AND PERFORMANCE","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Investmentx Trust(TEMIT) Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterProductsLinks")).get(2));
	verifyExactText(locateElement("xpath","//h1"),"TEMPLETON EMERGING MARKETS INVESTMENT TRUST","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on FUND CHARGES AND COSTS Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterProductsLinks")).get(3));
	verifyExactText(locateElement("xpath","//h1"),"FUND CHARGES AND COSTS","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on OUR PRODUCTS Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterProductsLinks")).get(4));
	verifyExactText(locateElement("xpath","//h1"),"OUR PRODUCTS","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Global Investment Outlook Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterInsightsLinks")).get(0));
	verifyExactText(locateElement("xpath","//h1"),"HOW MUCH FURTHER CAN GLOBAL GROWTH FLY?","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Blogs Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterInsightsLinks")).get(1));
	verifyExactText(locateElement("xpath","//h1"),"BLOGS","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Our Insights Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterInsightsLinks")).get(2));
	verifyExactText(locateElement("xpath","//h1"),"INSIGHTS","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Litrature Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterResourcesLinks")).get(0));
	verifyExactText(locateElement("xpath","//h1"),"LITERATURE","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Investor Education Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterResourcesLinks")).get(1));
	verifyExactText(locateElement("xpath","//h1"),"INVESTOR EDUCATION","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Investment Advice Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterResourcesLinks")).get(2));	
	verifyExactText(locateElement("xpath","//h1"),"INVESTMENT ADVICE","Page Header",true);
	driver.navigate().back();
	
	reportStep("Click on Your Account Link","INFO");	
	click(locateElements("xpath", getLocalePropertyValue("objIDEHmPageFooterResourcesLinks")).get(3));
	verifyExactText(locateElement("xpath","//h1"),"YOUR ACCOUNT","Page Header",true);
	driver.navigate().back();
	return this;
	}

	
	public UKHomePage validateCaveats() throws InterruptedException{
		reportStep("Validating the Caveats in HOme Page","INFO");	
		
		moveContextToElement(locateElement("xpath", getLocalePropertyValue("objIDEHmPageCaveat")));
		verifyElementExist("xpath", getLocalePropertyValue("objIDEHmPageCaveat"),"Caveat", true);
		
		return this;
	}
	
	public ContactUsPage validateContacts() throws InterruptedException{
		reportStep("Validating the Contacts in HOme Page","INFO");	
		
		List<WebElement> topRightLinks = locateElements("xpath", getLocalePropertyValue("objHmPageTopRightLinks"));
		
		reportStep("Click on Contact Us Link at top right corner","INFO");	
		click(topRightLinks.get(0));
		
		return new ContactUsPage(driver,test);
	}
	
	public UKHomePage validateCareers() throws InterruptedException{
		reportStep("Validating the Careers in HOme Page","INFO");	
		
		List<WebElement> topRightLinks = locateElements("xpath", getLocalePropertyValue("objHmPageTopRightLinks"));
		
		reportStep("Click on Careers Link at top right corner","INFO");	
		click(topRightLinks.get(1));
		
		verifyTwoStringsPartially(driver.getCurrentUrl(), "franklintempletoncareers",true);
		
		return this;
	}
	
	public UKHomePage validateUKLogo() throws InterruptedException{
		reportStep("Validating the UK Logo in HOme Page","INFO");	
		
		List<WebElement> topRightLinks = locateElements("xpath", getLocalePropertyValue("objHmPageTopRightLinks"));
		
		reportStep("Click on UK Logo Link at top right corner","INFO");	
		click(topRightLinks.get(2));
		
		verifyExactText(locateElement("xpath","//h1"),"CHOOSE YOUR COUNTRY","Page Header",true);
		
		verifyExactText(locateElements("xpath","//h2").get(0),"NORTH AMERICA","Region(Country) Header",true);
		verifyExactText(locateElements("xpath","//h2").get(1),"SOUTH AMERICA","Region(Country) Header",true);
		verifyExactText(locateElements("xpath","//h2").get(2),"EUROPE","Region(Country) Header",true);
		verifyExactText(locateElements("xpath","//h2").get(3),"ASIA PACIFIC","Region(Country) Header",true);
		verifyExactText(locateElements("xpath","//h2").get(4),"MIDDLE EAST & AFRICA","Region(Country) Header",true);
		verifyExactText(locateElements("xpath","//h2").get(5),"OUR OTHER SITES","Region(Country) Header",true);
		
		return this;
	}
	public PriceAndPerformancePage navigateToPPSSPageDirectURL() throws FileNotFoundException, IOException
	{
		String dirctUrlSearch;
		if(prop.getProperty("EXECUTION_LANGUAGE").isEmpty())
			dirctUrlSearch=prop.getProperty("COUNTRY_NAME") + "_" + prop.getProperty("ACTOR").replace(" ", "");
		else
			dirctUrlSearch=prop.getProperty("COUNTRY_NAME") + "_" + prop.getProperty("EXECUTION_LANGUAGE") + "_" +  prop.getProperty("ACTOR").replace(" ", "");	
		
		sUrl=ExcelDataProvider.getCellDataByFilter("BaseData", "Smoke_Direct_URLs",dirctUrlSearch, 1).trim();
		driver.get(sUrl);
		if(verifyElementExist("tag", getLocalePropertyValue("objPpssPageHeading"))) {
			reportStep("User Navigated Successfully to PPSS Page through direct URL '" + sUrl + "'.", "PASS",true);
			//verifyPageHeading(getLocalePropertyValue("lblPPSSPageHeading"), "PPSS");
		}else
			reportStep("User may not Navigated Sucessfully to PPSS Page through direct URL '" + sUrl + "'.", "FAIL",true);		
		
		return new PriceAndPerformancePage(driver,test);
		
	}
	
	public FundOverviewPage navigateToProductsPageDirectURL() throws FileNotFoundException, IOException, InterruptedException
	{
		System.out.println("Ex Lan: " + prop.getProperty("EXECUTION_LANGUAGE"));
		String dirctUrlSearch;
		if(prop.getProperty("EXECUTION_LANGUAGE").isEmpty())
			dirctUrlSearch=prop.getProperty("COUNTRY_NAME") + "_" + prop.getProperty("ACTOR").replace(" ", "");
		else
			dirctUrlSearch=prop.getProperty("COUNTRY_NAME") + "_" + prop.getProperty("EXECUTION_LANGUAGE") + "_" +  prop.getProperty("ACTOR").replace(" ", "");	
		
		sUrl=ExcelDataProvider.getCellDataByFilter("BaseData", "Smoke_Direct_URLs",dirctUrlSearch, 2).trim();
		String fndName=ExcelDataProvider.getCellDataByFilter("BaseData", "Smoke_Direct_URLs",dirctUrlSearch, 3).trim();
		driver.get(sUrl);
		if(verifyElementExist("tag", getLocalePropertyValue("objPpssPageHeading"))) {
			reportStep("User Navigated Successfully to Products Page through direct URL '" + sUrl + "'.", "PASS",true);
			//verifyPageHeading(getLocalePropertyValue("lblPPSSPageHeading"), "PPSS");
		}else
			reportStep("User may not Navigated Sucessfully to Products Page through direct URL '" + sUrl + "'.", "FAIL",true);		
		
		return new FundOverviewPage(driver,test,fndName);
		
	}
	
/*	###########################################################################################################################################################
	########################################   methods for Smoke Test validation with Direct URL's    ############################################
	###########################################################################################################################################################
*/
}
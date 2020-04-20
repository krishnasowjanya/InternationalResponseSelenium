package wdMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
//import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.net.PortProber;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import utils.ExcelDataProvider;
import utils.Reporter;


public class SeMethods extends Reporter implements WdMethods{

	public RemoteWebDriver driver;	
	public Properties localeProp;
	public String sUrl,primaryWindowHandle,sHubUrl,sHubPort,sActor,sCountryName,sEnvironment,sExecutionLanguage,sLocaleFile,sExecutionOS;
	public boolean isLanguageRequired=false;
	public String excelName;
	public String excelSheetName;
	public HashMap<String,String> Environemnts=new HashMap<String,String>();
	public HashMap<String,String> Country=new HashMap<String,String>();
	
	public Properties prop;

	public SeMethods() {
		//System.out.println("From SeMethods - Constructor");
		prop = new Properties();
		localeProp = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			//sHubUrl = prop.getProperty("HUB");
			//sHubPort = prop.getProperty("PORT");			 
			
			sExecutionOS=prop.getProperty("EXECUTION_OS");
			sCountryName= prop.getProperty("COUNTRY_NAME");			
			sEnvironment=prop.getProperty("ENVIRONMENT");
			sExecutionLanguage=prop.getProperty("EXECUTION_LANGUAGE");
			sActor=prop.getProperty("ACTOR");
			sLocaleFile=prop.getProperty("LOCALE_FILE");
			
			//sExecutionLanguage=ExcelDataProvider.getCellDataByFilter("BaseData", "ExecutionConfig",sCountryName, 1).trim();
			//System.out.println("Language From SEMetods Cons: "+ sExecutionLanguage);
			//System.out.println("Actor From SEMetods Cons: "+ sActor);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startApp(String browser,String errorDesc) {
		try {
			String isValidLanguage, languageWithCountry,isValidActor,localeFile;
			//System.out.println("From SeMethods - StartAppr");
			//System.out.println("Country from XML: " + Country);
			//System.out.println("error desc: " + errorDesc);
			
			//Checking any Config issues. If any stop the execution by reporting the issue
			if(!errorDesc.isEmpty())
				reportStep(errorDesc, "FAIL",false);
			
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			//sHubUrl = prop.getProperty("HUB");
			//sHubPort = prop.getProperty("PORT");			 
			
			//System.out.println("Exe.OS: " + sExecutionOS );
			sExecutionOS=prop.getProperty("EXECUTION_OS");
			sCountryName= prop.getProperty("COUNTRY_NAME");			
			sEnvironment=prop.getProperty("ENVIRONMENT");
			sExecutionLanguage=prop.getProperty("EXECUTION_LANGUAGE");
			sActor=prop.getProperty("ACTOR");
			sLocaleFile=prop.getProperty("LOCALE_FILE");			
			//System.out.println("Exe.OS after: " + sExecutionOS );

			//Launching the Browser
			if(browser.equalsIgnoreCase("chrome")) {
				if(sExecutionOS.equals("WINDOWS")) {
					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");					
					
					//Non-Headless Mode
					
					
					driver = new ChromeDriver();
					
					//Headless Mode
/*					ChromeOptions options=new ChromeOptions();		          
					options.setHeadless(true);
					driver = new ChromeDriver(options);
*/					
					//Grid Version				
	/*				DesiredCapabilities capability = DesiredCapabilities.chrome();
					capability.setBrowserName("chrome");
					capability.setPlatform(Platform.WIN10);	
					//driver = new RemoteWebDriver(new URL("http://10.144.44.93:4444/wd/hub"), capability); //My Local System IP
					driver = new RemoteWebDriver(new URL("http://10.144.20.161:4444/wd/hub"), capability); //Agent end with 2737 IP	
					//driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
	*/				
				}else if(sExecutionOS.equals("LINUX")) {
					System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
					
					//headless mode
					ChromeOptions options=new ChromeOptions();		          
					options.setHeadless(true);
					//****************  Below commented options are not required for Linux execution. Just to know available methods/arugments tried but not working
					//options.addArguments("--headless");									
					//options.addArguments("--window-size=1280,1696");					
					//options.setPageLoadStrategy(PageLoadStrategy.NONE);
					
					//To avoid the error Chrome not reachable
					//options.addArguments("--no-sandbox");
					//options.addArguments("--disable-setuid-sandbox");
					
					//options.addArguments("--ignore-certificate-errors");  //To avoid any certificaiton errors in URL
					//options.addArguments("--test-type");
					//****************  
					
					options.addArguments("--disable-gpu");
					options.addArguments("--window-size=1920,1080");  //Normal Window size. Since site is in Responsive in design, required to fix the window size during execution else it may show the site according to the defailt size and script may fail
					//driver = new ChromeDriver(options);
					
					DesiredCapabilities capabilities = DesiredCapabilities.chrome(); //To avoid any certification errors in URL. Solved blank page captured when execution https:// URL in Linux
			        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
					capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			        driver = new ChromeDriver(capabilities);
					
				}else {
					System.out.println("Execution OS may not Valid. Please ensure");
					reportStep("Execution OS may not Valid. Please ensure", "FAIL");
				}	
				
			} else if(browser.equalsIgnoreCase("ie")) {
				
				if(sExecutionOS.equals("WINDOWS")) {
				 	DesiredCapabilities capabilities = new DesiredCapabilities();
			        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			        System.setProperty("webdriver.ie.driver","./drivers/IEDriverServer.exe");
			        driver = new InternetExplorerDriver(capabilities);
			        
	/*				System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
					driver = new InternetExplorerDriver();*/
				}else {
					reportStep("Currently IE Browser supports only for WINDOWS OS. Please ensure", "FAIL");}	
			} else if(browser.equalsIgnoreCase("firefox")) {
				
				if(sExecutionOS.equals("WINDOWS")) {
					System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
					driver = new FirefoxDriver();
				}else
					reportStep("Currently Framework may not support Firefox browser for OS other than WINDOWS. Please ensure", "FAIL");
			}
			
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);			
			if(sEnvironment.equals("PROD"))
				sUrl=ExcelDataProvider.getCellDataByFilter("BaseData", "Production_Environment",sCountryName, 1);
			else
				sUrl=ExcelDataProvider.getCellDataByFilter("BaseData", "Environment",sEnvironment, 1) + ExcelDataProvider.getCellDataByFilter("BaseData", "Country",sCountryName, 1) + "/";
			
			System.out.println("################################ Execution Details #####################################################");
			System.out.println("                                                                                                        ");
			System.out.println("		Environment			: " + sEnvironment);
			System.out.println("		Browser				: " + browser);
			System.out.println("		Applicaiton URL			: " + sUrl);			
			System.out.println("		Country				: " + sCountryName);			
			System.out.println("		Language			: " + sExecutionLanguage);			
			System.out.println("		Actor				: " + sActor);
			System.out.println("                                                                                                        ");
			System.out.println("########################################################################################################");
			
			driver.get(sUrl);
			driver.manage().window().maximize();	
			reportStep("The browser:" + browser + " launched successfully", "PASS", true);						
			reportStep("Test is Run in "+sEnvironment+" Environment", "INFO");				
			
		} 
			catch (WebDriverException e) {			
			if(driver!=null)
				 closeAllBrowsers();
			e.printStackTrace();
			System.out.println("Webdriver Exceptin occured!!!!");
			try {
			 	if(sExecutionOS.equals("WINDOWS"))
			 		killBrowserWebDriverBasedOnOS("WINDOWS");
			 	else if(sExecutionOS.equals("LINUX"))
			 		killBrowserWebDriverBasedOnOS("LINUX");
			 	
				//Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				System.out.println("Process Killed");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Looks the chromedriver.exe process not exist currently");
				e1.printStackTrace();
			}
			throw new RuntimeException("FAILED");
			//reportStep("The browser:" + browser + " could not be launched", "FAIL");
		}
			catch (Exception e) {			
			e.printStackTrace();
			if(driver!=null)
				 closeAllBrowsers();
		}	

	}

	public WebElement locateElement(String locator, String locValue) {
		try {
			switch(locator) {
			case("id"): return driver.findElementById(locValue);
			case("link"): return driver.findElementByLinkText(locValue);
			case("partial"): return driver.findElementByPartialLinkText(locValue);
			case("xpath"):return driver.findElementByXPath(locValue);
			case("name"): return driver.findElementByName(locValue);
			case("class"): return driver.findElementByClassName(locValue);
			case("tag"):return driver.findElementByTagName(locValue);
			case("css"):return driver.findElementByCssSelector(locValue);
			
			default:
				reportStep("No Such Locator '" + locator + "' Exist. Please ensure given locator is valid/handled.", "FAIL",true);				
			}
		} catch (NoSuchElementException e) {
			reportStep("The element with locator "+locator+" and with value "+locValue+" not found.","FAIL",true);
			throw new RuntimeException();
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		}
		return null;
	}

	public WebElement locateElement(String locValue) {
		return driver.findElementById(locValue);
	}


	public void type(WebElement ele, String data) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.elementToBeClickable(ele));		
			ele.clear();
			ele.sendKeys(data);
			reportStep("The data: "+data+" entered successfully in field :", "PASS",true);
		} catch (InvalidElementStateException e) {
			reportStep("The element is not interactable","FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		}
	}

	public void typeAndChoose(WebElement ele, String data) {
		try {
			ele.clear();
			ele.sendKeys(data);
			new Actions(driver).sendKeys(Keys.ARROW_DOWN).build().perform();
			reportStep("The data: "+data+" entered successfully in field :"+ele, "PASS",true);
		} catch (InvalidElementStateException e) {
			reportStep("The element: "+ele+" is not interactable","FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		}
	}
	
	public void click(WebElement ele) {
		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.elementToBeClickable(ele));			
			text = ele.getText();
			ele.click();
			reportStep("The element : "+text+" is clicked ", "PASS");
		} catch (InvalidElementStateException e) {
			reportStep("The element: "+text+" is not interactable", "FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		} 

	}
	
	/*with Snap click function*/
	public void click(WebElement ele, boolean bSnap) {
		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.elementToBeClickable(ele));			
			text = ele.getText();
			ele.click();
			reportStep("The element : "+text+" is clicked ", "PASS",true);
		} catch (InvalidElementStateException e) {
			reportStep("The element: "+text+" is not interactable", "FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		} 

	}
	
	

	public String getText(WebElement ele) {	
		String bReturn = "";
		try {
			bReturn = ele.getText();
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		}
		return bReturn;
	}

	public String getTitle() {		
		String bReturn = "";
		try {
			bReturn =  driver.getTitle();
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		} 
		return bReturn;
	}

	public String getAttribute(WebElement ele, String attribute) {		
		String bReturn = "";
		try {
			bReturn=  ele.getAttribute(attribute);
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		} 
		return bReturn;
	}

	public void selectDropDownUsingText(WebElement ele, String value) {
		try {
			new Select(ele).selectByVisibleText(value);
			reportStep("The dropdown is selected with text '"+value + "'","PASS",true);
			
		}catch(NoSuchElementException e) {
			reportStep("NoSuchElementException"+e.getMessage(), "FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		}

	}

	public void selectDropDownUsingIndex(WebElement ele, int index) {
		try {
			new Select(ele).selectByIndex(index);
			reportStep("The dropdown is selected with index '"+index +"'","PASS",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		} 

	}

	public boolean verifyTitle(String expectedTitle) {
		boolean bReturn =false;
		try {
			if(getTitle().contains(expectedTitle)) {
				reportStep("The actual title contains the expected text "+expectedTitle,"PASS");
				bReturn= true;
			}else {
				reportStep(getTitle()+" The actual title doesn't matches the expected text "+expectedTitle,"FAIL",true);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} 
		return bReturn;

	}

	public void verifyExactText(WebElement ele, String expectedText) {
		try {
			String actual=getText(ele);
			if(getText(ele).replaceAll("\n", " ").equals(expectedText)) {
				reportStep("The expected text '" + expectedText + "' matches the actual '"+actual + "'","PASS");
			}else {
				reportStep("The expected text '" + expectedText + "' doesn't matches the actual '"+actual + "'","FAIL",true);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
			}
	
	}

	public void verifyPartialText(WebElement ele, String expectedText) {
		try {
			if(getText(ele).replaceAll("\n", " ").contains(expectedText)) {
				reportStep("The expected text contains the actual '"+expectedText + "'","PASS");
			}else {
				reportStep("The expected text doesn't contain the actual '"+expectedText + "'","FAIL",true);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} 
	}

	public void verifyPartialText(WebElement ele, String expectedText, boolean bSnap) {
		try {
			System.out.println(getText(ele));
			if(getText(ele).replaceAll("\n", " ").contains(expectedText)) {
				reportStep("The expected text contains the actual '"+expectedText + "'","PASS",bSnap);
			}else {
				reportStep("The expected text doesn't contain the actual '"+expectedText + "'","FAIL",true);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} 
	}
	
	
	public void verifyExactAttribute(WebElement ele, String attribute, String value) {
		try {
			if(getAttribute(ele, attribute).equals(value)) {
				reportStep("The expected attribute :"+attribute+" value matches the actual "+value,"PASS");
			}else {
				reportStep("The expected attribute :"+attribute+" value does not matches the actual "+value,"FAIL",true);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} 

	}
	
	public void verifyExactAttribute(WebElement ele, String attribute, String value,boolean isScrShotRequried) {
		try {
			if(getAttribute(ele, attribute).equals(value)) {
				reportStep("The expected attribute :"+attribute+" value matches the actual "+value,"PASS",isScrShotRequried);
			}else {
				reportStep("The expected attribute :"+attribute+" value does not matches the actual "+value,"FAIL",isScrShotRequried);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} }

	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		try {
			if(getAttribute(ele, attribute).contains(value)) {
				reportStep("The expected attribute :"+attribute+" value contains the actual "+value,"PASS");
			}else {
				reportStep("The expected attribute :"+attribute+" value does not contains the actual "+value,"FAIL",true);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		}
	}

	public void verifySelected(WebElement ele) {
		try {
			if(ele.isSelected()) {
				reportStep("The element "+ele+" is selected","PASS");
			} else {
				reportStep("The element "+ele+" is not selected","FAIL",true);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		}
	}

	public void verifyDisplayed(WebElement ele) {
		try {
			if(ele.isDisplayed()) {
				reportStep("The element "+ele+" is visible","PASS");
			} else {
				reportStep("The element "+ele+" is not visible","FAIL",true);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} 
	}

	public void switchToWindow(int index) {
		try {
			Set<String> allWindowHandles = driver.getWindowHandles();
			List<String> allHandles = new ArrayList<>();
			allHandles.addAll(allWindowHandles);
			driver.switchTo().window(allHandles.get(index));
		} catch (NoSuchWindowException e) {
			reportStep("The driver could not move to the given window by index "+index,"FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		}
	}

	public void switchToFrame(WebElement ele) {
		try {
			driver.switchTo().frame(ele);
			reportStep("switch In to the Frame "+ele,"PASS");
		} catch (NoSuchFrameException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} 
	}

	public void acceptAlert() {
		String text = "";		
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.accept();
			reportStep("The alert "+text+" is accepted.","PASS");
		} catch (NoAlertPresentException e) {
			reportStep("There is no alert present.","FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		}  
	}

	public void dismissAlert() {
		String text = "";		
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.dismiss();
			reportStep("The alert "+text+" is dismissed.","PASS");
		} catch (NoAlertPresentException e) {
			reportStep("There is no alert present.","FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} 

	}

	public String getAlertText() {
		String text = "";		
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
		} catch (NoAlertPresentException e) {
			reportStep("There is no alert present.","FAIL",true);
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);
		} 
		return text;
	}

	public long takeSnap(){
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+number+".jpg"));
		
		} catch (WebDriverException e) {
			reportStep("The browser could not be accessed for snapshot.","FAIL",true);
		} catch (IOException e) {
			reportStep("The snapshot could not be taken","FAIL",true);
		}
		return number;
	}

	public void closeBrowser() {
		try {
			driver.close();
			reportStep("The browser is closed","INFO");
		} catch (Exception e) {
			reportStep("The browser could not be closed","INFO");
		}
	}

	public void closeAllBrowsers() {
		try {
			driver.quit();
			reportStep("The opened browsers are closed","INFO");
		} catch (Exception e) {
			reportStep("The browsers could not be closed","INFO");
		}
	}

	public void verifyDisplayed(WebElement ele, String eleName) {
		try
		{
			if (ele.isDisplayed())
				reportStep("Element : " + eleName + " is Displayed","PASS");
			else
				reportStep("Element : " + eleName + " may not Displayed","FAIL",true);
					
		}catch(ElementNotVisibleException e) {
			reportStep("Element may not visible","FAIL",true);	
		}catch (InvalidElementStateException e) {
			reportStep("Currenlty system may not do any action on this element " + eleName,"FAIL",true);}	
		
	}

	public void verifyTwoStringsExactly(String actualText, String expectedText) {

			try {			
				if (actualText.equals(expectedText))
					reportStep("Expected Text : '" + expectedText + "' exactly matches with Actual Text: '" + actualText + "'","PASS");
				else
					reportStep("Expected Text : '" + expectedText + "' not exactly matches with Actual Text: '" + actualText + "'","FAIL",true);
						
			}catch(NullPointerException e) {
				reportStep("Expected/Actual text may be null","FAIL",true);
				
			}
		}

	public void verifyTwoStringsExactly(String actualText, String expectedText,boolean bsnap) {

		try {			
			if (actualText.equals(expectedText))
				reportStep("Expected Text : '" + expectedText + "' exactly matches with Actual Text: '" + actualText + "'","PASS",bsnap);
			else
				reportStep("Expected Text : '" + expectedText + "' not exactly matches with Actual Text: '" + actualText + "'","FAIL",true);
					
		}catch(NullPointerException e) {
			reportStep("Expected/Actual text may be null","FAIL",true);
			
		}
	}
	
	public void verifyTwoStringsPartially(String actualText, String expectedText) {

		try {			
			if (actualText.contains(expectedText))
				reportStep("Expected Text : '" + expectedText + "' exactly matches with Actual Text: '" + actualText + "'","PASS");
			else
				reportStep("Expected Text : '" + expectedText + "' not exactly matches with Actual Text: '" + actualText + "'","FAIL",true);
					
		}catch(NullPointerException e) {
			reportStep("Expected/Actual text may be null","FAIL",true);
			
		}
	}
	
	public void verifyTwoStringsPartially(String actualText, String expectedText, boolean bsnap) {

		try {			
			if (actualText.contains(expectedText))
				reportStep("Expected Text : '" + expectedText + "' exactly matches with Actual Text: '" + actualText + "'","PASS",bsnap);
			else
				reportStep("Expected Text : '" + expectedText + "' not exactly matches with Actual Text: '" + actualText + "'","FAIL",true);
					
		}catch(NullPointerException e) {
			reportStep("Expected/Actual text may be null","FAIL",true);
			
		}
	}
	
	public void mouseOver(WebElement ele) {
		
		//WebElement web_Element_To_Be_Hovered = webDriver.findElement(By.cssSelector(selector_For_Web_Element_To_Be_Hovered));
		Actions builder = new Actions(driver);
		builder.moveToElement(ele).build().perform();		
	}

	public boolean verifyElementExist(String locator, String value) {
		
		//reportStep("Checking '" + elementName + "' is Exist","INFO");
		if (locateElements(locator, value).size()>0) 			
			return true;		
		else
			return false;
	}

	public void verifyElementExist(String locator, String value, String elementName) {		
		reportStep("Checking '" + elementName + "' is Exist","INFO");
		if (locateElements(locator, value).size()>0)
			reportStep("'" + elementName + "' Exist","PASS");			
		else reportStep("'" + elementName + "' Not Exist","FAIL",true);
		
	}

	public void verifyElementExist(String locator, String value, String elementName,boolean scrShotRequired) {
		reportStep("Checking '" + elementName + "' is Exist","INFO");
		if (locateElements(locator, value).size()>0)
			reportStep("'" + elementName + "' Exist","PASS",scrShotRequired);			
		else reportStep("'" + elementName + "' Not Exist","FAIL",scrShotRequired);
	
	}

	public List<WebElement> locateElements(String locator, String locValue) {
		try {
			switch(locator) {
			case("id"): return driver.findElementsById(locValue);
			case("name"): return driver.findElementsByName(locValue);
			case("link"): return driver.findElementsByLinkText(locValue);
			case("xpath"):return driver.findElementsByXPath(locValue);
			case("class"): return driver.findElementsByClassName(locValue);
			case("tag"): return driver.findElementsByTagName(locValue);
			case("css"): return driver.findElementsByCssSelector(locValue);
			}
			
		}catch(NoSuchElementException e) {
			reportStep("The Element with locator '" + locator+ "' and value '" + locValue +"' not found/Exist","FAIL",true);
		}
		return null;
	}
	
	public void clickWithNoSnap(WebElement ele) {		
		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.elementToBeClickable(ele));			
			text = ele.getText();
			ele.click();
			reportStep("The element : "+text+" is clicked ", "PASS",false);
		} catch (InvalidElementStateException e) {
			reportStep("The element: "+text+" is not interactable", "FAIL",false);
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",false);
		}
		
	}
	
	public long takeFullSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try {
			//To take Full page Screeshot -using aSHOT
			Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		    ImageIO.write(fpScreenshot.getImage(),"JPG",new File("./reports/images/"+number+".jpg"));
			
		} catch (WebDriverException e) {
			reportStep("The browser could not be accessed for snapshot.","FAIL",true);
		} catch (IOException e) {
			reportStep("The snapshot could not be taken","INFO",true);
		}
		return number;
	}

	public void captureFullScreen(String desc) {
		try {
			reportStep(desc, "PASS", true, true);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLocalePropertyValue(String Property) {
		
		if (localeProp.getProperty(Property)!=null)
			return localeProp.getProperty(Property);
		else {
			reportStep("No such Property '" + Property + "' Exist in the Property file.", "FAIL",true);
			return null;}
	}

	public int getRandomNumberBetween(int min, int max) {
		
		Random random=new Random();
		return min + random.nextInt(max);
	}

	public WebElement locateElement(WebElement elm,String locator, String locValue) {
		try {
			switch(locator) {
			case("id"): return elm.findElement(By.id(locValue));
			case("link"): return elm.findElement(By.linkText(locValue));
			case("partial"): return elm.findElement(By.partialLinkText(locValue));
			case("xpath"):return elm.findElement(By.xpath(locValue));
			case("name"): return elm.findElement(By.name(locValue));
			case("class"): return elm.findElement(By.className(locValue));
			case("tag"):return elm.findElement(By.tagName(locValue));
			case("css"):return elm.findElement(By.cssSelector(locValue));
			}
		} catch (NoSuchElementException e) {
			reportStep("The element with locator "+locator+" and with value "+locValue+" not found.","FAIL",true);
			throw new RuntimeException();
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		}
		return null;
	}

	public List<WebElement> locateElements(WebElement elm,String locator, String locValue) {
		try {
			switch(locator) {
			case("id"): return elm.findElements(By.id(locValue));
			case("link"): return elm.findElements(By.linkText(locValue));
			case("partial"): return elm.findElements(By.partialLinkText(locValue));
			case("xpath"):return elm.findElements(By.xpath(locValue));
			case("name"): return elm.findElements(By.name(locValue));
			case("class"): return elm.findElements(By.className(locValue));
			case("tag"):return elm.findElements(By.tagName(locValue));
			case("css"):return elm.findElements(By.cssSelector(locValue));
			}
		} catch (NoSuchElementException e) {
			reportStep("The element with locator "+locator+" and with value "+locValue+" not found.","FAIL",true);
			throw new RuntimeException();
		} catch (WebDriverException e) {
			reportStep("WebDriverException"+e.getMessage(), "FAIL",true);
		}
		return null;
	}

	public void verifyExactText(WebElement ele, String expectedText, String eleName) {
		try {
			String actual=getText(ele);
			if(getText(ele).replaceAll("\n", " ").equals(expectedText)) {
				reportStep(eleName + " Value '" + actual + "' exactly matches with Expected Value '" + expectedText + "'","PASS");
			}else {
				reportStep(eleName + " Value '" + actual + "' may not exactly matches with Expected Value '" + expectedText + "'","FAIL",true);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL",true);}
		
	}
	
	public void verifyExactText(WebElement ele, String expectedText, String eleName,boolean isScrShotRequired) {
		try {
			String actual=getText(ele);
			if(getText(ele).equals(expectedText)) {
				reportStep(eleName + " Value '" + actual + "' exactly matches with Expected Value '" + expectedText + "'","PASS",isScrShotRequired);
			}else {
				reportStep(eleName + " Value '" + actual + "' may not exactly matches with Expected Value '" + expectedText + "'","FAIL",isScrShotRequired);
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : "+e.getMessage(), "FAIL");}
		
	}
	
	
	public void moveContextToElement(WebElement element) {
	       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	       }
	              

	   public void fluentwaitForObjectToLoad(By loc) {
	          FluentWait wait = new FluentWait<>(driver);
	          wait.withTimeout(20000, TimeUnit.MILLISECONDS);
	          wait.pollingEvery(250, TimeUnit.MILLISECONDS);
	          wait.ignoring(NoSuchElementException.class);
	          wait.until(ExpectedConditions.elementToBeClickable(loc));
	   }
	   
	   public void fluentwaitForObjectToLoadTable(By loc,By loc1) {
	          FluentWait wait = new FluentWait<>(driver);
	          wait.withTimeout(20000, TimeUnit.MILLISECONDS);
	          wait.pollingEvery(250, TimeUnit.MILLISECONDS);
	          wait.ignoring(NoSuchElementException.class);
	          wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(loc,loc1));
	   }

// Added by Prateek

public void verifyStringContainsOtherString(String actualText, String expectedText) {

	try {			
		if (actualText.contains(expectedText))
			reportStep("Expected Text : '" + expectedText + "' exactly matches with Actual Text: '" + actualText + "'","PASS");
		else
			reportStep("Expected Text : '" + expectedText + "' not exactly matches with Actual Text: '" + actualText + "'","FAIL",true);
				
	}catch(NullPointerException e) {
		reportStep("Expected/Actual text may be null","FAIL",true);
		
	}
	}

public void killBrowserWebDriverBasedOnOS(String os) throws IOException {
    
    if(os.equals("LINUX")) {
           System.out.println("Killing all browsers and chromedrivers before suite in LINUX");
//         Runtime.getRuntime().exec("pgrep chrome | xargs kill");
/*           Runtime.getRuntime().exec("pkill -u SYSTEM chrome");
           Runtime.getRuntime().exec("pgrep chromedriver | xargs kill");
*/           
/*           Runtime.getRuntime().exec("pkill -u \"SYSTEM\" \"Google Chrome\"");*/
           Runtime.getRuntime().exec("pgrep \"chromedriver\" | xargs kill");
		   System.out.println("Killed all browsers and chromdrivers");
    }
    
    else
    {
           System.out.println("Killing all browsers and chromedrivers before suite in WINDOWS");
           Runtime.getRuntime().exec("taskkill /f /fi \"USERNAME eq SYSTEM\" /im chrome.exe");
           Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		   System.out.println("Killed all browsers and chromdrivers");
    }
}
}

package wdMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import utils.ExcelDataProvider;
//import wdMethods.SeMethods;
import utils.WebTableFunctions;

public class ProjectMethods extends SeMethods{
	
	public String browserName;
/*	public String excelName;
	public String excelSheetName;*/
	public String country;
	public  String testCaseID;
	public String localePropertyFile;
	public LocalDateTime now = LocalDateTime.now();	
	public String newDirectoryName ;
	public boolean isConfigIssue=false;
	public String errorDesc="";
	public static boolean isDataProviderPresent;
	public static int successCount,failureCount,skipCount;
/*	public ProjectMethods() {
		System.out.println("TEST ProjectMethods cons ");
	}*/

	@BeforeSuite
	public void beforeSuite() throws IOException{
		//System.out.println("From Project Method - Before Suite");
		successCount=0;
		failureCount=0;
		skipCount=0;
		 Method[] methods;
		if(this.getClass().getSuperclass().toString().contains("internationalTests"))
		methods = this.getClass().getSuperclass().getDeclaredMethods();
			else
		  methods = this.getClass().getDeclaredMethods();
		
		/*System.out.println(this.getClass().getSuperclass() );
		System.out.println(methods.length);*/
		 Method method=null;
		 for(int i=0;i<methods.length;i++) {
			 if(!methods[i].getName().equals("setValues")) {
			method = methods[i];	
			 break;
			 }
		 } 
		
		 //putting this code before the execution to kill the SYSTEM browser and chromedriver		 	
		 	
		 if(method.getAnnotations()[0].toString().contains("fetchData"))
			isDataProviderPresent = true;
		else
			isDataProviderPresent = false;
		
		startResult();
		
		//Trigger Hub & Node for Selenium Grid
		/*		try {		
					
					System.out.println("Bamboo Path: " + bambooPath);
		            String[] command = {"cmd.exe", "/C", "Start", "C:\\Bamboo\\RemoteAgents\\agent1\\xml-data\\build-dir\\TQAPR-IRDBUILD-JOB1\\grid\\Hub.bat"};
		            Process p1 =  Runtime.getRuntime().exec(command);           
		            
		            String[] command1 = {"cmd.exe", "/C", "Start", "C:\\Bamboo\\RemoteAgents\\agent1\\xml-data\\build-dir\\TQAPR-IRDBUILD-JOB1\\grid\\ChromeNode.bat"};
		            Process p2 =  Runtime.getRuntime().exec(command1);
		            Thread.sleep(10000);
		            System.out.println("Batch file executed");
		            
		        } catch (IOException ex) {
		        	reportStep("Starting Hub & Note Failed. Please check the bat file", "FAIL");
		        }catch(InterruptedException e){
		        	reportStep("Starting Hub & Note Failed. UnExpected Error Occurs", "FAIL");
				}*/
	}

	@BeforeTest
	public void beforeTest(){
	}	
	
	//@Parameters({"executionEnvironment","country"})	
	@Parameters({"country"})
	@BeforeMethod	
	public void beforeMethod(@Optional("NotFromTestNGXML") String Country){
		
		//System.out.println("Befroe Method - Started");
		String ExecutionLang;
		String isValidLanguage, languageWithCountry,isValidActor,localeFile="";
		FileOutputStream out;

		try {			
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			//sHubUrl = prop.getProperty("HUB");
			//sHubPort = prop.getProperty("PORT");
			
			//Assigning the Country to execute based on Execution Mode (Maven / Manually)
			if (Country.equals("NotFromTestNGXML")) {
				sCountryName= prop.getProperty("COUNTRY_NAME");
				sEnvironment= prop.getProperty("ENVIRONMENT");	
			}
			else {
				sCountryName=Country;
				//sEnvironment=ExeEnv;
				sEnvironment=System.getProperty("environment");
//				sEnvironment="PROD";
			}
			
			if(sCountryName.isEmpty())
				errorDesc="Please ensure the Execution Country. Please refer the Config File before executing manually";
			
			//Retrieve Execution Language from BaseData - ExecutionConfig excel sheet
			ExecutionLang=ExcelDataProvider.getCellDataByFilter("BaseData", "ExecutionConfig",sCountryName, 1).trim();			
			sExecutionLanguage=ExecutionLang;		
			
			//Retrieving the Actor to execute from BaseData - ExecutionCofng excel sheet
			sActor=ExcelDataProvider.getCellDataByFilter("BaseData", "ExecutionConfig",sCountryName, 2).trim();
			
			//Checking the given country supports Multilingual
			boolean isMultilingualCountry=false;
			if(ExcelDataProvider.getCellDataByFilter("BaseData", "MultiLingualCountries",sCountryName, 0)!=null)					
				isMultilingualCountry=true;
			
			if(sExecutionLanguage.equals("NA")) {
				languageWithCountry=sCountryName;
				localeFile="./src/main/resources/dictionary_" + sCountryName + ".properties";
				if(isMultilingualCountry) {
					isConfigIssue=true;
					errorDesc="Execution Language required for this Execution Country '" + sCountryName + "'. Please Ensure with BaseDate file (MultiLingualCountries Tab) before executing";					
				}else {
				localeProp.load(new FileInputStream(new File("./src/main/resources/dictionary_" + sCountryName + ".properties")));			
					}
				}
			else {
				languageWithCountry=sCountryName + "-" + sExecutionLanguage;				
				if(ExcelDataProvider.getCellDataByFilter("BaseData", "ACTORS",languageWithCountry, 0)==null) {
					isConfigIssue=true;
					errorDesc="Execution Language '" + sExecutionLanguage + "' may not applicable for the Execution Country '" + sCountryName + "'. Please Ensure with BaseDate file before executing";					
				}else {
				localeFile="./src/main/resources/dictionary_" + sCountryName + "_" + sExecutionLanguage + "Lang.properties";
				localeProp.load(new FileInputStream(new File("./src/main/resources/dictionary_" + sCountryName + "_" + sExecutionLanguage + "Lang.properties")));
					}
				}			
			
			//Checking the Given ACTOR availability	for the given Country and Execution Language		
			if(sExecutionLanguage.equals("NA"))
				languageWithCountry=sCountryName;
			else 
				languageWithCountry=sCountryName + "-" + sExecutionLanguage;
			
			if(ExcelDataProvider.getCellDataByFilter("BaseData", "ACTORS",languageWithCountry, 1).trim().equals("NA"))
				sActor="";	
				
			if(!ExcelDataProvider.getCellDataByFilter("BaseData", "ACTORS",languageWithCountry, 1).trim().contains(sActor)) {				
				isConfigIssue=true;
				errorDesc="Given ACTOR '" + sActor + "' may not applicable for Execution Language '" + sExecutionLanguage + "' and Country '" + sCountryName + "'. Please Ensure with BaseDate file before executing";
			}			
			
			//Storing Environment Vairables in Config File
			out = new FileOutputStream(new File("./src/main/resources/config.properties"));
			prop.setProperty("COUNTRY_NAME", sCountryName);
			prop.setProperty("ENVIRONMENT", sEnvironment);
			if(!ExecutionLang.contains("NA"))
				prop.setProperty("EXECUTION_LANGUAGE", ExecutionLang);
			else
				prop.setProperty("EXECUTION_LANGUAGE", "");
			prop.setProperty("LOCALE_FILE", localeFile);
			prop.setProperty("ACTOR", sActor);
			prop.store(out, "IRD Project Properties");			
			out.close();
			
			//Loading updated Config File
			prop.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			sCountryName= prop.getProperty("COUNTRY_NAME");
			sEnvironment= prop.getProperty("ENVIRONMENT");
			System.out.println("Env:" + sEnvironment);
			
			sExecutionLanguage= prop.getProperty("EXECUTION_LANGUAGE");
			sActor=prop.getProperty("ACTOR");
			sLocaleFile=prop.getProperty("LOCALE_FILE");	
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@2		
		
		
		//Added below line to retrieve the Test Case ID
		testCaseID=this.getClass().getSimpleName().split("_")[0].trim();
		
		
		
		test = startTestCase(testCaseID + " : " + testCaseName, testDescription);
		test.assignCategory(category);
		test.assignAuthor(authors);
//		System.out.println("from Before method config issue: " + isConfigIssue);
//		reportStep("before method", "INFO", false);
		startApp(browserName,errorDesc);		
	}
		

	
	@AfterSuite
	public void afterSuite(ITestContext c) throws IOException{
		endResult();
		//Adding below code to change the reporting format
		String actorString,reportName;
		String langString;
/*		if(sActor.equals(""))
			actorString="";
		else
			actorString = " ("+sActor.toUpperCase()+ ") ";		
	
		
		if(sExecutionLanguage.equals(""))
			langString="";
		else
			langString = " ("+sExecutionLanguage.toUpperCase()+ ") ";
		
		if (!c.getSuite().getName().equals("Default suite")) {
			
			newDirectoryName="./reports/"+c.getSuite().getName()+ actorString + langString +DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss").format(now) ;
		
		}else {
			 newDirectoryName = "./reports/"+this.getClass().getSimpleName()+" ("+sCountryName.toUpperCase()+ ") " + actorString + langString + DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss").format(now);
		
		}*/
		
		//Adding below code to change the reporting format
		if (!c.getSuite().getName().equals("Default suite"))
			reportName="./reports/"+c.getSuite().getName();
		else
			reportName = "./reports/"+this.getClass().getSimpleName()+"_"+sCountryName.toUpperCase();		

		if(sActor.isEmpty())
			reportName=reportName;
		else
			reportName=reportName + "_" + sActor.toUpperCase();
		
		if(sExecutionLanguage.isEmpty())
			reportName=reportName;
		else
			reportName=reportName + "_" + sExecutionLanguage.toUpperCase();
		
		newDirectoryName=reportName+"_" + DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss").format(now);
		newDirectoryName=newDirectoryName.replace(" ", "");
		
	   File  directory = new File(newDirectoryName);
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    else 
	    {
	    	System.out.println(newDirectoryName + " directory exists"); 
	    }
	    
//	    System.out.println("suite name psk:"+c.getSuite().getName());
	   
		File dir = new File("./reports");
		    if(dir.isDirectory()) {
		        File[] content = dir.listFiles();
		        for(int i = 0; i < content.length; i++) {
		        	 File file = content[i];
		        	 if(file.getName().contains("report") || file.getName().equals("images"))
		 	        // renaming the file and moving it to a new location
			 	        if(file.renameTo(new File(directory + "/" +file.getName())))
			 	        {
			 	            // if file copied successfully then delete the original file
			 	            file.delete();
			 	            System.out.println("File moved successfully");
			 	        }
			 	        else
			 	        {
			 	            System.out.println("Failed to move the file");
			 	        }
		 	 	  
		        	}
		        	
		    	}    
		  //System.out.println("Success: " + successCount + " Failure: " + failureCount + " Skip: " + skipCount);
		    /*		    String[] strTo=ExcelDataProvider.getCellDataByFilter("BaseData", "E-Mail","ToRecipient", 1).trim().split(",");
		    		    email em=new email();
		    		    em.sendEmail(successCount, failureCount, skipCount, strTo,sCountryName.toUpperCase());*/
		    		    
		    		    
		    		    //Kill Selenium Grid Hub & Node Process if any
		    /*            try {
		                	String[] command3 = {"cmd.exe", "/C", "Start", "C:\\Bamboo\\RemoteAgents\\agent1\\xml-data\\build-dir\\TQAPR-IRDBUILD-JOB1\\grid\\KillHubAndNote.bat"};
		    				Process p3 =  Runtime.getRuntime().exec(command3);
		    				System.out.println("Processed Killed--1");
		    			} catch (IOException e) {				
		    				e.printStackTrace();
		    				System.out.println("Processed may not Killed");
		    			} */
	
		    if(sExecutionOS.equals("WINDOWS")) {
		 	//	killBrowserWebDriverBasedOnOS("WINDOWS");
		 		
			 	//System.out.println("Killing all browsers and chromedrivers before suite");
//				Runtime.getRuntime().exec("taskkill /f /fi \"USERNAME eq SYSTEM\" /im chrome.exe");
	//			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		 		
			}else if(sExecutionOS.equals("LINUX"))
				killBrowserWebDriverBasedOnOS("LINUX");
		 	
	
	
	}

	@AfterTest
	public void afterTest(){
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result){
		
	    int status = result.getStatus();
	    //System.out.println("Succes Percentage: " + ITestResult.SUCCESS_PERCENTAGE_FAILURE);
	    switch (status) {
	        case ITestResult.SUCCESS:	            
	        	//System.out.println("PASS");
	        	successCount=successCount+1;
	            break;
	        case ITestResult.FAILURE:	            
	        	//System.out.println("FAIL");
	        	failureCount=failureCount+1;
	            break;
	        case ITestResult.SKIP:	            
	        	//System.out.println("SKIP");
	        	skipCount=skipCount+1;
	            break;
	        default:
	            throw new RuntimeException("Invalid status");
	    }    
		closeAllBrowsers();
		endTestcase();
	}
	
	@DataProvider(name="fetchData")
	public  Object[][] getData(){
		String DPcountry ="undefined";
		
		if(this.getClass().toString().contains("internationalTests")) {
			String testName=this.getClass().toString();
			DPcountry = testName.split("\\.")[1];
		}

		System.out.println(DPcountry);
		System.out.println(sExecutionLanguage);
		if(DPcountry.equals("Canada") && sExecutionLanguage.equals("French"))
				return ExcelDataProvider.getExcelData(excelName,DPcountry+"_French");
			else
				return ExcelDataProvider.getExcelData(excelName,DPcountry);		
	}
	
/*	public Object[][] getExcelData(String exlName,String exlSheetName)
	{
		return ExcelDataProvider.getExcelData(excelName,excelSheetName);
	}*/
	
	public String getExcelName()
	{
		return excelName;
	}
	public void printUnexpectedLogDuringExecution(Exception e)
	{
		reportStep("Unexpected Failure/Error Occurs during Execution: " + e.getMessage(), "FAIL");
	}
	
	
	public String getShareClassFronFundName(String FundName)
	{
		
		return FundName.split(" - ")[1].trim();
	}
	
	public void validateLabelwithAsOfDateDaily(WebElement label,String lblName) {
		List<WebElement> spanEle = WebTableFunctions.getAllTagsInsideAnElement(label, "span");
		String spanEleText1 = spanEle.get(0).getText();
		String spanEleText2 = spanEle.get(1).getText();
		String spanEleText3 = spanEle.get(2).getText();
		String spanEleText4 = spanEle.get(3).getText();
		if (spanEleText1.contains(lblName) && spanEleText2.contains(getLocalePropertyValue("lblDateFormatPart1")) && spanEleText4.contains(getLocalePropertyValue("lblDateFormatPart2")) && spanEleText3.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(lblName+" Label is displayed as Expected: "+label.getText(),"PASS");
		else
			reportStep(lblName+" Label is not displayed as Expected: "+label.getText(),"FAIL");			
	}
	
	public void validateLabelwithAsOfDateDailyftLabel(WebElement label,String lblName) {
		List<WebElement> spanEle = WebTableFunctions.getAllTagsInsideAnElement(label, "ft-label");
		String spanEleText1 = spanEle.get(0).getText();
		String spanEleText2 = spanEle.get(1).getText();
		String spanEleText3 = spanEle.get(2).getText();
		String spanEleText4 = spanEle.get(3).getText();
		if (spanEleText1.contains(lblName) && spanEleText2.contains(getLocalePropertyValue("lblDateFormatPart1")) && spanEleText4.contains(getLocalePropertyValue("lblDateFormatPart2")) && spanEleText3.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(lblName+" Label is displayed as Expected: "+label.getText(),"PASS");
		else
			reportStep(lblName+" Label is not displayed as Expected: "+label.getText(),"FAIL");			
	}
	
	public void validateLabelwithAsOfDateMonthly(WebElement label,String lblName) {
		List<WebElement> spanEle = WebTableFunctions.getAllTagsInsideAnElement(label, "span");
		String spanEleText1 = spanEle.get(0).getText();
		int k=1;
		if(spanEle.get(k).getAttribute("class").equals("footnote"))
			k++;
		String spanEleText2 = spanEle.get(k).getText();
		String spanEleText3 = spanEle.get(k+1).getText();
		String spanEleText4 = spanEle.get(k+2).getText();
		if (spanEleText1.contains(lblName) && spanEleText2.contains(getLocalePropertyValue("lblDateFormatPart1")) && spanEleText4.contains(getLocalePropertyValue("lblDateFormatPart3")) && spanEleText3.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(lblName+" Label is displayed as Expected: "+label.getText(),"PASS");
		else
			reportStep(lblName+" Label is not displayed as Expected: "+label.getText(),"FAIL");			
	}
	
	public void validateLabelwithAsOfDateOnly(WebElement label,String lblName) {
		List<WebElement> spanEle = WebTableFunctions.getAllTagsInsideAnElement(label, "span");
		String spanEleText1 = spanEle.get(0).getText();
		int k=1;
		if(spanEle.get(k).getAttribute("class").equals("footnote"))
			k++;
		String spanEleText2 = spanEle.get(k).getText();
		String spanEleText3 = spanEle.get(k+1).getText();
	
		if (spanEleText1.contains(lblName) && spanEleText2.contains(getLocalePropertyValue("lblDateFormatPart1"))  && spanEleText3.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(lblName+" Label is displayed as Expected: "+label.getText(),"PASS");
		else
			reportStep(lblName+" Label is not displayed as Expected: "+label.getText(),"FAIL");			
	}
	
	public void validateLabelwithAsOfDateMonthlyftLabel(WebElement label,String lblName) {
		List<WebElement> spanEle = WebTableFunctions.getAllTagsInsideAnElement(label, "ft-label");
		String spanEleText1 = spanEle.get(0).getText();
		int k=1;
		if(spanEle.get(k).getAttribute("class").equals("footnote"))
			k++;
		String spanEleText2 = spanEle.get(k).getText();
		String spanEleText3 = spanEle.get(k+1).getText();
		String spanEleText4 = spanEle.get(k+2).getText();
		if (spanEleText1.contains(lblName) && spanEleText2.contains(getLocalePropertyValue("lblDateFormatPart1")) && spanEleText4.contains(getLocalePropertyValue("lblDateFormatPart3")) && spanEleText3.matches("[0-3]\\d.\\d{2}.\\d{4}"))
			reportStep(lblName+" Label is displayed as Expected: "+label.getText(),"PASS");
		else
			reportStep(lblName+" Label is not displayed as Expected: "+label.getText(),"FAIL");			
	}
	
	public void validateFormat(WebElement ele,String format,String msgText) {
		String tableElement = ele.getText().trim().replace("\n", " ");
		if (tableElement.matches(format)) 
			reportStep(msgText+" value is displayed as Expected: "+tableElement,"PASS");
		else
			reportStep(msgText+" value is not displayed as Expected: "+tableElement,"FAIL");			
	}
	
	public void validateDownloadXLS(String fileName, String downloadbuttonObj) throws InterruptedException{
		Thread.sleep(1000);
		reportStep("Validating download XLS button","INFO");
		
		String PATH = "C:\\Users\\"+System.getProperty("user.name")+"\\Downloads";
	    String directoryName = PATH;
	    String archivePortfolipReportsxls= directoryName+"\\archiveFiles";
	     
	   
	    File directory = new File(archivePortfolipReportsxls);
	    	if (! directory.exists()){
	    		directory.mkdir();
	    			}
	    	else {
	    		System.out.println("archivefiles directory exists"); 
	    			}
	    File downloadDirectory = new File(PATH);
	    if(downloadDirectory.isDirectory()) {
	        File[] content = downloadDirectory.listFiles();
	        		for(int i = 0; i < content.length; i++) {
	        			File file = content[i];
	        				if(file.getName().contains(fileName)) {
		        
	        						// renaming the file and moving it to a new location
	        							LocalDateTime now = LocalDateTime.now(); 
	        							if(file.renameTo(new File(archivePortfolipReportsxls + "\\" +file.getName()+DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss").format(now)))){
	        							// if file copied successfully then delete the original file
	        							file.delete();
	        							System.out.println("File moved successfully:"+file.getName());
	        											}
	        							else{
	        							System.out.println("Failed to move the file:"+file.getName());
	        								}
	        						}
	        					}
							}
	    if(verifyElementExist("xpath",downloadbuttonObj)) {
	    	WebElement holdingsTableDownloadPortfolioXLSButton=locateElement("xpath", downloadbuttonObj);
			click(holdingsTableDownloadPortfolioXLSButton);
	    }
	    Thread.sleep(10000);
	    if(downloadDirectory.isDirectory()) {
	        File[] content = downloadDirectory.listFiles();
	        boolean fDownloaded = false;
	        		for(int i = 0; i < content.length; i++) {
	        				File file = content[i];
	        					if(file.getName().contains(fileName)) {
	        						reportStep("XLS file is downloaded as Expected:"+file.getName(),"PASS",true);
	        						fDownloaded = true;
	        						break;
	        					}
	        				}
	        if(!fDownloaded)
	        	reportStep("XLS file is not downloaded as Expected","FAIL",true);
	    			}
	   
	}
	
	//To Retrieve Test Data from Test Data Sheet
	public HashMap<String,String> getTestData(String TestCaseID)
	{
	
		HashMap<String,String> testData;
		testData=	ExcelDataProvider.getExcelRowAsHashMapByTestID("TestData", sCountryName,TestCaseID);	
		
		return testData;
	}
	
	public void waitForLoad(WebDriver driver) throws InterruptedException {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
		Thread.sleep(2000);
	}
	
	public void verifyPageHeading(String expectedHeading,String pageName) {
		if(verifyElementExist("xpath", "//h1"))
			verifyExactText(locateElement("xpath", "//h1"), expectedHeading, pageName + " Page Heading");
		else
			reportStep("Heading may not exsit in " + pageName +" Page", "FAIL");
	
	}
	
	public void waitForElementToBeClickable(WebElement ele,int waitTime) {	
		WebDriverWait wait=new WebDriverWait(driver,waitTime);
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	
	public void validateDownloadXLS(String fileNameToValidate, WebElement downloadLink, String expectedFileType) throws InterruptedException{
        Thread.sleep(1000);
        reportStep("Validating " + fileNameToValidate + "." + expectedFileType + " File download","INFO");
        
        String PATH = "C:\\Users\\"+System.getProperty("user.name")+"\\Downloads";
     String directoryName = PATH;
     String archivePortfolipReportsxls= directoryName+"\\archiveFiles";
      
    
     File directory = new File(archivePortfolipReportsxls);
        if (! directory.exists()){
               directory.mkdir();
                      }
        else {
               System.out.println("archivefiles directory exists"); 
                      }
     File downloadDirectory = new File(PATH);
     if(downloadDirectory.isDirectory()) {
         File[] content = downloadDirectory.listFiles();
                      for(int i = 0; i < content.length; i++) {
                            File file = content[i];
                                   if(file.getName().contains(fileNameToValidate)) {
                
                                                 // renaming the file and moving it to a new location
                                                        LocalDateTime now = LocalDateTime.now(); 
                                                        if(file.renameTo(new File(archivePortfolipReportsxls + "\\" +file.getName()+DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss").format(now)))){
                                                        // if file copied successfully then delete the original file
                                                        file.delete();
                                                        System.out.println("File archieved successfully:"+file.getName());
                                                                                   }
                                                        else{
                                                        System.out.println("Failed to archieve the file:"+file.getName());
                                                               }
                                                 }
                                          }
                                          }
 
        WebElement holdingsTableDownloadPortfolioXLSButton=downloadLink;
               click(holdingsTableDownloadPortfolioXLSButton);
               //explicitAngularJSWait();
 
     Thread.sleep(10000);
     if(downloadDirectory.isDirectory()) {
         File[] content = downloadDirectory.listFiles();
         boolean fDownloaded = false;
                      for(int i = 0; i < content.length; i++) {
                                   File file = content[i];
                                   		System.out.println(file.getName());
                                   		//String str=file.getName();                                   		
                                   		//String[] fileType=file.getName().toString().split(".");
                                   		System.out.println("ss: " + fileNameToValidate + "." + expectedFileType);
                                          if(file.getName().contains(fileNameToValidate + "." + expectedFileType)) {
	                                       		//String[] fileType=str.split(".");	                                       		
/*	                                       		for (String a : fileType) {
	                                             System.out.println(a.g);}  */                        		

	                                       		//if(fileType[1].equals(expectedFileType))
	                                       			reportStep(expectedFileType + " File: "+file.getName() + " dwonloaded successfully","PASS");
/*	                                       		else
	                                       			reportStep(expectedFileType + " File: "+file.getName() + " may not dwonloaded successfully","PASS");
*/                                                 fDownloaded = true;
                                                 break;
                                          }
                                   }
         if(!fDownloaded)
               reportStep(fileNameToValidate + "." + expectedFileType + " may not dwonloaded successfully","FAIL");
                      }
    
 }
	
	public void verifyBreadCrumb(String pageName,String breadcrumbToVerify,WebElement breadCrumbElement)
	{		
		//String strBC=locateElement("xpath", getLocalePropertyValue("objBreadCrumb")).getText();
		String strBC=breadCrumbElement.getText();		
		if(strBC.equals(breadcrumbToVerify.trim()))
			reportStep(pageName + " Page Breadcrumb displayed as Expected" , "PASS");
		else
			reportStep(pageName + " Page Breadcrumb may not displayed as Expected" , "FAIL");

	}
	
	public void verifyBannerImageAndHeading(String BillboardImgFile,String BillboardHeader)
	{
		WebDriverWait wait=new WebDriverWait(driver, 200);	
		wait.until(ExpectedConditions.elementToBeClickable(locateElement("xpath",localeProp.getProperty("objBannerImage"))));
		
		String imageFile=getAttribute(locateElement("xpath", getLocalePropertyValue("objBannerImage")), "style");
		if(imageFile=="" || imageFile==null ||imageFile.isEmpty())
			imageFile=getAttribute(locateElement("xpath", getLocalePropertyValue("objBannerImageAlt")), "style");
		System.out.println(imageFile);
		System.out.println(locateElement("xpath",getLocalePropertyValue("objICStrategeisBannerHeading")).getText().trim());
		if(imageFile.contains(BillboardImgFile) && locateElement("xpath",getLocalePropertyValue("objICStrategeisBannerHeading")).getText().trim().equals(BillboardHeader))
			reportStep(BillboardHeader + " page Banner & Banner Heading displayed as Expected", "PASS",true);
		else
			reportStep("Either " + BillboardHeader + " Banner or Banner Heading may not displayed as Expected", "FAIL",true);
		
	}
	


}


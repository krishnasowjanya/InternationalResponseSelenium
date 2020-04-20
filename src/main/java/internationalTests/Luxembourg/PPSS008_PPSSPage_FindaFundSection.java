package internationalTests.Luxembourg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PPSS008_PPSSPage_FindaFundSection extends ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating PPSS Page Find a Fund Search";
		testDescription = "Validating PPSS Page Find a Fund Search functionality by providing different search items";
	}
	
	@Test(groups="PPSS")
	public void ppssPageFindAFundValidation() throws InterruptedException, FileNotFoundException, IOException {		
		
try {
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PPSS008");
			
			new PriceAndPerformancePage(driver, test)
			.checkFindAFundComponentExist()
			.checkFindAFundInnerText()
			.enterStringToSearchAndValidateResult(data)
			.checkResetFeatureByClickingXButton(data)
			.enterStringToSearchAndValidateFlyoutMenuAccess(data);
		
	}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}

}

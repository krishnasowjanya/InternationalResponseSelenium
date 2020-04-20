package internationalTests.Offshore;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PPSS007_PPSSPage_Filters extends internationalTests.Luxembourg.PPSS007_PPSSPage_Filters{


	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Validating PPSS Page Filters";
		testDescription = "Validating PPSS Page Filters";
	}
	
	@Test(groups="PPSS")
	public void ppssPageFilterValidation() throws InterruptedException {		
		
		try {
			HashMap<String,String> data=new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.getTestData(sCountryName,"PPSS007");
			
			new PriceAndPerformancePage(driver, test)
			.selectShareClass(data.get("ShareClassFilter"))
			.selectAssetClass(data.get("AssetClassFilter"))
			.selectInvestmentVehicle(data.get("InvestmentVehicleFilter"))
			.selectInvestmentCategory(data.get("InvestmentCategoryFilter"))
			.selectRegion(data.get("RegionFilter"))
			.selectCurrency(data.get("CurrencyFilter"))
			.verifyFilterResult(data);
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
		
	}

}





package internationalTests.Canada;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PFOL016_SectorExposure  extends ProjectMethods {
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Portfolio";
		excelSheetName="Luxembourg";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Portfolio - Validating Sector Exposure widget";
		testDescription = "Validating Sector Exposure for applicable funds";
	}
	
	@Test(groups="FundPortfolio")
	public void validateSectorExposureWidget() throws InterruptedException {		
		
		try {
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.goToK2AlternativeFundPage()		
			.clickPortfolioTab() 
			.verifySectorExposureDateFormat()		
			.verifyFootNoteCaveat();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	
	}


}

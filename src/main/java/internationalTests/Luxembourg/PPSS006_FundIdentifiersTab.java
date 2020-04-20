package internationalTests.Luxembourg;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class PPSS006_FundIdentifiersTab extends ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "";
		excelSheetName="";
		authors = "Mohan";
		category = "Smoke";
		testCaseName = "Fund Identifier Tab";
		testDescription = "Validating Fund Identifiers, data Type and Format";
	}
	
	@Test(groups="COREWEB-364")
	public void validateFndIdentifiers() throws InterruptedException {
		
		try {
			System.out.println("Validating Fund Identifer Tab Funds and available Identifiers");
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickProducts()
			.clickPriceAndPerformance()
			.waitUntilAllFundsLoaded()
			.clickFundIdentifierTab()		
			.verifyFundIdentifiers()
			.verifyFundIdentifiersDataFormats();
		
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}	

	}

}

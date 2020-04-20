package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR010_PFIC_AnnualInfoStatement extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - PFIC Annual Information Statement";
		testDescription = "Validating Resources PFIC Annual Information Statement";
	}
	
	@Test
	public void validateNonResidentTaxForms() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickResources()
			.clickPFICAnnualInfoStatments()
			.verifyPageBreadCrumb()
			.verifyPageLayoutCanada()
			.verifyTabs()
			.verifyFundDocumentsPageLayoutCanada()
			.verifyDropdownValuesCanada()
			.verifyEditColumnsTabCanada()
			.verifySaveViewResetButton()
			.verifyEditColumnsFunctionality();

			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

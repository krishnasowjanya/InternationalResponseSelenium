package internationalTests.Canada.Marketing;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import internationalPages.Products.PriceAndPerformancePage;
import internationalPages.Products.UKHomePage;
import wdMethods.ProjectMethods;

public class RESR012_PlanningMegamenu extends  ProjectMethods{
	
	@BeforeClass
	public void setValues() {
		browserName = "chrome";
		excelName = "Funds";
		excelSheetName="";
		authors = "Prateek";
		category = "Smoke";
		testCaseName = "Marketing - Resources - Planning Megamenu";
		testDescription = "Validating Resources Planning Megamenu";
	}
	
	@Test
	public void validatePlanningMegamenu() throws InterruptedException {		
		try {
			System.out.println("Test Case: " + testDescription);
		
			new UKHomePage(driver, test)
			.selectActor(sActor)
			.clickPlanning()
			.validatePlanningMegamenu();
			

			
		}catch(Exception e){
			printUnexpectedLogDuringExecution(e);
		}
		
	}
}

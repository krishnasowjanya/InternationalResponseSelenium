package utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class WebTableFunctions {

	public static int getRowWithCellText(WebElement tableObj,String text) {
		int index = 0;
		WebElement baseTable = tableObj;
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		while(index < tableRows.size()){
		
		if (tableRows.get(index).getText().contains(text)) 
				return index;
			index++;	
		}
		return -1;
	}
	
	public static String getCellData(WebElement tableObj,int row,int col) {
	try {	
		WebElement baseTable = tableObj;
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		List<WebElement> td_collection=tableRows.get(row).findElements(By.xpath("th"));
			td_collection.addAll( tableRows.get(row).findElements(By.xpath("td")));
	         return td_collection.get(col).getText();
		}
	catch(IndexOutOfBoundsException e) {
		//e.printStackTrace();
		return null;
	}
	}
	
	
	public static WebElement getCellElement(WebElement tableObj,int row,int col) {
		try {	
			WebElement baseTable = tableObj;
			List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
			List<WebElement> td_collection=tableRows.get(row).findElements(By.xpath("th"));
				td_collection.addAll( tableRows.get(row).findElements(By.xpath("td")));
		         return td_collection.get(col);
		         	}
		catch(IndexOutOfBoundsException e) {
			//e.printStackTrace();
			return null;
		}
		}
	
	public static List<WebElement> getAllTagsInsideAnElement(WebElement tableObj,String tagName) {
		try {	
			WebElement baseTable = tableObj;
			List<WebElement> tableRows = baseTable.findElements(By.tagName(tagName));
		         return tableRows;
		         	}
		catch(Exception e) {
			return null;
		}
	}
}
	

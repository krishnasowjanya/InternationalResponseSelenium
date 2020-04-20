package utils;



import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

public class TextFormatFunctions {

	  public static void main(String a[]){
	        
	        
	      
	    }
	  
	
			
			public static String getElementColor(WebElement ele)
			{
				String color = ele.getCssValue("color");		
				String hex = Color.fromString(color).asHex();		
				
				return hex;
			} 
 
		

}

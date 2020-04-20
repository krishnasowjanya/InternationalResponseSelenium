package utils;


public class CurrencyHelper {

	 public static String RetrieveCurrencySymbols(String CurrencyName) {
	      
	    	  
	    	switch (CurrencyName) {
			case "USD":
				return "$";
				
			case "EUR":
				return "€";
				
			default: 
				System.out.println("The Country is not present in helper right now, Add the case for it first.");
				return null;
				
			
			}

	 }
}

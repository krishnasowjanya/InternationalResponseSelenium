package utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;

import wdMethods.ProjectMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.*;

public class commonMethods extends ProjectMethods{
	
	public commonMethods() throws FileNotFoundException, IOException
	{
		//Loading Locale File
		localeProp.load(new FileInputStream(new File(sLocaleFile)));
	}
	
	public static String getElementColor(WebElement ele)
	{
		String color = ele.getCssValue("color");		
		String hex = Color.fromString(color).asHex();		
		
		return hex;
	}	

	/**
	 * Verify the given string is in given Type.
	 * @param str	-	String to validate 
	 * @param format	- Type to validate
	 * @return true/false
	 */
	public static boolean verifyStringType(String str,String Type)
	{
		boolean isFormat=false;
		if(Type.equals("ALPHA-NUMERIC"))
			isFormat= str.matches("[a-zA-Z0-9]+");
		
		else if(Type.equals("NUMERIC"))
			isFormat= str.matches("[0-9]+");
		
		else if(Type.equals("ALPHABETIC"))
			isFormat= str.matches("[a-zA-Z]+");
		
		else
			//String splChrs = "-/@#$%^&_+=()" ;
			//Below will check the given string has characters in given character set.
			isFormat = str.matches("[" + Type + "]+");		
	
		return isFormat;
	}

	public static void main(String[] str)
	{

		System.out.println(verifyStringType("1258AAAasdfas","NUMERIC"));

	}
	 
	
/*	String a = "12.0"; //Valid
	String b = "12.5"; //Invalid

	Pattern p = Pattern.compile( "([0-9]*)\\.[0]" );

	Matcher m = p.matcher(a);
	m.matches(); //TRUE

	Matcher m = p.matcher(b);
	m.matches(); //FALSE
*/

//////
public boolean isInteger( String input )
{
   try
   {
      Integer.parseInt( input );      
      return true;
   }
   catch( Exception e)
   {
      return false;
   }
}
public boolean verifyColorCode( WebElement eleToCheck,String colorName )
{
	boolean isExpColor=false;
	String clrCode=getElementColor(eleToCheck);
	System.out.println(clrCode);
	
	switch (colorName) {
	case "RED":
		if(clrCode.equals("#cb0000"))
			isExpColor=true;
	case "GREEN":
		if(clrCode.equals("#4e9d2d"))
			isExpColor=true;
	case "GREY":
		if(clrCode.equals("#808080"))
			isExpColor=true;		
	case "BLACK":
		if(clrCode.equals("#000"))
			isExpColor=true;
	} 
  
	return isExpColor;
/*   if(isExpColor)
       reportStep("Given String Color is " + colorName + " as Expected","PASS");         
     else
  	 reportStep("Given String Color is not as expected color: " + colorName,"FAIL");   */
}

public void verifyFormat(String valueToVerify,String expFormat)
{
	
    if (valueToVerify.matches(expFormat)) 
        reportStep(valueToVerify + " value fromat matches as Expceted","PASS");
    else
	 	reportStep(valueToVerify + " value fromat not matches as Expceted","FAIL");
}
public boolean verifyNumericValueFormat(String valueToVerify,int IntegerCount,int DecimalCount,String separator,String Currency,String expFormat)
{
/*	String str1;

	int IntCount=expFormat.split(separator)[0].length();
	int DecCount=expFormat.split(separator)[0];
	if(Currency.isEmpty())
		str1="\\d{1," + IntegerCount + "},\\d{" + DecimalCount + "}";
	else if(Currency.length()>1)
		str1="[" + Currency + "]\\d{1," + IntegerCount + "},\\d{" + DecimalCount + "}";
	else
		str1=Currency + "\\d{1," + IntegerCount + "},\\d{" + DecimalCount + "}";
	
    if (valueToVerify.matches(str1)) 
        reportStep("Given String " + + " Matches the format: " + ,"PASS");
 else
        reportStep("Distribution per share Value is not displayed as Expected:"+tableElement,"FAIL"); */
    
	
	return false;
}

public String getAsAtDate(WebElement ele)
{
	
	try
	{
	String[] temp=ele.getText().split(getLocalePropertyValue("lblFOAsAtDateTxt"));
	
	
	if(temp[1].contains("\\("))
		return temp[1].split("\\(")[0].trim();
	else
		return temp[1].trim();
	}catch(Exception e){
		e.printStackTrace();
		return "";
	}
	
}

public String getAsAtDateAlt(WebElement ele)
{
	try
	{
	String[] temp=ele.getText().split(getLocalePropertyValue("lblFOAsAtDateTxtAlt"));
	if(temp[1].contains("\\("))
		return temp[1].split("\\(")[0].trim();
	else
		return temp[1].trim();
	}catch(Exception e){
		e.printStackTrace();
		return "";
	}
	
}


/*
I use the following regex to check whether a string is numeric or not. 

((-|\\+)?[0-9]+(\\.[0-9]+)?)+ 

Valid: 
4324 
+4123 
12321.43 
-123.432432 
100.00 

Invalid: 
3243. 
32 3232 
1231.32131.333 
- 
+-1232134.12 
Any text 

Example: 
?
1
2
3
4
5
if ("-2324.00".matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
    System.out.println("Is a number");
} else {
    System.out.println("Is not a number");*/

/*So finally it should be: (-|\\+)?[0-9]+(\\.[0-9]+)? 
and in case of limitimg decimal places (-|\\+)?[0-9]+(\\.[0-9]{1,2}+)?*/
		
}
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;

import wdMethods.ProjectMethods;
import wdMethods.WdMethods;


public class dateFunctions {
	
    
	    public static void main(String a[]) throws ParseException{
	        
/*	        //System.out.println("Valid? : " + VerifyDateFormat("21/05/2008","dd/MM/yyyy"));
	        System.out.println("Valid? : " + VerifyDateFormat("2016/12/20","yyyy/MM/dd"));
	        
	        String str="http://rcovlnx0188:8202/en-gb/adviser/products/overview/13086/G/franklin-us-opportunities-fund";
	        //String FundNumber=str.split("overview\\/")[1].substring(0, 5);
	        String FundNumber1=str.split("overview\\/")[1].split("\\/")[0];
	        System.out.println(FundNumber1);*/
	    	
	    	//dateFunctions.addMonth();
	    	
/*	    	String input = "Dec,19,2017";
	    	SimpleDateFormat format = new SimpleDateFormat("MMM,dd,yyyy");
	    	Date date = format.parse(input);
	    	//Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
	    	Calendar calendar = Calendar.getInstance();
	    	calendar.setTime(date);
	    	calendar.add(Calendar.MONTH, -3);
	    	System.out.println(calendar.get(Calendar.YEAR));
	    	System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
	    	System.out.println(new SimpleDateFormat("MMM").format(calendar.getTime()));*/
	    	
/*			Date date= new Date();
			Calendar cal = Calendar.getInstance();
			//String month=new SimpleDateFormat("MMM").format(cal.getTime());
			String month="Nov";
			System.out.println(month);
			String expMonth="InvalidMonth";
			if(month.equals("Jan") || month.equals("Feb") || month.equals("Mar"))
				expMonth="Dec";
			else if(month.equals("Apr") || month.equals("May") || month.equals("Jun"))
				expMonth="Mar";
			else if(month.equals("Jul") || month.equals("Aug") || month.equals("Sep"))
				expMonth="Jun";
			else if(month.equals("Oct") || month.equals("Nov") || month.equals("Dec"))
				expMonth="Sep";		
				
			System.out.println(expMonth)*/;
			
			
	/*		int monthNumber = 10;
			System.out.println(Month.of(monthNumber).name().substring(0, 3));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date date1=format.parse("23/07/2017");
			System.out.println(new SimpleDateFormat("MMM").format(date1));
			System.out.println(new SimpleDateFormat("YYYY").format(date1));
			//System.out.println(date1.getMonth()+1);
			*/
	    	//Calendar calendar = Calendar.getInstance();
	    	//System.out.println(calendar.get(Calendar.YEAR));
			
			int year = Calendar.getInstance().get(Calendar.YEAR);
			System.out.println(year);
			
			

	    	
	    }	    
	    

	    public static boolean VerifyDateFormat(String text,String Format) {
	        //if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
	        //if (text == null || !text.matches("[21]\\d\\d\\d/[01]\\d/[0-3]\\d")) //=> yyyy/MM/dd
	    	String formt,regExpn;
	    	SimpleDateFormat df;
	    	boolean isExpFormat=false;
	    	
	    	switch (Format) {
			case "dd/MM/yyyy":
				df = new SimpleDateFormat(Format);
				regExpn="[0-3]\\d/[01]\\d/[21]\\d\\d\\d";
				break;
			case "MM/dd/yyyy":
				df = new SimpleDateFormat(Format);
				regExpn="[01]\\d/[0-3]\\d/[21]\\d\\d\\d";
				break;
			case "yyyy/MM/dd":
				df = new SimpleDateFormat(Format);
				regExpn="[21]\\d\\d\\d/[01]\\d/[0-3]\\d";
				break;
			case "dd.MM.yyyy":
				df = new SimpleDateFormat(Format);
				regExpn="[0-3]\\d.[01]\\d.[21]\\d\\d\\d";
				break;
			default:
				df = new SimpleDateFormat(Format);
				regExpn="[21]\\d\\d\\d/[01]\\d/[0-3]\\d";
				break;
			}
	    	
	    	
	        if (text == null || !text.matches(regExpn)) //=> dd/MM/yyyy
	        	isExpFormat= false;
	        	df.setLenient(false);
	        try {
	            df.parse(text);
	            isExpFormat= true;
	        } catch (ParseException ex) {
	        	isExpFormat= false;
	        }
	        
	        return isExpFormat;
	        	
	    }
	    
	    public static String addMonth() throws ParseException
	    {
	    	
	    	Calendar cal = Calendar.getInstance();
	    	cal.add(Calendar.MONTH, -3);
	    	System.out.println(cal.get(Calendar.MONTH));
	    	Date mydate=cal.getTime();
	    	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    	System.out.println(mydate);
	    	System.out.println(format1.format(mydate));
	    	
	    	
	    	//System.out.println(cal.getTime());	    	
	    	// Output "Wed Sep 26 14:23:28 EST 2012"

	    	//String formatted = format1.format(cal.getTime());
	    	//System.out.println(formatted);
	    	// Output "2012-09-26"

	    	//System.out.println(format1.parse(formatted));	    
	    	// Output "Wed Sep 26 00:00:00 EST 2012"
	    	
	   /* 	//create Calendar instance
	        Calendar now = Calendar.getInstance();
	       
	        System.out.println("Current date : " + (now.get(Calendar.MONTH) + 1)
	                            + "-"
	                            + now.get(Calendar.DATE)
	                            + "-"
	                            + now.get(Calendar.YEAR));
	       
	        //add months to current date using Calendar.add method
	        now.add(Calendar.MONTH,10);
	     
	        System.out.println("date after 10 months : " + (now.get(Calendar.MONTH) + 1)
	                            + "-"
	                            + now.get(Calendar.DATE)
	                            + "-"
	                            + now.get(Calendar.YEAR));
	     
	       
	        //substract months from current date using Calendar.add method
	        now = Calendar.getInstance();
	        now.add(Calendar.MONTH, -5);
	     
	        System.out.println("date before 5 months : " + (now.get(Calendar.MONTH) + 1)
	                            + "-"
	                            + now.get(Calendar.DATE)
	                            + "-"
	                            + now.get(Calendar.YEAR));*/
	        
	        return null;
	    }
	    
	    public static String getFormatedDate(Date inputDate,String dateFormat) throws ParseException
	    {	    	
 	
	    	//Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
	    	Calendar calendar = Calendar.getInstance();
	    	System.out.println(calendar.getTime());
	    	calendar.setTime(inputDate);
	    	//calendar.add(Calendar.MONTH, -3);
	    	//System.out.println(calendar.getTime());
	    	if(dateFormat.equals("yy"))
	    		return new SimpleDateFormat("yy").format(calendar.getTime());
	    	else if(dateFormat.equals("MMM"))
	    		return new SimpleDateFormat("MMM").format(calendar.getTime());
	    	
	    	System.out.println(new SimpleDateFormat("yy").format(calendar.getTime()));
	    	System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
	    	System.out.println(new SimpleDateFormat("MMM").format(calendar.getTime()));
	    	return null;
	    }


	    public static int getYearFromDate(Date date) {
	        int result = -1;
	        if (date != null) {
	            Calendar cal = Calendar.getInstance();
	            cal.setTime(date);
	            result = cal.get(Calendar.YEAR);
	        }
	        return result;
	    }

	    
	
}



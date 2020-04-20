package exceptions;

public class ExcelColumnEmptyException extends Exception {
	String s1;
	public ExcelColumnEmptyException(String s2) {
	      s1 = s2;
	   } 
	   @Override
	   public String toString() { 
	      return (s1);
	   }
}

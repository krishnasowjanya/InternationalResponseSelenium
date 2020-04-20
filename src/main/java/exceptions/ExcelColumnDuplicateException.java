package exceptions;

public class ExcelColumnDuplicateException extends Exception {
	String s1;
	public ExcelColumnDuplicateException(String s2) {
	      s1 = s2;
	   } 
	   @Override
	   public String toString() { 
	      return (s1);
	   }
}

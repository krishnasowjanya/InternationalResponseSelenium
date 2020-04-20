package exceptions;

public class TestCaseIDNotFoundException extends Exception {
	String s1;
	public TestCaseIDNotFoundException(String s2) {
	      s1 = s2;
	   } 
	   @Override
	   public String toString() { 
	      return (s1);
	   }
}

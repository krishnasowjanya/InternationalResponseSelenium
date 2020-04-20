package utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class grid {
	
	public static void main(String[] str) throws MalformedURLException, InterruptedException
	{
	DesiredCapabilities capability = DesiredCapabilities.chrome();	
	capability.setBrowserName("chrome");
	//capability.setPlatform(Platform.WIN10);
	//capability.setVersion()
	//capability.setCapability(,);
	WebDriver driver = new RemoteWebDriver(new URL("http://10.144.44.194:4444/wd/hub"), capability);
	driver.get("https://www.franklintempleton.com/");
	System.out.println("Frome TEst-2");
	Thread.sleep(2000);
	}
}

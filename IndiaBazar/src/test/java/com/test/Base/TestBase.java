package com.test.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
	public WebDriver driver;
	public WebDriverWait explicitlyWait;
	Properties prop;
	final int PAGE_LOAD_TIME=30;
	final int IMPLICIT_WAIT=10;
	final int EXPLICIT_WAIT=10;
	
	public TestBase() {
		prop=new Properties();
		
		try {
			FileInputStream fin=new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
			prop.load(fin);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initial(String browsername) {
		driver=getBrowser(browsername);
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIME, TimeUnit.SECONDS);
//		explicitlyWait=new WebDriverWait(driver, 2);
		driver.get(prop.getProperty("URL"));
		
	}
	
	public void quitApp() {
		driver.close();
		driver.quit();
	}
	
	
	
	private WebDriver getBrowser(String browsernm) {
		WebDriver crntDriver=null;
		String prjPath=System.getProperty("user.dir");
		if(browsernm.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", prjPath+"\\src\\test\\java\\Driver\\chromedriver.exe");
			crntDriver=new ChromeDriver();
		}else if(browsernm.equalsIgnoreCase("Internet Explorer")) {
			
			crntDriver=new InternetExplorerDriver();
		}else if(browsernm.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", prjPath+"\\src\\test\\java\\Driver\\msedgedriver.exe");
			crntDriver=new EdgeDriver();
		}
		
		return crntDriver;
	}
}

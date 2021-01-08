package com.test.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


public class TestBase {
	public static WebDriver driver;
	
	public Properties prop;
	final int PAGE_LOAD_TIME=30;
	final int IMPLICIT_WAIT=10;
	
	
	public Logger logger=LogManager.getLogger(TestBase.class);
	
	
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
	
	@BeforeTest	
	public void setUp() {
		try {
			initial("Chrome");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void tearDown() {
//		quitApp();
	}
	
	
	public void initial(String browsername) throws IOException {
		driver=getBrowser(browsername);
		logger.info(browsername+" has got launched.");
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIME, TimeUnit.SECONDS);
//		explicitlyWait=new WebDriverWait(driver, 2);
		String urlString=prop.getProperty("URL");
		driver.get(urlString);
//		takeScreenShot("HomepageScreenshot");
		logger.info("naviagated to "+ urlString);
	}
	
	public void quitApp() {
		driver.close();
		driver.quit();
		logger.info("Browser got closed and quit");
	}
	
//	public void takeScreenShot(String outFileName) throws IOException {
//		TakesScreenshot scrShot=(TakesScreenshot)driver;
//		File scrFile=scrShot.getScreenshotAs(OutputType.FILE);
//		File dFile=new File(System.getProperty("user.dir")+"\\src\\test\\java\\com\\test\\ScreenShot\\"+outFileName+".png");
//		FileUtils.copyFile(scrFile,dFile );	
//	}
	
	private WebDriver getBrowser(String browsernm) {
		WebDriver crntDriver=null;
		String prjPath=System.getProperty("user.dir");
		if(browsernm.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", prjPath+"\\Driver\\chromedriver.exe");
			crntDriver=new ChromeDriver();
		}else if(browsernm.equalsIgnoreCase("Internet Explorer")) {
			
			crntDriver=new InternetExplorerDriver();
		}else if(browsernm.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", prjPath+"\\Driver\\msedgedriver.exe");
			crntDriver=new EdgeDriver();
		}
		
		return crntDriver;
	}
	
}

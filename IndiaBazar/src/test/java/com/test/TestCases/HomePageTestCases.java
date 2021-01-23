package com.test.TestCases;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.test.Base.TestBase;
import com.test.Pages.Homepage;
import com.test.Pages.ProductsPage;

public class HomePageTestCases extends TestBase {
	Homepage homepage;
	ProductsPage pruductsPage;

	public HomePageTestCases() {
		super();
	}

	@Parameters("browser")
	@BeforeClass
	void beforeStart(@Optional("Chrome") String browser) throws IOException {
//		String browser = "Chrome";
//		initial(browser);
		homepage = PageFactory.initElements(driver, Homepage.class);
		pruductsPage = PageFactory.initElements(driver, ProductsPage.class);
		setLogger("-----Test Class Name ---"+this.getClass().getName()+"------------");
	}

	@AfterClass
	void afterFinish() {
//		quitApp();
	}
	
	@BeforeMethod
	public void nameBefore(Method method)
	{
	    setLogger("---Test name: " + method.getName()+"---------------");       
	}

	@AfterMethod
	public void resultTest(ITestResult result) {
		String reString="";
		 try
		 {
		    if(result.getStatus() == ITestResult.SUCCESS)
		    {
		        reString=" PASSED ";
		    }

		    else if(result.getStatus() == ITestResult.FAILURE)
		    {
		    	reString=" FAILED ";
		    }

		     else if(result.getStatus() == ITestResult.SKIP ){
		    	 reString=" SKIPPED ";
		    }
		}
		   catch(Exception e)
		   {
		     e.printStackTrace();
		   }
		 setLogger("test got "+reString);
	}
	
	
	@Test(enabled=true)//retryAnalyzer = com.test.Listeners.RetryAnalyzer.class)
	void TC_1() throws Exception {
//		logger.info("in the TC_1");
		homepage.clickOnShopmenu();
		logger.info("clicked on Shop menu");
//		Reporter.log("clicked on Shop menu");
//		takeScreenShot("ProductSS");
		pruductsPage.clickOnHomemenu();
		logger.info("clicked on Home menu");
//		Reporter.log("clicked on Home menu");
		
		int slidersCnt = homepage.getSliderCnt();
		Assert.assertEquals(slidersCnt, 3);
//		System.out.println("it is TC_1 in homepage");
	}

	@Test (enabled = true)
	void TC_2() {
		logger.info("in the TC_2");
		homepage.clickOnShopmenu();
		logger.info("clicked on Shop menu");
		pruductsPage.clickOnHomemenu();
		logger.info("clicked on Home menu");
		int newArrivalsCnt = homepage.getArrivalsCnt();
		Assert.assertEquals(newArrivalsCnt, 3);
//		System.out.println("it is TC_2 in homepage");
	}

}

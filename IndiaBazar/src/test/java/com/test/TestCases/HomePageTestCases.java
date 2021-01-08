package com.test.TestCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
	}

	@AfterClass
	void afterFinish() {
//		quitApp();
	}

	@Test()//retryAnalyzer = com.test.Listeners.RetryAnalyzer.class)
	void TC_1() throws Exception {
//		logger.info("in the TC_1");
//		homepage.clickOnShopmenu();
//		logger.info("clicked on Shop menu");
//		Reporter.log("clicked on Shop menu");
////		takeScreenShot("ProductSS");
//		pruductsPage.clickOnHomemenu();
//		logger.info("clicked on Home menu");
//		Reporter.log("clicked on Home menu");
//		
//		int slidersCnt = homepage.getSliderCnt();
//		assertEquals(slidersCnt, 2);
		System.out.println("it is TC_1 in homepage");
	}

	@Test (enabled = true)
	void TC_2() {
//		logger.info("in the TC_2");
//		homepage.clickOnShopmenu();
//		logger.info("clicked on Shop menu");
//		pruductsPage.clickOnHomemenu();
//		logger.info("clicked on Home menu");
//		int newArrivalsCnt = homepage.getArrivalsCnt();
//		assertEquals(newArrivalsCnt, 3);
		System.out.println("it is TC_2 in homepage");
	}

}

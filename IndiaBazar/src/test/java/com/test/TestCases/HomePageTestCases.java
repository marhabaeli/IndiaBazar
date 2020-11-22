package com.test.TestCases;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
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

//	@Parameters("browser")
	@BeforeClass
	void beforeStart() {// @Optional("Chrome") String browser) {
		String browser = "Chrome";
		initial(browser);
		homepage = PageFactory.initElements(driver, Homepage.class);
		pruductsPage = PageFactory.initElements(driver, ProductsPage.class);
	}

	@AfterClass
	void afterFinish() {
		quitApp();
	}

	@Test
	void TC_1() {
		homepage.clickOnShopmenu();
		pruductsPage.clickOnHomemenu();
		int slidersCnt = homepage.getSliderCnt();
		assertEquals(slidersCnt, 3);
	}

	@Test
	void TC_2() {
		homepage.clickOnShopmenu();
		pruductsPage.clickOnHomemenu();
		int newArrivalsCnt = homepage.getArrivalsCnt();
		assertEquals(newArrivalsCnt, 3);
	}

}

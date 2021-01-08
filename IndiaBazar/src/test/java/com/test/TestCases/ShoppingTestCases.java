package com.test.TestCases;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.test.Base.TestBase;
import com.test.Pages.CheckOutPage;
import com.test.Pages.Homepage;
import com.test.Pages.Shoppage;
import com.test.Utility.UtilClass;

public class ShoppingTestCases extends TestBase {

	Homepage homepage;
	Shoppage shoppage;
	CheckOutPage checkoutpage;
	
	@BeforeClass
	void setInit() {
		homepage=PageFactory.initElements(driver, Homepage.class);
		shoppage=PageFactory.initElements(driver, Shoppage.class);
		checkoutpage=PageFactory.initElements(driver, CheckOutPage.class);
	}
	
	//Filter By price
	@Test(enabled=false)
	void TC_1() {
		homepage.clickOnShopmenu();
		//the slider's min value is 150, max value is 500
		int val1=(int)(Math.random()*100+50);  //to put min value,get it from 50, 150
		int val2=(int)(Math.random()*60+10);	//to put max value, get it from 10, 70
		
		if(val1>val2) {
			int t=val2; val2=val1; val1=t;
		}
		if(val2-val1<40) {
			val1-=10;
			val2+=10;
		}
		System.out.println(val1+","+val2);
		shoppage.clickOnslider(val1, val2*(-1));
		logger.info("Price slider got clicked");
		shoppage.clickOnFilter();
		logger.info("Filter button got clicked");
		
		ArrayList<String> priceRange=shoppage.getSpanValue();
		int min=Integer.parseInt(priceRange.get(0));
		int max=Integer.parseInt(priceRange.get(1));
		
		Assert.assertTrue(shoppage.isFiltered(min, max));
		
	}
	
	//Product Categories functionality
	@Test(enabled=false)
	void TC_2() {
		homepage.clickOnShopmenu();
		int clickableElemIndx=1;
		shoppage.clickOnCtgLnk(clickableElemIndx);
		String lnkCaption=shoppage.prdctCtgLst.get(clickableElemIndx).findElement(By.xpath("./a")).getText();
		String lnkCnt=shoppage.prdctCtgLst.get(clickableElemIndx).findElement(By.xpath("./span")).getText();
		lnkCnt=lnkCnt.replace("(", "");
		lnkCnt=lnkCnt.replace(")", "");
		
		Assert.assertTrue(shoppage.isCategorized(lnkCaption) && shoppage.ctgPrdctLst.size()==Integer.parseInt(lnkCnt));
	}
	
	//Default sorting---Sort by Popularity item
	@Test(enabled=false)
	void TC_3() {
		homepage.clickOnShopmenu();
		shoppage.selSortingbyName("Sort by popularity");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Default sorting----Sort by Average ratings
	@Test(enabled=false)
	void TC_4() {
		homepage.clickOnShopmenu();
		shoppage.selSortingbyName("Sort by average rating");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Default sorting----Sort by Newness ratings
	@Test(enabled=false)
	void TC_5() {
		homepage.clickOnShopmenu();
		shoppage.selSortingbyName("Sort by newness");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Default sorting----Sort by Low to High Item
	@Test(enabled=false)
	void TC_6() {
		homepage.clickOnShopmenu();
		shoppage.selSortingbyName("Sort by price: low to high");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Default sorting----Sort by High to Low Item
	@Test(enabled=false)
	void TC_7() {
		homepage.clickOnShopmenu();
		shoppage.selSortingbyName("Sort by price: high to low");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Read More option indicates the Out Of Stock
	@Test(enabled=false)
	void TC_8() {
		homepage.clickOnShopmenu();
		for(WebElement elem: shoppage.sortedElemLst) {
			WebElement readMoreElem=elem.findElement(By.xpath("./a[2]"));
			if(readMoreElem.getText().equals("Read more")) {
				readMoreElem.click();
				break;
			}
		}		
		Assert.assertTrue(shoppage.isOOSIndc());
	}
	
	//Sale functionality
	@Test(enabled=false)
	void TC_9() {
		homepage.clickOnShopmenu();
		for(WebElement elem: shoppage.sortedElemLst) {
			if(elem.findElement(By.xpath("./a/*[1]")).getText().equalsIgnoreCase("SALE!")) {
				elem.findElement(By.xpath("./a")).click();
				break;
			}
		}
		String tagname=driver.findElement(By.xpath("//p[@class=\"price\"]/*[1]")).getTagName();
		Assert.assertEquals(tagname, "del");
		
	}

	//Add to basket-view basket functionality
	@Test(enabled=false)
	void TC_10() {
		homepage.clickOnShopmenu();
		for(WebElement elem: shoppage.sortedElemLst) {
			WebElement add2CardElem=elem.findElement(By.xpath("./a[2]"));
			if(add2CardElem.getText().equalsIgnoreCase("Add to basket")) {
				add2CardElem.click();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				elem.findElement(By.xpath("./a[3]")).click();
				break;
			}
		}
//		 User can view that Book in the Menu item with price .
		
		Assert.assertTrue(shoppage.isItemInCart());
		
		shoppage.clickOnItemInCart();
		
//		The total always < subtotal because taxes are added in the subtotal
		Assert.assertTrue(shoppage.isTotalGreatThanSub());
		
		shoppage.clickOnPrChkOutBtn();
		
//		User can view Billing Details,Order Details,Additional details and Payment gateway details.
		UtilClass.wait.until(ExpectedConditions.visibilityOf(checkoutpage.fName));
		Assert.assertTrue(shoppage.isDetailsThere());
		
		String[] billInfo= {"tester01","tester","tester01@gmail.com","2121212121","United States", "2134 Fox Drive", "GrandLake",
				"Kansas", "66087"};
		checkoutpage.fillAndSendBill(billInfo);
		
		checkoutpage.clickOnPlaceOrder();
		
		//Assert if complete payment
		UtilClass.wait.until(ExpectedConditions.visibilityOf(checkoutpage.completePayment));
		Assert.assertTrue(checkoutpage.isPaymentDone());
	}
		

	//Add To Basket button -View Basket through Item link
	@Test(enabled=false)
	void TC_11() {
		homepage.clickOnShopmenu();
		for(WebElement elem: shoppage.sortedElemLst) {
			WebElement add2CartElem=elem.findElement(By.xpath("./a[2]"));
			if(add2CartElem.getText().equalsIgnoreCase("Add to basket")) {
				add2CartElem.click();
				break;
			}
		}
//		 User can view that Book in the Menu item with price .
		UtilClass.wait.until(ExpectedConditions.attributeContains(By.xpath("//ul[@id=\"main-nav\"]/li[6]/a"), "title", "View your shopping cart"));
		Assert.assertTrue(shoppage.isItemInCart());
		
		shoppage.clickOnItemInCart();
		
//		The total always < subtotal because taxes are added in the subtotal
		Assert.assertTrue(shoppage.isTotalGreatThanSub());
		
		shoppage.clickOnPrChkOutBtn();
		
//		User can view Billing Details,Order Details,Additional details and Payment gateway details.
		UtilClass.wait.until(ExpectedConditions.visibilityOf(checkoutpage.fName));
		Assert.assertTrue(shoppage.isDetailsThere());
		
		String[] billInfo= {"tester01","tester","tester01@gmail.com","2121212121","United States", "2134 Fox Drive", "GrandLake",
				"Kansas", "66087"};
		checkoutpage.fillAndSendBill(billInfo);
		
		checkoutpage.clickOnPlaceOrder();
		
		//Assert if complete payment
		UtilClass.wait.until(ExpectedConditions.visibilityOf(checkoutpage.completePayment));
		Assert.assertTrue(checkoutpage.isPaymentDone());
	}
}

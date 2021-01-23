package com.test.TestCases;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
	UtilClass utility=new UtilClass();
	@BeforeClass
	void setInit() {
		homepage=PageFactory.initElements(driver, Homepage.class);
		shoppage=PageFactory.initElements(driver, Shoppage.class);
		checkoutpage=PageFactory.initElements(driver, CheckOutPage.class);
		setLogger("-----Test Class Name ---"+this.getClass().getName()+"------------");
	}
	
	@BeforeMethod
	public void nameBefore(Method method)
	{
	    setLogger("---Test name: " + method.getName()+"---------------");       
	}
	
	//Filter By price
	@Test(enabled=true, priority=1)
	void TC_1() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
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
//		System.out.println(val1+","+val2);
		shoppage.clickOnslider(val1, val2*(-1));
		setLogger("Price slider got clicked");
		shoppage.clickOnFilter();
		setLogger("Filter button got clicked");
		
		ArrayList<String> priceRange=shoppage.getSpanValue();
		int min=Integer.parseInt(priceRange.get(0));
		int max=Integer.parseInt(priceRange.get(1));
		
		Assert.assertTrue(shoppage.isFiltered(min, max));
		
	}
	
	//Product Categories functionality
	@Test(enabled=true, priority=2)
	void TC_2() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		int clickableElemIndx=1;
		shoppage.clickOnCtgLnk(clickableElemIndx);
		String lnkCaption=shoppage.prdctCtgLst.get(clickableElemIndx).findElement(By.xpath("./a")).getText();
		String lnkCnt=shoppage.prdctCtgLst.get(clickableElemIndx).findElement(By.xpath("./span")).getText();
		setLogger(lnkCaption+" "+lnkCnt+ "got clicked");
		lnkCnt=lnkCnt.replace("(", "");
		lnkCnt=lnkCnt.replace(")", "");
		
		Assert.assertTrue(shoppage.isCategorized(lnkCaption) && shoppage.ctgPrdctLst.size()==Integer.parseInt(lnkCnt));
	}
	
	//Default sorting---Sort by Popularity item
	@Test(enabled=true, priority=3)
	void TC_3() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		shoppage.selSortingbyName("Sort by popularity");
		setLogger("Sort by popularity got selected");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Default sorting----Sort by Average ratings
	@Test(enabled=true, priority=4)
	void TC_4() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		shoppage.selSortingbyName("Sort by average rating");
		setLogger("Sort by average rating got selected");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Default sorting----Sort by Newness ratings
	@Test(enabled=true, priority=5)
	void TC_5() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		shoppage.selSortingbyName("Sort by newness");
		setLogger("Sort by newness got selected");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Default sorting----Sort by Low to High Item
	@Test(enabled=true, priority=6)
	void TC_6() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		shoppage.selSortingbyName("Sort by price: low to high");
		setLogger("Sort by price: low to high got selected");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Default sorting----Sort by High to Low Item
	@Test(enabled=true, priority=7)
	void TC_7() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		shoppage.selSortingbyName("Sort by price: high to low");
		setLogger("Sort by price: high to low");
		Assert.assertTrue(shoppage.getSortedElemCnt()>0);
	}
	
	//Read More option indicates the Out Of Stock
	@Test(enabled=true, priority=8)
	void TC_8() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		for(WebElement elem: shoppage.sortedElemLst) {
			WebElement readMoreElem=elem.findElement(By.xpath("./a[2]"));
			if(readMoreElem.getText().equals("Read more")) {
				setLogger(elem.findElement(By.xpath("./a[1]")).getText()+" got selected");
				readMoreElem.click();
				break;
			}
		}		
		Assert.assertTrue(shoppage.isOOSIndc());
	}
	
	//Sale functionality
	@Test(enabled=true, priority=9)
	void TC_9() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		for(WebElement elem: shoppage.sortedElemLst) {
			if(elem.findElement(By.xpath("./a/*[1]")).getText().equalsIgnoreCase("SALE!")) {
				setLogger(elem.findElement(By.xpath("./a/h3")).getText()+" got selected");
				elem.findElement(By.xpath("./a")).click();
				break;
			}
		}
		String tagname=driver.findElement(By.xpath("//p[@class=\"price\"]/*[1]")).getTagName();
		Assert.assertEquals(tagname, "del");
		
	}

	//Add to basket-view basket functionality
	@Test(enabled=true, priority=10)
	void TC_10() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		for(WebElement elem: shoppage.sortedElemLst) {
			WebElement add2CardElem=elem.findElement(By.xpath("./a[2]"));
			if(add2CardElem.getText().equalsIgnoreCase("Add to basket")) {
				setLogger(elem.findElement(By.xpath("./a[1]/h3")).getText()+" got selected");
				add2CardElem.click();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				elem.findElement(By.xpath("./a[3]")).click();
				setLogger("viewing cart conditions.....");
				break;
			}
		}
//		 User can view that Book in the Menu item with price .
		
		Assert.assertTrue(shoppage.isItemInCart());
		
		shoppage.clickOnItemInCart();
		setLogger("checking item details.....");
		
//		The total always < subtotal because taxes are added in the subtotal
		Assert.assertTrue(shoppage.isTotalGreatThanSub());
		
		shoppage.clickOnPrChkOutBtn();
		setLogger("clicked on PROCEED TO CHECKOUT");
		
//		User can view Billing Details,Order Details,Additional details and Payment gateway details.
//		UtilClass.wait.until(ExpectedConditions.visibilityOf(checkoutpage.fName));
//		utility.wait.until(ExpectedConditions.visibilityOf(checkoutpage.fName));
		utility.setExplicitWait().until(ExpectedConditions.visibilityOf(checkoutpage.fName));
		Assert.assertTrue(shoppage.isDetailsThere());
		
		String[] billInfo= {"tester01","tester","tester01@gmail.com","2121212121","United States", "2134 Fox Drive", "GrandLake",
				"Kansas", "66087"};
		checkoutpage.fillAndSendBill(billInfo);
		setLogger("user info got filled");
		
		checkoutpage.clickOnPlaceOrder();
		setLogger("clicked on PLACE ORDER");
		
		//Assert if complete payment
//		UtilClass.wait.until(ExpectedConditions.visibilityOf(checkoutpage.completePayment));
//		utility.wait.until(ExpectedConditions.visibilityOf(checkoutpage.completePayment));
		utility.setExplicitWait().until(ExpectedConditions.visibilityOf(checkoutpage.completePayment));
		Assert.assertTrue(checkoutpage.isPaymentDone());
	}
		

	//Add To Basket button -View Basket through Item link
	@Test(enabled=true, priority=11)
	void TC_11() {
		homepage.clickOnShopmenu();
		setLogger("clicked on shopmenu");
		for(WebElement elem: shoppage.sortedElemLst) {
			WebElement add2CartElem=elem.findElement(By.xpath("./a[2]"));
			if(add2CartElem.getText().equalsIgnoreCase("Add to basket")) {
				setLogger(elem.findElement(By.xpath("./a[1]/h3")).getText()+" got selected");
				add2CartElem.click();
				setLogger("viewing cart conditions.....");
				break;
			}
		}
//		 User can view that Book in the Menu item with price .
//		UtilClass.wait.until(ExpectedConditions.attributeContains(By.xpath("//ul[@id=\"main-nav\"]/li[6]/a"), "title", "View your shopping cart"));
//		utility.wait.until(ExpectedConditions.attributeContains(By.xpath("//ul[@id=\"main-nav\"]/li[6]/a"), "title", "View your shopping cart"));
		utility.setExplicitWait().until(ExpectedConditions.attributeContains(By.xpath("//ul[@id=\"main-nav\"]/li[6]/a"), "title", "View your shopping cart"));
		Assert.assertTrue(shoppage.isItemInCart());
		
		shoppage.clickOnItemInCart();
		setLogger("checking item details.....");
		
//		The total always < subtotal because taxes are added in the subtotal
		Assert.assertTrue(shoppage.isTotalGreatThanSub());
		
		shoppage.clickOnPrChkOutBtn();
		setLogger("clicked on PROCEED TO CHECKOUT");
		
//		User can view Billing Details,Order Details,Additional details and Payment gateway details.
//		UtilClass.wait.until(ExpectedConditions.visibilityOf(checkoutpage.fName));
//		utility.wait.until(ExpectedConditions.visibilityOf(checkoutpage.fName));
		utility.setExplicitWait().until(ExpectedConditions.visibilityOf(checkoutpage.fName));
		Assert.assertTrue(shoppage.isDetailsThere());
		
		String[] billInfo= {"tester01","tester","tester01@gmail.com","2121212121","United States", "2134 Fox Drive", "GrandLake",
				"Kansas", "66087"};
		checkoutpage.fillAndSendBill(billInfo);
		setLogger("user info got filled");
		
		checkoutpage.clickOnPlaceOrder();
		setLogger("clicked on PLACE ORDER");
		
		//Assert if complete payment
//		UtilClass.wait.until(ExpectedConditions.visibilityOf(checkoutpage.completePayment));
//		utility.wait.until(ExpectedConditions.visibilityOf(checkoutpage.completePayment));
		utility.setExplicitWait().until(ExpectedConditions.visibilityOf(checkoutpage.completePayment));
		Assert.assertTrue(checkoutpage.isPaymentDone());
	}
}

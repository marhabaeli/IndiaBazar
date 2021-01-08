package com.test.Pages;

import java.awt.ItemSelectable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.xpath.XPath;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.test.Base.TestBase;

public class Shoppage extends TestBase {

//	@FindBy(className="ui-slider-range ui-widget-header ui-corner-all")
	@FindBy(xpath="//div[@class=\"price_slider_wrapper\"]/div/span")
	List<WebElement> sliderLst;
	
	@FindBy(xpath="//button[contains(text(),\"Filter\")]")
	WebElement filterBtn;
	
	@FindBy(xpath="//div[@class=\"price_label\"]/span")
	List<WebElement> spanLst;
	
	@FindBy(xpath="//ul[@class=\"products masonry-done\"]/li")
	List<WebElement> filterRslt;
	
	
	//The Product Categories 
	@FindBy(xpath="//ul[@class=\"product-categories\"]/li")
	public List<WebElement> prdctCtgLst;
	
	//The categorized products
	@FindBy(xpath="//ul[@class=\"products masonry-done\"]/li")
	public List<WebElement> ctgPrdctLst;
	
	//The Default sorting select elem
	@FindBy(className="orderby")
	WebElement SortTypeElem;
	
	//The sorted Elem list
	@FindBy(xpath="//ul[@class=\"products masonry-done\"]/li")
	public List<WebElement> sortedElemLst;
	
	//The outof indicator
	@FindBy(className="stock out-of-stock")
	WebElement oosIndct;
	
	//View Basket Link
	
	//Item in menu
	@FindBy(xpath="//li[@id=\"wpmenucartli\"]/a/span")
	public List<WebElement> itemsInBasket;
	
	//Basket Totals rows
	@FindBy(xpath="//div[@class=\"cart_totals \"]//span[@class=\"woocommerce-Price-amount amount\"]")
	List<WebElement> basketTotals;
	
	//Processed Checkout button
	@FindBy(xpath="//a[contains(text(),\"Proceed to Checkout\")]")
	WebElement prChkoutBtn;
	
	//Billing Details,Order Details,Additional details
	@FindBy(tagName="h3")
	List<WebElement> BillOrderAddtDetails;
	//Payment Details
	@FindBy(xpath="//div[@id=\"payment\"]//li")
	List<WebElement> pymDetails;
	
	//Actions
	public void clickOnslider(int min, int max) {
//		slider.click();
		Actions slideAction=new Actions(driver);
		slideAction.dragAndDropBy(sliderLst.get(0), min, 0).build().perform();
		slideAction.dragAndDropBy(sliderLst.get(1), max, 0).build().perform();
	}
	
	public void clickOnFilter() {
		filterBtn.click();
	}
	
	public ArrayList<String> getSpanValue() {
		ArrayList<String> spanVal= new ArrayList<String>();
		int i=0;
		for (WebElement elem : spanLst) {
			spanVal.add(elem.getText().substring(1));
		}
		return spanVal;
	}
	
	public boolean isFiltered(int min, int max) {
		boolean flag=true;
		for (WebElement elem : filterRslt) {
			float curValue=Float.parseFloat(elem.findElement(By.xpath("./a[1]/span/span")).getText().substring(1));
			if(curValue<min || curValue>max) {
				flag=false;
				break;
			}		
		}
		return flag;
	}

	public void clickOnCtgLnk(int i) {
		System.out.println(prdctCtgLst.get(i).getText());
		prdctCtgLst.get(i).findElement(By.xpath("./a")).click();
	}
	
	public boolean isCategorized(String type) {
		boolean flag=true;
		for(WebElement elem: ctgPrdctLst) {
			if(! elem.findElement(By.xpath("./a/h3")).getText().contains(type)) {
				flag=false;
				break;
			}
		}
		return flag;
	}
	
	public void selSortingbyName(String sortType) {
		Select selElems=new Select(SortTypeElem);
		selElems.selectByVisibleText("Sort by popularity");
	}

	public int getSortedElemCnt() {
		return sortedElemLst.size();
	}

	public boolean isOOSIndc() {
		return oosIndct.isDisplayed();
	}

	public boolean isItemInCart() {
		String item=itemsInBasket.get(0).getText().replace(" Item", "");
		item=item.replace("s", "");
		if(Integer.parseInt(item)>0	&& Double.parseDouble(itemsInBasket.get(1).getText().substring(1))>0.0)
			return true;
		else 
			return false;
	}
	
	public void clickOnItemInCart() {
		itemsInBasket.get(0).click();
	}
	
	public boolean isTotalGreatThanSub() {
		int res= basketTotals.get(0).getText().compareTo(basketTotals.get(1).getText());
		if(res>=0)
			return true;
		else {
			return false;
		}
	}
	
	public void clickOnPrChkOutBtn() {
		prChkoutBtn.click();
	}
	
	public boolean isDetailsThere() {
		if(BillOrderAddtDetails.get(0).getText().equals("Billing Details") 
				&& BillOrderAddtDetails.get(1).getText().equals("Additional Information")
				&& BillOrderAddtDetails.get(2).getText().equals("Your order")
				&& pymDetails.size()==4) {
			return true;
		}
		else 
			return false;
	}
	
}

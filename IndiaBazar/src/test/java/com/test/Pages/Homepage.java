package com.test.Pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.test.Base.TestBase;

public class Homepage extends TestBase {
	
	//Elements

	@FindBy(xpath = "//div[@data-slide-duration=\"0\"]")
	List<WebElement> slidersElements;
	
	@FindBy(xpath = "//div[contains(@class, \"col3-1\")]/div[contains(@id,\"text-22-sub_row_1-0\")]")
	List<WebElement> arrivalsElements;	
	
	@FindBy(xpath="//li[@id=\"menu-item-40\"]")
	WebElement shopMenueElement;
	
	
	//Actions
	public int getSliderCnt() {
//		System.out.println(slidersElements.size());
		return slidersElements.size();
	}
	
	public int getArrivalsCnt() {
		return arrivalsElements.size();
	}
	
	public void clickOnShopmenu() {
		shopMenueElement.click();
	}
	
}

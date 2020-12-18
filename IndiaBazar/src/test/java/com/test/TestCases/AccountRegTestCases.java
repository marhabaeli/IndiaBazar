package com.test.TestCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.Base.TestBase;
import com.test.Pages.AccountPage;
import com.test.Pages.Homepage;
import com.test.Utility.UtilClass;

public class AccountRegTestCases extends TestBase {

	Homepage homepage;
	AccountPage accntPage;
	int cnt_reg=0, cnt_login=0;
	
	@BeforeClass
	void initSetup() {
		try {
			initial("Chrome");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		homepage=PageFactory.initElements(driver, Homepage.class);
		accntPage=PageFactory.initElements(driver, AccountPage.class);
	}
	
	@AfterClass
	void afterFinish() {
		quitApp();
	}
	
	@BeforeMethod
	void setBeforeMethod() {
		System.out.println("----------------");;
	}
	
	@Test(dataProvider="AccountData")
//	Register with valid email and psw
	void TC_1(String email, String psw) {
		homepage.clickOnMyAccntBtn();
		accntPage.setRegEmail(email);
		accntPage.setRegPsw(psw);
		accntPage.clickOnRegSubmit();
		switch (cnt_reg++) {
		case 0:
			UtilClass.setExplicitWait(accntPage.logoutBtn);
			Assert.assertTrue(accntPage.logoutBtn.isDisplayed());
			accntPage.clickLogout();
			break;
		case 1:
			Assert.assertFalse(accntPage.logoutBtn.isDisplayed());
			break;
		case 2:
//			Assert.assertTrue(accntPage.isRegisterEnabled());
//			break;
		case 3:
		case 4:
			WebElement errElem=driver.findElement(By.xpath("//div[@id=\"page-36\"]/div/div/ul/li"));
			String errTxt=errElem.getText();
			Assert.assertTrue(errTxt.contains("Error"));
			break;

		default:
			break;
		}
		
//		System.out.println(cnt_reg);
	}
	
	@Test(dataProvider="AccountData")
	void TC_2(String email, String psw) {
		
		
	}
	
	@DataProvider(name= "AccountData")
	Object[][] getAccntInfo(){
		String infoFilePath=System.getProperty("user.dir")+prop.getProperty("excelPath");
		System.out.println(infoFilePath);
		return UtilClass.getAccntFromExcel(infoFilePath);		
	}
}

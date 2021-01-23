package com.test.TestCases;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
	int cnt_reg=1, cnt_login=1;
	UtilClass utility=new UtilClass();
	@BeforeClass
	void initSetup() {
//		try {
//			initial("Chrome");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		homepage=PageFactory.initElements(driver, Homepage.class);
		accntPage=PageFactory.initElements(driver, AccountPage.class);
		setLogger("-----Test Class Name ---"+this.getClass().getName()+"------------");
	}
	
	@AfterClass
	void afterFinish() {
//		quitApp();
	}
	
	@BeforeMethod
	void setBeforeMethod(Method method) {
		setLogger("---Test name: " + method.getName()+"---------------");  
	}
	
	//testing Register function with different values
	@Test(dataProvider="AccountData")
//	Register with valid email and psw
	void TC_1(String email, String psw) {
//		setLogger("email="+email+", psw="+psw);
		homepage.clickOnMyAccntBtn();
		setLogger("clicked on myaccount button");
		accntPage.setRegEmail(email);
		setLogger("entered email: "+email);
		accntPage.setRegPsw(psw);
		setLogger("entered psw: "+psw);
		accntPage.clickOnRegSubmit();
		setLogger("Register button got clicked");
		switch (cnt_reg++) {
		case 1:
//			UtilClass.wait.until(ExpectedConditions.visibilityOf(accntPage.logoutBtn));
//			utility.wait.until(ExpectedConditions.visibilityOf(accntPage.logoutBtn));
			utility.setExplicitWait().until(ExpectedConditions.visibilityOf(accntPage.logoutBtn));
			Assert.assertTrue(accntPage.logoutBtn.isDisplayed());
			accntPage.clickLogout();
			break;
		case 2:
			Assert.assertFalse(accntPage.logoutBtn.isDisplayed());
			break;
		case 3:
//			Assert.assertTrue(accntPage.isRegisterEnabled());
//			break;
		case 4:
		case 5:
			Assert.assertTrue(accntPage.getErrTxt().contains("Error"));
			break;
		default:
			break;
		}
		
//		System.out.println(cnt_reg);
	}
	
	
	//testing Login functions with different values
	@Test(dataProvider="AccountData")
	void TC_2(String email, String psw) {
//		setLogger("email="+email+", psw="+psw);
		homepage.clickOnMyAccntBtn();
		setLogger("Clicked on myaccount button");
		accntPage.setLoginUsernm(email);
		setLogger("entered email: "+email);
		accntPage.setLoginPsw(psw);
		setLogger("entered psw: "+psw);
		accntPage.clickLoginBtn();
		setLogger("clicked on Login Button");
		
		switch (cnt_login++) {
		case 1:
//			UtilClass.wait.until(ExpectedConditions.visibilityOf(accntPage.logoutBtn));
//			utility.wait.until(ExpectedConditions.visibilityOf(accntPage.logoutBtn));
			utility.setExplicitWait().until(ExpectedConditions.visibilityOf(accntPage.logoutBtn));
			Assert.assertTrue(accntPage.logoutBtn.isDisplayed());
			accntPage.clickLogout();
			break;
		case 2:
			Assert.assertTrue(accntPage.getErrTxt().contains("ERROR"));
			break;
		case 3:
//			Assert.assertTrue(accntPage.isRegisterEnabled());
//			break;
		case 4:
		case 5:
			Assert.assertTrue(accntPage.getErrTxt().contains("Error"));
			break;

		default:
			break;
		}
		
	}
	
	
	//Password should be masked
	@Test
	void TC_3() {
		homepage.clickOnMyAccntBtn();
		String psw="1234";
		setLogger("passed password: "+psw);
		accntPage.setLoginPsw(psw);
//		System.out.println(accntPage.login_psw.getAttribute("value"));
		Assert.assertTrue(accntPage.login_psw.getAttribute("value").equals(psw) && accntPage.login_psw.getAttribute("type").equals("password"));
	}
	
	//Handles case sensitive
	@Test(dataProvider="AccountData")
	void TC_4(String email, String psw) {
		setLogger("email="+email+", psw="+psw);
		homepage.clickOnMyAccntBtn();
		accntPage.setLoginUsernm(email);
		accntPage.setLoginPsw(psw.toUpperCase());
		accntPage.clickLoginBtn();
		
		Assert.assertTrue(accntPage.getErrTxt().contains("ERROR: The password"));
	}
	
	//Handles Authentication
	@Test(dataProvider="AccountData")
	void TC_5(String email, String psw) {
		setLogger("email="+email+", psw="+psw);
		homepage.clickOnMyAccntBtn();
		accntPage.setLoginUsernm(email);
		accntPage.setLoginPsw(psw);
		accntPage.clickLoginBtn();
//		UtilClass.setExplicitWait(accntPage.logoutBtn);
//		UtilClass.wait.until(ExpectedConditions.visibilityOf(accntPage.logoutBtn));
//		utility.wait.until(ExpectedConditions.visibilityOf(accntPage.logoutBtn));
		utility.setExplicitWait().until(ExpectedConditions.visibilityOf(accntPage.logoutBtn));
		accntPage.clickLogout();
		driver.navigate().back();
		Assert.assertTrue(accntPage.isRegisterEnabled());
		
	}
	
	@DataProvider(name= "AccountData")
	Object[][] getAccntInfo(Method m){
		String infoFilePath=System.getProperty("user.dir")+prop.getProperty("excelPath");
//		System.out.println(infoFilePath);
		String[][] result=UtilClass.getAccntFromExcel(infoFilePath);
		
		if(m.getName().equals("TC_4") || m.getName().equals("TC_5"))
			return new String[][] {result[0]};
		else
			return result; 		
	}
}

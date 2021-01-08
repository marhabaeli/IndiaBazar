package com.test.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.test.Base.TestBase;

public class AccountPage extends TestBase {
	@FindBy (id = "reg_email")
	WebElement reg_Email;
	
	@FindBy (id="reg_password")
	WebElement reg_psw;
	
	@FindBy(name="register")
	WebElement reg_submit;
	
	@FindBy(xpath="//div[@id=\"page-36\"]/div/div/nav/ul/li[6]/a")
	public WebElement logoutBtn;
	
	@FindBy(id="username")
	WebElement login_usrnm;
	
	@FindBy(id="password")
	public WebElement login_psw;
	
	@FindBy(name="login")
	WebElement loginBtn;
	
	@FindBy(xpath="//div[@id=\"page-36\"]/div/div/ul/li")
	WebElement errElem;
	
	
//	actions
	public void setRegEmail(String email) {
		reg_Email.sendKeys(email);
		}
	
	public void setRegPsw(String psw) {
		reg_psw.sendKeys(psw);
	}
	
	public void clickOnRegSubmit() {
		reg_submit.click();
	}
	
	public void clickLogout() {
		logoutBtn.click();
	}
	
	public boolean isRegisterEnabled() {
		return reg_submit.isEnabled();
	}
	
	public void setLoginUsernm(String usrnm) {
		login_usrnm.sendKeys(usrnm);
	}
	
	public void setLoginPsw(String psw) {
		login_psw.sendKeys(psw);
	}
	
	public void clickLoginBtn() {
		loginBtn.click();
		
	}
	
	public String getErrTxt() {
		return errElem.getText();
	}
	
}

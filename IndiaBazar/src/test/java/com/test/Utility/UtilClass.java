package com.test.Utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.test.Base.TestBase;

public class UtilClass extends TestBase{
	
	public void takeScreenShot(String outFileName) {
		TakesScreenshot scrShot=(TakesScreenshot)driver;
		File scrFile=scrShot.getScreenshotAs(OutputType.FILE);
		File dFile=new File(System.getProperty("user.dir")+"\\src\\test\\java\\com\\test\\ScreenShot\\"+outFileName+".png");
		try {
			FileUtils.copyFile(scrFile,dFile );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
 
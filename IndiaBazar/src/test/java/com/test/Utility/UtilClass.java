package com.test.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.Base.TestBase;

public class UtilClass extends TestBase{
//	public static WebDriverWait explicitlyWait;
	static final int EXPLICIT_WAIT=10;
	static public WebDriverWait wait=new WebDriverWait(driver, EXPLICIT_WAIT);
	
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
	
	public static String[][] getAccntFromExcel(String fpath) {
		String[][] reStrings= null;
		try {
			FileInputStream fin=new FileInputStream(fpath);
			XSSFWorkbook xwbook=new XSSFWorkbook(fin);
			XSSFSheet xSheet=xwbook.getSheetAt(0);
			int rowCnt=xSheet.getPhysicalNumberOfRows();
			reStrings=new String[rowCnt][2];
			
			for(int i=0;i<rowCnt-1;i++) {
				Row row=xSheet.getRow(i+1);
				if(row.getCell(0) !=null)
					reStrings[i][0]=row.getCell(0).getStringCellValue();
				else
					reStrings[i][0]="";
				
				if(row.getCell(1) !=null)
					reStrings[i][1]=row.getCell(1).getStringCellValue();
				else 
					reStrings[i][1]="";
			}
			
			reStrings[rowCnt-1][0]="";
			reStrings[rowCnt-1][1]="";
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reStrings;
	}
	
//	public static void setExplicitWait(WebElement wElement) {
//		WebDriverWait wait=new WebDriverWait(driver, EXPLICIT_WAIT);
//		wait.until(ExpectedConditions.visibilityOf(wElement));
//	}
	
}
 
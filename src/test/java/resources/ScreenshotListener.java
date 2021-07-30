package resources;

import java.io.*;
import java.util.*;
import java.text.*;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.*;

import org.testng.*;

import helperClasses.ActionsHelper;


//Listener class to capture screenshots after test failure and add to report
public class ScreenshotListener extends TestListenerAdapter {
	WebDriver driver =null;

	@Override 
	public void onTestFailure(ITestResult result) 
	{ 
		
		System.out.println("Taking screenshot");
		Reporter.log("Taking screenshot");
		ITestContext context = result.getTestContext();

		driver = (WebDriver) context.getAttribute("WebDriver");


		Calendar calendar = Calendar.getInstance();
		//If test result is failed, the screenshot will be saved in current working directory\\ test-output\\failure_screenshots folder
        if(!result.isSuccess()){

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		SimpleDateFormat timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String methodName = result.getName();

		

		try {

			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath()+"/target";
			//Append method name and current timestamp to file name

			File destFile = new File((String) reportDirectory+"/failure_screenshots/"+methodName+"_"+timeStamp.format(calendar.getTime())+".png");
			FileUtils.copyFile(scrFile, destFile);
			Reporter.log("<a href='"+ destFile.getAbsolutePath() + "'> <img src='"+ destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");

			
		} catch (IOException e) {
			Reporter.log("Error in take screenshot");
		}
		}
	}

}

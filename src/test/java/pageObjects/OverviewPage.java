package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import helperClasses.ActionsHelper;


//Page class for Overview page
public class OverviewPage {

	//Locators in overview page 
	protected static WebDriver driver;

	public  ActionsHelper actions;
	
	//Locator for open new account link
	private By openAccountLink = By.xpath("//li/a[.='Open New Account']");

	//Locator for bill pay link
	private By billPayLink = By.linkText("Bill Pay");

	//Constructor
	 public OverviewPage (WebDriver driver) {
		
		 OverviewPage.driver = driver;
        actions = new ActionsHelper(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);

	}
	 
	 //CLick open new account link and return instance of open account page
	 public OpenAccountPage clickOpenNewAccount() {
		
		 actions.click(openAccountLink);
		 
		// actions.waitForPageLoad();
		 
		 return new OpenAccountPage(driver);
		 
	 }

	 //Verify the data in the overview page. Verify user name is displayed
	public boolean verifyData(String name) {
		return actions.getText(By.xpath("//div[@id='leftPanel']/p")).contains(name);
		//return driver.findElement(By.xpath("//div[@id='leftPanel']/p")).getText().contains(name);
		
	}

	//Verify the account table is disabled
	public boolean verifyAccountTableDisplayed() {
		return actions.verifyElementExists(By.id("accountTable"));
	//	return driver.findElements(By.id("accountTable")).size() > 0;

	}

	//Click bill pay link and return instance of bill payment page
	public BillPayPage clickBillPay() {
		
		actions.click(billPayLink);
		return new BillPayPage(driver);
	}
}

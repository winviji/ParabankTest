package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Reporter;

import helperClasses.ActionsHelper;


//Page class for the login page
public class LoginPage {

	//Locators for the login page
	
	protected static WebDriver driver;
	//Locator for username field
	private By userNameText = By.xpath("//input[@name='username']");
	//Locator for password field
	private By pwdText = By.xpath("//input[@name='password']");
	//Locator for login button
	private By loginBtn = By.xpath("//input[@type = 'submit' and @value='Log In']");;
	
	
	public  ActionsHelper actions;

	//Constructor
	 public LoginPage (WebDriver driver) {
		
		 LoginPage.driver = driver;
         actions = new ActionsHelper(driver);
         PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);

	}
	 
	 //Login using given username and pwd. Return instance of overview page
	 public OverviewPage login(String userName, String pwd) {
		 Reporter.log("Login as "+userName);
		 actions.type(userNameText, userName);
		 actions.type(pwdText, pwd);
		 actions.click(loginBtn);
		 return new OverviewPage(driver);
	 }
}

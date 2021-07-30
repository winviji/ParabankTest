package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import helperClasses.ActionsHelper;

//	Page class for open account page
public class OpenAccountPage {
	protected static WebDriver driver;

	//Field locators
	protected By accountTypeSelect = By.id("type");
	protected By fromAccountSelect = By.id("fromAccountId");
	protected By newAccountLink = By.id("newAccountId");
	protected By openAccountButton = By.xpath("//input[@type='submit']");
	
	private By accountNumberLabel = By.id("accountId");
	public  ActionsHelper actions;
	
	public OpenAccountPage (WebDriver driver) {
		
		 OpenAccountPage.driver = driver;
	        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);

		 actions = new ActionsHelper(driver);

	}
	
	//Method to open new savings account
	public void openSavingsAccount(String fromAccount) throws InterruptedException {
		actions.waitForElement(accountTypeSelect);
		

		actions.select(accountTypeSelect,"SAVINGS");
		//new WebDriverWait(driver, 30).until(ExpectedConditions.textToBePresentInElementLocated(fromAccountSelect, fromAccount));
		actions.select(fromAccountSelect, fromAccount);
		actions.click(openAccountButton);
		
		
	}
	
	//Method to verify the account opened
	public String verifyAccountOpened() {
		
		actions.waitForElement(By.xpath("//h1[.='Account Opened!']"));
		return actions.getText(newAccountLink);
		
	}

	//Method to click the new account hyperlink
	public AccountDetailPage clickNewAccount() throws InterruptedException {
		actions.click(newAccountLink);
		actions.waitForElement(accountNumberLabel);
		//Wait for labels to appear
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver drive) {
                return actions.getText(accountNumberLabel).length() != 0;
            }
        });
		return new AccountDetailPage(driver);
	}
	
	//Method to open new checking account
	public void openCheckingAccount(String fromAccount) throws InterruptedException {
		actions.select(accountTypeSelect,"CHECKING");
		actions.select(fromAccountSelect, fromAccount);
		actions.click(openAccountButton);
	}

}

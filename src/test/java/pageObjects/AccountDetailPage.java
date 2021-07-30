package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Reporter;

import helperClasses.ActionsHelper;

//Page class for account detail page

public class AccountDetailPage {

	protected static WebDriver driver;

	public  ActionsHelper actions;

	private By accountNumberLabel = By.id("accountId");

	private By accountTypeLabel = By.id("accountType");
	

	private By accountBalanceLabel = By.id("balance");

	private By availableBalanceLabel = By.id("availableBalance");

	
	public AccountDetailPage (WebDriver driver) {
		
		 AccountDetailPage.driver = driver;
		 actions = new ActionsHelper(driver);
	     PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);

	}
	
	
	//Get account number from Account details page and return as string
	public String getAccountNumber() throws InterruptedException {
		
		actions.waitForElement(accountNumberLabel);
		Thread.sleep(1000);
		String accountNumber = actions.getText(accountNumberLabel);
		return accountNumber;//.equals(savingsAccountNumber.trim());
	}
	//Get account type from Account details page and return as string

	public String getAccountType() throws InterruptedException {
		actions.waitForElement(accountTypeLabel);
		Thread.sleep(1000);

		String type = actions.getText(accountTypeLabel);
		return type;//.equals(accountType.trim());
	}
	
	//Get account number from Account balance page and return as string

	public String getAccountBalance() throws InterruptedException {
		actions.waitForElement(accountBalanceLabel);
		Thread.sleep(1000);

		return actions.getText(accountBalanceLabel);
	}
	
	//Get account number from Account available balance page and return as string

	public String getAvailableBalance() throws InterruptedException {
		actions.waitForElement(availableBalanceLabel);
		Thread.sleep(1000);

		return (actions.getText(availableBalanceLabel));
	}


	//View account details for selected account no

	public void viewAccountDetail(String accountNumber) {
		Reporter.log("Get activity details for account "+accountNumber);
		driver.get("https://parabank.parasoft.com/parabank/activity.htm?id="+accountNumber);
		
		
	}

	//View tramsaction details 

	public boolean verifyTransactionDetail(String transactionDetail) {
		return actions.verifyElementExists(By.xpath("//table[@id='transactionTable']//td/a[.='"+transactionDetail+"']"));
		
		
	}

	//veify account debit transaction 

	public boolean verifyDebitTransactionAmount(String amount) {
		return actions.verifyElementExists(By.xpath("//table[@id='transactionTable']//td[@ng-if=\"transaction.type == 'Debit'\" and .='"+amount+"']"));
				
		
	}

	//verify transaction date 

	public boolean verifyTransactionDate(String date) {
		return actions.verifyElementExists(By.xpath("//table[@id='transactionTable']//td[.='"+date+"']"));
		
	}

	//veify account credit transaction 

	public boolean verifyCreditTransactionAmount(String amount) {
		// TODO Auto-generated method stub
		return actions.verifyElementExists(By.xpath("//table[@id='transactionTable']//td[@ng-if=\"transaction.type == 'Credit'\" and .='"+amount+"']"));

	}
}

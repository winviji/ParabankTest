package pageObjects;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Reporter;

import helperClasses.ActionsHelper;

//Page class for Bill payment page
public class BillPayPage {

	protected static WebDriver driver;

	public  ActionsHelper actions;

	//Field locators 
	private By payeeNameText = By.name("payee.name");
	private By addressText = By.name("payee.address.street");
	private By cityText =  By.name("payee.address.city");
	private By stateText =  By.name("payee.address.state");
	private By zipText =  By.name("payee.address.zipCode");;
	private By phoneText  =  By.name("payee.phoneNumber");;;
	private By accNoText =  By.name("payee.accountNumber");;
	private By verifyAccNoText =  By.name("verifyAccount");;
	private By amountText =  By.name("amount");;
	private By fromAccNoSelect =  By.name("fromAccountId");

	private By errorLabel = By.xpath("//span[@class='error']");

	private By sendPaymentButton = By.xpath("//input[@value='Send Payment']");

	private By paymentCompleteText = By.xpath("//h1[.='Bill Payment Complete']");



	private By payeeNameConfirmationText = By.id("payeeName");

	private By amountConfirmationText = By.id("amount");

	private By fromAccountConfirmationText = By.id("fromAccountId");	


	public  BillPayPage(WebDriver driver) {

		BillPayPage.driver = driver;
		actions = new ActionsHelper(driver);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);

	}

	/*
	 * Method to complete bill payment and verify that no errors appear
	 */
	public boolean verifyBillPay(String payeeName, String address, String city, String state, String zip, String phone, String accNo, String verifyAccNo, String amount, String fromAccount) throws InterruptedException {

		Reporter.log("Payee name: "+payeeName);
		actions.type(payeeNameText, payeeName);
		Reporter.log("Address: "+address);
		actions.type(addressText, address);
		Reporter.log("city: "+city);
		actions.type(cityText, city);
		Reporter.log("state: "+state);
		actions.type(stateText, state);
		Reporter.log("Zipcode: "+zip);
		actions.type(zipText, zip);
		Reporter.log("phone: "+phone);
		actions.type(phoneText, phone);
		Reporter.log("accNo: "+accNo);
		actions.type(accNoText, accNo);
		Reporter.log("verifyAccNo: "+verifyAccNo);
		actions.type(verifyAccNoText, verifyAccNo);
		Reporter.log("amount: "+amount);
		actions.type(amountText, amount);
		Reporter.log("fromAccount: "+fromAccount);

		actions.select(fromAccNoSelect, fromAccount);

		actions.click(sendPaymentButton);


		Thread.sleep(1000);
		return ! actions.verifyElementExists(errorLabel);

	}


	//REturn the error message displayed in bill payment page
	public String getError() {
		return actions.getText(errorLabel);
	}

	//Verify confirmation message is displayed after successful bill payment
	public boolean verifyConfirmationMessage() {
		return actions.verifyElementExists(paymentCompleteText);
	}

	//Verify the payee name matches given name
	public boolean verifyPayeeName(String payeeName) {
		return actions.getText(payeeNameConfirmationText).equals(payeeName.trim());
	}

	//Verify the amount matches given amount

	public boolean verifyAmount(String amount) {
		return actions.getText(amountConfirmationText).equals(amount.trim());
	}

	//Verify the from acocunt matches the given account

	public boolean verifyFromAccount(String fromAccount) {
		return actions.getText(fromAccountConfirmationText).equals(fromAccount.trim());
	}
}


package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import helperClasses.ActionsHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.AccountDetailPage;
import pageObjects.BillPayPage;
import pageObjects.LoginPage;
import pageObjects.OpenAccountPage;
import pageObjects.OverviewPage;


@Listeners(resources.ScreenshotListener.class)				

public class TestCreateNewAccount {

	//Declare and Initialize the global fields 
	public WebDriver driver;
	String url = "https://parabank.parasoft.com/parabank/index.htm";

	String username= "john";
	String pwd = "demo";
	String name = "John Smith";

	String savingsAccountNumber,checkingAccountNumber;

	//Declare method to pass driver object to Listener class
	@BeforeClass
	public void setDriver(ITestContext context){
		context.setAttribute("WebDriver", driver);
	}
	
	//Setup the  driver before test
	@BeforeTest
	public void setup() {
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
		driver.get(url);

	}
	
	//Test method to create new savings account
	@Test(priority=1)
	public void testCreateNewSavingsAccount() throws InterruptedException

	{
		Reporter.log("Test Create new Saving Account");;
		
		LoginPage loginPage = new LoginPage(driver);
		OverviewPage overviewPage = loginPage.login(username, pwd);
		Reporter.log("Verify name is correct after login");
		Assert.assertTrue(overviewPage. verifyData( name));

		Assert.assertTrue(overviewPage.verifyAccountTableDisplayed());
		
		Reporter.log("Click Open new Account");
		OpenAccountPage openAccountPage = overviewPage.clickOpenNewAccount();
		Reporter.log("Open savings Account");;
		openAccountPage.openSavingsAccount("13344"); 
		savingsAccountNumber =		  openAccountPage.verifyAccountOpened();
		Reporter.log("Savings Account no "+savingsAccountNumber); 

		System.out.println("Savings Account no "+savingsAccountNumber); 
		//Verify New account detail page
		Reporter.log("Click on the new accountID Link");
		AccountDetailPage accountDetailPage = openAccountPage.clickNewAccount();
		Reporter.log("Verify accountId is correct in Account details page");
		Assert.assertEquals(accountDetailPage.getAccountNumber(),savingsAccountNumber );
		Reporter.log("Verify account Type is correct in Account details page");

		Assert.assertEquals(accountDetailPage.getAccountType(),"SAVINGS");
		Reporter.log("Verify account Balance is correct in Account details page");

		Assert.assertEquals(accountDetailPage.getAccountBalance(),"$100.00");
		Reporter.log("Verify available balance is correct in Account details page");

		Assert.assertEquals(accountDetailPage.getAvailableBalance(),"$100.00");

		

	}
	//Test method to create new checking account

	@Test(priority=2)
	public void testCreateNewCheckingAccount() throws InterruptedException

	{
		Reporter.log("Test Create new Checking Account");;

		//Create new checking  account 
		LoginPage loginPage = new LoginPage(driver);
		OverviewPage overviewPage = loginPage.login(username, pwd);
		Reporter.log("Verify name is correct after login");

		Assert.assertTrue(overviewPage. verifyData( name));

		Assert.assertTrue(overviewPage.verifyAccountTableDisplayed());
		Reporter.log("Click Open new Account");

		OpenAccountPage openAccountPage = overviewPage.clickOpenNewAccount();
		Reporter.log("Open Checking  Account");;

		openAccountPage.openCheckingAccount("13344"); 
		checkingAccountNumber =		  openAccountPage.verifyAccountOpened();
		Reporter.log("Checkings Account no "+checkingAccountNumber); 

		System.out.println("Checkings Account no "+checkingAccountNumber); 

		Reporter.log("Click on the new accountID Link");

		AccountDetailPage accountDetailPage = openAccountPage.clickNewAccount();
		Reporter.log("Verify accountId is correct in Account details page");

		Assert.assertEquals(accountDetailPage.getAccountNumber(),checkingAccountNumber);
		Reporter.log("Verify account Type is correct in Account details page");

		Assert.assertEquals(accountDetailPage.getAccountType(),"CHECKING");
		Reporter.log("Verify available balance is correct in Account details page");

		Assert.assertEquals(accountDetailPage.getAccountBalance(),"$100.00");
		Reporter.log("Verify available balance is correct in Account details page");

		Assert.assertEquals(accountDetailPage.getAvailableBalance(),"$100.00");

		
	}

	//Test method to Validate mandatory fields and logic in bill payment screen

	@Test(priority=4)
	public void testBillPaymentFieldValidation() throws InterruptedException
	{



		// Bill Pay
		// Transfer $200 from savings account to checking account


		//Verify user cannot proceed to bill pay without mandatory fields


				
		Reporter.log("Test Bill Payment field validation. ");
		LoginPage loginPage = new LoginPage(driver);
		OverviewPage overviewPage = loginPage.login(username, pwd);
		Reporter.log("Verify name is correct after login");

		Assert.assertTrue(overviewPage. verifyData( name));

		Assert.assertTrue(overviewPage.verifyAccountTableDisplayed());
		Reporter.log("Click Bill pay link");
		BillPayPage billPayPage = overviewPage.clickBillPay();
		//Verify user cannot proceed to bill pay without mandatory fields
		Reporter.log("Verify user cannot proceed to bill pay without mandatory fields");
		
		Reporter.log("Check bill payment fails if Payee name is blank. Verify error message");
		Assert.assertFalse(billPayPage.verifyBillPay("", "address", "city", "state", "454353", "7788993343", savingsAccountNumber, savingsAccountNumber, "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "Payee name is required.", "Error message displayed is incorrect: ");

		//Address
		Reporter.log("Check bill payment fails if address is blank. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "", "city", "state", "454353", "7788993343", savingsAccountNumber, savingsAccountNumber, "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "Address is required.", "Error message displayed is incorrect: ");

		//City
		Reporter.log("Check bill payment fails if City is blank. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "", "state", "454353", "7788993343", savingsAccountNumber, savingsAccountNumber, "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "City is required.", "Error message displayed is incorrect: ");

		//state
		Reporter.log("Check bill payment fails if State is blank. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city", "", "454353", "7788993343", savingsAccountNumber, savingsAccountNumber, "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "State is required.", "Error message displayed is incorrect: ");
		//zipcode
		Reporter.log("Check bill payment fails if zipcode is blank. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city", "state", "", "7788993343", savingsAccountNumber, savingsAccountNumber, "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "Zip Code is required.", "Error message displayed is incorrect: ");

		//phone
		Reporter.log("Check bill payment fails if Phone no is blank. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city", "state", "4343", "", savingsAccountNumber, savingsAccountNumber, "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "Phone number is required.", "Error message displayed is incorrect: ");
		//account no
		Reporter.log("Check bill payment fails if Payee account no is blank. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city", "state", "4343", "8898945755", "", savingsAccountNumber, "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "Account number is required.", "Error message displayed is incorrect: ");

		//account no
		Reporter.log("Check bill payment fails if confirm Payee account no is blank. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city", "state", "4343", "8898945755", savingsAccountNumber, "", "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "Account number is required.", "Error message displayed is incorrect: ");

		//amount 
		Reporter.log("Check bill payment fails if Amount is blank. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city", "state", "4343", "8898945755", savingsAccountNumber, savingsAccountNumber, "", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "The amount cannot be empty.", "Error message displayed is incorrect: ");

		//account no
		// Verify user cannot proceed to bill pay if account no and confirm account no mismatch

		Reporter.log("Check bill payment fails if Account nos do not match. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city", "state", "4343", "8898945755", savingsAccountNumber, "23213", "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "The account numbers do not match.", "Error message displayed is incorrect: ");

		//account no validation for text

		Reporter.log("Check bill payment fails if invalid value is entered in account no field. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city", "state", "4343", "8898945755", "abcd", "23213", "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "Please enter a valid number.");



		//verify account no validation for text

		Reporter.log("Check bill payment fails if invalid value is entered in confirm account no field. Verify error message");

		Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city", "state", "4343", "8898945755", savingsAccountNumber, "bbbb", "200", checkingAccountNumber));

		Assert.assertEquals(billPayPage.getError(), "Please enter a valid number.");


		//verify invalid account no validation
		//Verify user cannot proceed to bill pay if account number is wrong

		 Reporter.log("Check bill payment fails if entered account no does not exist. Verify error message");

		  Assert.assertFalse(billPayPage.verifyBillPay("name", "address", "city","state", "4343", "8898945755", savingsAccountNumber+"1234",savingsAccountNumber+"1234", "200",checkingAccountNumber),"Invalid account Number not validated");
		  
		  Assert.assertEquals(billPayPage.getError(), "Account number does not exist");
		 
	}
	//Test Bill Payment of $200 from account :"+checkingAccountNumber +" to " +savingsAccountNumber created above

	@Test(priority=3)
	public void testBillPaymentFeature() throws InterruptedException
	{
		String amount = "200";
		Reporter.log("Test Bill Payment of $200 from account :"+checkingAccountNumber +" to " +savingsAccountNumber );
		LoginPage loginPage = new LoginPage(driver);
		OverviewPage overviewPage = loginPage.login(username, pwd);
		Reporter.log("Verify that the name is correct after login");

		Assert.assertTrue(overviewPage. verifyData( name));

		Assert.assertTrue(overviewPage.verifyAccountTableDisplayed());
		Reporter.log("Click Bill pay link");
		BillPayPage billPayPage = overviewPage.clickBillPay();
		Reporter.log("Enter valid values in all required fields and complete payment");
		Assert.assertTrue(billPayPage.verifyBillPay("name", "address", "city", "state", "4343", "8898945755", savingsAccountNumber,savingsAccountNumber, amount, checkingAccountNumber),"Error in bill payment");
		Reporter.log("Verify the onfirmation message");
		Assert.assertTrue(billPayPage.verifyConfirmationMessage());
		Reporter.log("Verify the Payee name ");
		Assert.assertTrue(billPayPage.verifyPayeeName("name"));
		Reporter.log("Verify the  Amount ");

		Assert.assertTrue(billPayPage.verifyAmount("$200.00"));
		Reporter.log("Verify Source account no is correct ");

		Assert.assertTrue(billPayPage.verifyFromAccount(checkingAccountNumber));
		Reporter.log("Go to account activity page for "+checkingAccountNumber);
		//Go to account activity page
		AccountDetailPage accountDetailPage = new AccountDetailPage(driver);
		accountDetailPage.viewAccountDetail(checkingAccountNumber);
		//driver.get("https://parabank.parasoft.com/parabank/activity.htm?id="+checkingAccountNumber);
		Reporter.log("Verify that the account no is correct" );
		Assert.assertEquals(accountDetailPage.getAccountNumber(),checkingAccountNumber,"Account no is incorrect");

		Reporter.log("Verify that the account type is correct" );

		Assert.assertEquals(accountDetailPage.getAccountType(),"CHECKING");
		Reporter.log("Verify the account balance is correct after Bill payment" );

		Assert.assertEquals(accountDetailPage.getAccountBalance(),"-$100.00");
		Reporter.log("Verify that the available balance is correct after Bill payment" );

		Assert.assertEquals(accountDetailPage.getAvailableBalance(),"$0.00");

		Reporter.log("Verify that the transaction for Bill payment is correct" );

		Assert.assertTrue(accountDetailPage.verifyTransactionDetail("Bill Payment to name"));
		Reporter.log("Verify that the transaction amount is correct" );

		Assert.assertTrue(accountDetailPage.verifyDebitTransactionAmount("$200.00"));
		//	Assert.assertTrue(accountDetailPage.verifyTransactionDate("07-27-2021"));
		Reporter.log("Go to account activity page for "+savingsAccountNumber);

		accountDetailPage.viewAccountDetail(savingsAccountNumber);
		//	driver.get("https://parabank.parasoft.com/parabank/activity.htm?id="+checkingAccountNumber);
		Reporter.log("Verify that the account no is correct" );

		Assert.assertEquals(accountDetailPage.getAccountNumber(),savingsAccountNumber,"Account no incorrect in detail page"+savingsAccountNumber);
		Reporter.log("Verify that the account type is correct" );

		Assert.assertEquals(accountDetailPage.getAccountType(),"SAVINGS","Account type incorrect for account no "+savingsAccountNumber);
		Reporter.log("Verify the account balance is correct after Bill payment" );

		Assert.assertEquals(accountDetailPage.getAccountBalance(),"$300.00","Account balance incorrect after credit in account no "+savingsAccountNumber);
	
		Reporter.log("Verify that the available balance is correct after Bill payment" );

		Assert.assertEquals(accountDetailPage.getAvailableBalance(),"$300.00","Available balance incorrect after credit in account no "+savingsAccountNumber);

		Reporter.log("Verify that the transaction for Bill payment is correct" );

		Assert.assertTrue(accountDetailPage.verifyTransactionDetail("Bill Payment to name"));
		
		Reporter.log("Verify that the credit transaction amount is correct" );

		Assert.assertTrue(accountDetailPage.verifyCreditTransactionAmount("$200.00"));

	}

	//	@AfterMethod(alwaysRun=true)
	//	public void catchExceptions(ITestResult result){
	//	    Calendar calendar = Calendar.getInstance();
	//	    SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
	//	    String methodName = result.getName();
	//	    String reportDirectory= "//Screenshots";
	//	    if(!result.isSuccess()){
	//	       ActionsHelper actions = new ActionsHelper(driver);
	//	       actions.TakeScreenshot(reportDirectory+"//"+methodName+"_"+formater.format(calendar.getTime())+".png");
	//
	//	    }
	//	}

	//Logout after each test
	@AfterMethod(alwaysRun=true)
	public void clearUp() {

		logout();
		

	}
	//Close the browser after completing test
	@AfterTest
	public void quit()
	{
		driver.quit();
	}
	
	//Logout method
	private void logout() {

		
		ActionsHelper actions = new ActionsHelper(driver);
		if(actions.verifyElementExists(By.xpath("//a[.='Log Out']")))
		{
			actions.click(By.xpath("//a[.='Log Out']"));
		}

	}
}
